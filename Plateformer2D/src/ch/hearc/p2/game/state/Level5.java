package ch.hearc.p2.game.state;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import ch.hearc.p2.game.character.Ghost;
import ch.hearc.p2.game.character.Player;
import ch.hearc.p2.game.character.SnakeLava;
import ch.hearc.p2.game.character.Spider;
import ch.hearc.p2.game.character.SpinnerHalf;
import ch.hearc.p2.game.character.VoidFlyer;
import ch.hearc.p2.game.level.object.Coin;
import ch.hearc.p2.game.level.object.Key;

public class Level5 extends LevelState {

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/
    
    public Level5(String startingLevel) {
	super(startingLevel);
	ID = 105;
	this.startinglevel = startingLevel;
	nextLevel = 106; // Si c'est le dernier level, 0 pour l'instant

    }
    
    /*------------------------------------------------------------------*\
    |*				Methodes Public		    		*|
    \*------------------------------------------------------------------*/
    
    @Override
    public void instanciation() throws SlickException {
	// ID for the next state (0 is main menu, ID+1 is the next level)

	player = new Player(2 * 70, 2 * 70);

	// Remplis ennemis
	ennemies.add(new SpinnerHalf(7 * 70, 4 * 70));
	ennemies.add(new SpinnerHalf(9 * 70, 4 * 70));
	ennemies.add(new Spider(34 * 70, 18 * 70));
	ennemies.add(new SnakeLava(37 * 70, 16 * 70));
	ennemies.add(new VoidFlyer(34 * 70, 2 * 70));
	ennemies.add(new VoidFlyer(42 * 70, 6 * 70));
	ennemies.add(new Ghost(19 * 70, 1 * 70));

	// Remplis Objectifs
	objectives.add(new Coin(1 * 70, 16 * 70));
	objectives.add(new Coin(42 * 70, 3 * 70));
	objectives.add(new Coin(17 * 70, 15 * 70));
	objectives.add(new Coin(16 * 70, 6 * 70));
	objectives.add(new Key(18 * 70, 3 * 70));

	// setup music (si rien, on garde la même musique qu'au niveau d'avant)
	musiclvl = new Music("ressources/audio/music/lvl5.ogg");

	player.setWeapon(1);

	initialisationSuite();

    }

}