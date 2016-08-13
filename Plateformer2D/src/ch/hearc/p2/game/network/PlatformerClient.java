
package ch.hearc.p2.game.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import ch.hearc.p2.game.enums.Facing;
import ch.hearc.p2.game.enums.ProjectileType;
import ch.hearc.p2.game.enums.Team;
import ch.hearc.p2.game.network.Packet.Packet0LoginRequest;
import ch.hearc.p2.game.network.Packet.Packet10Cases;
import ch.hearc.p2.game.network.Packet.Packet11CaseTaken;
import ch.hearc.p2.game.network.Packet.Packet1LoginAnswer;
import ch.hearc.p2.game.network.Packet.Packet2Message;
import ch.hearc.p2.game.network.Packet.Packet3Team;
import ch.hearc.p2.game.network.Packet.Packet4StartGame;
import ch.hearc.p2.game.network.Packet.Packet6SendData;
import ch.hearc.p2.game.network.Packet.Packet7AllPlayers;
import ch.hearc.p2.game.network.Packet.Packet8Projectile;
import ch.hearc.p2.game.network.Packet.Packet9Disconnect;
import ch.hearc.p2.game.projectile.ProjectileData;

public class PlatformerClient {

    /*------------------------------------------------------------------*\
    |*				Attributs Private		    	*|
    \*------------------------------------------------------------------*/

    private Client client;

    private HashMap<String, PlayerData> playersData;

    private ArrayList<Metadata> players;

    private LinkedList<ProjectileData> toAddprojectiles;
    private LinkedList<String> disconnectedPlayers;

    private ArrayList<CaseData> cases;

    private Team team;
    private String pseudo;

    private Boolean isConnected;

    private static PlatformerClient plClient = null;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			    	*|
    \*------------------------------------------------------------------*/

    private PlatformerClient() throws IOException {
	client = new Client();

	register();

	playersData = new HashMap<String, PlayerData>();
	players = new ArrayList<Metadata>();

	toAddprojectiles = new LinkedList<ProjectileData>();
	disconnectedPlayers = new LinkedList<String>();

	team = null;
	pseudo = "";

	isConnected = false;

	NetworkListener nl = new NetworkListener(client, this);
	client.addListener(nl);

	client.start();
    }

    /*------------------------------------------------------------------*\
    |*				Méthodes Static 		    	*|
    \*------------------------------------------------------------------*/

    public static PlatformerClient getInstance() throws IOException {
	if (plClient == null) {
	    plClient = new PlatformerClient();
	    return plClient;
	} else {
	    return plClient;
	}
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Private		    	*|
    \*------------------------------------------------------------------*/

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
	kryo.register(Packet9Disconnect.class);
	kryo.register(Packet10Cases.class);
	kryo.register(Packet11CaseTaken.class);

	kryo.register(PlayerData.class);
	kryo.register(ProjectileData.class);
	kryo.register(Facing.class);
	kryo.register(ProjectileType.class);
	kryo.register(CaseData.class);
	kryo.register(Team.class);
	kryo.register(Metadata.class);
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		 	    	*|
    \*------------------------------------------------------------------*/

    public void connect(String adresse) throws IOException {
	if (client.isConnected())
	    disconnect();

	if (adresse.isEmpty())
	    adresse = "localhost";

	client.connect(5000, adresse, 54555);
    }

    public void disconnect() {
	client.close();
    }

    public void sendTCP(Object o) {
	client.sendTCP(o);
    }

    public void sendUPD(Object o) {
	client.sendUDP(o);
    }

    public void putPlayersData(String pseudo, PlayerData data) {
	playersData.put(pseudo, data);
	playersData.replace(pseudo, data);
    }

    public void putProjectile(ProjectileData p) {
	toAddprojectiles.add(p);
    }

    public void addDisconnectedPlayer(String pseudo) {
	disconnectedPlayers.add(pseudo);
    }

    /*------------------------------*\
    |*		Set		    *|
    \*------------------------------*/

    public void setTeam(Team team) {
	this.team = team;
    }

    public void setConnected(Boolean b) {
	isConnected = b;
    }

    public void setPseudo(String pseudo) {
	this.pseudo = pseudo;
    }

    public void setPlayers(ArrayList<Metadata> players) {
	this.players = players;
    }

    public void setCases(ArrayList<CaseData> cases) {
	this.cases = cases;
    }

    /*------------------------------*\
    |*		Get		    *|
    \*------------------------------*/

    public Team getTeam() {
	return team;
    }

    public Boolean isConnected() {
	return isConnected;
    }

    public HashMap<String, PlayerData> getPlayersData() {
	return playersData;
    }

    public String getPseudo() {
	return pseudo;
    }

    public ArrayList<Metadata> getPlayers() {
	return players;
    }

    public LinkedList<ProjectileData> getProjectileData() {
	LinkedList<ProjectileData> l = new LinkedList<ProjectileData>(toAddprojectiles);
	toAddprojectiles.clear();
	return l;
    }

    public LinkedList<String> getDisconnectedPlayer() {
	LinkedList<String> cpy = new LinkedList<String>(disconnectedPlayers);
	disconnectedPlayers.clear();
	return cpy;
    }

    public ArrayList<CaseData> getCases() {
	return this.cases;
    }
}
