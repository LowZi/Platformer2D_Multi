
package ch.hearc.p2.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import ch.hearc.p2.server.Packet.Packet0LoginRequest;
import ch.hearc.p2.server.Packet.Packet1LoginAnswer;
import ch.hearc.p2.server.Packet.Packet2Message;
import ch.hearc.p2.server.Packet.Packet3Team;
import ch.hearc.p2.server.Packet.Packet4StartGame;
import ch.hearc.p2.server.Packet.Packet6SendData;
import ch.hearc.p2.server.Packet.Packet7AllPlayers;
import ch.hearc.p2.server.Packet.Packet8Projectile;
import ch.hearc.p2.server.Packet.Packet9Disconnect;

public class NetworkListener extends Listener {

    /*------------------------------------------------------------------*\
    |*				Attributs Private		    	*|
    \*------------------------------------------------------------------*/

    private Server server;
    private PlatformerServer pfServer;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			    	*|
    \*------------------------------------------------------------------*/

    public NetworkListener(Server server, PlatformerServer pfServer) {
	super();
	this.server = server;
	this.pfServer = pfServer;
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
	String pseudo = pfServer.removePlayer(c);

	Packet7AllPlayers allPlayers = new Packet7AllPlayers();
	allPlayers.players = pfServer.getPlayersTeam();
	server.sendToAllTCP(allPlayers);

	Packet9Disconnect disconnectedPlayer = new Packet9Disconnect();
	disconnectedPlayer.pseudo = pseudo;
	server.sendToAllTCP(disconnectedPlayer);

	System.out.println("[SERVER] Someone has disconnected");
    }

    @Override
    public void received(Connection c, Object o) {
	System.out.println("[SERVER] A message was received");

	if (o instanceof Packet0LoginRequest) {
	    Packet1LoginAnswer loginAnswer = new Packet1LoginAnswer();
	    String s = ((Packet0LoginRequest) o).pseudo;
	    loginAnswer.accepted = pfServer.addPlayer(s, c);

	    c.sendTCP(loginAnswer);

	    if (loginAnswer.accepted) {
		Packet3Team team = new Packet3Team();
		if (server.getConnections().length % 2 == 0) {
		    team.team = "BLUE";
		} else {
		    team.team = "RED";
		}

		Packet7AllPlayers allPlayers = new Packet7AllPlayers();
		pfServer.addPlayer(s, team.team);
		allPlayers.players = pfServer.getPlayersTeam();

		c.sendTCP(team);
		server.sendToAllTCP(allPlayers);
	    }
	}

	if (o instanceof Packet2Message) {
	    String message = ((Packet2Message) o).message;

	    if (message.equals("READY")) {
		pfServer.addReady();

		if (pfServer.getReady() >= 2) {
		    Packet4StartGame start = new Packet4StartGame();
		    start.id = 701;
		    server.sendToAllTCP(start);
		}
	    }
	}

	if (o instanceof Packet6SendData) {
	    server.sendToAllExceptTCP(c.getID(), o);
	}

	if (o instanceof Packet8Projectile) {
	    server.sendToAllExceptTCP(c.getID(), o);
	}
    }
}
