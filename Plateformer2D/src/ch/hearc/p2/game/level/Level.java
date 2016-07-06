package ch.hearc.p2.game.level;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import ch.hearc.p2.game.WindowGame;
import ch.hearc.p2.game.character.Character;
import ch.hearc.p2.game.character.Player;
import ch.hearc.p2.game.level.object.Objective;
import ch.hearc.p2.game.level.tile.AirTile;
import ch.hearc.p2.game.level.tile.HalfDownTile;
import ch.hearc.p2.game.level.tile.HalfTile;
import ch.hearc.p2.game.level.tile.SolidTile;
import ch.hearc.p2.game.level.tile.Tile;

public class Level {

    private TiledMap map;
    private Player player;

    private ArrayList<Character> characters;
    private ArrayList<LevelObject> levelObjects;

    private Tile[][] tiles;
    private Tile[][] limite;
    private Tile[][] end;

    private Image background;

    // ShakeFields
    public static final float SHAKE_DECAY = 0.2f;
    public static final int SHAKE_DELAY = 10;
    public static final boolean SHAKE_SNAP = false;
    public static final int SHAKE_INTENSITY = 19;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			        *|
    \*------------------------------------------------------------------*/

    public Level(String level, Player player) throws SlickException {
	map = new TiledMap("ressources/level/" + level + ".tmx");
	background = new Image("ressources/background/" + map.getMapProperty("back.png", "back.png"));
	characters = new ArrayList<Character>();
	levelObjects = new ArrayList<LevelObject>();

	this.player = player;
	addCharacter(player);
	loadTileMap();
	loadLimite();
	loadEnd();

    }

    /*------------------------------------------------------------------*\
    |*				Methodes Private	    		*|
    \*------------------------------------------------------------------*/

    private void loadTileMap() {
	// create an array to hold all the tiles in the map
	tiles = new Tile[map.getWidth()][map.getHeight()];

	int layerIndex = map.getLayerIndex("CollisionLayer");

	if (layerIndex == -1) {
	    // we can clean this up later with an exception if we want, but
	    // because we make the maps ourselfs this will suffice for now
	    System.err.println("Map does not have the layer \"CollisionLayer\"");
	    System.exit(0);
	}

	// loop through the whole map
	for (int x = 0; x < map.getWidth(); x++) {
	    for (int y = 0; y < map.getHeight(); y++) {

		// get the tile
		int tileID = map.getTileId(x, y, layerIndex);

		Tile tile = null;

		// and check what kind of tile it is (
		switch (map.getTileProperty(tileID, "tileType", "solid")) {
		case "air":
		    tile = new AirTile(x, y, "");
		    break;
		case "half":
		    tile = new HalfTile(x, y, "");
		    break;
		case "halfDown":
		    tile = new HalfDownTile(x, y, "");
		    break;
		default:
		    tile = new SolidTile(x, y, "");
		    break;
		}
		tiles[x][y] = tile;
	    }
	}

    }

    private void loadLimite() {
	// create an array to hold all the tiles in the map
	limite = new Tile[map.getWidth()][map.getHeight()];

	int layerIndex = map.getLayerIndex("Limite");

	if (layerIndex == -1) {
	    // we can clean this up later with an exception if we want, but
	    // because we make the maps ourselfs this will suffice for now
	    System.err.println("Map does not have the layer \"Limite\"");
	    System.exit(0);
	}

	// loop through the whole map
	for (int x = 0; x < map.getWidth(); x++) {
	    for (int y = 0; y < map.getHeight(); y++) {

		// get the tile
		int tileID = map.getTileId(x, y, layerIndex);

		Tile tile = null;

		// and check what kind of tile it is (
		switch (map.getTileProperty(tileID, "tileType", "solid")) {
		case "air":
		    tile = new AirTile(x, y, "");
		    break;
		default:
		    tile = new SolidTile(x, y, "");
		    break;
		}
		limite[x][y] = tile;
	    }
	}
    }

    private void loadEnd() {
	// create an array to hold all the tiles in the map
	end = new Tile[map.getWidth()][map.getHeight()];

	int layerIndex = map.getLayerIndex("End");

	if (layerIndex == -1) {
	    // we can clean this up later with an exception if we want, but
	    // because we make the maps ourselfs this will suffice for now
	    System.err.println("Map does not have the layer \"End\"");
	    System.exit(0);
	}

	// loop through the whole map
	for (int x = 0; x < map.getWidth(); x++) {
	    for (int y = 0; y < map.getHeight(); y++) {

		// get the tile
		int tileID = map.getTileId(x, y, layerIndex);

		Tile tile = null;

		// and check what kind of tile it is (
		switch (map.getTileProperty(tileID, "tileType", "solid")) {
		case "air":
		    tile = new AirTile(x, y, "");
		    break;
		default:
		    tile = new SolidTile(x, y, "");
		    break;
		}
		end[x][y] = tile;
	    }
	}
    }

    private void renderBackground() {

	// first calculate the maximum amount we can "scroll" the background
	// image before we have the rightmore or bottom most pixel on the screen
	float backgroundXScrollValue = (background.getWidth() - WindowGame.WINDOW_WIDTH / WindowGame.SCALE_W);
	float backgroundYScrollValue = (background.getHeight() - WindowGame.WINDOW_HEIGHT / WindowGame.SCALE_H);

	// we do the same for the map
	// By changing the size of the tiled (200 instead of 128), the
	// background render smoother
	float mapXScrollValue = ((float) map.getWidth() * 200 - WindowGame.WINDOW_WIDTH / WindowGame.SCALE_W);
	float mapYScrollValue = ((float) map.getHeight() * 200 - WindowGame.WINDOW_HEIGHT / WindowGame.SCALE_H);

	// and now calculate the factor we have to multiply the offset with,
	// making sure we multiply the offset by -1 to get it to negative
	float scrollXFactor = backgroundXScrollValue / mapXScrollValue * -1;
	float scrollYFactor = backgroundYScrollValue / mapYScrollValue * -1;
	// and now draw it using the factor and the offset to see where we start
	// drawing
	background.draw(this.getXOffset() * scrollXFactor, this.getYOffset() * scrollYFactor);
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		    		*|
    \*------------------------------------------------------------------*/

    public void addCharacter(Character c) {
	characters.add(c);
    }

    public void removeCharacter(Character c) {
	characters.remove(c);
    }

    public void removeCharacter(ArrayList<Character> list) {
	characters.removeAll(list);
    }

    public ArrayList<Character> getCharacters() {
	return characters;
    }

    public void render() {
	// render the map first
	int offset_x = getXOffset();
	int offset_y = getYOffset();

	renderBackground();

	// then render the map
	map.render(-(offset_x % 70), -(offset_y % 70), offset_x / 70, offset_y / 70, map.getTileWidth(),
		map.getHeight());

	// and then render the characters on top of the map

	for (LevelObject obj : levelObjects) {
	    obj.render(offset_x, offset_y);
	}

	for (Character c : characters) {
	    c.render(offset_x, offset_y);
	}

    }

    public void render(float shakeX, float shakeY) {
	// render the map first
	int offset_x = getXOffset();
	int offset_y = getYOffset();
	offset_x += shakeX;
	offset_y += shakeY;

	renderBackground();

	// then render the map
	map.render(-(offset_x % 70), -(offset_y % 70), offset_x / 70, offset_y / 70, map.getTileWidth(),
		map.getHeight());

	// Rendu des personnages mais pas du joueur
	for (Character c : characters) {
	    if (!(c instanceof Player))
		c.render(offset_x, offset_y);
	}

	// On afficher le joueur après les autres perso pour être au premier
	// plan
	player.render(offset_x, offset_y);

	for (LevelObject obj : levelObjects) {
	    obj.render(offset_x, offset_y);
	}
    }

    public void removeObject(LevelObject obj) {
	levelObjects.remove(obj);
    }

    public void removeObjects(ArrayList<LevelObject> objects) {
	levelObjects.removeAll(objects);
    }

    public void addLevelObject(LevelObject objective) {
	levelObjects.add(objective);
    }

    public void addLevelObject(ArrayList<LevelObject> addQueue) {
	levelObjects.addAll(addQueue);

    }

    /*-----------------------*\
    |*		Get	     *|
    \*-----------------------*/

    public Tile[][] getTiles() {
	return tiles;
    }

    public Player getPlayer() {
	return player;
    }

    public Tile[][] getLimite() {
	return limite;
    }

    public Tile[][] getEnd() {
	return end;
    }

    public int getYOffset() {
	int offset_y = 0;

	int half_heigth = WindowGame.BASE_WINDOW_HEIGHT / 2;

	int maxY = map.getHeight() * 70 - half_heigth;

	if (player.getY() < half_heigth) {
	    offset_y = 0;
	} else if (player.getY() > maxY) {
	    offset_y = maxY - half_heigth;
	} else {
	    offset_y = (int) (player.getY() - half_heigth);
	}

	return offset_y;
    }

    public int getXOffset() {
	int offset_x = 0;

	// the first thing we are going to need is the half-width of the screen,
	// to calculate if the player is in the middle of our screen
	int half_width = WindowGame.BASE_WINDOW_WIDTH / 2;

	// next up is the maximum offset, this is the most right side of the
	// map, minus half of the screen offcourse
	int maxX = map.getWidth() * 70 - half_width;

	// now we have 3 cases here
	if (player.getX() < half_width) {
	    // the player is between the most left side of the map, which is
	    // zero and half a screen size which is 0+half_screen
	    offset_x = 0;
	} else if (player.getX() > maxX) {
	    // the player is between the maximum point of scrolling and the
	    // maximum width of the map
	    // the reason why we substract half the screen again is because we
	    // need to set our offset to the topleft position of our screen
	    offset_x = maxX - half_width;
	} else {
	    // the player is in between the 2 spots, so we set the offset to the
	    // player, minus the half-width of the screen
	    offset_x = (int) (player.getX() - half_width);
	}

	return offset_x;
    }

    public ArrayList<Objective> getObjectives() {
	ArrayList<Objective> objectives = new ArrayList<Objective>();
	for (LevelObject obj : levelObjects) {
	    if (obj instanceof Objective)
		objectives.add((Objective) obj);
	}
	return objectives;
    }

    public ArrayList<LevelObject> getLevelObjects() {
	return levelObjects;
    }

    public TiledMap getMap() {
	return map;
    }

    /*-----------------------*\
    |*		Set	     *|
    \*-----------------------*/

    public void setBackground(Image i) {
	background = i;
    }

}