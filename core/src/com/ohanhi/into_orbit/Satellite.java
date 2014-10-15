package com.ohanhi.into_orbit;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

/**
 * Object that can be launched into space and will be affected by planets' gravitational fields.
 *
 * @author ohanhi
 */
public class Satellite extends Body {

    private static final float VISUAL_RADIUS = 10;

    private static double dist(double dx, double dy) {
        return Math.sqrt(dx*dx + dy*dy);
    }

    private static double dist(double x1, double y1, double x2, double y2) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        return Satellite.dist(dx, dy);
    }

    private static double gravitationalForce(float m1, float m2, double r) {
        return Game.GRAVITATIONAL_CONSTANT * (m1*m2 / (r*r));
    }

    private Color color;
    private double vx;
    private double vy;
    private float curX;
    private float curY;
    private float traveled;
    private ArrayList<Planet> planets;
    private ArrayList<float[]> pathVertices;
    private boolean collided;

    public Satellite(float x, float y, double vx, double vy, PlanetSystem system) {
        super(x, y, 0.1f);

        this.curX = x;
        this.curY = y;
        this.color = Color.MAGENTA;
        this.vx = vx;
        this.vy = vy;
        this.planets = system.getPlanets();
        this.pathVertices = new ArrayList();
        this.traveled = 0;
    }

    @Override
    public float getRadius() {
        return Satellite.VISUAL_RADIUS;
    }

    @Override
    public float getX() {
        return curX;
    }

    @Override
    public float getY() {
        return curY;
    }


    public double getTraveled() {
        return traveled;
    }

    public void drawToRenderer(ShapeRenderer renderer) {
        // draw path
        renderer.setColor(Color.WHITE);
        for (int i = 0; i < pathVertices.size(); i++) {
            float point[] = pathVertices.get(i);
            renderer.circle(point[0], point[1], 1);
        }
        // draw self
        renderer.setColor(color);
        renderer.circle(getX(), getY(), getRadius());
    }

    private boolean checkCollision(float r, float radius1, float radius2) {
        return r < ( radius1 + radius2 + 2*Game.COLLISION_DISTANCE );
    }

    public boolean move(double dt, long gameTick) {

        double ax;
        double ay;

        /*
        * For each planet:
        *   1) Check for collision
        *   2) If not collided, calculate accelerations based
        *      on gravitational force
        */
        for (int i = 0; i < planets.size(); i++) {
            Planet planet = planets.get(i);

            double dx = curX - planet.getX();
            double dy = curY - planet.getY();
            double r = Satellite.dist(dx, dy);

            collided = this.checkCollision((float)r, getRadius(), planet.getRadius());

            if (collided) {
                break;
            }

            double f = Satellite.gravitationalForce(getM(), planet.getM(), r);
            ax = -1.0d * (f * (dx / r)) / getM();
            ay = -1.0d * (f * (dy / r)) / getM();

            vx += ax * dt;
            vy += ay * dt;
        }

        // Move the satellite.
        float oldX = curX;
        float oldY = curY;
        curX += vx * dt;
        curY += vy * dt;

        // Grow traveled distance
        traveled += Satellite.dist(oldX, oldY, curX, curY);

        // Add current position to pathVertices
        if (gameTick % 5 == 0) {
            float point[] = {curX, curY};
            pathVertices.add(0, point);
        }

        return collided;
    }

    public boolean hasCollided() {
        return collided;
    }
}
