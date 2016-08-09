
package ch.hearc.p2.server;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class GameMap {

    /*------------------------------------------------------------------*\
    |*			Attributs Private				*|
    \*------------------------------------------------------------------*/

    private ArrayList<Point> casesSpawnPoints;
    private String mapName;

    /*------------------------------------------------------------------*\
    |*			Constructeurs					*|
    \*------------------------------------------------------------------*/

    public GameMap(String mapName) {
	// map = new TiledMap("ressources/level/" + mapName + ".tmx", false);
	casesSpawnPoints = new ArrayList<Point>();
	this.mapName = "ressources/level/" + mapName + ".tmx";
    }

    /*------------------------------------------------------------------*\
    |*			Methodes Public					*|
    \*------------------------------------------------------------------*/

    public void loadCases() throws ParserConfigurationException, SAXException, IOException {
	SAXParserFactory parserFactor = SAXParserFactory.newInstance();
	SAXParser parser = parserFactor.newSAXParser();
	SAXParserMap handler = new SAXParserMap();

	parser.parse(new File(mapName), handler);

	// Print all employees.
	for (Point p : SAXParserMap.spawns) {
	    System.out.println(p.getX());
	    System.out.println(p.getY());
	}
    }

    /*------------------------------*\
    |*		Set		    *|
    \*------------------------------*/

    /*------------------------------*\
    |*		Get		    *|
    \*------------------------------*/

    /*------------------------------------------------------------------*\
    |*			Methodes Private				*|
    \*------------------------------------------------------------------*/
}
