package ch.hearc.p2.game.level.tile;

import ch.hearc.p2.game.physics.AABoundingRect;

public class HalfDownTile extends Tile {

    // Pour les tiles "Pont"

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/

    public HalfDownTile(int x, int y, String type) {
	super(x, y, type);
	boundingShape = new AABoundingRect(x * 70, y * 70 + 47, 70, 23);
    }

}