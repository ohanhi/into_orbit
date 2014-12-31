package com.ohanhi.into_orbit;

import static java.lang.Math.pow;

/**
 * Created by ohan on 15.10.2014.
 */
public abstract class Body {

    private float x;
    private float y;
    private float m;
    private float radius;
    private float radiusK;

    public Body(float x, float y, float m, float radiusK) {
        this.x = x;
        this.y = y;
        this.m = m;
        this.radius = (float)(pow((3 * (m / Const.BODY_DENSITY)) / 4 * Math.PI, 1/3d) * 3);
        this.radiusK = radiusK;
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
        return radius * radiusK;
    }
}
