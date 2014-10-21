package com.ohanhi.into_orbit;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

/**
 * Group of planets that are contained within the canvas.
 *
 * @author ohanhi
 */
public class PlanetSystem {

    private Planet[] planets;

    public PlanetSystem(Planet[] planets) {
        this.planets = planets;
        setPlanetColors();
    }

    private void setPlanetColors() {
        for (int i = 0; i < planets.length; i++) {
            planets[i].setColor(
                    Const.PLANET_COLORS[i%Const.PLANET_COLORS.length]
            );
        }
    }

    public Planet[] getPlanets() {
        return planets;
    }

    public void drawToRenderer(ShapeRenderer renderer) {
        for (int i = 0; i < planets.length; i++) {
            planets[i].drawToRenderer(renderer);
        }
    }

}
