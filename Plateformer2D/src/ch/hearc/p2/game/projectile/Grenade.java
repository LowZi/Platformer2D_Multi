package ch.hearc.p2.game.projectile;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import ch.hearc.p2.game.physics.AABoundingRect;

public class Grenade extends Projectile {

    protected Image grenade;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			    	*|
    \*------------------------------------------------------------------*/

    public Grenade(float x, float y, float x_velocity, float y_velocity) throws SlickException {
	super(x, y);
	this.x_velocity = x_velocity;
	this.y_velocity = y_velocity;
	grenade = new Image("ressources/tiles/item/bomb.png");
	grenade = grenade.getScaledCopy(0.5f);
	boundingShape = new AABoundingRect(x, y, 15f, 15f);
	maximumFallSpeed = 1;
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		 	    	*|
    \*------------------------------------------------------------------*/

    @Override
    public void render(float offset_x, float offset_y) {
	grenade.draw(x - offset_x, y - offset_y);
    }
}
