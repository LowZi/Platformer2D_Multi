package ch.hearc.p2.game.character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import ch.hearc.p2.game.enums.Facing;
import ch.hearc.p2.game.enums.Team;
import ch.hearc.p2.game.level.tile.Tile;
import ch.hearc.p2.game.physics.AABoundingRect;
import ch.hearc.p2.game.weapon.LanceGrenade;
import ch.hearc.p2.game.weapon.Mitraillette;
import ch.hearc.p2.game.weapon.Uzi;
import ch.hearc.p2.game.weapon.Weapon;

public class PlayerOnline extends Character {

    private List<Weapon> weapons;
    private HashMap<Facing, Image> jumpSprite;

    private Sound jump;
    private Sound coin;

    private long damage1;
    private long damage2;

    private int weaponIndex;
    private int height;
    private int width;
    private int point;

    private Team team;
    private String pseudo;

    /*------------------------------------------------------------------*\
    |*				Constructeurs			  	*|
    \*------------------------------------------------------------------*/

    public PlayerOnline(float x, float y, Team team, String pseudo) throws SlickException {
	super(x, y);

	damage1 = System.currentTimeMillis();
	damage2 = System.currentTimeMillis();

	point = 0;

	height = 90;
	width = 60;

	this.team = team;
	if (team == Team.BLUE)
	    loadBlueSprite();
	else
	    loadRedSprite();

	this.pseudo = pseudo;

	boundingShape = new AABoundingRect(x, y, width, height);

	accelerationSpeed = 0.002f;
	maximumSpeed = 0.55f;
	maximumFallSpeed = 0.6f;
	decelerationSpeed = 0.002f;

	life = 6;
	isDead = false;

	weapons = new LinkedList<Weapon>();
	weapons.add(new Mitraillette(0, 0));
	weapons.add(new LanceGrenade(0, 0));
	weapons.add(new Uzi(0, 0));
	weaponIndex = 0;

	jump = new Sound("ressources/audio/sound/jump.ogg");
	coin = new Sound("ressources/audio/sound/coin.ogg");
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Private		    	*|
    \*------------------------------------------------------------------*/

    private void loadRedSprite() throws SlickException {
	sprites = setSprite(new Image("ressources/character/player/red/p3_stand.png"), sprites);
	movingAnimations = setMovingAnimation(new Image[] { new Image("ressources/character/player/red/p3_walk01.png"),
		new Image("ressources/character/player/red/p3_walk02.png"),
		new Image("ressources/character/player/red/p3_walk03.png"),
		new Image("ressources/character/player/red/p3_walk04.png"),
		new Image("ressources/character/player/red/p3_walk05.png"),
		new Image("ressources/character/player/red/p3_walk06.png"),
		new Image("ressources/character/player/red/p3_walk07.png"),
		new Image("ressources/character/player/red/p3_walk08.png"),
		new Image("ressources/character/player/red/p3_walk09.png"),
		new Image("ressources/character/player/red/p3_walk10.png"),
		new Image("ressources/character/player/red/p3_walk11.png") }, 50, movingAnimations);

	jumpSprite = setSprite(new Image("ressources/character/player/red/p3_jump.png"), jumpSprite);
	hitedSprites = setSprite(new Image("ressources/character/player/red/p3_hurt.png"), hitedSprites);
	deadPicture = new Image("ressources/character/player/flag/flagRed.png");
    }

    private void loadBlueSprite() throws SlickException {
	sprites = setSprite(new Image("ressources/character/player/blue/p2_stand.png"), sprites);
	movingAnimations = setMovingAnimation(new Image[] { new Image("ressources/character/player/blue/p2_walk01.png"),
		new Image("ressources/character/player/blue/p2_walk02.png"),
		new Image("ressources/character/player/blue/p2_walk03.png"),
		new Image("ressources/character/player/blue/p2_walk04.png"),
		new Image("ressources/character/player/blue/p2_walk05.png"),
		new Image("ressources/character/player/blue/p2_walk06.png"),
		new Image("ressources/character/player/blue/p2_walk07.png"),
		new Image("ressources/character/player/blue/p2_walk08.png"),
		new Image("ressources/character/player/blue/p2_walk09.png"),
		new Image("ressources/character/player/blue/p2_walk10.png"),
		new Image("ressources/character/player/blue/p2_walk11.png") }, 50, movingAnimations);

	jumpSprite = setSprite(new Image("ressources/character/player/blue/p2_jump.png"), jumpSprite);
	hitedSprites = setSprite(new Image("ressources/character/player/blue/p2_hit.png"), hitedSprites);
	deadPicture = new Image("ressources/character/player/flag/flagBlue.png");
    }

    /*------------------------------------------------------------------*\
    |*				Methodes Public		 	  	*|
    \*------------------------------------------------------------------*/

    @Override
    public void updateBoundingShape() {
	boundingShape.updatePosition(x + 5, y + 2);
	weapons.get(weaponIndex).setPlayerFacing(this.facing);
    }

    public void shoot(int mouseX, int mouseY) throws SlickException {
	weapons.get(weaponIndex).shoot(x, y + height / 2, mouseX, mouseY);
	this.facing = weapons.get(weaponIndex).getWay();
	weapons.get(weaponIndex).setPlayerFacing(this.facing);
	boundingShape.updatePosition(x + 5, y + 2);

	if (this.facing == Facing.LEFT && x_velocity < 0.76)
	    x_velocity += 0.15;
	else if (this.facing == Facing.RIGHT && x_velocity > -0.76)
	    x_velocity -= 0.15;
    }

    public void addPoint(int i) {
	point += i;
	coin.play(1, (float) 0.3);
    }

    @Override
    public void jump() {
	if (onGround) {
	    y_velocity = -1f;
	    jump.play(1, (float) 0.5);
	}
    }

    public String getPseudo() {
	return pseudo;
    }

    @Override
    public void render(int offset_x, int offset_y) {

	time1 = System.currentTimeMillis();
	damage1 = System.currentTimeMillis();
	

	if (isDead == false && hited == false) {
	    if (movingAnimations != null && moving && onGround == true) {
		movingAnimations.get(facing).draw(x - offset_x, y - offset_y);
	    } else if (movingAnimations != null && onGround == false) {
		jumpSprite.get(facing).draw(x - offset_x, y - offset_y);
	    } else {
		sprites.get(facing).draw(x - offset_x, y - offset_y);
	    }
	} else if (hited == true && isDead == false) {

	    if (hitedMovingAnimations != null && moving) {
		hitedMovingAnimations.get(facing).draw(x - offset_x, y - offset_y);
	    } else {
		hitedSprites.get(facing).draw(x - offset_x, y - offset_y);
	    }
	    if (time1 - time2 > 50)
		hited = false;
	} else
	    deadPicture.draw(x - offset_x, y - offset_y);

    }

    @Override
    public void damage(int value) {
	if (damage1 - damage2 > 500) {
	    this.life -= value;
	    damage2 = System.currentTimeMillis();
	}
    }

    /*-----------------------*\
    |*		Set	     *|
    \*-----------------------*/

    public void setWeaponIndex(int index) {
	this.weaponIndex = index;
    }

    public void setWeapon(int index) {
	this.weaponIndex = index;
    }

    public void setPoint(int i) {
	point = i;
    }

    public void setTeam(Team team) throws SlickException {
	if (team == Team.RED)
	    loadRedSprite();
	else
	    loadBlueSprite();
	this.team = team;
    }

    /*-----------------------*\
    |*		Get	     *|
    \*-----------------------*/

    public int getWeaponIndex() {
	return weaponIndex;
    }

    public Weapon getWeapon() {
	return weapons.get(weaponIndex);
    }

    public int getPoint() {
	return point;
    }

    public Team getTeam() {
	return team;
    }

}