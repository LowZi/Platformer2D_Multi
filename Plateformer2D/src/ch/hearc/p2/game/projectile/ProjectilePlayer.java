package ch.hearc.p2.game.projectile;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import ch.hearc.p2.game.physics.AABoundingRect;

public class ProjectilePlayer extends Projectile {

    protected Image bullet;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			    	*|
    \*------------------------------------------------------------------*/

    public ProjectilePlayer(float x, float y, float x_velocity, float y_velocity) throws SlickException {
	super(x, y);
	this.x_velocity = x_velocity;
	this.y_velocity = y_velocity;

	bullet = new Image("ressources/tiles/item/bullet.png");
	bullet = bullet.getScaledCopy(0.4f);
	boundingShape = new AABoundingRect(x, y, 15, 15);
	maximumFallSpeed = (float) 0.75;

    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		 	    	*|
    \*------------------------------------------------------------------*/

    @Override
    public void render(float offset_x, float offset_y) {
	bullet.draw(x - 2 - offset_x, y - 2 - offset_y);
    }

}