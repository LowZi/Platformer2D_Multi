
package ch.hearc.p2.server.game;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.esotericsoftware.kryonet.Connection;

public class GameMulti {

    /*------------------------------------------------------------------*\
    |*			Attributs Private				*|
    \*------------------------------------------------------------------*/

    private Map<Connection, String> players;
    private Map<String, String> playersTeam;
    private int ready;

    private GameMap gameMap;
    private GameScore gameScore;

    private static final int MAX_PLAYER = 4;

    /*------------------------------------------------------------------*\
    |*			Constructeurs					*|
    \*------------------------------------------------------------------*/

    public GameMulti() {
	players = new HashMap<Connection, String>(MAX_PLAYER);
	playersTeam = new HashMap<String, String>(MAX_PLAYER);

	gameMap = new GameMap("lvl1Online");
	gameScore = new GameScore();

	ready = 0;
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		 	    	*|
    \*------------------------------------------------------------------*/

    public boolean addPlayer(String s, Connection c) {
	if (players.size() < MAX_PLAYER) {
	    players.put(c, s);
	    return true;
	} else {
	    return false;
	}
    }

    public void addPlayer(String s, String team) {
	playersTeam.put(s, team);
    }

    public void addReady() {
	ready++;
    }

    public String removePlayer(Connection c) {
	String pseudo = players.get(c);
	players.remove(c);
	playersTeam.remove(pseudo);
	return pseudo;
    }

    public void startGame() throws ParserConfigurationException, SAXException, IOException {
	gameMap.loadCases();
	gameMap.createCases();
    }

    /*------------------------------*\
    |*		Get		    *|
    \*------------------------------*/

    public int getReady() {
	return ready;
    }

    public int getNbPlayers() {
	return players.size();
    }

    public Map<Connection, String> getPlayers() {
	return players;
    }

    public Map<String, String> getPlayersTeam() {
	return playersTeam;
    }

    public GameMap getGameMap() {
	return this.gameMap;
    }

    public GameScore getGameScore() {
	return this.gameScore;
    }

    public void reset() {
	players = new HashMap<Connection, String>(MAX_PLAYER);
	playersTeam = new HashMap<String, String>(MAX_PLAYER);

	gameMap = new GameMap("lvl1Online");
	gameScore = new GameScore();

	ready = 0;
	
    }

    /*------------------------------------------------------------------*\
    |*			Methodes Private				*|
    \*------------------------------------------------------------------*/

}
