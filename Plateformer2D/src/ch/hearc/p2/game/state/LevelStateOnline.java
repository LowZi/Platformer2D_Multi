package ch.hearc.p2.game.state;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
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
import ch.hearc.p2.game.network.GameScore;
import ch.hearc.p2.game.network.IndividualScore;
import ch.hearc.p2.game.network.Metadata;
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
    protected boolean drawScore;
    
    protected Image cursor;
    protected Image backgroundDead;
    protected Image rouge;
    protected Image bleu;

    protected ArrayList<PlayerOnline> otherPlayers;
    protected ArrayList<Objective> cases;
    protected LinkedList<String> disconnectedPlayers;
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
    protected ArrayList<Metadata> playersMetadata;

    protected PlatformerClient plClient;
    protected Packet6SendData packetPlayerData;

    protected GameScore gameScore;

    protected String pseudo;

    private Font font;

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
	cursor = new Image("ressources/cursor/viseur.png");
	backgroundDead = new Image("ressources/hud/BackgroundDead.png");
	bleu = new Image("ressources/menu/bleu.png");
	rouge = new Image("ressources/menu/rouge.png");
	packetPlayerData = new Packet6SendData();
	disconnectedPlayers = new LinkedList<String>();
	spawnBlue = new ArrayList<Tile>();
	spawnRed = new ArrayList<Tile>();
	font = new TrueTypeFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.BOLD, 28), false);
	initialisation();
	drawScore = false;

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
	    e.printStackTrace();
	}

	pseudo = plClient.getPseudo();
	// Get players with their team
	playersMetadata = plClient.getPlayers();

	player.setPseudo(pseudo);
	player.setTeam(plClient.getTeam());

	for (Metadata metadata : playersMetadata) {
	    PlayerOnline pl = new PlayerOnline(20 * 70, 16 * 70, metadata.getTeam(), metadata.getPseudo());
	    if (!pseudo.equals(metadata.getPseudo())) {
		level.addCharacter(pl);
		otherPlayers.add(pl);
		pl.setWeapon(0);
	    }
	}

	gameScore = plClient.getGameScore();

    }

    @Override
    public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
	if (plClient.isGameFinished())
	    this.sbg.enterState(1002);

	// Pour voir si le player est pas mort
	if (player.getLife() <= 0) {
	    player.setXVelocity(0);
	    player.setDead(true);
	    player.setMoving(false);
	    respawn();
	}

	// if the player has a new weapon
	if (weapon != player.getWeapon()) {
	    weapon = player.getWeapon();
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
	playerData.indexWeapon = player.getWeaponIndex();
	packetPlayerData.data = playerData;
	packetPlayerData.pseudo = pseudo;

	plClient.sendTCP(packetPlayerData);

	getProjectileData();

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
	    if (obj instanceof Projectile) {
		((Projectile) obj).setShooter(pseudo);
		((Projectile) obj).setTeam(player.getTeam());
	    }
	    level.addLevelObject(obj);
	    shakeAmt = Level.SHAKE_INTENSITY;
	    if (!(obj instanceof MuzzleFlash) && !(obj instanceof Grenade))
		shake();
	}
	weapon.clearToAddList();

	List<ProjectileData> projEnnemieToAdd = plClient.getProjectileData();
	for (ProjectileData obj : projEnnemieToAdd) {
	    if (obj.proj == ProjectileType.NORMAL)
		level.addLevelObject(
			new ProjectilePlayer(obj.x, obj.y, obj.x_velocity, obj.y_velocity, obj.shooter, obj.team));
	    if (obj.proj == ProjectileType.GRENADE)
		level.addLevelObject(new Grenade(obj.x, obj.y, obj.x_velocity, obj.y_velocity, obj.shooter, obj.team));
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

	// Score
	gameScore = plClient.getGameScore();

    }

    private void respawn() {
	player.setLife(6);
	player.setWeapon(0);
	player.getWeapon().resetMunition();
	new java.util.Timer().schedule(new java.util.TimerTask() {
	    @Override
	    public void run() {
		player.setLife(6);
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
		    packet.shooter = pseudo;
		    packet.team = plClient.getTeam();
		    plClient.sendTCP(packet);
		}

		if (obj instanceof Grenade) {
		    packet.x = obj.getX();
		    packet.y = obj.getY();
		    packet.xVelocity = obj.getXVelocity();
		    packet.yVelocity = obj.getYVelocity();
		    packet.type = ProjectileType.GRENADE;
		    packet.shooter = pseudo;
		    packet.team = plClient.getTeam();
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
		p = itOtherPlayers.next();
		if (p.getPseudo().equals(pseudoPlayer)) {
		    p.setX(playerData.x);
		    p.setY(playerData.y);
		    p.setMoving(playerData.isMoving);
		    p.setLife(playerData.life);
		    p.setDead(playerData.isDead);
		    p.setFacing(playerData.facing);
		    p.setWeapon(playerData.indexWeapon);
		    p.getWeapon().setX(playerData.x + 30);
		    p.getWeapon().setY(playerData.y + 30);
		    p.getWeapon().setPlayerFacing(playerData.facing);
		}

	    }
	}

    }

    private void updateCases() throws SlickException {
	for (CaseData cd : plClient.getCases()) {
	    cases.add(new Case(cd));
	}

	plClient.getCases().clear();

	for (Objective o : cases) {
	    level.addLevelObject(o);
	}

	cases.clear();
    }

    @Override
    public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
	g.scale(WindowGame.SCALE_W, WindowGame.SCALE_H);
	g.setFont(font);
	level.render(shakeX, shakeY);
	hud.update();
	hud.render(g, player);

	for (PlayerOnline p : otherPlayers) {
	    g.drawString(p.getPseudo(), p.getX() - level.getXOffset(), p.getY() - level.getYOffset() - 50);
	}

	g.drawString(pseudo, player.getX() - level.getXOffset(), player.getY() - level.getYOffset() - 50);

	if (shakeX != 0 && shakeY != 0)
	    g.translate(-shakeX, -shakeY);

	if (player.isDead())
	    g.drawImage(backgroundDead, 0, 0);
	
	if(drawScore)
	{
		bleu.draw(WindowGame.BASE_WINDOW_WIDTH / 4, 150);
		g.drawString("(" + gameScore.getBlueTeamScore() + ")", WindowGame.BASE_WINDOW_WIDTH / 4 + bleu.getWidth() + 15,
			145);
		rouge.draw((WindowGame.BASE_WINDOW_WIDTH / 4) * 3 - 50, 150);
		g.drawString("(" + gameScore.getRedTeamScore() + ")",
			(WindowGame.BASE_WINDOW_WIDTH / 4) * 3 + rouge.getWidth() - 35, 145);
		
		int y = 275;
		for (String line : getBlueScoreString().split("\n"))
		    g.drawString(line, WindowGame.BASE_WINDOW_WIDTH / 6, y += font.getLineHeight());

		y = 275;
		for (String line : getRedScoreString().split("\n"))
		    g.drawString(line, (WindowGame.BASE_WINDOW_WIDTH / 6)*4, y += font.getLineHeight());
	}
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
	if(key == Input.KEY_TAB)
	    drawScore = false;
	
    }
    
    @Override
    public void keyPressed(int key, char code){
	if(key == Input.KEY_TAB)
	    drawScore = true;
	
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

    private String getBlueScoreString() {
	String toReturn = "Pseudo      Kill      Death\n\n";

	HashMap<String, IndividualScore> bluePlayersScore = gameScore.getBluePlayersScore();

	Iterator<String> blueIt = bluePlayersScore.keySet().iterator();
	while (blueIt.hasNext()) {
	    String pseudo = blueIt.next();
	    IndividualScore score = bluePlayersScore.get(pseudo);
	    toReturn += pseudo + "            " + score.getKill() + "            " + score.getDeath() + "\n";
	}

	return toReturn;
    }

    private String getRedScoreString() {
	String toReturn = "Pseudo      Kill      Death\n\n";

	HashMap<String, IndividualScore> redPlayersScore = gameScore.getRedPlayersScore();

	Iterator<String> redIt = redPlayersScore.keySet().iterator();
	while (redIt.hasNext()) {
	    String pseudo = redIt.next();
	    IndividualScore score = redPlayersScore.get(pseudo);
	    toReturn += pseudo + "            " + score.getKill() + "            " + score.getDeath() + "\n";
	}

	return toReturn;
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