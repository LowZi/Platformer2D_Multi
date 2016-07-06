package ch.hearc.p2.game.character;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import ch.hearc.p2.game.physics.AABoundingRect;

public class Spider extends Ennemie {

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/

    public Spider(float x, float y) throws SlickException {

	super(x, y);
	dead = false;
	// setSprite(new Image("ressources/sprites/p2_walk01.png"));

	sprites = setSprite(new Image("ressources/character/ennemi/spider.png"), sprites);
	movingAnimations = setMovingAnimation(new Image[] { new Image("ressources/character/ennemi/spider_walk1.png"),
		new Image("ressources/character/ennemi/spider_walk2.png") }, 80, movingAnimations);

	Image spider_hit = new Image("ressources/character/ennemi/spider_hit.png");

	hitedSprites = setSprite(spider_hit, hitedSprites);
	hitedMovingAnimations = setMovingAnimation(new Image[] { spider_hit, spider_hit }, 80, hitedMovingAnimations);

	boundingShape = new AABoundingRect(x, y, 56, 48);
	deadPicture = new Image("ressources/character/ennemi/spider_dead.png");
	accelerationSpeed = 0.008f;
	maximumSpeed = 0.8f;
	decelerationSpeed = 0.008f;
	life = 5;
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		 	  	*|
    \*------------------------------------------------------------------*/

    @Override
    public void updateBoundingShape() {
	boundingShape.updatePosition(x, y);
    }

    @Override
    public void moveRandom() {
	float randomNum = rand.nextInt(50 + 1);
	float randomWay = rand.nextInt((1 - (-1)) + 1) + (-1);
	if (randomWay < 1) {
	    moveLeft((int) randomNum);
	} else {
	    moveRight((int) randomNum);
	}

    }

}