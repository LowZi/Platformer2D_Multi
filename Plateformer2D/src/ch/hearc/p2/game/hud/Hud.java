package ch.hearc.p2.game.hud;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import ch.hearc.p2.game.WindowGame;
import ch.hearc.p2.game.character.Player;

public class Hud {

    private Image[] life;
    private Image[] numbers;
    private Image coin;
    private Image cleVide;
    private Image clePleine;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/

    public Hud() {

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
	coin = new Image("ressources/hud/hudCoin.png");
	cleVide = new Image("ressources/hud/hudKey_red_empty.png");
	clePleine = new Image("ressources/hud/hudKey_red.png");
    }

    public void render(Graphics g, Player p) {
	g.resetTransform();

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

	int point = p.getPoint();
	int x = 1680;
	if (point == 0)
	    g.drawImage(numbers[0], x, 18);
	while (point > 0) {
	    g.drawImage(numbers[point % 10], x, 18);
	    point = point / 10;
	    x -= 80;
	}
	g.drawImage(coin, 1780, 20);

	int munition = p.getWeapon().getMunition();
	x = 210;
	if (munition == 0)
	    g.drawImage(numbers[0], x, 950);
	while (munition > 0) {
	    g.drawImage(numbers[munition % 10], x, 950);
	    munition = munition / 10;
	    x -= 80;
	}

	x = 1780;
	if (p.hasKey())
	    g.drawImage(clePleine, x, 950);
	else
	    g.drawImage(cleVide, x, 950);
    }

}