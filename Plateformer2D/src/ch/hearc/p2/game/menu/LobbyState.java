package ch.hearc.p2.game.menu;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ch.hearc.p2.game.WindowGame;
import ch.hearc.p2.game.enums.Team;
import ch.hearc.p2.game.network.Metadata;
import ch.hearc.p2.game.network.PlatformerClient;

public class LobbyState extends BasicGameState {

    public static final int ID = 1001;
    private static boolean start;
    private static int id;

    private Image background;
    private Image cursor;
    private Image bleu;
    private Image rouge;
    private Image player1;

    private Graphics localImgPlayer1;
    private Font font;

    private PlatformerClient pf;
    private ArrayList<Metadata> players;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			    	*|
    \*------------------------------------------------------------------*/

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
	start = false;
	font = new TrueTypeFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.BOLD, 35), false);

	// Background image
	background = new Image("ressources/background/background.jpg");

	// Cursor image
	cursor = new Image("ressources/cursor/hand_cursor.png");

	// Color for button when mous is over
	// Color color = new Color(255, 157, 67, 180);

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

	try {
	    pf = PlatformerClient.getInstance();
	    if (!pf.isConnected())
		game.enterState(1000);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
	g.scale(WindowGame.SCALE_W, WindowGame.SCALE_H);
	g.setFont(font);

	// Render background
	background.draw(0, 0, WindowGame.BASE_WINDOW_WIDTH, WindowGame.BASE_WINDOW_HEIGHT);
	bleu.draw(WindowGame.BASE_WINDOW_WIDTH / 4, 150);
	rouge.draw((WindowGame.BASE_WINDOW_WIDTH / 4) * 3 - rouge.getWidth(), 150);

	int xRed = (WindowGame.BASE_WINDOW_WIDTH / 4) * 3 - rouge.getWidth();
	int xBlue = WindowGame.BASE_WINDOW_WIDTH / 4;
	int yRed = 250;
	int yBlue = 250;

	players = pf.getPlayers();

	for (Metadata metadata : players) {
	    if (metadata.getTeam() == Team.BLUE) {
		localImgPlayer1.setBackground(new Color(67, 167, 223));
		localImgPlayer1.clear();
		localImgPlayer1.flush();
		player1.draw(xBlue, yBlue);
		g.drawString(metadata.getPseudo(), xBlue + 10, yBlue + 10);
		yBlue += 100;
	    } else {
		localImgPlayer1.setBackground(new Color(211, 59, 39));
		localImgPlayer1.clear();
		localImgPlayer1.flush();
		player1.draw(xRed, yRed);
		g.drawString(metadata.getPseudo(), xRed + 10, yRed + 10);
		yRed += 100;
	    }
	}

    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
	if (start)
	    game.enterState(id);
    }

    public static void startGame(int gameID) {
	start = true;
	id = gameID;
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