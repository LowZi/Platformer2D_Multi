
package ch.hearc.p2.server.game;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import ch.hearc.p2.server.data.CaseData;
import ch.hearc.p2.server.util.SAXParserMap;

public class GameMap {

    /*------------------------------------------------------------------*\
    |*			Attributs Private				*|
    \*------------------------------------------------------------------*/

    private ArrayList<Point> casesSpawnPoints;
    private ArrayList<CaseData> casesData;
    private String mapName;

    /*------------------------------------------------------------------*\
    |*			Constructeurs					*|
    \*------------------------------------------------------------------*/

    public GameMap(String mapName) {
	casesSpawnPoints = new ArrayList<Point>();
	casesData = new ArrayList<CaseData>();
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

	casesSpawnPoints = handler.getCasesSpawnPoints();
    }

    public void createCases() {
	for (Point p : casesSpawnPoints) {
	    casesData.add(new CaseData(p));
	}
    }

    public void spawnCase(float x, float y) {
	casesData.add(new CaseData(x, y));
    }

    /*------------------------------*\
    |*		Set		    *|
    \*------------------------------*/

    /*------------------------------*\
    |*		Get		    *|
    \*------------------------------*/

    public ArrayList<CaseData> getCasesData() {
	return this.casesData;
    }

    /*------------------------------------------------------------------*\
    |*			Methodes Private				*|
    \*------------------------------------------------------------------*/
}
