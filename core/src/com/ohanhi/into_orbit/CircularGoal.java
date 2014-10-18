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
    private int contactGoal;
    private int contacts = 0;

    public CircularGoal(float x, float y, float radius, int contactGoal) {
        super();
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.contactGoal = contactGoal;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }

    public void addContact() {
        contacts++;
        if (contacts >= contactGoal) this.achieve();
    }

    public void drawToRenderer(ShapeRenderer renderer) {
        renderer.setColor(Color.LIGHT_GRAY);
        renderer.circle(x, y, radius);
    }
}
