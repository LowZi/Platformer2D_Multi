
package ch.hearc.p2.server.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ch.hearc.p2.server.data.CaseData;
import ch.hearc.p2.server.data.PlayerData;
import ch.hearc.p2.server.data.ProjectileType;

public class Packet {
    
    public static class Packet0LoginRequest{String pseudo;}
    public static class Packet1LoginAnswer{boolean accepted = false;}
    public static class Packet2Message{String message;}
    public static class Packet3Team{String team;}
    public static class Packet4StartGame{int id;}
    public static class Packet6SendData{String pseudo; PlayerData data;}
    public static class Packet7AllPlayers{public Map players = new HashMap<String, String>();}
    public static class Packet8Projectile{ public float x; public float y; public float xVelocity; public float yVelocity; public ProjectileType type;}
    public static class Packet9Disconnect{public String pseudo;}
    public static class Packet10Cases{public ArrayList<CaseData> casesData = new ArrayList<CaseData>();}
    public static class Packet11CaseTaken{public float x; public float y;}
   }
