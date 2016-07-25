
package ch.hearc.p2.game.projectile;

import ch.hearc.p2.game.enums.ProjectileType;

public class ProjectileData {

    public int damage;
    public float x_velocity;
    public float y_velocity;
    public float x;
    public float y;
    public ProjectileType proj;
    
    public ProjectileData(float x2, float y2, float xVelocity, float yVelocity, ProjectileType proj) {
	x = x2;
	y = y2;
	x_velocity = xVelocity;
	y_velocity = yVelocity;
	this.proj = proj;
    }

}
