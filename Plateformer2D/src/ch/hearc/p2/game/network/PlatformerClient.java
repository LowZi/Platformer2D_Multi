
package ch.hearc.p2.game.network;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import ch.hearc.p2.game.network.Packet.Packet0LoginRequest;
import ch.hearc.p2.game.network.Packet.Packet1LoginAnswer;
import ch.hearc.p2.game.network.Packet.Packet2Message;
import ch.hearc.p2.game.network.Packet.Packet3Team;

public class PlatformerClient {

    private Client client;
    private static PlatformerClient plClient = null;
    private String team = "";

    private PlatformerClient() {
	client = new Client();
	register();

	NetworkListener nl = new NetworkListener();
	nl.init(client, this);
	client.addListener(nl);

	client.start();

	try {
	    client.connect(5000, "localhost", 54555);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    client.stop();
	}
    }

    public static PlatformerClient getInstance() {
	if (plClient == null) {
	    plClient = new PlatformerClient();
	    return plClient;
	} else{
	    return plClient;
	}
    }

    private void register() {
	Kryo kryo = client.getKryo();
	kryo.register(Packet0LoginRequest.class);
	kryo.register(Packet1LoginAnswer.class);
	kryo.register(Packet2Message.class);
	kryo.register(Packet3Team.class);
    }

    public void sendTCP(Object o) {
	client.sendTCP(o);
    }

    public void sendUPD(Object o) {
	client.sendUDP(o);
    }

    public void setTeam(String team) {
	this.team = team;
    }
    
    public String getTeam()
    {
	return team;
    }

}
