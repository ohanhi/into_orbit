package com.ohanhi.into_orbit;

import com.badlogic.gdx.graphics.Color;
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
    }

    @Override
    public void reset() {
        super.reset();
        contacts = 0;
        animationFrames = 0;
    }

    public void drawToRenderer(ShapeRenderer renderer) {
        if (isAchieved()) {
            Color c = new Color(Const.GOAL_ACHIEVED_COLOR)
                    .mul(1 + 1.0f*animationFrames/animationLength);
            renderer.setColor(c);
            if (animationFrames > 0) {
                animationFrames--;
            }
        } else {
            renderer.setColor(Const.GOAL_COLOR);
        }
        renderer.circle(x, y, getRadius());
    }
}
