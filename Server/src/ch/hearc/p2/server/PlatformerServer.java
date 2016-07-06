
package ch.hearc.p2.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import ch.hearc.p2.server.Packet.Packet0LoginRequest;
import ch.hearc.p2.server.Packet.Packet1LoginAnswer;
import ch.hearc.p2.server.Packet.Packet2Message;
import ch.hearc.p2.server.Packet.Packet3Team;

public class PlatformerServer {

    private Server server;
    private Map<Integer, Connection> players;
    public static final int MAX_PLAYER = 4;

    public PlatformerServer() throws IOException {
	players = new HashMap<Integer, Connection>(MAX_PLAYER);
	server = new Server();
	registerPackets();

	NetworkListener nl = new NetworkListener();
	nl.init(server, this);
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

    public boolean addPlayer(Connection c) {
	if (players.size() < MAX_PLAYER) {
	    players.put(1, c);
	    return true;
	} else {
	    return false;
	}
    }

    public int getNbPlayers() {
	return players.size();
    }

    public Map<Integer, Connection> getPlayers() {
	return players;
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
