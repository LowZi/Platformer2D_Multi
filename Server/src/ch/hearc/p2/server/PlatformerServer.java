
package ch.hearc.p2.server;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import ch.hearc.p2.server.Packet.Packet0LoginRequest;
import ch.hearc.p2.server.Packet.Packet1LoginAnswer;
import ch.hearc.p2.server.Packet.Packet2Message;
import ch.hearc.p2.server.Packet.Packet3Team;

public class PlatformerServer {

    private Server server;

    public PlatformerServer() throws IOException {
	server = new Server();
	registerPackets();

	NetworkListener nl = new NetworkListener();
	nl.init(server);
	server.addListener(nl);

	server.bind(54555);
	server.start();
    }

    private void registerPackets() {
	Kryo kryo = server.getKryo();
	kryo.register(Packet0LoginRequest.class);
	kryo.register(Packet1LoginAnswer.class);
	kryo.register(Packet2Message.class);
	kryo.register(Packet3Team.class);
    }

    public static void main(String[] args) {
	try {
	    new PlatformerServer();
	    System.out.println("Server started");
	    Log.set(Log.LEVEL_DEBUG);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
