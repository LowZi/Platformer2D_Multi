package ch.hearc.p2.game.character;

import java.util.HashMap;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import ch.hearc.p2.game.enums.Facing;
import ch.hearc.p2.game.level.LevelObject;

public abstract class Character extends LevelObject {

    protected int life;

    protected Facing facing;

    protected Image deadPicture;

    protected boolean moving = false;
    protected boolean dead;
    protected boolean hited;

    protected HashMap<Facing, Image> sprites;
    protected HashMap<Facing, Animation> movingAnimations;
    protected HashMap<Facing, Image> hitedSprites;
    protected HashMap<Facing, Animation> hitedMovingAnimations;

    protected List<LevelObject> toAddList;

    protected float accelerationSpeed = 1;
    protected float decelerationSpeed = 1;
    protected float maximumSpeed = 1;

    protected long time1;
    protected long time2;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/

    public Character(float x, float y) throws SlickException {
	super(x, y);
	time1 = System.currentTimeMillis();
	time2 = System.currentTimeMillis();
	dead = false;
	hited = false;

	sprites = setSprite(new Image("ressources/character/ennemi/bee.png"), sprites);
	deadPicture = new Image("ressources/character/ennemi/bee.png");
	hitedSprites = setSprite(new Image("ressources/character/ennemi/bee_hit.png"), hitedSprites);

	facing = Facing.RIGHT;
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Protected		    	*|
    \*------------------------------------------------------------------*/

    protected HashMap<Facing, Animation> setMovingAnimation(Image[] images, int frameDuration,
	    HashMap<Facing, Animation> movingAnim) {
	movingAnim = new HashMap<Facing, Animation>();

	movingAnim.put(Facing.RIGHT, new Animation(images, frameDuration));

	Animation facingLeftAnimation = new Animation();
	for (Image i : images) {
	    facingLeftAnimation.addFrame(i.getFlippedCopy(true, false), frameDuration);
	}
	movingAnim.put(Facing.LEFT, facingLeftAnimation);
	return movingAnim;

    }

    protected HashMap<Facing, Image> setSprite(Image i, HashMap<Facing, Image> sprite) {
	sprite = new HashMap<Facing, Image>();
	sprite.put(Facing.RIGHT, i);
	sprite.put(Facing.LEFT, i.getFlippedCopy(true, false));
	return sprite;
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		 	  	*|
    \*------------------------------------------------------------------*/

    public List<LevelObject> getToAddList() {
	return toAddList;
    }

    public void clearToAddList() {
	toAddList.clear();
    }

    public void damage(int value) {
	this.life -= value;
    }

    // move towards x_velocity = 0
    public void decelerate(int delta) {
	if (x_velocity > 0) {
	    x_velocity -= decelerationSpeed * delta;
	    if (x_velocity < 0)
		x_velocity = 0;
	} else if (x_velocity < 0) {
	    x_velocity += decelerationSpeed * delta;
	    if (x_velocity > 0)
		x_velocity = 0;
	}
    }

    public void jump() {
	if (onGround)
	    y_velocity = -1f;
    }

    public void moveLeft(int delta) {
	// if we aren't already moving at maximum speed
	if (x_velocity > -maximumSpeed) {
	    // accelerate
	    x_velocity -= accelerationSpeed * delta;
	    if (x_velocity < -maximumSpeed) {
		// and if we exceed maximum speed, set it to maximum speed
		x_velocity = -maximumSpeed;
	    }
	}
	moving = true;
	facing = Facing.LEFT;
    }

    public void moveRight(int delta) {
	if (x_velocity < maximumSpeed) {
	    x_velocity += accelerationSpeed * delta;
	    if (x_velocity > maximumSpeed) {
		x_velocity = maximumSpeed;
	    }
	}
	moving = true;
	facing = Facing.RIGHT;
    }

    public void render(int offset_x, int offset_y) {

	time1 = System.currentTimeMillis();

	if (dead == false && hited == false) {
	    if (movingAnimations != null && moving) {
		movingAnimations.get(facing).draw(x - offset_x, y - offset_y);
	    } else {
		sprites.get(facing).draw(x - offset_x, y - offset_y);
	    }
	} else if (hited == true && dead == false) {

	    if (hitedMovingAnimations != null && moving) {
		hitedMovingAnimations.get(facing).draw(x - offset_x, y - offset_y);
	    } else {
		hitedSprites.get(facing).draw(x - offset_x, y - offset_y);
	    }
	    if (time1 - time2 > 50)
		hited = false;
	} else
	    deadPicture.draw(x - offset_x, y - offset_y);

    }

    public void hit() {
	time2 = System.currentTimeMillis();
	hited = true;
    }

    /*------------------------------*\
    |*		    Set	   	    *|
    \*------------------------------*/

    public void setMoving(boolean b) {
	moving = b;
    }

    public void setLife(int life) {
	this.life = life;
    }

    /*------------------------------*\
    |*		    Get	   	    *|
    \*------------------------------*/

    public int getLife() {
	return life;
    }

    /*------------------------------*\
    |*		    Is	   	    *|
    \*------------------------------*/

    public boolean isMoving() {
	return moving;
    }

}
