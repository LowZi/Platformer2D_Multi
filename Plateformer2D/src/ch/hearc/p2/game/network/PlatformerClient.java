
package ch.hearc.p2.game.network;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import ch.hearc.p2.game.enums.Facing;
import ch.hearc.p2.game.enums.ProjectileType;
import ch.hearc.p2.game.network.Packet.Packet0LoginRequest;
import ch.hearc.p2.game.network.Packet.Packet1LoginAnswer;
import ch.hearc.p2.game.network.Packet.Packet2Message;
import ch.hearc.p2.game.network.Packet.Packet3Team;
import ch.hearc.p2.game.network.Packet.Packet4StartGame;
import ch.hearc.p2.game.network.Packet.Packet6SendData;
import ch.hearc.p2.game.network.Packet.Packet7AllPlayers;
import ch.hearc.p2.game.network.Packet.Packet8Projectile;
import ch.hearc.p2.game.projectile.ProjectileData;

public class PlatformerClient {

    private Client client;
    private static PlatformerClient plClient = null;
    private String team = "";
    private String pseudo = "";
    private HashMap<String, PlayerData> playersData;
    private HashMap<String, String> playersTeam;
    private LinkedList<ProjectileData> toAddprojectiles;

    private PlatformerClient() throws IOException {
	client = new Client();
	register();
	playersData = new HashMap<String, PlayerData>();
	playersTeam = new HashMap<String, String>();
	toAddprojectiles = new LinkedList<ProjectileData>();
	
	NetworkListener nl = new NetworkListener();
	nl.init(client, this);
	client.addListener(nl);

	client.start();

	client.connect(5000, "localhost", 54555);

    }

    public PlatformerClient(String adresse) throws IOException {
	client = new Client();
	register();
	playersData = new HashMap<String, PlayerData>();
	playersTeam = new HashMap<String, String>();
	toAddprojectiles = new LinkedList<ProjectileData>();
	NetworkListener nl = new NetworkListener();
	nl.init(client, this);
	client.addListener(nl);

	client.start();

	client.connect(5000, adresse, 54555);

    }

    public static PlatformerClient getInstance() throws IOException {
	if (plClient == null) {
	    plClient = new PlatformerClient();
	    return plClient;
	} else {
	    return plClient;
	}
    }

    public static PlatformerClient getInstance(String adresse) throws IOException {
	if (plClient == null) {
	    plClient = new PlatformerClient(adresse);
	    return plClient;
	} else {
	    return plClient;
	}
    }

    private void register() {
	Kryo kryo = client.getKryo();
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

	kryo.register(ch.hearc.p2.game.network.PlayerData.class);
	kryo.register(ch.hearc.p2.game.projectile.ProjectileData.class);
	kryo.register(Facing.class);
	kryo.register(ProjectileType.class);

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

    public String getTeam() {
	return team;
    }

    public void putPlayersData(String pseudo, PlayerData data) {
	playersData.put(pseudo, data);
	playersData.replace(pseudo, data);
    }

    public HashMap<String, PlayerData> getPlayersData() {
	return playersData;
    }

    public void setPseudo(String pseudo) {
	this.pseudo = pseudo;

    }

    public String getPseudo() {
	return pseudo;
    }

    public void setPlayers(Map<String, String> players) {
	this.playersTeam = (HashMap<String, String>) players;
    }

    public Map<String, String> getPlayers() {
	return playersTeam;
    }

    public void putProjectile(ProjectileData p) {
	toAddprojectiles.add(p);
    }

    public LinkedList<ProjectileData> getProjectileData() {
	LinkedList<ProjectileData> l = new LinkedList<ProjectileData>(toAddprojectiles);
	toAddprojectiles.clear();
	return l;
    }
}
