
package ch.hearc.p2.server.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import ch.hearc.p2.server.data.Metadata;
import ch.hearc.p2.server.data.PlayerMetadata;
import ch.hearc.p2.server.data.Team;
import ch.hearc.p2.server.network.Packet.Packet12GameScore;
import ch.hearc.p2.server.network.Packet.Packet14GameFinished;

public class GameMulti {

    /*------------------------------------------------------------------*\
    |*			Attributs Private				*|
    \*------------------------------------------------------------------*/

    private Server server;

    private ArrayList<PlayerMetadata> playersConnected;

    private GameMap gameMap;
    private GameScore gameScore;

    private Timer timer;
    private int timeLeft;

    private Boolean inGame;

    public static final int MAX_PLAYER = 4;
    private static final int GAME_DURATION = 120;

    /*------------------------------------------------------------------*\
    |*			Constructeurs					*|
    \*------------------------------------------------------------------*/

    public GameMulti(Server server) {
	this.server = server;

	playersConnected = new ArrayList<PlayerMetadata>(MAX_PLAYER);

	gameMap = new GameMap("lvl1Online");
	gameScore = new GameScore();

	timer = new Timer(1000, new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		timeLeft--;

		sendScore();

		if (timeLeft == 0)
		    endGame();
	    }
	});

	inGame = false;
	timeLeft = GAME_DURATION; // seconds
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		 	    	*|
    \*------------------------------------------------------------------*/

    public boolean addPlayer(Connection connection, String pseudo) {
	if (playersConnected.size() < MAX_PLAYER && inGame == false && isPseudoFree(pseudo)) {
	    playersConnected.add(new PlayerMetadata(connection, pseudo));
	    return true;
	} else {
	    return false;
	}
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

	gameScore.initializeScore(getListBluePlayers(), getListRedPlayers());

	timer.start();
    }

    public void endGame() {
	timer.stop();

	Packet14GameFinished gameFinished = new Packet14GameFinished();
	gameFinished.gameScore = this.gameScore;
	gameFinished.stateEnd = 1002;
	gameFinished.winningTeam = this.gameScore.getWinningTeam();

	server.sendToAllTCP(gameFinished);
    }

    public void sendScore() {
	// Send score and time left
	Packet12GameScore score = new Packet12GameScore();
	score.gameScore = this.gameScore;
	score.timeLeft = this.timeLeft;
	server.sendToAllTCP(score);

	// Debug
	gameScore.dump();
    }

    public void reset() {
	playersConnected = new ArrayList<PlayerMetadata>(MAX_PLAYER);

	gameMap = new GameMap("lvl1Online");
	gameScore = new GameScore();

	timer = new Timer(1000, new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		timeLeft--;

		sendScore();

		if (timeLeft == 0)
		    endGame();
	    }
	});

	inGame = false;
	timeLeft = GAME_DURATION; // seconds

	// Debug
	System.out.println("Serveur reset");
    }

    /*------------------------------*\
    |*		Is		    *|
    \*------------------------------*/

    public boolean isAllPlayersReady() {
	for (PlayerMetadata p : playersConnected) {
	    if (!p.isReady())
		return false;
	}
	return true;
    }

    /*------------------------------*\
    |*		Get		    *|
    \*------------------------------*/

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

    public Boolean getInGame() {
	return inGame;
    }

    /*------------------------------*\
    |*		Set		    *|
    \*------------------------------*/

    public void setPlayerTeam(Connection connection, Team team) {
	searchPlayerMetadata(connection).setTeam(team);
    }

    public void setPlayerReady(Connection connection, boolean ready) {
	searchPlayerMetadata(connection).setReady(ready);
    }

    public void setInGame(boolean b) {
	inGame = b;
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

    private ArrayList<String> getListRedPlayers() {
	ArrayList<String> redPlayers = new ArrayList<String>();
	for (PlayerMetadata pm : playersConnected) {
	    if (pm.getTeam() == Team.RED) {
		redPlayers.add(pm.getPseudo());
	    }
	}
	return redPlayers;
    }

    private boolean isPseudoFree(String pseudo) {
	for (PlayerMetadata pm : playersConnected) {
	    if (pm.getPseudo().equals(pseudo))
		return false;
	}
	return true;
    }

    private ArrayList<String> getListBluePlayers() {
	ArrayList<String> bluePlayers = new ArrayList<String>();
	for (PlayerMetadata pm : playersConnected) {
	    if (pm.getTeam() == Team.BLUE) {
		bluePlayers.add(pm.getPseudo());
	    }
	}
	return bluePlayers;
    }

}
