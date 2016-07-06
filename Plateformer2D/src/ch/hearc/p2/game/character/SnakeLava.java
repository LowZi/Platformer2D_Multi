package ch.hearc.p2.game.character;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import ch.hearc.p2.game.physics.AABoundingRect;

public class SnakeLava extends Ennemie {

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/
    
    public SnakeLava(float x, float y) throws SlickException {

	super(x, y);
	dead = false;
	// setSprite(new Image("ressources/sprites/p2_walk01.png"));

	sprites = setSprite(new Image("ressources/character/ennemi/snakeLava.png"), sprites);
	movingAnimations = setMovingAnimation(new Image[] { new Image("ressources/character/ennemi/snakeLava_ani.png"),
		new Image("ressources/character/ennemi/snakeLava.png") }, 200, movingAnimations);

	Image snakeLava_hit = new Image("ressources/character/ennemi/snakeLava_hit.png");

	hitedSprites = setSprite(snakeLava_hit, hitedSprites);
	hitedMovingAnimations = setMovingAnimation(new Image[] { snakeLava_hit, snakeLava_hit }, 80,
		hitedMovingAnimations);

	boundingShape = new AABoundingRect(x, y, 50, 145);
	deadPicture = new Image("ressources/character/ennemi/snakeLava_dead.png");

	life = 5;
	setMoving(true);
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		 	  	*|
    \*------------------------------------------------------------------*/
    
    @Override
    public void updateBoundingShape() {
	boundingShape.updatePosition(x, y);
    }
}