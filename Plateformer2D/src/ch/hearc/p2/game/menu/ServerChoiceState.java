package ch.hearc.p2.game.menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ch.hearc.p2.game.WindowGame;
import ch.hearc.p2.game.network.PlatformerClient;

public class ServerChoiceState extends BasicGameState {

    public static final int ID = 1000;

    private SlickButton entrer;

    private Image background;
    private Image cursor;
    private Image avertissement;
    private Image retour;

    private SlickButton retourButton;

    private boolean failConnect;

    private TextField ip;
    private TextField pseudo;
    
    private Font font;

    public static String adresseIp;

    private PlatformerClient pfClient;

    private Sound rollover;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			    	*|
    \*------------------------------------------------------------------*/

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
	font = new TrueTypeFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.BOLD, 35), false);
	
	// Background image
	background = new Image("ressources/background/background.jpg");

	// Sound
	rollover = new Sound("ressources/audio/sound/rollover.ogg");

	// Cursor image
	cursor = new Image("ressources/cursor/hand_cursor.png");

	failConnect = false;
	avertissement = new Image("ressources/menu/avertissement.png");
	retour = new Image("ressources/menu/retour.jpg");

	// Color for button when mous is over
	Color color = new Color(255, 157, 67, 180);

	// Button "Quitter"
	Image connecterImage = new Image("ressources/menu/connecter.png");

	entrer = new SlickButton(container, connecterImage,
		WindowGame.BASE_WINDOW_WIDTH / 2 - connecterImage.getWidth() / 2, 450, connecterImage.getWidth(),
		connecterImage.getHeight(), new ComponentListener() {

		    @Override
		    public void componentActivated(AbstractComponent arg0) {
			// Connection dans le lobby
			try {
			    adresseIp = ip.getText();
			    pfClient = PlatformerClient.getInstance(adresseIp);
			    pfClient.setPseudo(pseudo.getText());
			    game.enterState(1001);
			} catch (Exception ex) {
			    failConnect = true;
			}
		    }
		});
	entrer.setMouseOverColor(color);
	entrer.setMouseDownSound(rollover);

	retourButton = new SlickButton(container, retour, WindowGame.BASE_WINDOW_WIDTH / 2 - retour.getWidth() / 2, 600,
		retour.getWidth(), retour.getHeight(), new ComponentListener() {

		    @Override
		    public void componentActivated(AbstractComponent arg0) {
			game.enterState(0);

		    }
		});
	retourButton.setMouseDownSound(rollover);
	retourButton.setMouseOverColor(color);

	// TextField
	ip = new TextFieldPlateformer(container, font,
		WindowGame.BASE_WINDOW_WIDTH / 2 - connecterImage.getWidth() / 2, 150, connecterImage.getWidth(),
		connecterImage.getHeight());
	pseudo = new TextFieldPlateformer(container, font,
		WindowGame.BASE_WINDOW_WIDTH / 2 - connecterImage.getWidth() / 2, 300, connecterImage.getWidth(),
		connecterImage.getHeight());

    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		 	    	*|
    \*------------------------------------------------------------------*/

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
	// Change the image of the cursor when we enter in the state
	container.setMouseCursor(cursor, 0, 0);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
	g.scale(WindowGame.SCALE_W, WindowGame.SCALE_H);

	

	// Render background
	background.draw(0, 0, WindowGame.BASE_WINDOW_WIDTH, WindowGame.BASE_WINDOW_HEIGHT);

	if (failConnect)
	    avertissement.draw(WindowGame.BASE_WINDOW_WIDTH / 2 - avertissement.getWidth() / 2, 750,
		    avertissement.getWidth(), avertissement.getHeight());

	retourButton.render(container, g);

	// Render the button
	entrer.render(container, g);
	ip.render(container, g);
	pseudo.render(container, g);
	
	g.setFont(font);
	g.drawString("IP du serveur: ", WindowGame.BASE_WINDOW_WIDTH / 4 , 150);
	g.drawString("Pseudo: ", WindowGame.BASE_WINDOW_WIDTH / 4 , 300);

    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

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