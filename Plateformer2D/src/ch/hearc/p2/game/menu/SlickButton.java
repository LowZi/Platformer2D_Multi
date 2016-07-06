
package ch.hearc.p2.game.menu;

import org.newdawn.slick.Image;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;

import ch.hearc.p2.game.WindowGame;

public class SlickButton extends MouseOverArea {

    /*------------------------------------------------------------------*\
    |*				Constructeurs			    	*|
    \*------------------------------------------------------------------*/

    public SlickButton(GUIContext container, Image image, int x, int y, int width, int height,
	    ComponentListener listener) {
	super(container, image, x, y, width, height, listener);
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		 	    	*|
    \*------------------------------------------------------------------*/

    /*
     * We override these methods for apply the correct scale on the cursor
     * coordonates
     */

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
	oldx *= 1 / WindowGame.SCALE_W;
	oldy *= 1 / WindowGame.SCALE_H;
	newx *= 1 / WindowGame.SCALE_W;
	newy *= 1 / WindowGame.SCALE_H;
	super.mouseMoved(oldx, oldy, newx, newy);
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
	oldx *= 1 / WindowGame.SCALE_W;
	oldy *= 1 / WindowGame.SCALE_H;
	newx *= 1 / WindowGame.SCALE_W;
	newy *= 1 / WindowGame.SCALE_H;
	super.mouseDragged(oldx, oldy, newx, newy);
    }

    @Override
    public void mousePressed(int button, int mx, int my) {
	mx *= 1 / WindowGame.SCALE_W;
	my *= 1 / WindowGame.SCALE_H;
	super.mousePressed(button, mx, my);
    }

    @Override
    public void mouseReleased(int button, int mx, int my) {
	mx *= 1 / WindowGame.SCALE_W;
	my *= 1 / WindowGame.SCALE_H;
	super.mouseReleased(button, mx, my);
    }
}
