package ch.hearc.p2.game.level.object;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import ch.hearc.p2.game.level.LevelObject;
import ch.hearc.p2.game.physics.AABoundingRect;

public class Objective extends LevelObject {

    protected Image image;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/
    
    public Objective(float x, float y) throws SlickException {
	super(x, y);

	// add the right animation for this objective
	image = new Image("ressources/tiles/item/coinGold.png");
	boundingShape = new AABoundingRect(x, y, 35, 35);
    }
    
    /*------------------------------------------------------------------*\
    |*				Methodes Public		 	  	*|
    \*------------------------------------------------------------------*/

    @Override
    public void render(float offset_x, float offset_y) {
	image.draw(x - offset_x, y - offset_y);
    }

}