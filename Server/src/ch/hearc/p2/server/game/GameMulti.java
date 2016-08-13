
package ch.hearc.p2.server.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.esotericsoftware.kryonet.Connection;

import ch.hearc.p2.server.data.Metadata;
import ch.hearc.p2.server.data.PlayerMetadata;
import ch.hearc.p2.server.data.Team;

public class GameMulti {

    /*------------------------------------------------------------------*\
    |*			Attributs Private				*|
    \*------------------------------------------------------------------*/

    private ArrayList<PlayerMetadata> playersConnected;

    private GameMap gameMap;
    private GameScore gameScore;

    private Boolean inGame;

    public static final int MAX_PLAYER = 2;

    /*------------------------------------------------------------------*\
    |*			Constructeurs					*|
    \*------------------------------------------------------------------*/

    public GameMulti() {

	playersConnected = new ArrayList<PlayerMetadata>(MAX_PLAYER);

	gameMap = new GameMap("lvl1Online");
	gameScore = new GameScore();

	inGame = false;
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		 	    	*|
    \*------------------------------------------------------------------*/

    public boolean addPlayer(Connection connection, String pseudo) {
	if (playersConnected.size() < MAX_PLAYER && inGame == false) {
	    playersConnected.add(new PlayerMetadata(connection, pseudo));
	    return true;
	} else {
	    return false;
	}
    }

    public void setPlayerTeam(Connection connection, Team team) {
	searchPlayerMetadata(connection).setTeam(team);
    }

    public void setPlayerReady(Connection connection, boolean ready) {
	searchPlayerMetadata(connection).setReady(ready);
    }

    public String removePlayer(Connection connection) {
	PlayerMetadata playerMetadata = searchPlayerMetadata(connection);
	String pseudo = playerMetadata.getPseudo();
	playersConnected.remove(playerMetadata);
	return pseudo;
    }

    public void startGame() throws ParserConfigurationException, SAXException, IOException {
	gameMap.loadCases();
	gameMap.createCases();
    }

    /*------------------------------*\
    |*		Get		    *|
    \*------------------------------*/

    public boolean isAllPlayersReady() {
	for (PlayerMetadata p : playersConnected) {
	    if (!p.isReady())
		return false;
	}
	return true;
    }

    public int getNbPlayers() {
	return playersConnected.size();
    }

    public ArrayList<Metadata> getPlayersConnected() {
	ArrayList<Metadata> m = new ArrayList<Metadata>();
	for (PlayerMetadata playerMetadata : playersConnected) {
	    m.add(new Metadata(playerMetadata.getPseudo(), playerMetadata.getTeam()));
	}
	return m;
    }

    public GameMap getGameMap() {
	return this.gameMap;
    }

    public GameScore getGameScore() {
	return this.gameScore;
    }

    public void reset() {
	playersConnected = new ArrayList<PlayerMetadata>();

	gameMap = new GameMap("lvl1Online");
	gameScore = new GameScore();

	inGame = false;
    }

    public void setInGame(boolean b) {
	inGame = b;
    }

    public Boolean getInGame() {
	return inGame;
    }

    /*------------------------------------------------------------------*\
    |*			Methodes Private				*|
    \*------------------------------------------------------------------*/

    private PlayerMetadata searchPlayerMetadata(Connection connection) {
	Iterator<PlayerMetadata> it = playersConnected.iterator();
	PlayerMetadata playerMetadata;
	while (it.hasNext()) {
	    playerMetadata = it.next();
	    if (playerMetadata.getConnection().equals(connection)) {
		return playerMetadata;
	    }
	}
	return null;
    }

}
