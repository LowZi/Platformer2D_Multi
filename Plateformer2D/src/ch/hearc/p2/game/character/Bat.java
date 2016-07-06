package ch.hearc.p2.game.character;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import ch.hearc.p2.game.physics.AABoundingRect;
import ch.hearc.p2.game.projectile.ProjectileAbeille;

public class Bat extends Ennemie {

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/
    
    public Bat(float x, float y) throws SlickException {

	super(x, y);
	dead = false;

	sprites = setSprite(new Image("ressources/character/ennemi/bat.png"),
		sprites);
	movingAnimations = setMovingAnimation(
		new Image[] { new Image("ressources/character/ennemi/bat.png"),
			new Image("ressources/character/ennemi/bat_fly.png") },
		100, movingAnimations);

	Image bee = new Image("ressources/character/ennemi/bat_hit.png");

	hitedSprites = setSprite(bee, hitedSprites);
	hitedMovingAnimations = setMovingAnimation(new Image[] { bee, bee }, 80, hitedMovingAnimations);

	boundingShape = new AABoundingRect(x, y, 79, 42); //Moyenne entre les deux sprites de l'animation
	deadPicture = new Image("ressources/character/ennemi/bat_dead.png");
	accelerationSpeed = 0.004f;
	maximumSpeed = 0.7f;
	maximumFallSpeed = 0.0f;
	decelerationSpeed = 0.001f;
	life = 6;
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		 	  	*|
    \*------------------------------------------------------------------*/
    
    @Override
    public void updateBoundingShape() {
	boundingShape.updatePosition(x , y);
    }

    @Override
    public void shoot() throws SlickException {
	float randomX = rand.nextInt((1 - (-1)) + 1) + (-1);
	float randomY = rand.nextInt((1 - (-1)) + 1) + (-1);

	toAddList.add(new ProjectileAbeille(x + 10, y + 10, randomX, randomY));
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
    /*------------------------------*\
    |*		    Set	   	    *|
    \*------------------------------*/

    @Override
    public void setMaximumFallSpeed(float maximumFallSpeed) {
	this.maximumFallSpeed = maximumFallSpeed;
    }

}