package com.ohanhi.into_orbit;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by ohan on 17.10.2014.
 */
public class CircularGoal extends Goal {
    private float x;
    private float y;
    private float radius;
    private float radiusK;
    private int contactGoal;
    private int contacts = 0;
    private int animationFrames = 0;
    private final int animationLength = 8;

    public CircularGoal(float x, float y, float radius, int contactGoal, float radiusK) {
        super();
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.radiusK = radiusK;
        this.contactGoal = contactGoal;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius * radiusK;
    }

    @Override
    public boolean achieve() {
        boolean firstTime = super.achieve();
        if (firstTime) animationFrames = animationLength;
        return firstTime;
    }

    public void addContact() {
        contacts++;

        if (contacts >= contactGoal) this.achieve();
        if (!isAchieved()) animationFrames = animationLength;
    }

    @Override
    public void reset() {
        super.reset();
        contacts = 0;
        animationFrames = 0;
    }

    public float getDrawRadius() {
        return getRadius() + animationFrames;
    }

    public int contactsRemaining() {
        if (isAchieved()) return 0;
        return contactGoal - contacts;
    }

    public void renderNumber(SpriteBatch batch, BitmapFont font, int screenHeight) {
        if (contactsRemaining() > 1) {
            String text = "+" + (contactsRemaining()-1);
            font.setScale(0.5f);
            BitmapFont.TextBounds bounds = font.getBounds(text);
            float x = getX() - bounds.width / 2;
            float y = getY() - bounds.height / 2;
            y = screenHeight - y;
            font.setColor(Const.GOAL_FONT_COLOR);
            font.draw(batch, text, x, y);
            font.setScale(1);
        }
    }

    public void drawToRenderer(ShapeRenderer renderer) {
        if (isAchieved()) {
            float ratio = 1.0f*animationFrames/animationLength;
            Color c = Const.blendColor(Const.GOAL_COLOR, Const.GOAL_ACHIEVED_COLOR, ratio);

            renderer.setColor(c);
        } else {
            renderer.setColor(Const.GOAL_COLOR);
        }
        renderer.circle(x, y, getDrawRadius());

        if (animationFrames > 0) {
            animationFrames--;
        }
    }

}
