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

    private static double dist(double dx, double dy) {
        return Math.sqrt(dx * dx + dy * dy);
    }

    private static double dist(double x1, double y1, double x2, double y2) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        return Satellite.dist(dx, dy);
    }

    private static double gravitationalForce(float m1, float m2, double r) {
        return Const.GRAVITATIONAL_CONSTANT * (m1*m2 / (r*r));
    }

    private Color color = Const.HERO_COLOR;
    private double vx;
    private double vy;
    private float curX;
    private float curY;
    private float traveled;
    private Planet[] planets;

    public ArrayList<float[]> getPathVertices() {
        return pathVertices;
    }

    private ArrayList<float[]> pathVertices;
    private boolean collided;
    private double maxVelocity = 0;
    protected Game game;
    private Goal lastMoveInsideGoal = null;

    public Satellite(float x, float y, double vx, double vy, Game game) {
        super(x, y, 0.1f, game.radiusK);

        this.curX = x;
        this.curY = y;
        this.vx = vx;
        this.vy = vy;
        this.game = game;
        this.planets = game.planetSystem.getPlanets();
        this.pathVertices = new ArrayList<float[]>(Const.PATH_VERTEX_COUNT);
        this.traveled = 0;
    }

    @Override
    public float getRadius() {
        return Const.SATELLITE_RADIUS * game.radiusK;
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

    public int getTraveledRound() {
        return (int)Math.round(getTraveled());
    }

    public double getMaxVelocity() {
        return maxVelocity;
    }

    public int getMaxVelocityRound() {
        return (int)Math.round(getMaxVelocity());
    }

    private boolean checkCollision(float r, float radius1, float radius2) {
        return r < ( radius1 + radius2 + 2* Const.COLLISION_DISTANCE );
    }

    private boolean isWithinBoundaries() {
        return ( curX > -1 * Const.SCREEN_BORDER_THRESHOLD
                && curX < game.screenWidth + Const.SCREEN_BORDER_THRESHOLD
                && curY > -1 * Const.SCREEN_BORDER_THRESHOLD
                && curY < game.screenHeight + Const.SCREEN_BORDER_THRESHOLD );
    }

    public boolean move(double dt, long gameTick, int subsamples) {

        for (int i = 0; i < subsamples; i++) {
            double ax;
            double ay;

            if (!isWithinBoundaries()) {
                collided = true;
                game.resetIfNotWon();
                return false;
            }

            /*
            * For each Goal:
            *   1) Check for "collision"
            *   2) Bump up contact count on collision
            */
            for (Goal goal : game.goals) {
                if (goal instanceof CircularGoal) {
                    CircularGoal g = ((CircularGoal) goal);
                    float r = (float)Satellite.dist(curX, curY, g.getX(), g.getY());
                    if (checkCollision(r, Const.SATELLITE_REACH_RADIUS * game.radiusK, g.getRadius())) {
                        if (lastMoveInsideGoal == null) {
                            lastMoveInsideGoal = g;
                            g.addContact();
                        }
                    } else if (lastMoveInsideGoal == g) {
                        lastMoveInsideGoal = null;
                    }
                }
            }

            /*
            * For each planet:
            *   1) Check for collision
            *   2) If not collided, calculate accelerations based
            *      on gravitational force
            */
            for (Planet planet : planets) {

                double dx = curX - planet.getX();
                double dy = curY - planet.getY();
                double r = Satellite.dist(dx, dy);

                collided = checkCollision((float)r, getRadius(), planet.getRadius());

                if (collided) {
                    planet.twinkle();
                    game.resetIfNotWon();
                    break;
                }

                double f = Satellite.gravitationalForce(getM(), planet.getM(), r);
                ax = -1.0d * (f * (dx / r)) / getM();
                ay = -1.0d * (f * (dy / r)) / getM();

                vx += ax * dt;
                vy += ay * dt;
            }

            if (!collided) {

                // Move the satellite.
                float oldX = curX;
                float oldY = curY;
                curX += vx * dt;
                curY += vy * dt;

                float v = (float)Satellite.dist(vx, vy);
                if (v > maxVelocity) maxVelocity = v;

                // Grow traveled distance
                traveled += Satellite.dist(oldX, oldY, curX, curY);

                // Add current position to pathVertices
                if (i+1 == subsamples && gameTick % 2 == 0) {
                    if (pathVertices.size() >= Const.PATH_VERTEX_COUNT) {
                        pathVertices.remove(pathVertices.size()-1);
                    }
                    float point[] = {curX, curY, v};
                    pathVertices.add(0, point);
                }

            }
        }

        return !collided;
    }

    public boolean hasCollided() {
        return collided;
    }
}
