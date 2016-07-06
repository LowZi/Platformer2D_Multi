package ch.hearc.p2.game.state;

import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import ch.hearc.p2.game.character.Ghost;
import ch.hearc.p2.game.character.Player;
import ch.hearc.p2.game.character.Spider;
import ch.hearc.p2.game.character.VoidFlyer;
import ch.hearc.p2.game.level.object.Coin;
import ch.hearc.p2.game.level.object.Key;

public class Level4 extends LevelState {

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/
    
    public Level4(String startingLevel) {
	super(startingLevel);
	ID = 104;
	this.startinglevel = startingLevel;
	nextLevel = 105; // Si c'est le dernier level, 0 pour l'instant

    }
    
    /*------------------------------------------------------------------*\
    |*				Methodes Public		    		*|
    \*------------------------------------------------------------------*/

    @Override
    public void instanciation() throws SlickException {
	// ID for the next state (0 is main menu, ID+1 is the next level)

	player = new Player(3 * 70, 15 * 70);

	// Remplis ennemis
	ennemies.add(new Ghost(16 * 70, 13 * 70));
	ennemies.add(new Ghost(10 * 70, 9 * 70));
	ennemies.add(new VoidFlyer(38 * 70, 15 * 70));
	ennemies.add(new VoidFlyer(42 * 70, 9 * 70));
	ennemies.add(new VoidFlyer(12 * 70, 2 * 70));
	ennemies.add(new VoidFlyer(33 * 70, 5 * 70));
	ennemies.add(new Spider(34 * 70, 18 * 70));

	// Remplis Objectifs
	objectives.add(new Coin(1 * 70, 16 * 70));
	objectives.add(new Coin(39 * 70, 16 * 70));
	objectives.add(new Coin(35 * 70, 7 * 70));
	objectives.add(new Coin(48 * 70, 8 * 70));
	objectives.add(new Key(41 * 70, 11 * 70));

	// setup music (si rien, on garde la même musique qu'au niveau d'avant)
	musiclvl = new Music("ressources/audio/music/lvl4.ogg");

	player.setWeapon(0);
	
	initialisationSuite();
	
	level.setBackground(new Image("ressources/background/forest.png"));

    }

}