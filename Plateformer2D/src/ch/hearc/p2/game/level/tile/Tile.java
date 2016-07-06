package ch.hearc.p2.game.level.tile;

import ch.hearc.p2.game.physics.BoundingShape;

public class Tile {

    protected int x;
    protected int y;

    protected String type;

    protected BoundingShape boundingShape;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/

    public Tile(int x, int y, String type) {
	this.x = x;
	this.y = y;
	this.type = type;
	boundingShape = null;
    }

    /*-----------------------*\
    |*		Get	     *|
    \*-----------------------*/

    public BoundingShape getBoundingShape() {
	return boundingShape;
    }

    public int getX() {
	return x;
    }

    public int getY() {
	return y;
    }

    public String getType() {
	return type;
    }
}
