package ch.hearc.p2.game.menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ch.hearc.p2.game.WindowGame;
import ch.hearc.p2.game.network.PlatformerClient;

public class LobbyState extends BasicGameState {

    public static final int ID = 1001;

    private Image background;
    private Image cursor;
    private Image bleu;
    private Image rouge;
    private Image player1;
    private Graphics localImgPlayer1;

    private String team;
    private static String ip;

    private PlatformerClient pf;

    private Sound rollover;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			    	*|
    \*------------------------------------------------------------------*/

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
	// Background image
	background = new Image("ressources/background/background.jpg");

	// Sound
	rollover = new Sound("ressources/audio/sound/rollover.ogg");

	// Cursor image
	cursor = new Image("ressources/cursor/hand_cursor.png");

	// Color for button when mous is over
	//Color color = new Color(255, 157, 67, 180);

	// Button "Quitter"
	bleu = new Image("ressources/menu/bleu.png");
	rouge = new Image("ressources/menu/rouge.png");

	try {
	    player1 = new Image(227, 80);
	    localImgPlayer1 = player1.getGraphics();

	} catch (SlickException e) {
	}

    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		 	    	*|
    \*------------------------------------------------------------------*/

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
	// Change the image of the cursor when we enter in the state
	container.setMouseCursor(cursor, 0, 0);

	pf = PlatformerClient.getInstance();

    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
	g.scale(WindowGame.SCALE_W, WindowGame.SCALE_H);

	// Render background
	background.draw(0, 0, WindowGame.BASE_WINDOW_WIDTH, WindowGame.BASE_WINDOW_HEIGHT);
	bleu.draw(WindowGame.BASE_WINDOW_WIDTH / 4, 150);
	rouge.draw((WindowGame.BASE_WINDOW_WIDTH / 4) * 3 - rouge.getWidth(), 150);

	if (team.equals("RED"))
	    player1.draw((WindowGame.BASE_WINDOW_WIDTH / 4) * 3 - rouge.getWidth(), 250);
	else
	    player1.draw(WindowGame.BASE_WINDOW_WIDTH / 4, 250);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
	
	team = pf.getTeam();

	if (team.equals("RED")) {
	    localImgPlayer1.setBackground(new Color(211, 59, 39));
	    localImgPlayer1.clear();
	    localImgPlayer1.flush();
	} else {
	    localImgPlayer1.setBackground(new Color(67, 167, 223));
	    localImgPlayer1.clear();
	    localImgPlayer1.flush();
	}

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