package ch.hearc.p2.game.physics;

import java.util.ArrayList;

import org.newdawn.slick.tiled.TiledMap;

import ch.hearc.p2.game.level.tile.Tile;

public class AABoundingRect extends BoundingShape {

    public float x;
    public float y;
    public float width;
    public float height;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			    	*|
    \*------------------------------------------------------------------*/

    public AABoundingRect(float x, float y, float width, float height) {
	this.x = x;
	this.y = y;
	this.width = width;
	this.height = height;
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		 	    	*|
    \*------------------------------------------------------------------*/

    @Override
    public void updatePosition(float newX, float newY) {
	this.x = newX;
	this.y = newY;
    }

    @Override
    public void movePosition(float x, float y) {
	this.x += x;
	this.y += y;
    }

    @Override
    public boolean checkCollision(AABoundingRect rect, TiledMap map) {

	return !(rect.x > this.x + width || rect.x + rect.width < this.x || rect.y > this.y + height
		|| rect.y + rect.height < this.y);

    }

    @Override
    public boolean checkCollision(AABoundingRect rect) {
	return !(rect.x > this.x + width || rect.x + rect.width < this.x || rect.y > this.y + height
		|| rect.y + rect.height < this.y);
    }

    @Override
    public ArrayList<Tile> getTilesOccupying(Tile[][] tiles) {
	ArrayList<Tile> occupiedTiles = new ArrayList<Tile>();
	// System.out.println(tiles.length);
	// we go from the left of the rect towards to right of the rect, making
	// sure we round upwards to a multiple of 70 or we might miss a few
	// tiles
	for (int i = (int) x; i <= x + width + (70 - width % 70); i += 70) {
	    for (int j = (int) y; j <= y + height + (70 - height % 70); j += 70) {
		try {
		    occupiedTiles.add(tiles[i / 70][j / 70]);
		} catch (Exception e) {
		}

	    }
	}
	return occupiedTiles;
    }

    @Override
    public ArrayList<Tile> getGroundTiles(Tile[][] tiles) {

	ArrayList<Tile> tilesUnderneath = new ArrayList<Tile>();
	int j = (int) (y + height + 1);

	for (int i = (int) x; i <= x + width + (70 - width % 70); i += 70) {
	    try {
		tilesUnderneath.add(tiles[(i / 70)][j / 70]);
	    } catch (Exception e) {
	    }
	}

	return tilesUnderneath;
    }

}
