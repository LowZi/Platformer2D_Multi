
package ch.hearc.p2.game.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import ch.hearc.p2.game.enums.Team;

public class GameScore {

    /*------------------------------------------------------------------*\
    |*			Attributs Private				*|
    \*------------------------------------------------------------------*/

    private int blueTeamScore;
    private int redTeamScore;

    private HashMap<String, IndividualScore> bluePlayersScore;
    private HashMap<String, IndividualScore> redPlayersScore;
    /*------------------------------------------------------------------*\
    |*			Constructeurs					*|
    \*------------------------------------------------------------------*/

    public GameScore() {
	bluePlayersScore = new HashMap<String, IndividualScore>();
	redPlayersScore = new HashMap<String, IndividualScore>();

	blueTeamScore = 0;
	redTeamScore = 0;
    }

    /*------------------------------------------------------------------*\
    |*			Methodes Public					*|
    \*------------------------------------------------------------------*/

    public void initializeScore(ArrayList<String> bluePlayers, ArrayList<String> redPlayers) {
	for (String pseudo : bluePlayers) {
	    bluePlayersScore.put(pseudo, new IndividualScore());
	}

	for (String pseudo : redPlayers) {
	    redPlayersScore.put(pseudo, new IndividualScore());
	}

	blueTeamScore = 0;
	redTeamScore = 0;
    }

    public void addKill(String pseudoKiller, String pseudoKilled) {
	addKill(pseudoKiller);
	addDeath(pseudoKilled);
    }

    /*------------------------------*\
    |*		Set	   	    *|
    \*------------------------------*/

    /*------------------------------*\
    |*		Get		    *|
    \*------------------------------*/

    public int getBlueTeamScore() {
	return this.blueTeamScore;
    }

    public int getRedTeamScore() {
	return this.redTeamScore;
    }

    public HashMap<String, IndividualScore> getRedPlayersScore() {
	return redPlayersScore;
    }

    public HashMap<String, IndividualScore> getBluePlayersScore() {
	return bluePlayersScore;
    }

    public Team getWinningTeam() {
	if (blueTeamScore > redTeamScore)
	    return Team.BLUE;
	else if (redTeamScore > blueTeamScore)
	    return Team.RED;
	else
	    return null;
    }

    /*------------------------------------------------------------------*\
    |*			Methodes Private				*|
    \*------------------------------------------------------------------*/

    private void addKill(String pseudoKiller) {
	if (bluePlayersScore.get(pseudoKiller) != null) {
	    bluePlayersScore.get(pseudoKiller).addKill();
	    blueTeamScore++;
	} else if (redPlayersScore.get(pseudoKiller) != null) {
	    redPlayersScore.get(pseudoKiller).addKill();
	    redTeamScore++;
	}
    }

    private void addDeath(String pseudoKilled) {
	if (bluePlayersScore.get(pseudoKilled) != null) {
	    bluePlayersScore.get(pseudoKilled).addDeath();
	} else if (redPlayersScore.get(pseudoKilled) != null) {
	    redPlayersScore.get(pseudoKilled).addDeath();
	}
    }

}
