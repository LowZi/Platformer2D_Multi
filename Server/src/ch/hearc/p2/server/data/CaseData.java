
package ch.hearc.p2.server.data;

import java.awt.Point;
import java.util.Random;

public class CaseData {

    /*------------------------------------------------------------------*\
    |*			Attributs Private				*|
    \*------------------------------------------------------------------*/

    private float x;
    private float y;
    private int indexWeapon;

    private static final int NB_WEAPONS = 2;

    /*------------------------------------------------------------------*\
    |*			Constructeurs					*|
    \*------------------------------------------------------------------*/

    public CaseData() {
	this.x = 0;
	this.y = 0;
	this.indexWeapon = 1;
    }

    public CaseData(float x, float y) {
	this.x = x;
	this.y = y;
	this.indexWeapon = randomValue(NB_WEAPONS);
    }

    public CaseData(Point point) {
	this((float) point.getX(), (float) point.getY());
    }

    /*------------------------------------------------------------------*\
    |*							Methodes Public							*|
    \*------------------------------------------------------------------*/

    /*------------------------------*\
    |*		Get	            *|
    \*------------------------------*/

    public float getX() {
	return this.x;
    }

    public float getY() {
	return this.y;
    }

    public int getIndexWeapon() {
	return this.indexWeapon;
    }

    /*------------------------------------------------------------------*\
    |*			Methodes Private				*|
    \*------------------------------------------------------------------*/

    private int randomValue(int nbWeapons) {
	Random rand = new Random();

	int randomNum = rand.nextInt(nbWeapons) + 1; // "+ 1" for not given the
						     // basic weapon to the
						     // player who take the case

	return randomNum;
    }
}
