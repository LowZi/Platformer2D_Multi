
package ch.hearc.p2.server.data;

public class ProjectileData {

    public int damage = 1;
    public float x_velocity = 0;
    public float y_velocity = 0;
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