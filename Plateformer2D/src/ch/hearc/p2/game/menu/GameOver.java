package ch.hearc.p2.game.menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ch.hearc.p2.game.WindowGame;

public class GameOver extends BasicGameState {

    public static final int ID = 40;

    private SlickButton quitter;

    private Image background;
    private Image cursor;

    private Music deadMusic;

    private Sound rollover;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			    	*|
    \*------------------------------------------------------------------*/

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
	// Background image
	background = new Image("ressources/background/background.jpg");

	// Music
	deadMusic = new Music("ressources/audio/music/gameover.ogg");

	// Sound
	rollover = new Sound("ressources/audio/sound/rollover.ogg");

	// Cursor image
	cursor = new Image("ressources/cursor/hand_cursor.png");

	// Color for button when mous is over
	Color color = new Color(255, 157, 67, 180);

	// Button "Quitter"
	Image quitterImage = new Image("ressources/menu/quitter.png");

	quitter = new SlickButton(container, quitterImage,
		WindowGame.BASE_WINDOW_WIDTH / 2 - quitterImage.getWidth() / 2, 500, quitterImage.getWidth(),
		quitterImage.getHeight(), new ComponentListener() {

		    @Override
		    public void componentActivated(AbstractComponent arg0) {
			game.enterState(0);
		    }
		});

	quitter.setMouseOverColor(color);
	quitter.setMouseDownSound(rollover);
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		 	    	*|
    \*------------------------------------------------------------------*/

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
	// Change the image of the cursor when we enter in the state
	container.setMouseCursor(cursor, 0, 0);

	if (!deadMusic.playing())
	    deadMusic.loop(1, 0.4f);

    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
	g.scale(WindowGame.SCALE_W, WindowGame.SCALE_H);

	// Render background
	background.draw(0, 0, WindowGame.BASE_WINDOW_WIDTH, WindowGame.BASE_WINDOW_HEIGHT);

	// Render the button
	quitter.render(container, g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
	// empty
    }

    /**
     * The ID is used for identify the state. This will be used when a state has
     * to come bakc to the previous state.
     */
    @Override
    public int getID() {
	return ID;
    }
}