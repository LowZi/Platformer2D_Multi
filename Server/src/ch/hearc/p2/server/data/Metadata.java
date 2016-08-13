
package ch.hearc.p2.server.data;

public class Metadata {

    /*------------------------------------------------------------------*\
    |*			Attributs Private				*|
    \*------------------------------------------------------------------*/

    private String pseudo;
    private Team team;

    /*------------------------------------------------------------------*\
    |*			Constructeurs					*|
    \*------------------------------------------------------------------*/

    public Metadata() {
	this("null", null);
    }

    public Metadata(String pseudo, Team team) {
	this.pseudo = pseudo;
	this.team = team;
    }

    /*------------------------------------------------------------------*\
    |*			Methodes Public					*|
    \*------------------------------------------------------------------*/

    /*------------------------------*\
    |*		Set		    *|
    \*------------------------------*/

    public void setPseudo(String pseudo) {
	this.pseudo = pseudo;
    }

    public void setTeam(Team team) {
	this.team = team;
    }

    /*------------------------------*\
    |*		Get		    *|
    \*------------------------------*/

    public Team getTeam() {
	return this.team;
    }

    public String getPseudo() {
	return this.pseudo;
    }

    /*------------------------------------------------------------------*\
    |*			Methodes Private				*|
    \*------------------------------------------------------------------*/

}
