package com.ohanhi.into_orbit;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

/**
 * Group of planets that are contained within the canvas.
 *
 * @author ohanhi
 */
public class PlanetSystem {

    private ArrayList<Planet> planets;
    private int screenWidth;
    private int screenHeight;

    private ArrayList<Planet> randomizePlanets(final int n) {
        ArrayList<Planet> list = new ArrayList<Planet>(n);

        for (int i = 0; i < n; i++) {
            float x = (float)Math.random() * screenWidth;
            float y = (float)Math.random() * screenHeight;
            float m = (float)Math.random() * 300 + 80;
            Planet planet = new Planet(x, y, m);
            list.add(planet);
        }

        return list;
    }

    public PlanetSystem(final int n, final int screenWidth, final int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.planets = this.randomizePlanets(n);
    }

    public ArrayList<Planet> getPlanets() {
        return planets;
    }

    public void drawToRenderer(ShapeRenderer renderer) {
        for (int i = 0; i < planets.size(); i++) {
            planets.get(i).drawToRenderer(renderer);
        }
    }

}
