
package ch.hearc.p2.game.menu;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ch.hearc.p2.game.WindowGame;
import ch.hearc.p2.game.enums.Team;
import ch.hearc.p2.game.network.GameScore;
import ch.hearc.p2.game.network.IndividualScore;
import ch.hearc.p2.game.network.PlatformerClient;

public class EndGameMenu extends BasicGameState {

    public static final int ID = 1002;

    private SlickButton returnMainMenu;

    private Image background;
    private Image cursor;
    private Image bleu;
    private Image rouge;

    private Music deadMusic;

    private Sound rollover;

    private GameScore gameScore;
    private Team winningTeam;

    private Font fontBig;
    private Font fontSmall;
    private Font fontMedium;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			    	*|
    \*------------------------------------------------------------------*/

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
	fontBig = new TrueTypeFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.BOLD, 50), false);
	fontSmall = new TrueTypeFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.PLAIN, 30), false);
	fontMedium = new TrueTypeFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.PLAIN, 40), false);

	// Background image
	background = new Image("ressources/background/background.jpg");

	// Music
	deadMusic = new Music("ressources/audio/music/gameover.ogg");

	// Sound
	rollover = new Sound("ressources/audio/sound/rollover.ogg");

	// Cursor image
	cursor = new Image("ressources/cursor/hand_cursor.png");

	// Team image
	bleu = new Image("ressources/menu/bleu.png");
	rouge = new Image("ressources/menu/rouge.png");

	// Color for button when mous is over
	Color color = new Color(255, 157, 67, 180);

	// Button "Retour menu principal"
	Image recommencerImage = new Image("ressources/menu/retour.jpg");

	returnMainMenu = new SlickButton(container, recommencerImage,
		WindowGame.BASE_WINDOW_WIDTH / 2 - recommencerImage.getWidth() / 2, 500, recommencerImage.getWidth(),
		recommencerImage.getHeight(), new ComponentListener() {

		    @Override
		    public void componentActivated(AbstractComponent arg0) {
			game.enterState(0);
		    }
		});

	returnMainMenu.setMouseOverColor(color);
	returnMainMenu.setMouseDownSound(rollover);
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

	PlatformerClient plClient;
	try {
	    plClient = PlatformerClient.getInstance();
	    gameScore = plClient.getGameScore();
	    winningTeam = plClient.getWinningTeam();
	    plClient.setGameFinished(false);
	    plClient.disconnect();

	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
	g.scale(WindowGame.SCALE_W, WindowGame.SCALE_H);
	g.setFont(fontBig);

	// Render background
	background.draw(0, 0, WindowGame.BASE_WINDOW_WIDTH, WindowGame.BASE_WINDOW_HEIGHT);
	if (winningTeam == Team.BLUE)
	    g.drawString("Blue team win", (WindowGame.BASE_WINDOW_WIDTH / 2) - 50, 50);
	else if (winningTeam == Team.RED)
	    g.drawString("Red team win", (WindowGame.BASE_WINDOW_WIDTH / 2) - 50, 50);
	else
	    g.drawString("Draw", WindowGame.BASE_WINDOW_WIDTH / 2 - 20, 50);

	g.setFont(fontMedium);
	bleu.draw(WindowGame.BASE_WINDOW_WIDTH / 4, 150);
	g.drawString("(" + gameScore.getBlueTeamScore() + ")", WindowGame.BASE_WINDOW_WIDTH / 4 + bleu.getWidth() + 15,
		145);
	rouge.draw((WindowGame.BASE_WINDOW_WIDTH / 4) * 3 - 50, 150);
	g.drawString("(" + gameScore.getRedTeamScore() + ")",
		(WindowGame.BASE_WINDOW_WIDTH / 4) * 3 + rouge.getWidth() -35, 145);

	
	int y = 275;
	for (String line : getBlueScoreString().split("\n"))
	    g.drawString(line, WindowGame.BASE_WINDOW_WIDTH / 6, y += fontMedium.getLineHeight());

	y = 275;
	for (String line : getRedScoreString().split("\n"))
	    g.drawString(line, (WindowGame.BASE_WINDOW_WIDTH / 6)*4, y += fontMedium.getLineHeight());

	// Render the button
	returnMainMenu.render(container, g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
	// gameScore.dump();
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

    /**
     * The ID is used for identify the state. This will be used when a state has
     * to come bakc to the previous state.
     */
    @Override
    public int getID() {
	return ID;
    }
}
