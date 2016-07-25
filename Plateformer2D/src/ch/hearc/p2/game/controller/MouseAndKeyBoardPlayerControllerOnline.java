package ch.hearc.p2.game.controller;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import ch.hearc.p2.game.WindowGame;
import ch.hearc.p2.game.character.PlayerOnline;
import ch.hearc.p2.game.enums.Team;
import ch.hearc.p2.game.level.LevelOnline;
import ch.hearc.p2.game.level.tile.Tile;

public class MouseAndKeyBoardPlayerControllerOnline extends PlayerControllerOnline {

    private long time1;
    private long time2;

    private Robot robot;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/

    public MouseAndKeyBoardPlayerControllerOnline(PlayerOnline player, LevelOnline level) {
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
	if (i.isKeyPressed(Input.KEY_H)) {
	    ArrayList<Tile> spawns;
	    if (player.getTeam() == Team.BLUE)
		spawns = level.getBlueSpawn();
	    else
		spawns = level.getRedSpawn();

	    Random rand = new Random();

	    int randomNum = rand.nextInt((spawns.size()));

	    Tile tile = spawns.get(randomNum);
	    player.setX(tile.getX() * 70);
	    player.setY(tile.getY() * 70);

	    // Debug
	    System.out.println("Player at after pressing H: ");
	    System.out.println(player.getX() + "");
	    System.out.println(player.getY() + "");
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
