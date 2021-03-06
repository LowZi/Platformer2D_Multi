
package ch.hearc.p2.game.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import ch.hearc.p2.game.enums.Team;
import ch.hearc.p2.game.menu.LobbyState;
import ch.hearc.p2.game.network.Packet.Packet0LoginRequest;
import ch.hearc.p2.game.network.Packet.Packet10Cases;
import ch.hearc.p2.game.network.Packet.Packet12GameScore;
import ch.hearc.p2.game.network.Packet.Packet13Kill;
import ch.hearc.p2.game.network.Packet.Packet14GameFinished;
import ch.hearc.p2.game.network.Packet.Packet1LoginAnswer;
import ch.hearc.p2.game.network.Packet.Packet2Message;
import ch.hearc.p2.game.network.Packet.Packet3Team;
import ch.hearc.p2.game.network.Packet.Packet4StartGame;
import ch.hearc.p2.game.network.Packet.Packet6SendData;
import ch.hearc.p2.game.network.Packet.Packet7AllPlayers;
import ch.hearc.p2.game.network.Packet.Packet8Projectile;
import ch.hearc.p2.game.network.Packet.Packet9Disconnect;
import ch.hearc.p2.game.projectile.ProjectileData;

public class NetworkListener extends Listener {

    /*------------------------------------------------------------------*\
    |*				Attributs Private		    	*|
    \*------------------------------------------------------------------*/

    private Client client;
    private PlatformerClient plClient;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			    	*|
    \*------------------------------------------------------------------*/

    public NetworkListener(Client client, PlatformerClient plClient) {
	super();
	this.client = client;
	this.plClient = plClient;
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		 	    	*|
    \*------------------------------------------------------------------*/

    @Override
    public void connected(Connection arg0) {
	System.out.println("[CLIENT] You have connected.");
	Packet0LoginRequest packet0 = new Packet0LoginRequest();
	packet0.pseudo = plClient.getPseudo();
	client.sendTCP(packet0);
    }

    @Override
    public void disconnected(Connection arg0) {
	plClient.setGameFinished(true);
	System.out.println("[CLIENT] You have disconnected");

    }

    @Override
    public void received(Connection c, Object o) {
	if (o instanceof Packet1LoginAnswer) {
	    boolean answer = ((Packet1LoginAnswer) o).accepted;
	    if (!answer) {
		plClient.setConnected(false);
		c.close();
	    } else
		plClient.setConnected(true);
	}
	if (o instanceof Packet3Team) {
	    Team team = ((Packet3Team) o).team;
	    plClient.setTeam(team);

	    Packet2Message message = new Packet2Message();
	    message.message = "READY";
	    plClient.sendTCP(message);
	}

	if (o instanceof Packet4StartGame) {
	    int id = ((Packet4StartGame) o).id;
	    LobbyState.startGame(id);

	}

	if (o instanceof Packet7AllPlayers) {
	    plClient.setPlayers(((Packet7AllPlayers) o).players);
	}

	if (o instanceof Packet6SendData) {
	    String pseudo = ((Packet6SendData) o).pseudo;
	    PlayerData data = ((Packet6SendData) o).data;
	    plClient.putPlayersData(pseudo, data);
	}

	if (o instanceof Packet8Projectile) {

	    ProjectileData p = new ProjectileData(((Packet8Projectile) o).x, ((Packet8Projectile) o).y,
		    ((Packet8Projectile) o).xVelocity, ((Packet8Projectile) o).yVelocity, ((Packet8Projectile) o).type,
		    ((Packet8Projectile) o).shooter, ((Packet8Projectile) o).team);
	    plClient.putProjectile(p);
	}
	if (o instanceof Packet9Disconnect) {
	    String pseudo = ((Packet9Disconnect) o).pseudo;
	    plClient.addDisconnectedPlayer(pseudo);
	}

	if (o instanceof Packet10Cases) {
	    plClient.setCases(((Packet10Cases) o).casesData);
	}

	if (o instanceof Packet12GameScore) {
	    plClient.setGameScore(((Packet12GameScore) o).gameScore);
	    plClient.setTimeLeft(((Packet12GameScore) o).timeLeft);
	}

	if (o instanceof Packet13Kill) {
	    // Update the plClient's killFeed
	    String kill = ((Packet13Kill) o).pseudoKiller + " KILLED " + ((Packet13Kill) o).pseudoKilled;
	    plClient.addKillToKillFeed(kill);
	}

	if (o instanceof Packet14GameFinished) {
	    plClient.setGameFinished(true);
	    plClient.setGameScore(((Packet14GameFinished) o).gameScore);
	    plClient.setWinningTeam(((Packet14GameFinished) o).winningTeam);
	}
    }

}
