package com.ohanhi.into_orbit;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Planet is a solid body of mass that does not move.
 *
 * @author ohanhi
 */
public class Planet extends Body {


    private Color color;
    private int animationFrames;

    /**
     * Creates a new planet. Radius is log of mass.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @param m mass
     */
    public Planet(float x, float y, float m, float radiusK) {
        super(x, y, m, radiusK);

        this.color = Const.PLANET_COLORS[0];
    }

    public void setColor(float biggestMass) {
        this.color = Const.massToColor(getM(), biggestMass);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void twinkle() {
        animationFrames = 10;
    }

    public void drawToRenderer(final ShapeRenderer renderer) {
        renderer.setColor(color);
        renderer.circle(getX(), getY(), getRadius() + animationFrames);
        if (animationFrames > 0) animationFrames--;
    }

}
