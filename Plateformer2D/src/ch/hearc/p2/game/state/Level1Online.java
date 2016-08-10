package ch.hearc.p2.game.state;

import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import ch.hearc.p2.game.character.PlayerOnline;
import ch.hearc.p2.game.enums.Team;
import ch.hearc.p2.game.level.object.Case;
import ch.hearc.p2.game.level.object.Coin;

public class Level1Online extends LevelStateOnline {

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/

    public Level1Online(String startingLevel) {
	super(startingLevel);
	ID = 701;
	this.startinglevel = startingLevel;

    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		    		*|
    \*------------------------------------------------------------------*/

    @Override
    public void instanciation() throws SlickException {
	// at the start of the game we don't have a player yet
	player = new PlayerOnline(20 * 70, 16 * 70, Team.RED, "");

	// setup music
	musiclvl = new Music("ressources/audio/music/lvl1.ogg");

	// set weapon
	player.setWeapon(0);

	// instanciation du level etc
	initialisationSuite();

	// choix du background
	level.setBackground(new Image("ressources/background/desert.png"));
    }
}