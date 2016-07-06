package ch.hearc.p2.game.character;

import java.util.LinkedList;
import java.util.Random;

import org.newdawn.slick.SlickException;

import ch.hearc.p2.game.level.LevelObject;
import ch.hearc.p2.game.physics.AABoundingRect;

public class Ennemie extends Character {

    protected Random rand;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/

    public Ennemie(float x, float y) throws SlickException {

	super(x, y);
	rand = new Random();
	toAddList = new LinkedList<LevelObject>();

	boundingShape = new AABoundingRect(x, y, 45, 45);

	accelerationSpeed = 0.002f;
	maximumSpeed = 0.55f;
	maximumFallSpeed = 0.6f;
	decelerationSpeed = 0.001f;
	life = 6;
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		 	  	*|
    \*------------------------------------------------------------------*/

    @Override
    public void updateBoundingShape() {
	boundingShape.updatePosition(x + 20, y + 70);
    }

    public void shoot() throws SlickException {

    }

    public void moveRandom() {

    }

    /*-----------------------*\
    |*		Set	     *|
    \*-----------------------*/

    public void setMaximumFallSpeed(float maximumFallSpeed) {
	this.maximumFallSpeed = maximumFallSpeed;
    }

    public void setDead(Boolean b) {
	this.dead = b;
    }

    /*----------------------*\
    |*		Is	    *|
    \*----------------------*/

    public Boolean isDead() {
	return dead;
    }

}