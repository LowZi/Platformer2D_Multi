package ch.hearc.p2.game.character;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import ch.hearc.p2.game.physics.AABoundingRect;
import ch.hearc.p2.game.projectile.ProjectileAbeille;

public class Ghost extends Ennemie {

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/
    
    public Ghost(float x, float y) throws SlickException {

	super(x, y);
	dead = false;

	sprites = setSprite(new Image("ressources/character/ennemi/ghost_normal.png"), sprites);
	movingAnimations = setMovingAnimation(new Image[] { new Image("ressources/character/ennemi/ghost_normal.png"),
		new Image("ressources/character/ennemi/ghost.png") }, 600, movingAnimations);

	Image ghost = new Image("ressources/character/ennemi/ghost_hit.png");

	hitedSprites = setSprite(ghost, hitedSprites);
	hitedMovingAnimations = setMovingAnimation(new Image[] { ghost, ghost }, 80, hitedMovingAnimations);

	boundingShape = new AABoundingRect(x, y, 51, 73);
	deadPicture = new Image("ressources/character/ennemi/ghost_dead.png");
	accelerationSpeed = 0.003f;
	maximumSpeed = 0.6f;
	maximumFallSpeed = 0.0f;
	decelerationSpeed = 0.001f;
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

}