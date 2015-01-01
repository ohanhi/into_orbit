package com.ohanhi.into_orbit;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;

/**
 * Planet is a solid body of mass that does not move.
 *
 * @author ohanhi
 */
public class Planet extends Body {


    private Color color;
    private int animationFrames;

    public int animationFramePop() {
        if (animationFrames > 0) return animationFrames--;
        else return 0;
    }

    public int getAnimationFrames() {
        return animationFrames;
    }

    private Texture texture;
    private Game game;

    /**
     * Creates a new planet. Radius is log of mass.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @param m mass
     */
    public Planet(float x, float y, float m, Game game) {
        super(x, y, m, game.radiusK);
        this.game = game;
        this.color = Const.PLANET_COLORS[0];
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Texture getTexture() {
        return texture;
    }

    public Color getColor() {
        return color;
    }

    public void twinkle() {
        animationFrames = 10;
    }


    public void dispose() {
        texture.dispose();
    }

}
