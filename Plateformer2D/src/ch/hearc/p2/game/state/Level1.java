package ch.hearc.p2.game.state;

import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import ch.hearc.p2.game.character.Abeille;
import ch.hearc.p2.game.character.Player;
import ch.hearc.p2.game.character.SnakeLava;
import ch.hearc.p2.game.character.Spider;
import ch.hearc.p2.game.level.object.Coin;
import ch.hearc.p2.game.level.object.Key;

public class Level1 extends LevelState {

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/
    
    public Level1(String startingLevel) {
	super(startingLevel);
	ID = 101;
	this.startinglevel = startingLevel;
	nextLevel = 102;

    }
    
    /*------------------------------------------------------------------*\
    |*				Methodes Public		    		*|
    \*------------------------------------------------------------------*/
    
    @Override
    public void instanciation() throws SlickException {
	// at the start of the game we don't have a player yet
	player = new Player(1 * 70, 16 * 70);

	// Remplis ennmis
	ennemies.add(new Abeille(5 * 70, 6 * 70));
	ennemies.add(new Abeille(26 * 70, 9 * 70));
	ennemies.add(new Abeille(38 * 70, 9 * 70));
	ennemies.add(new Spider(31 * 70, 17 * 70));
	ennemies.add(new SnakeLava(15 * 70, 14 * 70));

	// Remplis Objectifs
	objectives.add(new Coin(8 * 70, 6 * 70));
	objectives.add(new Coin(23 * 70, 5 * 70));
	objectives.add(new Coin(37 * 70, 10 * 70));
	objectives.add(new Coin(35 * 70, 17 * 70));
	objectives.add(new Key(6 * 70, 7 * 70));

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