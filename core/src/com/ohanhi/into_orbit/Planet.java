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

    public void setColor(float biggestMass) {
        this.color = Const.massToColor(getM(), biggestMass);
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
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

    public void drawToBatch(SpriteBatch batch) {
        float scale = (getRadius() * 2) / texture.getWidth();
        float x = getX() - texture.getWidth()*0.5f*scale;
        float y = game.screenHeight - getY() - texture.getHeight()*0.5f*scale; // flipped
        batch.draw(texture,
                x,
                y,
                0,0,
                texture.getWidth(), texture.getHeight(), // width,height
                scale, scale, // scale
                0f, // rotation
                0, 0, // source anchor
                texture.getWidth(), texture.getHeight(), // source size
                false, false
        );
    }

}
