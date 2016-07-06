package ch.hearc.p2.game.state;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import ch.hearc.p2.game.WindowGame;
import ch.hearc.p2.game.character.Ennemie;
import ch.hearc.p2.game.character.Player;
import ch.hearc.p2.game.controller.MouseAndKeyBoardPlayerController;
import ch.hearc.p2.game.controller.PlayerController;
import ch.hearc.p2.game.hud.Hud;
import ch.hearc.p2.game.level.Level;
import ch.hearc.p2.game.level.LevelObject;
import ch.hearc.p2.game.level.object.Objective;
import ch.hearc.p2.game.menu.PauseGameState;
import ch.hearc.p2.game.physics.Physics;
import ch.hearc.p2.game.projectile.Grenade;
import ch.hearc.p2.game.weapon.MuzzleFlash;
import ch.hearc.p2.game.weapon.Weapon;

public abstract class LevelState extends BasicGameState {

    protected float shakeAmt = 0f;
    protected float shakeX = 0f;
    protected float shakeY = 0f;

    protected Level level;

    protected String startinglevel;

    protected Player player;
    protected PlayerController playerController;

    protected Physics physics;

    protected StateBasedGame sbg;

    protected Hud hud;

    protected Music musiclvl;

    protected boolean isPause;

    protected Image cursor;

    protected ArrayList<Ennemie> ennemies;
    protected ArrayList<Objective> objectives;

    protected Weapon weapon;

    protected long time1;
    protected long time2;

    protected int ID;
    protected int nextLevel;
    protected int shakeTime = Level.SHAKE_DELAY;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/

    public LevelState(String startingLevel) {
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

	cursor = new Image("ressources/cursor/viseur.png");

	initialisation();

    }

    public void initialisation() throws SlickException {
	// Avant instanciation
	ennemies = new ArrayList<Ennemie>();
	objectives = new ArrayList<Objective>();

	hud = new Hud();

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
	level = new Level(startinglevel, player);

	// and we create a controller, for now we use the
	// MouseAndKeyBoardPlayerController
	playerController = new MouseAndKeyBoardPlayerController(player, level);

	physics = new Physics(level);

	for (Ennemie e : ennemies) {
	    level.addCharacter(e);
	}
	for (Objective o : objectives) {
	    level.addLevelObject(o);
	}

	level.addLevelObject(weapon);

	isPause = false;

	hud.init();
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
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
    }

    @Override
    public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {

	// Pour gérer les entrées clavier
	playerController.handleInput(container.getInput(), delta);

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

	time1 = System.currentTimeMillis();

	// Pour gérer les ennemies
	Iterator<Ennemie> it = ennemies.iterator();
	while (it.hasNext()) {

	    Ennemie e = it.next();
	    if (e.getLife() <= 0) {
		{
		    e.setMaximumFallSpeed(0.8f);
		    e.setDead(true);
		    e.setXVelocity(0);
		}
	    }

	    List<LevelObject> toAddList = e.getToAddList();
	    for (LevelObject obj : toAddList) {
		level.addLevelObject(obj);
	    }
	    e.clearToAddList();

	    if (time1 - time2 > 1000) {
		if (!it.hasNext())
		    time2 = System.currentTimeMillis();
		if (!e.isDead()) {
		    e.moveRandom();
		    e.shoot();
		}
	    }
	}

	// Pour voir si le player est pas mort
	if (player.getLife() <= 0) {
	    // initialisation();
	    musiclvl.fade(20, 0, true);
	    sbg.enterState(40, new FadeOutTransition(), new FadeInTransition());
	}

	// Pour voir les objectifs restant
	objectives = level.getObjectives();

	// Pour voir si le niveau est fini
	if (physics.isOver() == true) {
	    // System.out.println("Level finished, loading lvl2");
	    if (player.hasKey()) {
		initialisation();
		sbg.enterState(nextLevel, new FadeOutTransition(), new FadeInTransition());
	    } else {
		physics.setOver(false);
	    }
	}

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

    @Override
    public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
	g.scale(WindowGame.SCALE_W, WindowGame.SCALE_H);

	if (shakeX != 0 && shakeY != 0)
	    level.render(shakeX, shakeY);
	else
	    level.render();

	hud.render(g, player);

	if (shakeX != 0 && shakeY != 0)
	    g.translate(-shakeX, -shakeY);
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