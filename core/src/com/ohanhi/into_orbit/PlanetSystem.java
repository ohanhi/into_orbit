package com.ohanhi.into_orbit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
        setPlanetTextures();
    }

    private void setPlanetColors() {
        for (int i = 0; i < planets.length; i++) {
            int n = (int)(Math.random() * Const.PLANET_COLORS.length);
            planets[i].setColor(Const.PLANET_COLORS[n]);
        }
    }

    private void setPlanetTextures() {
        for (int i = 0; i < planets.length; i++) {
            int n = (int)(Math.random() * Const.PLANET_TEXTURE_FILES.length);
            planets[i].setTexture(new Texture(Const.PLANET_TEXTURE_FILES[n]));
        }
    }

    public Planet[] getPlanets() {
        return planets;
    }

    public void dispose() {
        for (Planet planet : planets) {
            planet.dispose();
        }
        planets = null;
    }

    public void resume() {
        setPlanetTextures();
    }

}
