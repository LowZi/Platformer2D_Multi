
package ch.hearc.p2.server.network;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import ch.hearc.p2.server.data.CaseData;
import ch.hearc.p2.server.game.GameMulti;
import ch.hearc.p2.server.network.Packet.Packet0LoginRequest;
import ch.hearc.p2.server.network.Packet.Packet10Cases;
import ch.hearc.p2.server.network.Packet.Packet11CaseTaken;
import ch.hearc.p2.server.network.Packet.Packet1LoginAnswer;
import ch.hearc.p2.server.network.Packet.Packet2Message;
import ch.hearc.p2.server.network.Packet.Packet3Team;
import ch.hearc.p2.server.network.Packet.Packet4StartGame;
import ch.hearc.p2.server.network.Packet.Packet6SendData;
import ch.hearc.p2.server.network.Packet.Packet7AllPlayers;
import ch.hearc.p2.server.network.Packet.Packet8Projectile;
import ch.hearc.p2.server.network.Packet.Packet9Disconnect;

public class NetworkListener extends Listener {

    /*------------------------------------------------------------------*\
    |*				Attributs Private		    	*|
    \*------------------------------------------------------------------*/

    private Server server;
    private GameMulti gameMulti;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			    	*|
    \*------------------------------------------------------------------*/

    public NetworkListener(Server server, GameMulti gameMulti) {
	super();
	this.server = server;
	this.gameMulti = gameMulti;
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		 	    	*|
    \*------------------------------------------------------------------*/

    @Override
    public void connected(Connection arg0) {
	System.out.println("[SERVER] Someone has connected.");
    }

    @Override
    public void disconnected(Connection c) {
	String pseudo = gameMulti.removePlayer(c);

	Packet7AllPlayers allPlayers = new Packet7AllPlayers();
	allPlayers.players = gameMulti.getPlayersTeam();
	server.sendToAllTCP(allPlayers);

	Packet9Disconnect disconnectedPlayer = new Packet9Disconnect();
	disconnectedPlayer.pseudo = pseudo;
	server.sendToAllTCP(disconnectedPlayer);

	System.out.println("[SERVER] Someone has disconnected");
    }

    @Override
    public void received(Connection c, Object o) {
	// System.out.println("[SERVER] A message was received");

	if (o instanceof Packet0LoginRequest) {
	    Packet1LoginAnswer loginAnswer = new Packet1LoginAnswer();
	    String s = ((Packet0LoginRequest) o).pseudo;
	    loginAnswer.accepted = gameMulti.addPlayer(s, c);

	    c.sendTCP(loginAnswer);

	    if (loginAnswer.accepted) {
		Packet3Team team = new Packet3Team();
		if (server.getConnections().length % 2 == 0) {
		    team.team = "BLUE";
		} else {
		    team.team = "RED";
		}

		Packet7AllPlayers allPlayers = new Packet7AllPlayers();
		gameMulti.addPlayer(s, team.team);
		allPlayers.players = gameMulti.getPlayersTeam();

		c.sendTCP(team);
		server.sendToAllTCP(allPlayers);
	    }
	}

	if (o instanceof Packet2Message) {
	    String message = ((Packet2Message) o).message;

	    if (message.equals("READY")) {
		gameMulti.addReady();

		// If everybody is ready the game start
		if (gameMulti.getReady() >= 2) {
		    try {
			gameMulti.startGame();
		    } catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		    }

		    Packet4StartGame start = new Packet4StartGame();
		    start.id = 701;
		    server.sendToAllTCP(start);

		    Packet10Cases cases = new Packet10Cases();
		    cases.casesData = gameMulti.getGameMap().getCasesData();
		    server.sendToAllTCP(cases);
		    gameMulti.getGameMap().getCasesData().clear();
		}
	    }
	}

	if (o instanceof Packet6SendData) {
	    server.sendToAllExceptTCP(c.getID(), o);
	}

	if (o instanceof Packet8Projectile) {
	    server.sendToAllExceptTCP(c.getID(), o);
	}

	if (o instanceof Packet11CaseTaken) {
	    new java.util.Timer().schedule(new java.util.TimerTask() {
		@Override
		public void run() {
		    gameMulti.getGameMap().spawnCase(((Packet11CaseTaken) o).x, ((Packet11CaseTaken) o).y);

		    Packet10Cases cases = new Packet10Cases();
		    cases.casesData = gameMulti.getGameMap().getCasesData();
		    server.sendToAllTCP(cases);

		    gameMulti.getGameMap().getCasesData().clear();
		}
	    }, 5000);
	}
    }
}
