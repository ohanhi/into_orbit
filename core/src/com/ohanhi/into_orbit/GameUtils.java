package com.ohanhi.into_orbit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by ohan on 16.10.2014.
 */
public class GameUtils {
    public static final double GRAVITATIONAL_CONSTANT = 8.0d;
    public static final double COLLISION_DISTANCE = 0.0d;
    public static final double TIME_SPEED = 8;
    public static final float PATH_RADIUS = 2f;
    public static final float SATELLITE_VISUAL_RADIUS = 5f;
    public static final boolean PATH_COLORS = true;
    public static final int PATH_VERTEX_COUNT = 1000;

    public static final Skin SKIN = new Skin(Gdx.files.internal("uiskin.json"));

    public static Color hsvToRgb(float hue, float saturation, float value, float alpha) {

        int h = (int)(hue * 6);
        float f = hue * 6 - h;
        float p = value * (1 - saturation);
        float q = value * (1 - f * saturation);
        float t = value * (1 - (1 - f) * saturation);

        switch (h) {
            case 0: return new Color(value, t, p, alpha);
            case 1: return new Color(q, value, p, alpha);
            case 2: return new Color(p, value, t, alpha);
            case 3: return new Color(p, q, value, alpha);
            case 4: return new Color(t, p, value, alpha);
            case 5: return new Color(value, p, q, alpha);
            default: throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
        }
    }

    public static Color massToColor(float m, float biggestMass) {
        float hue = (float)(Math.atan(m / biggestMass) * 0.06 + 0.12) % 1;
        return hsvToRgb(hue, 1, 1, 1);
    }

    public static Color velocityToColor(float v) {
        float hue = Math.abs(1 - (v * 0.07f + 0.3f)) % 1;
        return hsvToRgb(hue, 1, 1, 0.5f);
    }
}
