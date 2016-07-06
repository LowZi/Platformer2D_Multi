package ch.hearc.p2.game.level.object;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import ch.hearc.p2.game.physics.AABoundingRect;

public class Key extends Objective {

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/

    public Key(float x, float y) throws SlickException {
	super(x, y);

	// add the right animation for this objective
	image = new Image("ressources/tiles/item/keyRed.png");
	boundingShape = new AABoundingRect(x, y, 45, 50);
    }

}