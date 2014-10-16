package com.ohanhi.into_orbit;

import static java.lang.Math.log;

/**
 * Created by ohan on 15.10.2014.
 */
public abstract class Body {

    private float x;
    private float y;
    private float m;
    private float radius;

    public Body(float x, float y, float m) {
        this.x = x;
        this.y = y;
        this.m = m;
        this.radius = (float)(log(m) * 3);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getM() {
        return m;
    }

    public float getRadius() {
        return radius;
    }
}
