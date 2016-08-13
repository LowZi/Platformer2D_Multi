
package ch.hearc.p2.server.data;

import com.esotericsoftware.kryonet.Connection;

public class PlayerMetadata extends Metadata {

    /*------------------------------------------------------------------*\
    |*			Attributs Private				*|
    \*------------------------------------------------------------------*/

    private Connection connection;
    private boolean ready;

    /*------------------------------------------------------------------*\
    |*			Constructeurs					*|
    \*------------------------------------------------------------------*/

    public PlayerMetadata(Connection connection, String pseudo) {
	super(pseudo, null);
	this.connection = connection;
	this.ready = false;
    }

    /*------------------------------------------------------------------*\
    |*			Methodes Public					*|
    \*------------------------------------------------------------------*/

    /*------------------------------*\
    |*		Is		    *|
    \*------------------------------*/

    public boolean isReady() {
	return ready;
    }

    /*------------------------------*\
    |*		Set		    *|
    \*------------------------------*/

    public void setReady(boolean ready) {
	this.ready = ready;
    }

    /*------------------------------*\
    |*		Get		    *|
    \*------------------------------*/

    public Connection getConnection() {
	return this.connection;
    }

    /*------------------------------------------------------------------*\
    |*			Methodes Private				*|
    \*------------------------------------------------------------------*/
}
