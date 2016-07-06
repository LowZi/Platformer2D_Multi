
package ch.hearc.p2.server;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import ch.hearc.p2.server.Packet.Packet0LoginRequest;
import ch.hearc.p2.server.Packet.Packet1LoginAnswer;
import ch.hearc.p2.server.Packet.Packet2Message;
import ch.hearc.p2.server.Packet.Packet3Team;

public class NetworkListener extends Listener {

    private Server server;

    public void init(Server server) {
	this.server = server;
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
	    loginAnswer.accepted = true;
	    c.sendTCP(loginAnswer);
	}
	if (o instanceof Packet2Message) {
	    String message = ((Packet2Message) o).message;
	    if(message == "CONFIRMED")
	    {
		Packet3Team team = new Packet3Team();
		team.team = "RED";
		c.sendTCP(team);
	    }
	}
    }

}
