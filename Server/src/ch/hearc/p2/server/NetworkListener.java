
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

public class NetworkListener extends Listener {

    private Server server;
    private PlatformerServer pfServer;

    public void init(Server server, PlatformerServer pfServer) {
	this.server = server;
	this.pfServer = pfServer;
    }

    @Override
    public void connected(Connection arg0) {
	System.out.println("[SERVER] Someone has connected.");

    }

    @Override
    public void disconnected(Connection arg0) {
	System.out.println("[SERVER] Someone has disconnected");

    }

    @Override
    public void received(Connection c, Object o) {
	System.out.println("[SERVER] A message was received");

	if (o instanceof Packet0LoginRequest) {
	    Packet1LoginAnswer loginAnswer = new Packet1LoginAnswer();
	    String s = ((Packet0LoginRequest) o).pseudo;
	    loginAnswer.accepted = pfServer.addPlayer(s, c);

	    if (loginAnswer.accepted) {
		c.sendTCP(loginAnswer);
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
	    } else {
		loginAnswer.accepted = false;
		c.sendTCP(loginAnswer);
	    }

	}

	if (o instanceof Packet2Message) {
	    String message = ((Packet2Message) o).message;
	    if (message.equals("READY")) {
		pfServer.addReady();
		if (pfServer.getReady() == 2) {

		    Packet4StartGame start = new Packet4StartGame();
		    start.id = 701;
		    server.sendToAllTCP(start);
		}
	    }
	}

	if (o instanceof Packet6SendData) {
	    server.sendToAllExceptTCP(c.getID(), o);
	    // PlayerData data = ((Packet6SendData)o).data;
	    // String pseudo = ((Packet6SendData)o).pseudo;
	    // if(!data.toAddList.isEmpty())
	    // System.out.println("[SERVER] [" + pseudo + "] toAdd" +
	    // data.toAddList.toString());

	}

	if (o instanceof Packet8Projectile) {
	    server.sendToAllExceptTCP(c.getID(), o);
	}

    }

}
