package ch.hearc.p2.game.weapon;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import ch.hearc.p2.game.level.LevelObject;
import ch.hearc.p2.game.physics.AABoundingRect;

public class MuzzleFlash extends LevelObject {

    private Animation animation;
    
    protected long time1;
    protected long time2;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/

    public MuzzleFlash(float x, float y) throws SlickException {
	super(x, y);
	x_velocity = 0;
	y_velocity = 0;
	maximumFallSpeed = 0;
	time1 = System.currentTimeMillis();
	time2 = System.currentTimeMillis();

	Image img00 = new Image("ressources/tiles/item/flash00.png");
	Image img01 = new Image("ressources/tiles/item/flash01.png");
	Image img02 = new Image("ressources/tiles/item/flash02.png");
	Image img03 = new Image("ressources/tiles/item/flash03.png");
	Image img04 = new Image("ressources/tiles/item/flash04.png");
	Image img05 = new Image("ressources/tiles/item/flash05.png");
	Image img06 = new Image("ressources/tiles/item/flash06.png");
	Image img07 = new Image("ressources/tiles/item/flash07.png");
	Image img08 = new Image("ressources/tiles/item/flash08.png");

	img00 = img00.getScaledCopy(0.1f);
	img01 = img01.getScaledCopy(0.1f);
	img02 = img02.getScaledCopy(0.1f);
	img03 = img03.getScaledCopy(0.1f);
	img04 = img04.getScaledCopy(0.1f);
	img05 = img05.getScaledCopy(0.1f);
	img06 = img06.getScaledCopy(0.1f);
	img07 = img07.getScaledCopy(0.1f);
	img08 = img08.getScaledCopy(0.1f);

	animation = new Animation(new Image[] { img00, img01, img02, img03, img04, img05, img06, img07, img08 }, 10,
		true);

	boundingShape = new AABoundingRect(x, y, 200, 200);
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		    		*|
    \*------------------------------------------------------------------*/

    @Override
    public void render(float offset_x, float offset_y) {
	animation.draw(x - offset_x, y - offset_y);
    }

    /*-----------------------*\
    |*		Set	     *|
    \*-----------------------*/

    public void setTime2(long time) {
	time2 = time;
    }

    /*-----------------------*\
    |*		Get	     *|
    \*-----------------------*/

    public long getTime1() {
	return time1;
    }

    public long getTime2() {
	return time2;
    }

}
