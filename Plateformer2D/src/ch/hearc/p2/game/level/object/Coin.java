package ch.hearc.p2.game.level.object;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import ch.hearc.p2.game.physics.AABoundingRect;

public class Coin extends Objective {

    private int value;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/

    public Coin(float x, float y) throws SlickException {
	super(x, y);
	value = 1;

	// add the right animation for this objective
	image = new Image("ressources/tiles/item/coinGold.png");
	boundingShape = new AABoundingRect(x, y, 50, 70);
    }

    /*-----------------------*\
    |*		Get	     *|
    \*-----------------------*/

    public int getValue() {
	return value;
    }

}