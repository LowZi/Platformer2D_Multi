package ch.hearc.p2.game.level.tile;

import ch.hearc.p2.game.physics.AABoundingRect;

public class HalfTile extends Tile {

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/

    public HalfTile(int x, int y, String type) {
	super(x, y, type);
	boundingShape = new AABoundingRect(x * 70, y * 70, 70, 35);
    }

}