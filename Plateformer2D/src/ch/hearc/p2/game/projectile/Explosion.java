package ch.hearc.p2.game.projectile;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import ch.hearc.p2.game.physics.AABoundingRect;

public class Explosion extends Projectile {
    
    protected long time1;
    protected long time2;
    
    protected Animation animation;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			    	*|
    \*------------------------------------------------------------------*/

    public Explosion(float x, float y) throws SlickException {
	super(x, y);
	this.x_velocity = 0;
	this.y_velocity = 0;
	time1 = System.currentTimeMillis();
	time2 = System.currentTimeMillis();
	damage = 10;

	Image img00 = new Image("ressources/tiles/item/regularExplosion00.png");
	Image img01 = new Image("ressources/tiles/item/regularExplosion01.png");
	Image img02 = new Image("ressources/tiles/item/regularExplosion02.png");
	Image img03 = new Image("ressources/tiles/item/regularExplosion03.png");
	Image img04 = new Image("ressources/tiles/item/regularExplosion04.png");
	Image img05 = new Image("ressources/tiles/item/regularExplosion05.png");
	Image img06 = new Image("ressources/tiles/item/regularExplosion06.png");
	Image img07 = new Image("ressources/tiles/item/regularExplosion07.png");
	Image img08 = new Image("ressources/tiles/item/regularExplosion08.png");

	img00 = img00.getScaledCopy(1.5f);
	img01 = img01.getScaledCopy(1.5f);
	img02 = img02.getScaledCopy(1.5f);
	img03 = img03.getScaledCopy(1.5f);
	img04 = img04.getScaledCopy(1.5f);
	img05 = img05.getScaledCopy(1.5f);
	img06 = img06.getScaledCopy(1.5f);
	img07 = img07.getScaledCopy(1.5f);
	img08 = img08.getScaledCopy(1.5f);

	animation = new Animation(new Image[] { img00, img01, img02, img03, img04, img05, img06, img07, img08 }, 60,
		true);

	boundingShape = new AABoundingRect(x, y, 200, 200);
	maximumFallSpeed = 0;
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		 	    	*|
    \*------------------------------------------------------------------*/

    @Override
    public void render(float offset_x, float offset_y) {
	animation.draw(x - offset_x, y - offset_y);
    }

    /*------------------------------*\
    |*		Set		    *|
    \*------------------------------*/

    public void setTime2(long time) {
	time2 = time;
    }

    /*------------------------------*\
    |*		Get		    *|
    \*------------------------------*/

    public long getTime1() {
	return time1;
    }

    public long getTime2() {
	return time2;
    }
}
