
package ch.hearc.p2.server.network;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import ch.hearc.p2.server.data.CaseData;
import ch.hearc.p2.server.data.Facing;
import ch.hearc.p2.server.data.Metadata;
import ch.hearc.p2.server.data.PlayerData;
import ch.hearc.p2.server.data.ProjectileData;
import ch.hearc.p2.server.data.ProjectileType;
import ch.hearc.p2.server.data.Team;
import ch.hearc.p2.server.game.GameMulti;
import ch.hearc.p2.server.game.GameScore;
import ch.hearc.p2.server.game.IndividualScore;
import ch.hearc.p2.server.network.Packet.Packet0LoginRequest;
import ch.hearc.p2.server.network.Packet.Packet10Cases;
import ch.hearc.p2.server.network.Packet.Packet11CaseTaken;
import ch.hearc.p2.server.network.Packet.Packet12GameScore;
import ch.hearc.p2.server.network.Packet.Packet13Kill;
import ch.hearc.p2.server.network.Packet.Packet14GameFinished;
import ch.hearc.p2.server.network.Packet.Packet1LoginAnswer;
import ch.hearc.p2.server.network.Packet.Packet2Message;
import ch.hearc.p2.server.network.Packet.Packet3Team;
import ch.hearc.p2.server.network.Packet.Packet4StartGame;
import ch.hearc.p2.server.network.Packet.Packet6SendData;
import ch.hearc.p2.server.network.Packet.Packet7AllPlayers;
import ch.hearc.p2.server.network.Packet.Packet8Projectile;
import ch.hearc.p2.server.network.Packet.Packet9Disconnect;

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
    private GameMulti gameMulti;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			    	*|
    \*------------------------------------------------------------------*/

    public PlatformerServer() throws IOException, ParserConfigurationException, SAXException {
	server = new Server();

	gameMulti = new GameMulti(server);

	registerPackets();

	NetworkListener nl = new NetworkListener(server, gameMulti);
	server.addListener(nl);

	server.bind(54555); // Set TCP port
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
	kryo.register(Packet10Cases.class);
	kryo.register(Packet11CaseTaken.class);
	kryo.register(Packet12GameScore.class);
	kryo.register(Packet13Kill.class);
	kryo.register(Packet14GameFinished.class);

	kryo.register(PlayerData.class);
	kryo.register(ProjectileData.class);
	kryo.register(Facing.class);
	kryo.register(ProjectileType.class);
	kryo.register(CaseData.class);
	kryo.register(Team.class);
	kryo.register(Metadata.class);
	kryo.register(GameScore.class);
	kryo.register(IndividualScore.class);
    }
}
