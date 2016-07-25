
package ch.hearc.p2.game.menu;

import org.newdawn.slick.Font;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.TextField;

import ch.hearc.p2.game.WindowGame;

public class TextFieldPlateformer extends TextField {

    /*------------------------------------------------------------------*\
    |*			Constructeurs					*|
    \*------------------------------------------------------------------*/

    public TextFieldPlateformer(GUIContext container, Font font, int x, int y, int width, int height) {
	super(container, font, x, y, width, height);
	// TODO Auto-generated constructor stub
    }

    /*------------------------------------------------------------------*\
    |*			Methodes Public					*|
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
