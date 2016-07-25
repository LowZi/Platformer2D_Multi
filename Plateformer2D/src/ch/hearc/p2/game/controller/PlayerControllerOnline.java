package ch.hearc.p2.game.controller;

import org.newdawn.slick.Input;

import ch.hearc.p2.game.character.PlayerOnline;
import ch.hearc.p2.game.level.LevelOnline;

public abstract class PlayerControllerOnline {

    protected PlayerOnline player;

    protected LevelOnline level;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			    *|
    \*------------------------------------------------------------------*/

    public PlayerControllerOnline(PlayerOnline player, LevelOnline level) {
	this.player = player;
	this.level = level;
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Abstract		    *|
    \*------------------------------------------------------------------*/

    public abstract void handleInput(Input i, int delta);

}
