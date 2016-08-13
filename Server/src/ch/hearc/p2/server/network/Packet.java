
package ch.hearc.p2.server.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ch.hearc.p2.server.data.CaseData;
import ch.hearc.p2.server.data.Metadata;
import ch.hearc.p2.server.data.PlayerData;
import ch.hearc.p2.server.data.ProjectileType;
import ch.hearc.p2.server.data.Team;

public class Packet {
    
    public static class Packet0LoginRequest{public String pseudo;}
    public static class Packet1LoginAnswer{public boolean accepted = false;}
    public static class Packet2Message{public String message;}
    public static class Packet3Team{public Team team;}
    public static class Packet4StartGame{public int id;}
    public static class Packet6SendData{public String pseudo; public PlayerData data;}
    public static class Packet7AllPlayers{public ArrayList<Metadata> players = new ArrayList<Metadata>();}
    public static class Packet8Projectile{ public float x; public float y; public float xVelocity; public float yVelocity; public ProjectileType type;}
    public static class Packet9Disconnect{public String pseudo;}
    public static class Packet10Cases{public ArrayList<CaseData> casesData = new ArrayList<CaseData>();}
    public static class Packet11CaseTaken{public float x; public float y;}
   }

