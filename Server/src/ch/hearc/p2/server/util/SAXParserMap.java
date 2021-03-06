package ch.hearc.p2.server.util;

import java.awt.Point;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXParserMap extends DefaultHandler {

    private ArrayList<Point> casesSpawnPoints = new ArrayList<Point>();
    private Point point = null;
    private Boolean isGroupObjectCaseSpawn = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

	switch (qName) {
	case "objectgroup": {
	    if (attributes.getValue("name").equals("CasesSpawns"))
		isGroupObjectCaseSpawn = true;
	    break;
	}
	case "object": {
	    if (isGroupObjectCaseSpawn) {
		point = new Point((int) (Float.parseFloat(attributes.getValue("x")) / 70.0),
			(int) (Float.parseFloat(attributes.getValue("y")) / 70.0));
		casesSpawnPoints.add(point);
	    }
	    break;
	}
	}
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
	switch (qName) {
	case "objectgroup": {
	    isGroupObjectCaseSpawn = false;
	    break;
	}
	}
    }

    public ArrayList<Point> getCasesSpawnPoints() {
	return casesSpawnPoints;
    }
}