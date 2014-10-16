package com.ohanhi.into_orbit;

import java.util.ArrayList;

/**
 * Created by ohan on 15.10.2014.
 */
public class LevelPack {

    private int w;
    private int h;

    public LevelPack(int w, int h) {
        this.w = w;
        this.h = h;
    }

    public PlanetSystem getLevel(int levelNo) {
        if (levelNo < 1 || levelNo > 3) {
            throw new IllegalArgumentException("Level number out of bounds");
        }
        ArrayList<Planet> planets = new ArrayList<Planet>();

        switch (levelNo) {
            case 1:
                planets.add(new Planet(0.5f * w, 0.5f * h, 1000));
                break;
            case 2:
                planets.add(new Planet(0.4f * w, 0.5f * h, 500));
                planets.add(new Planet(0.6f * w, 0.5f * h, 500));
                break;
            case 3:
                planets.add(new Planet(0.3f * w, 0.3f * h, 200));
                planets.add(new Planet(0.6f * w, 0.4f * h, 100));
                planets.add(new Planet(0.5f * w, 0.65f * h, 400));
        }

        return new PlanetSystem(planets);
    }

}
