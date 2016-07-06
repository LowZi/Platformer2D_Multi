package ch.hearc.p2.game.state;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import ch.hearc.p2.game.character.Bat;
import ch.hearc.p2.game.character.Ghost;
import ch.hearc.p2.game.character.Player;
import ch.hearc.p2.game.character.Spider;
import ch.hearc.p2.game.level.object.Coin;
import ch.hearc.p2.game.level.object.Key;

public class Level6 extends LevelState {

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/

    public Level6(String startingLevel) {
	super(startingLevel);
	ID = 106;
	this.startinglevel = startingLevel;
	nextLevel = 0; // Si c'est le dernier level, 0 pour l'instant

    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		    		*|
    \*------------------------------------------------------------------*/

    @Override
    public void instanciation() throws SlickException {
	// ID for the next state (0 is main menu, ID+1 is the next level)

	player = new Player(2 * 70, 22 * 70);

	// Remplis ennemis
	ennemies.add(new Bat(3 * 70, 15 * 70));
	ennemies.add(new Ghost(10 * 70, 12 * 70));
	ennemies.add(new Bat(15 * 70, 10 * 70));
	ennemies.add(new Ghost(31 * 70, 13 * 70));
	ennemies.add(new Bat(46 * 70, 14 * 70));
	ennemies.add(new Ghost(56 * 70, 6 * 70));
	ennemies.add(new Bat(44 * 70, 3 * 70));
	ennemies.add(new Ghost(21 * 70, 3 * 70));
	ennemies.add(new Bat(4 * 70, 8 * 70));
	ennemies.add(new Spider(20 * 70, 23 * 70));
	ennemies.add(new Spider(30 * 70, 23 * 70));
	ennemies.add(new Spider(40 * 70, 23 * 70));
	ennemies.add(new Spider(50 * 70, 23 * 70));

	// Remplis Objectifs
	objectives.add(new Coin(12 * 70, 20 * 70));
	objectives.add(new Coin(31 * 70, 16 * 70));
	objectives.add(new Coin(54 * 70, 11 * 70));
	objectives.add(new Coin(29 * 70, 4 * 70));
	objectives.add(new Key(15 * 70, 22 * 70));

	// setup music (si rien, on garde la même musique qu'au niveau d'avant)
	musiclvl = new Music("ressources/audio/music/lvl6.ogg");

	player.setWeapon(2);

	initialisationSuite();

    }

}