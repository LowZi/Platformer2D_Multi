package ch.hearc.p2.game.physics;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import ch.hearc.p2.game.character.Character;
import ch.hearc.p2.game.character.Ennemie;
import ch.hearc.p2.game.character.Player;
import ch.hearc.p2.game.level.Level;
import ch.hearc.p2.game.level.LevelObject;
import ch.hearc.p2.game.level.object.Coin;
import ch.hearc.p2.game.level.object.Key;
import ch.hearc.p2.game.level.object.Objective;
import ch.hearc.p2.game.level.tile.Tile;
import ch.hearc.p2.game.projectile.Explosion;
import ch.hearc.p2.game.projectile.Grenade;
import ch.hearc.p2.game.projectile.Projectile;
import ch.hearc.p2.game.projectile.ProjectileAbeille;
import ch.hearc.p2.game.projectile.ProjectilePlayer;
import ch.hearc.p2.game.weapon.MuzzleFlash;

public class Physics {

    private final float gravity = 0.0023f;

    private TiledMap map;

    private boolean isFinished = false;
    private boolean needShake = false;

    private ArrayList<LevelObject> removeQueue;
    private ArrayList<LevelObject> addQueue;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			    	*|
    \*------------------------------------------------------------------*/

    public Physics(Level level) throws SlickException {
	map = level.getMap();
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		 	    	*|
    \*------------------------------------------------------------------*/

    public void handlePhysics(Level level, int delta) throws SlickException {
	handleCharacters(level, delta);
	handleLevelObjects(level, delta);
    }

    /*------------------------------*\
    |*		Is		    *|
    \*------------------------------*/

    public boolean isOver() {
	return isFinished;
    }

    public boolean needShake() {
	return needShake;
    }

    /*------------------------------*\
    |*		Set	  	    *|
    \*------------------------------*/

    public void setOver(boolean isOver) {
	isFinished = isOver;
    }

    public void setShake(boolean shake) {
	needShake = shake;
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Private		    	*|
    \*------------------------------------------------------------------*/

    private void handleCharacters(Level level, int delta) throws SlickException {
	for (Character c : level.getCharacters()) {
	    ArrayList<LevelObject> removeQueueC = new ArrayList<LevelObject>();

	    // and now decelerate the character if he is not moving anymore
	    if (!c.isMoving()) {
		c.decelerate(delta);
	    }

	    handleGameObject(c, level, delta);

	    if (c instanceof Player) {

		// we have to check if he collides with anything special, such
		// as objectives for example
		for (LevelObject obj : level.getLevelObjects()) {

		    if (obj instanceof ProjectileAbeille) {
			// in case its an objective and its collides
			if (obj.getBoundingShape().checkCollision(c.getBoundingShape())) {
			    c.damage(((ProjectileAbeille) obj).getDamage());
			    removeQueueC.add(obj);
			    c.hit();
			}
		    }
		    if (obj instanceof Objective) {
			if (obj.getBoundingShape().checkCollision(c.getBoundingShape())) {
			    if (obj instanceof Coin)
				((Player) c).addPoint(((Coin) obj).getValue());
			    if (obj instanceof Key)
				((Player) c).setKey(true);
			    removeQueueC.add(obj);
			}
		    }

		}
		for (LevelObject obj : level.getCharacters()) {
		    if (obj instanceof Ennemie) {
			if (obj.getBoundingShape().checkCollision(c.getBoundingShape()) && !((Ennemie) obj).isDead()) {
			    c.damage(1);
			    c.hit();
			}
		    }
		}

	    }
	    if (c instanceof Ennemie) {
		for (LevelObject obj : level.getLevelObjects()) {

		    if (obj instanceof ProjectilePlayer) {
			if (obj.getBoundingShape().checkCollision(c.getBoundingShape())) {
			    if (((Ennemie) c).isDead() == false) {
				c.damage(1);
				c.hit();
				removeQueueC.add(obj);
			    }
			}
		    }

		    if (obj instanceof Explosion) {
			if (obj.getBoundingShape().checkCollision(c.getBoundingShape())) {
			    if (((Ennemie) c).isDead() == false) {
				c.damage(((Explosion) obj).getDamage());
				c.hit();
			    }
			}
		    }
		}
	    }
	    level.removeObjects(removeQueueC);
	}

    }

    private void handleLevelObjects(Level level, int delta) throws SlickException {
	removeQueue = new ArrayList<LevelObject>();
	addQueue = new ArrayList<LevelObject>();
	for (LevelObject obj : level.getLevelObjects()) {
	    handleGameObject(obj, level, delta);
	}
	level.addLevelObject(addQueue);
	level.removeObjects(removeQueue);
    }

    private void handleGameObject(LevelObject obj, Level level, int delta) throws SlickException {

	// first update the onGround of the object
	obj.setOnGround(isOnGroud(obj, level.getTiles()));

	// now apply gravitational force if we are not on the ground or when we
	// are about to jump
	if (!obj.isOnGround() || obj.getYVelocity() < 0)
	    obj.applyGravity(gravity * delta);
	else {
	    obj.setYVelocity(0);
	}

	// calculate how much we actually have to move
	float x_movement = obj.getXVelocity() * delta;
	float y_movement = obj.getYVelocity() * delta;

	// we have to calculate the step we have to take
	float step_y = 0;
	float step_x = 0;

	if (x_movement != 0) {

	    step_y = Math.abs(y_movement) / Math.abs(x_movement);
	    if (y_movement < 0)
		step_y = -step_y;

	    if (x_movement > 0)
		step_x = 1;
	    else
		step_x = -1;

	    if ((step_y > 1 || step_y < -1) && step_y != 0) {
		step_x = Math.abs(step_x) / Math.abs(step_y);
		if (x_movement < 0)
		    step_x = -step_x;
		if (y_movement < 0)
		    step_y = -1;
		else
		    step_y = 1;

	    }
	    if (checkCollision(obj, level.getTiles())) {
		if (obj instanceof Projectile) {
		    // removeQueue.add(obj);
		}
	    }
	} else if (y_movement != 0) {
	    // if we only have vertical movement, we can just use a step of 1
	    if (y_movement > 0)
		step_y = 1;
	    else
		step_y = -1;
	    if (checkCollision(obj, level.getTiles())) {
		if (obj instanceof Projectile) {
		    // removeQueue.add(obj);
		}
	    }

	}

	// and then do little steps until we are done moving
	while (x_movement != 0 || y_movement != 0) {

	    // we first move in the x direction
	    if (x_movement != 0) {
		// when we do a step, we have to update the amount we have to
		// move after this
		if ((x_movement > 0 && x_movement < step_x) || (x_movement > step_x && x_movement < 0)) {
		    step_x = x_movement;
		    x_movement = 0;
		} else
		    x_movement -= step_x;

		// then we move the object one step
		obj.setX(obj.getX() + step_x);

		// if we collide with any of the bounding shapes of the tiles we
		// have to revert to our original position
		if (checkCollision(obj, level.getTiles())) {
		    if (obj instanceof Projectile && !(obj instanceof Explosion)) {
			removeQueue.add(obj);
		    }
		    obj.setX(obj.getX() - step_x);
		    obj.setXVelocity(0);
		    x_movement = 0;
		}
		if (checkCollision(obj, level.getLimite())) {
		    if (obj instanceof Player)
			level.getPlayer().setLife(0);
		    if (obj instanceof Projectile)
			removeQueue.add(obj);
		    if (obj instanceof Ennemie)
			removeQueue.add(obj);
		}
		if (checkCollision(obj, level.getEnd())) {
		    if (obj instanceof Player)
			isFinished = true;
		}

	    }
	    // same thing for the vertical, or y movement
	    if (y_movement != 0) {
		if ((y_movement > 0 && y_movement < step_y) || (y_movement > step_y && y_movement < 0)) {
		    step_y = y_movement;
		    y_movement = 0;
		} else
		    y_movement -= step_y;

		obj.setY(obj.getY() + step_y);

		if (checkCollision(obj, level.getTiles())) {
		    if (obj instanceof Projectile && !(obj instanceof Explosion)) {
			removeQueue.add(obj);
		    }
		    obj.setY(obj.getY() - step_y);
		    obj.setYVelocity(0);
		    y_movement = 0;
		    break;
		}
		if (checkCollision(obj, level.getLimite())) {
		    if (obj instanceof Player)
			level.getPlayer().setLife(0);
		    if (obj instanceof Projectile)
			removeQueue.add(obj);
		    if (obj instanceof Ennemie)
			removeQueue.add(obj);
		}
		if (checkCollision(obj, level.getEnd())) {
		    if (obj instanceof Player)
			isFinished = true;
		}
	    }
	}

	if (obj instanceof Projectile && !(obj instanceof Explosion)) {
	    // if (obj instanceof Grenade)
	    // addQueue.add(new Explosion(obj.getX() - 100, obj.getY() - 100));
	    // lance flamme si décommenté
	    if (obj.isOnGround() == true) {
		removeQueue.add(obj);
		if (obj instanceof Grenade) {
		    addQueue.add(new Explosion(obj.getX() - 100, obj.getY() - 100));
		    needShake = true;
		}
	    }
	    if (obj.getYVelocity() == 0 || obj.getXVelocity() == 0) {
		removeQueue.add(obj);
		if (obj instanceof Grenade) {
		    addQueue.add(new Explosion(obj.getX() - 100, obj.getY() - 100));
		    needShake = true;
		}
	    }
	}

	if (obj instanceof Explosion) {
	    ((Explosion) obj).setTime2(System.currentTimeMillis());
	    if (((Explosion) obj).getTime2() - ((Explosion) obj).getTime1() > 540) {
		removeQueue.add(obj);
	    } else if (((Explosion) obj).getTime2() - ((Explosion) obj).getTime1() > 10) {
		((Explosion) obj).setDamage(0);
	    }
	}

	if (obj instanceof MuzzleFlash) {
	    ((MuzzleFlash) obj).setTime2(System.currentTimeMillis());
	    if (((MuzzleFlash) obj).getTime2() - ((MuzzleFlash) obj).getTime1() > 40) {
		removeQueue.add(obj);
	    }
	}

    }

    private boolean checkCollision(LevelObject obj, Tile[][] mapTiles) {
	// get only the tiles that matter
	ArrayList<Tile> tiles = obj.getBoundingShape().getTilesOccupying(mapTiles);
	for (Tile t : tiles) {
	    // if this tile has a bounding shape
	    if (t.getBoundingShape() != null) {
		if (t.getBoundingShape().checkCollision(obj.getBoundingShape(), map)) {
		    return true;
		}
	    }
	}
	return false;
    }

    private boolean isOnGroud(LevelObject obj, Tile[][] mapTiles) {
	// we get the tiles that are directly "underneath" the characters, also
	// known as the ground tiles
	ArrayList<Tile> tiles = obj.getBoundingShape().getGroundTiles(mapTiles);
	// we lower the bounding object a bit so we can check if we are
	// actually a bit above the ground
	obj.getBoundingShape().movePosition(0, 1);

	for (Tile t : tiles) {
	    // not every tile has a bounding shape (air tiles for example)
	    if (t.getBoundingShape() != null) {
		// if the ground and the lowered object collide, then we are on
		// the ground
		if (t.getBoundingShape().checkCollision(obj.getBoundingShape(), map)) {
		    // don't forget to move the object back up even if we are on
		    // the ground!
		    obj.getBoundingShape().movePosition(0, -1);
		    return true;
		}
	    }
	}

	// and obviously we have to move the object back up if we don't hit the
	// ground
	obj.getBoundingShape().movePosition(0, -1);

	return false;
    }
}