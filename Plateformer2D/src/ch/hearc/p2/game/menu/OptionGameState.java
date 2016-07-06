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

public class OptionGameState extends BasicGameState {

    public static final int ID = 60;

    private SlickButton res1920x1080;
    private SlickButton res1280x720;
    private SlickButton res1024x768;
    private SlickButton res800x600;
    private SlickButton fullscreen;
    private SlickButton fenetre;
    private SlickButton quitter;
    private SlickButton retour;

    private Image selected;
    private Image background;
    private Image cursor;

    private int y1Selected;
    private int y2Selected;

    private Sound rollover;

    private boolean isResoltionChanged;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			    	*|
    \*------------------------------------------------------------------*/

    public OptionGameState() {
	super();
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		 	    	*|
    \*------------------------------------------------------------------*/

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {

	// Sound
	rollover = new Sound("ressources/audio/sound/rollover.ogg");

	// Background image
	background = new Image("ressources/background/background.jpg");

	// Cursor image
	cursor = new Image("ressources/cursor/hand_cursor.png");

	// Color for button when mous is over
	Color color = new Color(255, 157, 67, 180);

	// Button "1920x1080"
	Image res1920x1080Image = new Image("ressources/menu/1920_1080.png");

	res1920x1080 = new SlickButton(container, res1920x1080Image,
		WindowGame.BASE_WINDOW_WIDTH / 2 - res1920x1080Image.getWidth() / 2, 25, res1920x1080Image.getWidth(),
		res1920x1080Image.getHeight(), new ComponentListener() {

		    @Override
		    public void componentActivated(AbstractComponent arg0) {
			changeResolution(1920, 1080);
			isResoltionChanged = true;
			y1Selected = 25;
		    }
		});

	res1920x1080.setMouseOverColor(color);
	res1920x1080.setMouseDownSound(rollover);

	// Button "1280x720"
	Image res1280x720Image = new Image("ressources/menu/1280_720.png");

	res1280x720 = new SlickButton(container, res1280x720Image,
		WindowGame.BASE_WINDOW_WIDTH / 2 - res1280x720Image.getWidth() / 2, 125, res1280x720Image.getWidth(),
		res1280x720Image.getHeight(), new ComponentListener() {

		    @Override
		    public void componentActivated(AbstractComponent arg0) {
			changeResolution(1280, 720);
			isResoltionChanged = true;
			y1Selected = 125;
		    }
		});

	res1280x720.setMouseOverColor(color);
	res1280x720.setMouseDownSound(rollover);

	// Button "1024x768"
	Image res1024x768Image = new Image("ressources/menu/1024_768.png");

	res1024x768 = new SlickButton(container, res1024x768Image,
		WindowGame.BASE_WINDOW_WIDTH / 2 - res1024x768Image.getWidth() / 2, 225, res1024x768Image.getWidth(),
		res1024x768Image.getHeight(), new ComponentListener() {

		    @Override
		    public void componentActivated(AbstractComponent arg0) {
			changeResolution(1024, 768);
			isResoltionChanged = true;
			y1Selected = 225;
		    }
		});

	res1024x768.setMouseOverColor(color);
	res1024x768.setMouseDownSound(rollover);

	// Button "800x600"
	Image res800x600Image = new Image("ressources/menu/800_600.png");

	res800x600 = new SlickButton(container, res800x600Image,
		WindowGame.BASE_WINDOW_WIDTH / 2 - res800x600Image.getWidth() / 2, 325, res800x600Image.getWidth(),
		res1024x768Image.getHeight(), new ComponentListener() {

		    @Override
		    public void componentActivated(AbstractComponent arg0) {
			changeResolution(800, 600);
			isResoltionChanged = true;
			y1Selected = 325;
		    }
		});

	res800x600.setMouseOverColor(color);
	res800x600.setMouseDownSound(rollover);

	// Button "Fullscreen"
	Image fullscreenImage = new Image("ressources/menu/fullscreen.png");

	fullscreen = new SlickButton(container, fullscreenImage,
		WindowGame.BASE_WINDOW_WIDTH / 2 - fullscreenImage.getWidth() / 2, 425, fullscreenImage.getWidth(),
		fullscreenImage.getHeight(), new ComponentListener() {

		    @Override
		    public void componentActivated(AbstractComponent arg0) {
			changeDisplay(true);
			isResoltionChanged = true;
			y2Selected = 425;
		    }
		});

	fullscreen.setMouseOverColor(color);
	fullscreen.setMouseDownSound(rollover);

	// Button "Fenetre"
	Image fenetreImage = new Image("ressources/menu/fenetre.png");

	fenetre = new SlickButton(container, fenetreImage,
		WindowGame.BASE_WINDOW_WIDTH / 2 - fenetreImage.getWidth() / 2, 525, fenetreImage.getWidth(),
		fenetreImage.getHeight(), new ComponentListener() {

		    @Override
		    public void componentActivated(AbstractComponent arg0) {
			changeDisplay(false);
			isResoltionChanged = true;
			y2Selected = 525;
		    }
		});

	fenetre.setMouseOverColor(color);
	fenetre.setMouseDownSound(rollover);

	// Button "Retour"
	Image retourImage = new Image("ressources/menu/retour.jpg");

	retour = new SlickButton(container, retourImage, WindowGame.BASE_WINDOW_WIDTH / 2 - retourImage.getWidth() / 2,
		625, retourImage.getWidth(), retourImage.getHeight(), new ComponentListener() {

		    @Override
		    public void componentActivated(AbstractComponent arg0) {
			game.enterState(0);
		    }
		});

	retour.setMouseOverColor(color);
	retour.setMouseDownSound(rollover);

	// Button "Quitter"
	Image quitterImage = new Image("ressources/menu/exit.png");

	quitter = new SlickButton(container, quitterImage,
		WindowGame.BASE_WINDOW_WIDTH / 2 - quitterImage.getWidth() / 2, 725, quitterImage.getWidth(),
		quitterImage.getHeight(), new ComponentListener() {

		    @Override
		    public void componentActivated(AbstractComponent arg0) {
			System.exit(0);
		    }
		});

	quitter.setMouseDownSound(rollover);

	// Image for showing the selected values
	selected = new Image("ressources/menu/selected.gif");

	// For know when to display "Quitter" button
	isResoltionChanged = false;
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
	container.setMouseCursor(cursor, 0, 0);

	placeSelectedIcon();
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
	// Scale the render for the selected resolution
	g.scale(WindowGame.SCALE_W, WindowGame.SCALE_H);

	// Render background
	background.draw(0, 0, WindowGame.BASE_WINDOW_WIDTH, WindowGame.BASE_WINDOW_HEIGHT);

	// Render the buttons
	res1920x1080.render(container, g);
	res1280x720.render(container, g);
	res1024x768.render(container, g);
	res800x600.render(container, g);

	fullscreen.render(container, g);
	fenetre.render(container, g);

	retour.render(container, g);
	if (isResoltionChanged)
	    quitter.render(container, g);

	// Render the selected icon
	g.drawImage(selected, WindowGame.BASE_WINDOW_WIDTH / 2 + 220, y1Selected);
	g.drawImage(selected, WindowGame.BASE_WINDOW_WIDTH / 2 + 220, y2Selected);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
	// empty
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

    /*------------------------------------------------------------------*\
    |*				Methodes Private		   	*|
    \*------------------------------------------------------------------*/

    /**
     * Set the value of y1Selected and y2Selected for place them beside the
     * selected resolution
     */
    private void placeSelectedIcon() {
	switch (WindowGame.WINDOW_WIDTH) {
	case 1920:
	    y1Selected = res1920x1080.getY();
	    break;
	case 1280:
	    y1Selected = res1280x720.getY();
	    break;
	case 1024:
	    y1Selected = res1024x768.getY();
	    break;
	case 800:
	    y1Selected = res800x600.getY();
	    break;

	default:
	    y1Selected = res800x600.getY();
	    break;
	}

	if (WindowGame.FULLSCREEN)
	    y2Selected = fullscreen.getY();
	else
	    y2Selected = fenetre.getY();
    }

    /**
     * Change the display parametre of the game. Change will be saved in the
     * user preferences and will be applicated in the next start
     * 
     * @param b
     *            : If this boolean is true the game will be set in fullscreen,
     *            else the game will be in windowed
     */
    private void changeDisplay(Boolean b) {
	WindowGame.PREFERENCES.putBoolean("fullscreen", b);
    }

    /**
     * Change the resolution of the game. Changes will be saved in the user
     * preferences and will be applicated in the next start
     * 
     * @param w
     *            : Width of the game
     * @param h
     *            : height of the game
     */
    private void changeResolution(int w, int h) {
	WindowGame.PREFERENCES.putInt("width", w);
	WindowGame.PREFERENCES.putInt("height", h);
    }
}