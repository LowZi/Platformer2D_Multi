
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
import ch.hearc.p2.server.Packet.Packet4StartGame;
import ch.hearc.p2.server.Packet.Packet6SendData;
import ch.hearc.p2.server.Packet.Packet7AllPlayers;
import ch.hearc.p2.server.Packet.Packet8Projectile;

public class PlatformerServer {

    private Server server;
    private Map<String, Connection> players;
    private Map<String, String> playersTeam;
    public static final int MAX_PLAYER = 4;
    private int ready = 0;

    public PlatformerServer() throws IOException {
	players = new HashMap<String, Connection>(MAX_PLAYER);
	playersTeam = new HashMap<String, String>(MAX_PLAYER);
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
	kryo.register(java.util.HashMap.class);

	kryo.register(java.util.List.class);
	kryo.register(java.util.LinkedList.class);
	kryo.register(java.util.ArrayList.class);

	kryo.register(Packet0LoginRequest.class);
	kryo.register(Packet1LoginAnswer.class);
	kryo.register(Packet2Message.class);
	kryo.register(Packet3Team.class);
	kryo.register(Packet4StartGame.class);
	kryo.register(Packet6SendData.class);
	kryo.register(Packet7AllPlayers.class);
	kryo.register(Packet8Projectile.class);
	
	kryo.register(PlayerData.class);
	kryo.register(ProjectileData.class);
	kryo.register(Facing.class);
	kryo.register(ProjectileType.class);

    }

    public boolean addPlayer(String s, Connection c) {
	if (players.size() < MAX_PLAYER) {
	    players.put(s, c);
	    return true;
	} else {
	    return false;
	}
    }

    public void addReady() {
	ready++;
    }

    public int getReady() {
	return ready;
    }

    public int getNbPlayers() {
	return players.size();
    }

    public Map<String, Connection> getPlayers() {
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

    public void addPlayer(String s, String team) {
	playersTeam.put(s, team);

    }

    public Map<String, String> getPlayersTeam() {
	return playersTeam;
    }

}
