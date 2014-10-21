package com.ohanhi.into_orbit;

/**
 * Created by ohan on 15.10.2014.
 */
public class LevelPack {

    private int w;
    private int h;

    private final Planet levelPlanets[][];

    private final Goal levelGoals[][];

    public LevelPack(Game game) {
        this.w = game.screenWidth;
        this.h = game.screenHeight;
        float radiusK = game.radiusK;
        this.levelPlanets = new Planet[][] {
                { new Planet(0.5f * w, 0.5f * h, 1000, radiusK) },
                { new Planet(0.4f * w, 0.5f * h, 500, radiusK),
                        new Planet(0.6f * w, 0.5f * h, 500, radiusK) },
                { new Planet(0.3f * w, 0.3f * h, 200, radiusK),
                        new Planet(0.6f * w, 0.4f * h, 100, radiusK),
                        new Planet(0.5f * w, 0.65f * h, 400, radiusK) }
        };
        this.levelGoals = new Goal[][] {
                { new CircularGoal(0.4f * w, 0.5f * h, 30, 1, radiusK),
                        new CircularGoal(0.6f * w, 0.5f * h, 30, 1, radiusK) },
                { new CircularGoal(0.5f * w, 0.2f * h, 30, 1, radiusK),
                        new CircularGoal(0.5f * w, 0.8f * h, 30, 1, radiusK) },
                { new CircularGoal(0.3f * w, 0.3f * h, 60, 1, radiusK),
                        new CircularGoal(0.5f * w, 0.65f * h, 100, 1, radiusK) },
        };
    }

    public PlanetSystem getLevelPlanets(int levelNo) {
        Planet[] planets = levelPlanets[levelNo - 1];

        return new PlanetSystem(planets);
    }

    public Goal[] getLevelGoals(int levelNo) {
        return levelGoals[levelNo - 1];
    }

    public void dispose() {
        for (int i = 0; i < levelPlanets.length; i++) {
            for (int j = 0; j < levelPlanets[i].length; j++) {
                levelPlanets[i][j] = null;
            }
        }
        for (int i = 0; i < levelGoals.length; i++) {
            for (int j = 0; j < levelGoals[i].length; j++) {
                levelGoals[i][j] = null;
            }
        }
    }

}
