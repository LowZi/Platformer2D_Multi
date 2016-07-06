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
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import ch.hearc.p2.game.WindowGame;

public class MainScreenGameState extends BasicGameState {

    public static final int ID = 0;

    private Image background;
    private Image cursor;

    private SlickButton jouer;
    private SlickButton niveaux;
    private SlickButton options;
    private SlickButton quitter;
    private SlickButton multijoueur;

    private Music sound;

    private Sound rollover;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			    	*|
    \*------------------------------------------------------------------*/

    public MainScreenGameState() {
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

	// Sounds
	sound = new Music("ressources/audio/music/home.ogg");
	rollover = new Sound("ressources/audio/sound/rollover.ogg");

	// Color for button when mous is over
	Color color = new Color(255, 157, 67, 180);

	// Button "jouer"
	Image jouerImage = new Image("ressources/menu/jouer.png");

	jouer = new SlickButton(container, jouerImage, WindowGame.BASE_WINDOW_WIDTH / 2 - jouerImage.getWidth() / 2,
		250, jouerImage.getWidth(), jouerImage.getHeight(), new ComponentListener() {

		    @Override
		    public void componentActivated(AbstractComponent arg0) {
			game.enterState(101, new FadeOutTransition(), new FadeInTransition());
		    }
		});

	jouer.setMouseDownSound(rollover);
	jouer.setMouseOverColor(color);

	// Button "niveaux"
	Image niveauxImage = new Image("ressources/menu/niveaux.png");

	niveaux = new SlickButton(container, niveauxImage,
		WindowGame.BASE_WINDOW_WIDTH / 2 - niveauxImage.getWidth() / 2, 400, niveauxImage.getWidth(),
		niveauxImage.getHeight(), new ComponentListener() {

		    @Override
		    public void componentActivated(AbstractComponent arg0) {
			game.enterState(11);
		    }
		});

	niveaux.setMouseDownSound(rollover);
	niveaux.setMouseOverColor(color);

	// Button "options"
	Image optionsImage = new Image("ressources/menu/options.png");

	options = new SlickButton(container, optionsImage,
		WindowGame.BASE_WINDOW_WIDTH / 2 - optionsImage.getWidth() / 2, 550, optionsImage.getWidth(),
		optionsImage.getHeight(), new ComponentListener() {

		    @Override
		    public void componentActivated(AbstractComponent arg0) {
			game.enterState(60);
		    }
		});

	options.setMouseDownSound(rollover);
	options.setMouseOverColor(color);

	// Button "quitter"
	Image quitterImage = new Image("ressources/menu/quitter.png");

	quitter = new SlickButton(container, quitterImage,
		WindowGame.BASE_WINDOW_WIDTH / 2 - quitterImage.getWidth() / 2, 700, quitterImage.getWidth(),
		quitterImage.getHeight(), new ComponentListener() {

		    @Override
		    public void componentActivated(AbstractComponent arg0) {
			System.exit(0);
		    }
		});

	quitter.setMouseDownSound(rollover);
	quitter.setMouseOverColor(color);

	// Button "multi"
	Image multiImage = new Image("ressources/menu/multi.jpg");

	multijoueur = new SlickButton(container, multiImage,
		WindowGame.BASE_WINDOW_WIDTH / 2 - multiImage.getWidth() / 2, 850, multiImage.getWidth(),
		multiImage.getHeight(), new ComponentListener() {

		    @Override
		    public void componentActivated(AbstractComponent arg0) {
			game.enterState(1000);
		    }
		});

	multijoueur.setMouseDownSound(rollover);
	multijoueur.setMouseOverColor(color);
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
	container.setMouseCursor(cursor, 0, 0);

	if (sound.playing() == false)
	    sound.loop(1, 0.4f);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
	// Scale the render for the selected resolution
	g.scale(WindowGame.SCALE_W, WindowGame.SCALE_H);

	// Render background
	background.draw(0, 0, WindowGame.BASE_WINDOW_WIDTH, WindowGame.BASE_WINDOW_HEIGHT);

	// Render the buttons
	jouer.render(container, g);
	niveaux.render(container, g);
	options.render(container, g);
	quitter.render(container, g);
	multijoueur.render(container, g);
    }

    @Override
    public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
	// empty
    }

    /*------------------------------*\
    |*		Get	            *|
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