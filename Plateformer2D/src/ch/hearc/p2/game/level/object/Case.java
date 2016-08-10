package ch.hearc.p2.game.level.object;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import ch.hearc.p2.game.network.CaseData;
import ch.hearc.p2.game.physics.AABoundingRect;

public class Case extends Objective {

    private int indexWeapon;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/

    public Case(float x, float y, int indexWeapon) throws SlickException {
	super(x, y);
	this.indexWeapon = indexWeapon;

	// add the right animation for this objective
	image = new Image("ressources/tiles/item/case.png");
	boundingShape = new AABoundingRect(x, y, 70, 70);
    }

    public Case(CaseData cd) throws SlickException {
	this(cd.getX() * 70, cd.getY() * 70, cd.getIndexWeapon());
    }

    /*-----------------------*\
    |*		Get	     *|
    \*-----------------------*/

    public int getIndexWeapon() {
	return indexWeapon;
    }

}