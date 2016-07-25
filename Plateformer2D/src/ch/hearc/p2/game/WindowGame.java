package ch.hearc.p2.game;

import java.util.prefs.Preferences;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import ch.hearc.p2.game.menu.DeadMenuInMulti;
import ch.hearc.p2.game.menu.GameOver;
import ch.hearc.p2.game.menu.LevelScreenGameState;
import ch.hearc.p2.game.menu.LobbyState;
import ch.hearc.p2.game.menu.MainScreenGameState;
import ch.hearc.p2.game.menu.OptionGameState;
import ch.hearc.p2.game.menu.PauseGameState;
import ch.hearc.p2.game.menu.ServerChoiceState;
import ch.hearc.p2.game.state.Level1;
import ch.hearc.p2.game.state.Level1Online;
import ch.hearc.p2.game.state.Level2;
import ch.hearc.p2.game.state.Level3;
import ch.hearc.p2.game.state.Level4;
import ch.hearc.p2.game.state.Level5;
import ch.hearc.p2.game.state.Level6;

public class WindowGame extends StateBasedGame {
    public static final Preferences PREFERENCES = Preferences.userNodeForPackage(WindowGame.class);

    public static final int TILE_SIZE = 70;

    public static final int BASE_WINDOW_WIDTH = 1920;
    public static final int BASE_WINDOW_HEIGHT = 1080;

    public static final float CAMERA_TILES_W = BASE_WINDOW_WIDTH / (float) TILE_SIZE;
    public static final float CAMERA_TILES_H = BASE_WINDOW_HEIGHT / (float) TILE_SIZE;

    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;
    public static boolean FULLSCREEN;

    public static float SCALE_W;
    public static float SCALE_H;

    public static final String GAME_NAME = "Platformer 2D";

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/

    public WindowGame() {
	super(GAME_NAME);
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		    		*|
    \*------------------------------------------------------------------*/

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {

	// create a level state, this state will do the whole logic and
	// rendering for individual levels
	addState(new MainScreenGameState());
	addState(new Level1("lvl1"));
	addState(new Level2("lvl2"));
	addState(new Level3("lvl3"));
	addState(new Level4("lvl4"));
	addState(new Level5("lvl5"));
	addState(new Level6("lvl6"));
	addState(new PauseGameState());
	addState(new GameOver());
	addState(new LevelScreenGameState());
	addState(new OptionGameState());
	
	//Online
	addState(new ServerChoiceState());
	addState(new LobbyState());
	addState(new Level1Online("lvl1Online"));
	addState(new DeadMenuInMulti());
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Static		    		*|
    \*------------------------------------------------------------------*/

    public static void initStaticValues() {
	WINDOW_WIDTH = PREFERENCES.getInt("width", 800);
	WINDOW_HEIGHT = PREFERENCES.getInt("height", 600);
	SCALE_W = 1 / ((CAMERA_TILES_W * TILE_SIZE) / WINDOW_WIDTH);
	SCALE_H = 1 / ((CAMERA_TILES_H * TILE_SIZE) / WINDOW_HEIGHT);
	FULLSCREEN = PREFERENCES.getBoolean("fullscreen", false);
    }

    public static void main(String[] args) throws SlickException {
	initStaticValues();
	AppGameContainer app = new AppGameContainer(new WindowGame());

	// set the size of the display to the width and height and fullscreen or
	// not
	app.setDisplayMode(WindowGame.WINDOW_WIDTH, WindowGame.WINDOW_HEIGHT, WindowGame.FULLSCREEN);
	// app.setShowFPS(true);
	// this will attempt to create a framerate of approximately 60 frames
	// per second
	app.setTargetFrameRate(150);
	app.start();
    }

}