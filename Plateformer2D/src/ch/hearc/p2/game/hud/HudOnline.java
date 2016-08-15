package ch.hearc.p2.game.hud;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import ch.hearc.p2.game.WindowGame;
import ch.hearc.p2.game.character.PlayerOnline;
import ch.hearc.p2.game.network.GameScore;
import ch.hearc.p2.game.network.PlatformerClient;

public class HudOnline {

    private Image[] life;
    private Image[] numbers;
    private int timeLeft;
    private GameScore gameScore;
    private Font font;
    protected ArrayBlockingQueue<String> killFeed;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/

    public HudOnline() {
	// rien
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		 	  	*|
    \*------------------------------------------------------------------*/

    public void init() throws SlickException {
	life = new Image[3];
	life[0] = new Image("ressources/hud/hudHeart_full.png");
	life[1] = new Image("ressources/hud/hudHeart_half.png");
	life[2] = new Image("ressources/hud/hudHeart_empty.png");
	numbers = new Image[10];
	numbers[0] = new Image("ressources/hud/hud0.png");
	numbers[1] = new Image("ressources/hud/hud1.png");
	numbers[2] = new Image("ressources/hud/hud2.png");
	numbers[3] = new Image("ressources/hud/hud3.png");
	numbers[4] = new Image("ressources/hud/hud4.png");
	numbers[5] = new Image("ressources/hud/hud5.png");
	numbers[6] = new Image("ressources/hud/hud6.png");
	numbers[7] = new Image("ressources/hud/hud7.png");
	numbers[8] = new Image("ressources/hud/hud8.png");
	numbers[9] = new Image("ressources/hud/hud9.png");
	font = new TrueTypeFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.BOLD, 40), false);

    }

    public void render(Graphics g, PlayerOnline p) {
	g.resetTransform();
	g.setFont(font);
	g.scale(WindowGame.SCALE_W, WindowGame.SCALE_H);

	switch (p.getLife()) {
	case 6:
	    g.drawImage(life[0], 20, 20);
	    g.drawImage(life[0], 148, 20);
	    g.drawImage(life[0], 286, 20);
	    break;
	case 5:
	    g.drawImage(life[0], 20, 20);
	    g.drawImage(life[0], 148, 20);
	    g.drawImage(life[1], 286, 20);
	    break;
	case 4:
	    g.drawImage(life[0], 20, 20);
	    g.drawImage(life[0], 148, 20);
	    g.drawImage(life[2], 286, 20);
	    break;
	case 3:
	    g.drawImage(life[0], 20, 20);
	    g.drawImage(life[1], 148, 20);
	    g.drawImage(life[2], 286, 20);
	    break;
	case 2:
	    g.drawImage(life[0], 20, 20);
	    g.drawImage(life[2], 148, 20);
	    g.drawImage(life[2], 286, 20);
	    break;
	case 1:
	    g.drawImage(life[1], 20, 20);
	    g.drawImage(life[2], 148, 20);
	    g.drawImage(life[2], 286, 20);
	    break;
	case 0:
	    g.drawImage(life[2], 20, 20);
	    g.drawImage(life[2], 148, 20);
	    g.drawImage(life[2], 286, 20);
	    break;
	default:
	    break;
	}

	// Position pour les points
	int x = 960;

	String score = "Blue " + gameScore.getBlueTeamScore() + " : " + gameScore.getRedTeamScore() + " Red";

	g.drawString(score, x, 18);

	int munition = p.getWeapon().getMunition();
	x = 210;

	if (munition == 0)
	    g.drawImage(numbers[0], x, 950);
	while (munition > 0) {
	    g.drawImage(numbers[munition % 10], x, 950);
	    munition = munition / 10;
	    x -= 80;
	}

	int seconds = timeLeft;
	x = 1680;
	if (seconds == 0) {
	    g.drawImage(numbers[0], x, 18);
	}
	if (seconds <= 9) {
	    g.drawImage(numbers[0], x - 80, 18);
	}
	while (seconds > 0) {
	    g.drawImage(numbers[seconds % 10], x, 18);
	    seconds = seconds / 10;
	    x -= 80;
	}

	int y = WindowGame.BASE_WINDOW_HEIGHT / 5;
	Iterator<String> it = killFeed.iterator();
	while (it.hasNext()) {
	    g.drawString(it.next(), 15, y += font.getLineHeight());
	}

    }

    public void update() {
	try {
	    PlatformerClient platformerClient = PlatformerClient.getInstance();
	    timeLeft = platformerClient.getTimeLeft();
	    gameScore = platformerClient.getGameScore();
	    killFeed = platformerClient.getKillFeed();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}