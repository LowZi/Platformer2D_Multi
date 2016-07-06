
package ch.hearc.p2.game.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import ch.hearc.p2.game.network.Packet.Packet0LoginRequest;
import ch.hearc.p2.game.network.Packet.Packet1LoginAnswer;
import ch.hearc.p2.game.network.Packet.Packet2Message;
import ch.hearc.p2.game.network.Packet.Packet3Team;

public class NetworkListener extends Listener {

    private Client client;
    private PlatformerClient plClient;

    public void init(Client client, PlatformerClient plClient) {
	this.client = client;
	this.plClient = plClient;
    }

    @Override
    public void connected(Connection arg0) {
	System.out.println("[CLIENT] You have connected.");
	client.sendTCP(new Packet0LoginRequest());
    }

    @Override
    public void disconnected(Connection arg0) {
	System.out.println("[CLIENT] You have disconnected");

    }

    @Override
    public void received(Connection c, Object o) {
	System.out.println("[SERVER] A message was received");
	if (o instanceof Packet1LoginAnswer) {
	    boolean answer = ((Packet1LoginAnswer) o).accepted;

	    if (answer) {
		Packet2Message p = new Packet2Message();
		p.message = "CONFIRMED";
		c.sendTCP(p);
	    } else {
		c.close();
	    }
	}
	if (o instanceof Packet3Team) {
	    String team = ((Packet3Team) o).team;
	    plClient.setTeam(team);
	}
    }

}
