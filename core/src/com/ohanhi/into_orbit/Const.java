package com.ohanhi.into_orbit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;

/**
 * Created by ohan on 16.10.2014.
 */
public class Const {
    public static final boolean DEBUG = true;

    public static final double GRAVITATIONAL_CONSTANT = 4.0d;
    public static final double COLLISION_DISTANCE = 0.0d;
    public static final double TIME_SPEED = 8;
    public static final float PATH_RADIUS = 1;
    public static final float SATELLITE_RADIUS = 1.6f;
    public static final float SATELLITE_REACH_RADIUS = 20f;
    public static final float BODY_DENSITY = 5f;
    public static final boolean PATH_COLORS = false;
    public static final int PATH_VERTEX_COUNT = 10000;
    public static final int SCREEN_BORDER_THRESHOLD = 2000;

    public static final String SAVE_LEVEL = "level";

    public static final Color GOAL_COLOR = new Color(1, 1, 1, 0.2f);
    public static final Color GOAL_FONT_COLOR = new Color(1, 1, 1, 0.4f);
    public static final Color GOAL_ACHIEVED_COLOR = new Color(1, 1, 1, 0);
    public static final Color HERO_COLOR = Color.valueOf("ffffffcc");
    public static final Color HERO_REACH_COLOR = GOAL_COLOR;
    public static final Color[] PLANET_COLORS = {
            Color.valueOf("FF5252"), // Red A200
            Color.valueOf("E91E63"), // Pink 500
            Color.valueOf("E040FB"), // Purple A200
            Color.valueOf("40C4FF"), // Light blue A200
            Color.valueOf("00E5FF"), // Cyan A400
            Color.valueOf("64FFDA"), // Teal A200
            Color.valueOf("69F0AE"), // Green A200
            Color.valueOf("00E676"), // Green A400
            Color.valueOf("FFEA00"), // Yellow A400
            Color.valueOf("FFC400"), // Amber A400
            Color.valueOf("FFAB40"), // Orange A200
            Color.valueOf("E0E0E0"), // Grey 300
            Color.valueOf("FFFFFF"), // White
            Color.valueOf("CFD8DC") // Blue gray 100
    };

    public static final FileHandle[] PLANET_TEXTURE_FILES = {
            Gdx.files.internal("planet-1-texture-filled.png"),
            Gdx.files.internal("planet-2-texture-filled.png"),
            Gdx.files.internal("planet-3-texture-filled.png"),
            Gdx.files.internal("planet-4-texture-filled.png")
    };
    public static final FileHandle SAVE_FILE = Gdx.files.local("save.json");

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
            default: throw new RuntimeException("Something went wrong when converting from HSV to RGB."
                    + " Input was " + hue + ", " + saturation + ", " + value + ", " + alpha);
        }
    }

    private static float blend(float v1, float v2, float ratio) {
        return v1 * ratio + v2 * (1-ratio);
    }

    public static Color blendColor(Color c1, Color c2, float ratio) {

        return new Color(
                blend(c1.r, c2.r, ratio),
                blend(c1.g, c2.g, ratio),
                blend(c1.b, c2.b, ratio),
                blend(c1.a, c2.a, ratio)
        );
    }

    public static Color pathVertexColor(float v, int idx) {
        float alpha = 1.0f * (PATH_VERTEX_COUNT - idx) / PATH_VERTEX_COUNT;
        if (PATH_COLORS) {
            float value = Math.abs(1 - (v * 0.07f + 0.3f)) % 1;
            return hsvToRgb(value, 1, 1, alpha);
        } else {
            return hsvToRgb(0, 0, 0.8f, alpha);
        }
    }
}
