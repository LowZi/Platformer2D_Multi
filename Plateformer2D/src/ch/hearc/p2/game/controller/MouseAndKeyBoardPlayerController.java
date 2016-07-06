package ch.hearc.p2.game.controller;

import java.awt.AWTException;
import java.awt.Robot;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import ch.hearc.p2.game.WindowGame;
import ch.hearc.p2.game.character.Player;
import ch.hearc.p2.game.level.Level;

public class MouseAndKeyBoardPlayerController extends PlayerController {

    private long time1;
    private long time2;
    
    private Robot robot;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/

    public MouseAndKeyBoardPlayerController(Player player, Level level) {
	super(player, level);
	time1 = System.currentTimeMillis();
	time2 = System.currentTimeMillis();
	try {
	    robot = new Robot();
	} catch (AWTException e) {
	    e.printStackTrace();
	}
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		 	  	*|
    \*------------------------------------------------------------------*/

    @Override
    public void handleInput(Input i, int delta) {
	// handle any input from the keyboard
	time1 = System.currentTimeMillis();

	handleKeyboardInput(i, delta);

	try {
	    handleMouseInput(i);
	    handleControllerInput(i, delta);
	} catch (SlickException e) {
	    e.printStackTrace();
	}

    }

    /*------------------------------------------------------------------*\
    |*				Methodes Private	 	  	*|
    \*------------------------------------------------------------------*/

    private void handleKeyboardInput(Input i, int delta) {
	// we can both use the WASD or arrow keys to move around, obviously we
	// can't move both left and right simultaneously
	if (i.isKeyDown(Input.KEY_A) || i.isKeyDown(Input.KEY_LEFT)) {
	    player.moveLeft(delta);
	} else if (i.isKeyDown(Input.KEY_D) || i.isKeyDown(Input.KEY_RIGHT)) {
	    player.moveRight(delta);
	} else {
	    // we dont move if we don't press left or right, this will have the
	    // effect that our player decelerates
	    player.setMoving(false);
	}
	if (i.isKeyDown(Input.KEY_SPACE)) {
	    player.jump();
	}
    }

    private void handleMouseInput(Input i) throws SlickException {

	if (i.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && time1 - time2 > player.getWeapon().getCadence()) {

	    int mouseWorldX = (int) (level.getXOffset() + (i.getMouseX() * (1 / WindowGame.SCALE_W)));
	    int mouseWorldY = (int) (level.getYOffset() + (i.getMouseY() * (1 / WindowGame.SCALE_H)));
	    player.shoot(mouseWorldX, mouseWorldY);
	    time2 = System.currentTimeMillis();
	}
    }

    private void handleControllerInput(Input i, int delta) throws SlickException {
	try {
	    int controlleur = i.getControllerCount() - 1;

	    // Work
	    if (i.isControllerLeft(Input.ANY_CONTROLLER)) {
		player.moveLeft(delta);
	    } else if (i.isControllerRight(Input.ANY_CONTROLLER)) {
		player.moveRight(delta);
	    }
	    // Work
	    if (i.isButtonPressed(4, controlleur)) {
		player.jump();
	    }

	    if (i.isButtonPressed(5, controlleur) && time1 - time2 > player.getWeapon().getCadence()) {
		int mouseWorldX = level.getXOffset() + i.getMouseX() - 64; // Ok
		int mouseWorldY = level.getYOffset() + i.getMouseY() - 95; // Ok
		player.shoot(mouseWorldX, mouseWorldY);
		time2 = System.currentTimeMillis();
	    }

	    if (i.getAxisValue(controlleur, 3) != 0 || i.getAxisValue(controlleur, 2) != 0) {

		int y = (int) (i.getAxisValue(controlleur, 2) * delta * 2);
		int x = (int) ((i.getAxisValue(controlleur, 3)) * delta * 2);

		robot.mouseMove(i.getMouseX() + x, i.getMouseY() + y);

	    }
	} catch (Exception ex) {
	}
    }
}
