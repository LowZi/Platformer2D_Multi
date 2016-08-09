
package ch.hearc.p2.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

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
import ch.hearc.p2.server.Packet.Packet9Disconnect;

public class PlatformerServer {

    public static void main(String[] args) {
	try {
	    new PlatformerServer();
	    System.out.println("Server started");
	    Log.set(Log.LEVEL_DEBUG);
	} catch (IOException | ParserConfigurationException | SAXException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    /*------------------------------------------------------------------*\
    |*				Attributs Private		    	*|
    \*------------------------------------------------------------------*/

    private Server server;
    private Map<Connection, String> players;
    private Map<String, String> playersTeam;
    private int ready;

    private GameMap gameMap;

    private static final int MAX_PLAYER = 4;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			    	*|
    \*------------------------------------------------------------------*/

    public PlatformerServer() throws IOException, ParserConfigurationException, SAXException {
	players = new HashMap<Connection, String>(MAX_PLAYER);
	playersTeam = new HashMap<String, String>(MAX_PLAYER);
	ready = 0;
	gameMap = new GameMap("lvl1Online");

	gameMap.loadCases();

	server = new Server();
	registerPackets();

	NetworkListener nl = new NetworkListener(server, this);
	server.addListener(nl);

	server.bind(54555); // Set de TCP port
	server.start();
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Private		    	*|
    \*------------------------------------------------------------------*/

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
	kryo.register(Packet9Disconnect.class);

	kryo.register(PlayerData.class);
	kryo.register(ProjectileData.class);
	kryo.register(Facing.class);
	kryo.register(ProjectileType.class);
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		 	    	*|
    \*------------------------------------------------------------------*/

    public boolean addPlayer(String s, Connection c) {
	if (players.size() < MAX_PLAYER) {
	    players.put(c, s);
	    return true;
	} else {
	    return false;
	}
    }

    public void addPlayer(String s, String team) {
	playersTeam.put(s, team);
    }

    public void addReady() {
	ready++;
    }

    public String removePlayer(Connection c) {
	String pseudo = players.get(c);
	players.remove(c);
	playersTeam.remove(pseudo);
	return pseudo;
    }

    /*------------------------------*\
    |*		Get		    *|
    \*------------------------------*/

    public int getReady() {
	return ready;
    }

    public int getNbPlayers() {
	return players.size();
    }

    public Map<Connection, String> getPlayers() {
	return players;
    }

    public Map<String, String> getPlayersTeam() {
	return playersTeam;
    }
}
