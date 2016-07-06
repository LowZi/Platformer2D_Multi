package ch.hearc.p2.game.state;

import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import ch.hearc.p2.game.character.Abeille;
import ch.hearc.p2.game.character.Player;
import ch.hearc.p2.game.character.SnakeLava;
import ch.hearc.p2.game.character.SpinnerHalf;
import ch.hearc.p2.game.level.object.Coin;
import ch.hearc.p2.game.level.object.Key;

public class Level2 extends LevelState {

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/

    public Level2(String startingLevel) {
	super(startingLevel);
	ID = 102;
	this.startinglevel = startingLevel;
	nextLevel = 103;

    }
    
    /*------------------------------------------------------------------*\
    |*				Methodes Public		    		*|
    \*------------------------------------------------------------------*/

    @Override
    public void instanciation() throws SlickException {
	// ID for the next state (0 is main menu, ID+1 is the next level)

	player = new Player(2 * 70, 14 * 70);

	// Remplis ennemis
	ennemies.add(new Abeille(16 * 70, 1 * 70));
	ennemies.add(new Abeille(42 * 70, 2 * 70));
	ennemies.add(new SnakeLava(9 * 70, 14 * 70));
	ennemies.add(new SnakeLava(13 * 70, 14 * 70));
	ennemies.add(new SnakeLava(17 * 70, 14 * 70));
	ennemies.add(new SpinnerHalf(17 * 70, 5 * 70));
	ennemies.add(new SpinnerHalf(20 * 70, 5 * 70));

	// Remplis Objectifs
	objectives.add(new Coin(17 * 70, 10 * 70));
	objectives.add(new Coin(1 * 70, 8 * 70));
	objectives.add(new Coin(36 * 70, 4 * 70));
	objectives.add(new Coin(18 * 70, 4 * 70));
	objectives.add(new Key(36 * 70, 11 * 70));

	// setup music (si rien, on garde la même musique qu'au niveau d'avant)
	musiclvl = new Music("ressources/audio/music/lvl2.ogg");

	player.setWeapon(2);

	initialisationSuite();

	level.setBackground(new Image("ressources/background/moutain.png"));

    }

}