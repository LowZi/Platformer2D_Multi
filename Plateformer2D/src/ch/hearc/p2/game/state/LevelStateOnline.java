package ch.hearc.p2.game.state;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ch.hearc.p2.game.WindowGame;
import ch.hearc.p2.game.character.PlayerOnline;
import ch.hearc.p2.game.controller.MouseAndKeyBoardPlayerControllerOnline;
import ch.hearc.p2.game.controller.PlayerControllerOnline;
import ch.hearc.p2.game.enums.ProjectileType;
import ch.hearc.p2.game.enums.Team;
import ch.hearc.p2.game.hud.HudOnline;
import ch.hearc.p2.game.level.Level;
import ch.hearc.p2.game.level.LevelObject;
import ch.hearc.p2.game.level.LevelOnline;
import ch.hearc.p2.game.level.object.Case;
import ch.hearc.p2.game.level.object.Objective;
import ch.hearc.p2.game.level.tile.Tile;
import ch.hearc.p2.game.menu.PauseGameState;
import ch.hearc.p2.game.network.CaseData;
import ch.hearc.p2.game.network.Packet.Packet6SendData;
import ch.hearc.p2.game.network.Packet.Packet8Projectile;
import ch.hearc.p2.game.network.PlatformerClient;
import ch.hearc.p2.game.network.PlayerData;
import ch.hearc.p2.game.physics.PhysicsOnline;
import ch.hearc.p2.game.projectile.Grenade;
import ch.hearc.p2.game.projectile.Projectile;
import ch.hearc.p2.game.projectile.ProjectileData;
import ch.hearc.p2.game.projectile.ProjectilePlayer;
import ch.hearc.p2.game.weapon.MuzzleFlash;
import ch.hearc.p2.game.weapon.Weapon;

public abstract class LevelStateOnline extends BasicGameState {

    protected float shakeAmt = 0f;
    protected float shakeX = 0f;
    protected float shakeY = 0f;

    protected LevelOnline level;

    protected String startinglevel;

    protected PlayerOnline player;
    protected PlayerControllerOnline playerController;

    protected PhysicsOnline physics;

    protected StateBasedGame sbg;

    protected HudOnline hud;

    protected Music musiclvl;

    protected boolean isPause;

    protected Image cursor;
    protected Image backgroundDead;

    protected ArrayList<PlayerOnline> otherPlayers;
    protected ArrayList<Objective> cases;
    protected LinkedList<String> disconnectedPlayers;
    protected ArrayList<Weapon> playersWeapon;
    protected ArrayList<Tile> spawnBlue;
    protected ArrayList<Tile> spawnRed;

    protected Weapon weapon;

    protected long time1;
    protected long time2;

    protected int ID;
    protected int shakeTime = Level.SHAKE_DELAY;

    protected PlayerData playerData;

    protected HashMap<String, PlayerOnline> playersOnline;
    protected HashMap<String, PlayerData> playerDataOnline;
    protected HashMap<String, String> playersTeam;

    protected PlatformerClient plClient;
    protected Packet6SendData packetPlayerData;

    protected String pseudo;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/

    public LevelStateOnline(String startingLevel) {
	this.startinglevel = startingLevel;
	time1 = System.currentTimeMillis();
	time2 = System.currentTimeMillis();
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		    		*|
    \*------------------------------------------------------------------*/

    @Override
    public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
	this.sbg = sbg;
	playersOnline = new HashMap<String, PlayerOnline>();
	playerData = new PlayerData();
	otherPlayers = new ArrayList<PlayerOnline>();
	playersWeapon = new ArrayList<Weapon>();
	cursor = new Image("ressources/cursor/viseur.png");
	backgroundDead = new Image("ressources/hud/BackgroundDead.png");
	packetPlayerData = new Packet6SendData();
	disconnectedPlayers = new LinkedList<String>();
	spawnBlue = new ArrayList<Tile>();
	spawnRed = new ArrayList<Tile>();
	initialisation();

    }

    public void initialisation() throws SlickException {
	// Avant instanciation

	// otherPlayers = new ArrayList<Character>();
	cases = new ArrayList<Objective>();

	hud = new HudOnline();

	instanciation();
    }

    public void instanciation() throws SlickException {
	// A coder dans les classes enfants
	initialisationSuite();
    }

    public void initialisationSuite() throws SlickException {
	musiclvl.setVolume(0.4f);
	weapon = player.getWeapon();
	// once we initialize our level, we want to load the right level
	level = new LevelOnline(startinglevel, player);

	spawnRed = level.getRedSpawn();
	spawnBlue = level.getBlueSpawn();
	// and we create a controller, for now we use the
	// MouseAndKeyBoardPlayerController
	playerController = new MouseAndKeyBoardPlayerControllerOnline(player, level);

	physics = new PhysicsOnline(level, player);

	level.addLevelObject(weapon);

	isPause = false;

	hud.init();
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
	player.setDead(false);
	container.setMouseCursor(
		cursor.getScaledCopy((int) (cursor.getWidth() * WindowGame.SCALE_W),
			(int) (cursor.getHeight() * WindowGame.SCALE_H)),
		cursor.getWidth() / 2, cursor.getHeight() / 2);

	if (isPause == true) {
	    musiclvl.resume();
	    isPause = false;
	} else {
	    initialisation();
	    musiclvl.loop();
	}

	try {
	    plClient = PlatformerClient.getInstance();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	pseudo = plClient.getPseudo();

	// Get players with their team
	playersTeam = (HashMap<String, String>) plClient.getPlayers();

	Set<String> cles = playersTeam.keySet();
	java.util.Iterator<String> it = cles.iterator();
	while (it.hasNext()) {
	    String pseudoPlayer = it.next();
	    String team = playersTeam.get(pseudoPlayer);

	    if (team.equals("BLUE")) {
		PlayerOnline pl = new PlayerOnline(20 * 70, 16 * 70, Team.BLUE, pseudoPlayer);
		if (!pseudo.equals(pseudoPlayer)) {
		    level.addCharacter(pl);
		    otherPlayers.add(pl);
		    Weapon w = new Weapon(20 * 70, 16 * 70);
		    level.addLevelObject(w);
		    playersWeapon.add(w);
		}

	    } else {
		PlayerOnline pl = new PlayerOnline(20 * 70, 16 * 70, Team.RED, pseudoPlayer);
		if (!pseudo.equals(pseudoPlayer)) {
		    level.addCharacter(pl);
		    otherPlayers.add(pl);
		    Weapon w = new Weapon(20 * 70, 16 * 70);
		    level.addLevelObject(w);
		    playersWeapon.add(w);
		}

	    }
	}
    }

    @Override
    public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {

	// Pour voir si le player est pas mort
	if (player.getLife() <= 0) {
	    player.setDead(true);
	    respawn();
	}

	if (weapon != player.getWeapon()) {
	    level.removeObject(weapon);
	    weapon = player.getWeapon();
	    level.addLevelObject(weapon);
	}

	// Pour gérer les entrées clavier
	if (!player.isDead())
	    playerController.handleInput(container.getInput(), delta);

	playerData.isMoving = player.isMoving();
	playerData.x = (int) player.getX();
	playerData.y = (int) player.getY();
	playerData.life = player.getLife();
	playerData.facing = player.getFacing();
	playerData.isDead = player.isDead();

	getProjectileData();

	packetPlayerData.data = playerData;
	packetPlayerData.pseudo = pseudo;

	plClient.sendTCP(packetPlayerData);

	handleDisconnectedPlayers();

	updatePlayersOnline();

	updateCases();

	// Pour gérer la physique
	physics.handlePhysics(level, delta);

	// Pour que l'arme suive le perso
	weapon.setX(player.getX() + 30);
	weapon.setY(player.getY() + 30);

	// Pour voir si le player a tiré (donc si l'arme doit tirer) et ajouter
	// les projectiles au niveau
	List<LevelObject> toAdd = weapon.getToAddList();
	for (LevelObject obj : toAdd) {
	    level.addLevelObject(obj);
	    shakeAmt = Level.SHAKE_INTENSITY;
	    if (!(obj instanceof MuzzleFlash) && !(obj instanceof Grenade))
		shake();
	}
	weapon.clearToAddList();

	List<ProjectileData> projEnnemieToAdd = plClient.getProjectileData();
	for (ProjectileData obj : projEnnemieToAdd) {
	    if (obj.proj == ProjectileType.NORMAL)
		level.addLevelObject(new ProjectilePlayer(obj.x, obj.y, obj.x_velocity, obj.y_velocity));
	    if (obj.proj == ProjectileType.GRENADE)
		level.addLevelObject(new Grenade(obj.x, obj.y, obj.x_velocity, obj.y_velocity));
	}

	time1 = System.currentTimeMillis();

	// Pour voir les objectifs restant
	// cases = level.getObjectives();

	if (shakeAmt > 0f) {
	    shakeTime -= delta;
	    // new shakeX/Y
	    if (shakeTime <= 0) {
		shake();
	    }
	}
	if (physics.needShake() == true) {
	    shakeAmt = Level.SHAKE_INTENSITY;
	    shake();
	    physics.setShake(false);
	}
    }

    private void respawn() {
	    player.setLife(6);
	    new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {
			    
			    player.setDead(false);
			    Random rand = new Random();

			    ArrayList<Tile> spawns;
			    if (player.getTeam() == Team.RED)
				spawns = spawnRed;

			    else
				spawns = spawnBlue;

			    int randomNum = rand.nextInt((spawns.size()));

			    Tile tile = spawns.get(randomNum);
			    player.setX(tile.getX() * 70);
			    player.setY(tile.getY() * 70);
			}
		    }, 5000);
	
    }

    private void handleDisconnectedPlayers() {
	disconnectedPlayers = plClient.getDisconnectedPlayer();

	Iterator<PlayerOnline> it = otherPlayers.iterator();
	while (it.hasNext()) {
	    PlayerOnline p = it.next();
	    String pseudo = p.getPseudo();
	    if (disconnectedPlayers.contains(pseudo)) {
		it.remove();
		playersOnline.remove(pseudo);
		level.removeCharacter(p);
	    }
	}

    }

    private void getProjectileData() {

	for (LevelObject obj : weapon.getToAddList()) {
	    if (obj instanceof Projectile) {
		// data.push(new ProjectileData(obj.getX(), obj.getY(),
		// obj.getXVelocity(), obj.getYVelocity()));
		// ProjectileData p = new ProjectileData(obj.getX(), obj.getY(),
		// obj.getXVelocity(), obj.getYVelocity());
		Packet8Projectile packet = new Packet8Projectile();
		if (obj instanceof ProjectilePlayer) {
		    packet.x = obj.getX();
		    packet.y = obj.getY();
		    packet.xVelocity = obj.getXVelocity();
		    packet.yVelocity = obj.getYVelocity();
		    packet.type = ProjectileType.NORMAL;
		    plClient.sendTCP(packet);
		}

		if (obj instanceof Grenade) {
		    packet.x = obj.getX();
		    packet.y = obj.getY();
		    packet.xVelocity = obj.getXVelocity();
		    packet.yVelocity = obj.getYVelocity();
		    packet.type = ProjectileType.GRENADE;
		    plClient.sendTCP(packet);
		}

	    }
	}
    }

    private void updatePlayersOnline() throws SlickException {
	playerDataOnline = plClient.getPlayersData();
	// PlayerOnline playerOnline;
	PlayerData playerData;

	// Liste "otherPlayers". Changer les data des others players par rapport
	// à leur nom (getPseudo) avec les
	// data de la map récupérer par le réseau

	// For every playersData we update the playersOnline
	Set<String> cles = playerDataOnline.keySet();
	java.util.Iterator<String> it = cles.iterator();
	while (it.hasNext()) {
	    String pseudoPlayer = it.next();
	    playerData = playerDataOnline.get(pseudoPlayer);

	    Iterator<PlayerOnline> itOtherPlayers = otherPlayers.iterator();

	    PlayerOnline p;
	    while (itOtherPlayers.hasNext()) {
		int i = 0;
		p = itOtherPlayers.next();
		if (p.getPseudo().equals(pseudoPlayer)) {
		    p.setX(playerData.x);
		    p.setY(playerData.y);
		    p.setMoving(playerData.isMoving);
		    p.setLife(playerData.life);
		    p.setDead(playerData.isDead);
		    p.setFacing(playerData.facing);

		    playersWeapon.get(i).setX(p.getX() + 30);
		    playersWeapon.get(i).setY(p.getY() + 30);
		    i++;
		}

	    }
	}

    }

    private void updateCases() throws SlickException {
	for (CaseData cd : plClient.getCases()) {
	    System.out.println("x: " + cd.getX());
	    System.out.println("y: " + cd.getY());
	    cases.add(new Case(cd));
	}

	plClient.getCases().clear();

	for (Objective o : cases) {
	    System.out.println(o);
	    level.addLevelObject(o);
	}

	cases.clear();
    }

    @Override
    public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
	g.scale(WindowGame.SCALE_W, WindowGame.SCALE_H);

	if (shakeX != 0 && shakeY != 0)
	    level.render(shakeX, shakeY);
	else
	    level.render();

	hud.render(g, player);

	int i = 0;
	for (PlayerOnline p : otherPlayers) {
	    if (!p.isDead())
		g.drawString(p.getPseudo(), p.getX() - level.getXOffset(), p.getY() - level.getYOffset() - 50);
	    i++;
	}
	g.drawString(pseudo, player.getX() - level.getXOffset(), player.getY() - level.getYOffset() - 50);

	if (shakeX != 0 && shakeY != 0)
	    g.translate(-shakeX, -shakeY);

	if (player.isDead())
	    g.drawImage(backgroundDead, 0, 0);
    }

    // this method is overriden from basicgamestate and will trigger once you
    // realease any key on your keyboard
    @Override
    public void keyReleased(int key, char code) {
	// if the key is escape, pause the level
	if (key == Input.KEY_ESCAPE) {
	    PauseGameState.setID_Last(ID);
	    isPause = true;
	    sbg.enterState(50);
	}
    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
	musiclvl.pause();
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Private	    		*|
    \*------------------------------------------------------------------*/

    private void shake() {
	shakeX = (float) (Math.random() * shakeAmt);
	shakeY = (float) (Math.random() * shakeAmt);
	if (Level.SHAKE_SNAP) {
	    shakeX = (int) shakeX;
	    shakeY = (int) shakeY;
	}
	shakeTime = Level.SHAKE_DELAY;
	shakeAmt -= Level.SHAKE_DECAY * Level.SHAKE_INTENSITY;
	if (shakeAmt < 0f)
	    shakeAmt = 0f;
    }

    /*-----------------------*\
    |*		Get	     *|
    \*-----------------------*/

    @Override
    public int getID() {
	// this is the id for changing states
	return ID;
    }

}