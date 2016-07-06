package ch.hearc.p2.game.level;

import ch.hearc.p2.game.physics.AABoundingRect;
import ch.hearc.p2.game.physics.BoundingShape;

public abstract class LevelObject {

    protected BoundingShape boundingShape;

    protected float x_velocity = 0;
    protected float y_velocity = 0;
    protected float x;
    protected float y;
    protected float maximumFallSpeed = 1;

    protected boolean onGround = true;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			        *|
    \*------------------------------------------------------------------*/

    public LevelObject(float x, float y) {
	this.x = x;
	this.y = y;

	// default bounding shape is a 70 by 70 box
	boundingShape = new AABoundingRect(x, y, 70, 70);
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		    		*|
    \*------------------------------------------------------------------*/

    public void applyGravity(float gravity) {
	// if we aren't already moving at maximum speed
	if (y_velocity < maximumFallSpeed) {
	    // accelerate
	    y_velocity += gravity;
	    if (y_velocity > maximumFallSpeed) {
		// and if we exceed maximum speed, set it to maximum speed
		y_velocity = maximumFallSpeed;
	    }
	}
    }

    public void updateBoundingShape() {
	boundingShape.updatePosition(x, y);
    }

    @SuppressWarnings("unused")
    public void render(float offset_x, float offset_y) {

    }

    /*-----------------------*\
    |*		Get	     *|
    \*-----------------------*/

    public float getYVelocity() {
	return y_velocity;
    }

    public float getXVelocity() {
	return x_velocity;
    }

    public float getX() {
	return x;
    }

    public float getY() {
	return y;
    }

    public BoundingShape getBoundingShape() {
	return boundingShape;
    }

    /*-----------------------*\
    |*		Set	     *|
    \*-----------------------*/

    public void setXVelocity(float f) {
	x_velocity = f;
    }

    public void setYVelocity(float f) {
	y_velocity = f;
    }

    public void setX(float f) {
	x = f;
	updateBoundingShape();
    }

    public void setY(float f) {
	y = f;
	updateBoundingShape();
    }

    public void setOnGround(boolean b) {
	onGround = b;
    }

    /*-----------------------*\
    |*		Is	     *|
    \*-----------------------*/

    public boolean isOnGround() {
	return onGround;
    }

}
