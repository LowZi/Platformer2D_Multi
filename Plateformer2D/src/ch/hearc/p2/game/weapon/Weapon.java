package ch.hearc.p2.game.weapon;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import ch.hearc.p2.game.enums.Facing;
import ch.hearc.p2.game.level.LevelObject;
import ch.hearc.p2.game.physics.AABoundingRect;
import ch.hearc.p2.game.projectile.ProjectilePlayer;

public class Weapon extends LevelObject {

    protected List<LevelObject> toAddList = new LinkedList<LevelObject>();

    protected Image arme;

    protected int munition;
    protected int cadence;

    protected Facing way;
    protected Facing playerFacing;

    protected Sound tir;

    protected Random random;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/

    public Weapon(float x, float y) throws SlickException {
	super(x, y);
	way = Facing.LEFT;
	playerFacing = Facing.RIGHT;
	munition = 100; // remplacer par mun
	x_velocity = 0;
	y_velocity = 0;
	cadence = 200;

	arme = new Image("ressources/tiles/item/raygunBig.png");

	boundingShape = new AABoundingRect(x, y, 0, 0);
	maximumFallSpeed = 0;
	tir = new Sound("ressources/audio/sound/shoot.ogg");
	random = new Random();
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		    		*|
    \*------------------------------------------------------------------*/

    @Override
    public void render(float offset_x, float offset_y) {
	if (playerFacing == Facing.RIGHT)
	    arme.draw(x - offset_x, y - offset_y);
	else {
	    arme.getFlippedCopy(true, false).draw(x - offset_x - 65, y - offset_y);
	}

    }

    public void shoot(float playerX, float playerY, int mouseX, int mouseY) throws SlickException {
	if (munition > 0) {
	    // Calcul de la vélocité X et Y
	    float velocityX = 3.5f;
	    float velocityY = 3.5f;

	    double angle = Math.atan(Math.abs(mouseY - playerY) / Math.abs(mouseX - playerX));

	    velocityX *= Math.cos(angle);
	    velocityY *= Math.sin(angle);

	    System.err.println(velocityX);

	    if (mouseX - playerX < 0) // Clique à gauche du joueur
	    {
		if (mouseY - playerY < 0) // Clique en dessous du joueur
		{
		    velocityY *= -1;

		} else // Clique en dessus du joueur
		{

		}
		velocityX *= -1;

		way = Facing.LEFT;
	    } else // Clique à droite du joueur
	    {
		if (mouseY - playerY < 0) // Clique en dessous du joueur
		{
		    velocityY *= -1;
		} else // Clique en dessus du joueur
		{

		}
		way = Facing.RIGHT;

	    }
	    if (way == Facing.RIGHT) {
		toAddList.add(new ProjectilePlayer(x + 50, y + 30, velocityX, velocityY));
		toAddList.add(new MuzzleFlash(x + 40, y + 10));
	    } else {
		toAddList.add(new ProjectilePlayer(x - 90, y + 30, velocityX, velocityY));
		toAddList.add(new MuzzleFlash(x - 85, y + 10));
	    }

	    munition--;

	    tir.play(1, 1);

	}
    }

    public void clearToAddList() {
	toAddList.clear();
    }

    /*-----------------------*\
    |*		Get	     *|
    \*-----------------------*/

    public List<LevelObject> getToAddList() {
	return toAddList;
    }

    public int getMunition() {
	return munition;
    }

    public Facing getWay() {
	return way;
    }

    public int getCadence() {
	return cadence;
    }

    /*-----------------------*\
    |*		Set	     *|
    \*-----------------------*/

    public void setPlayerFacing(Facing facing) {
	playerFacing = facing;
    }

}
