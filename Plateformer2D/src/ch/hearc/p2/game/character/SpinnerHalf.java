package ch.hearc.p2.game.character;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import ch.hearc.p2.game.physics.AABoundingRect;

public class SpinnerHalf extends Ennemie {

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/
    
    public SpinnerHalf(float x, float y) throws SlickException {

	super(x, y);
	dead = false;
	// setSprite(new Image("ressources/sprites/p2_walk01.png"));

	sprites = setSprite(new Image("ressources/character/ennemi/spinnerHalf.png"), sprites);
	movingAnimations = setMovingAnimation(new Image[] { new Image("ressources/character/ennemi/spinnerHalf.png"),
		new Image("ressources/character/ennemi/spinnerHalf_spin.png") }, 200, movingAnimations);

	Image spinner_hit = new Image("ressources/character/ennemi/spinnerHalf.png");

	hitedSprites = setSprite(spinner_hit, hitedSprites);
	hitedMovingAnimations = setMovingAnimation(new Image[] { spinner_hit, spinner_hit }, 80, hitedMovingAnimations);

	boundingShape = new AABoundingRect(x, y, 63, 30);
	deadPicture = new Image("ressources/character/ennemi/spinnerHalf_dead.png");

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
    
    @Override
    public void damage(int value) {
	//Insensible aux damages
    }
}