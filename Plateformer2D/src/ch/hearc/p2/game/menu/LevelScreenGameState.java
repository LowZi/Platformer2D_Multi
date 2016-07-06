package ch.hearc.p2.game.menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ch.hearc.p2.game.WindowGame;

public class LevelScreenGameState extends BasicGameState {

    public static final int ID = 11;

    private SlickButton niveau1;
    private SlickButton niveau2;
    private SlickButton niveau3;
    private SlickButton niveau4;
    private SlickButton niveau5;
    private SlickButton niveau6;
    private SlickButton retour;

    private Image background;
    private Image cursor;

    private Sound rollover;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			    	*|
    \*------------------------------------------------------------------*/

    public LevelScreenGameState() {
	super();
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		 	    	*|
    \*------------------------------------------------------------------*/

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
	// Background image
	background = new Image("ressources/background/background.jpg");

	// Cursor image
	cursor = new Image("ressources/cursor/hand_cursor.png");

	// Sound
	rollover = new Sound("ressources/audio/sound/rollover.ogg");

	// Color for button when mous is over
	Color color = new Color(255, 157, 67, 180);

	// Button level "1"
	Image niveau1Image = new Image("ressources/menu/niveaux/niveau1.jpg");

	niveau1 = new SlickButton(container, niveau1Image, 450, 300, niveau1Image.getWidth(), niveau1Image.getHeight(),
		new ComponentListener() {

		    @Override
		    public void componentActivated(AbstractComponent arg0) {
			game.enterState(101);
		    }
		});

	niveau1.setMouseOverColor(color);
	niveau1.setMouseDownSound(rollover);

	// Button level "2"
	Image niveau2Image = new Image("ressources/menu/niveaux/niveau2.jpg");

	niveau2 = new SlickButton(container, niveau2Image, 650, 300, niveau2Image.getWidth(), niveau2Image.getHeight(),
		new ComponentListener() {

		    @Override
		    public void componentActivated(AbstractComponent arg0) {
			game.enterState(102);
		    }
		});

	niveau2.setMouseOverColor(color);
	niveau2.setMouseDownSound(rollover);

	// Button level "3"
	Image niveau3Image = new Image("ressources/menu/niveaux/niveau3.jpg");

	niveau3 = new SlickButton(container, niveau3Image, 850, 300, niveau3Image.getWidth(), niveau3Image.getHeight(),
		new ComponentListener() {

		    @Override
		    public void componentActivated(AbstractComponent arg0) {
			game.enterState(103);
		    }
		});

	niveau3.setMouseOverColor(color);
	niveau3.setMouseDownSound(rollover);

	// Button level "4"
	Image niveau4Image = new Image("ressources/menu/niveaux/niveau4.jpg");

	niveau4 = new SlickButton(container, niveau4Image, 1050, 300, niveau4Image.getWidth(), niveau4Image.getHeight(),
		new ComponentListener() {

		    @Override
		    public void componentActivated(AbstractComponent arg0) {
			game.enterState(104);
		    }
		});

	niveau4.setMouseOverColor(color);
	niveau4.setMouseDownSound(rollover);

	// Button level "5"
	Image niveau5Image = new Image("ressources/menu/niveaux/niveau5.jpg");

	niveau5 = new SlickButton(container, niveau5Image, 1250, 300, niveau5Image.getWidth(), niveau5Image.getHeight(),
		new ComponentListener() {

		    @Override
		    public void componentActivated(AbstractComponent arg0) {
			game.enterState(105);
		    }
		});

	niveau5.setMouseOverColor(color);
	niveau5.setMouseDownSound(rollover);

	// Button level "6"
	Image niveau6Image = new Image("ressources/menu/niveaux/niveau6.jpg");

	niveau6 = new SlickButton(container, niveau6Image, 1450, 300, niveau6Image.getWidth(), niveau6Image.getHeight(),
		new ComponentListener() {

		    @Override
		    public void componentActivated(AbstractComponent arg0) {
			game.enterState(106);
		    }
		});

	niveau6.setMouseOverColor(color);
	niveau6.setMouseDownSound(rollover);

	// Button "Retour"
	Image retourImage = new Image("ressources/menu/retour.jpg");

	retour = new SlickButton(container, retourImage, WindowGame.BASE_WINDOW_WIDTH / 2 - retourImage.getWidth() / 2,
		550, retourImage.getWidth(), retourImage.getHeight(), new ComponentListener() {

		    @Override
		    public void componentActivated(AbstractComponent arg0) {
			game.enterState(0);
		    }
		});

	retour.setMouseOverColor(color);
	retour.setMouseDownSound(rollover);
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
	container.setMouseCursor(cursor, 0, 0);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
	// empty
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
	// Scale the render for the selected resolution
	g.scale(WindowGame.SCALE_W, WindowGame.SCALE_H);

	// Render background
	background.draw(0, 0, WindowGame.BASE_WINDOW_WIDTH, WindowGame.BASE_WINDOW_HEIGHT);

	// Render the buttons
	niveau1.render(container, g);
	niveau2.render(container, g);
	niveau3.render(container, g);
	niveau4.render(container, g);
	niveau5.render(container, g);
	niveau6.render(container, g);

	retour.render(container, g);
    }

    /*------------------------------*\
    |*		Get		    *|
    \*------------------------------*/

    /**
     * The ID is used for identify the state. This will be used when a state has
     * to come bakc to the previous state.
     */
    @Override
    public int getID() {
	return ID;
    }

}