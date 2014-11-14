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
                { new Planet(0.5f * w, 0.5f * h, 1000, game) }, // 1
                { new Planet(0.4f * w, 0.5f * h, 500, game),
                        new Planet(0.6f * w, 0.5f * h, 500, game) }, // 2
                { new Planet(0.3f * w, 0.3f * h, 200, game),
                        new Planet(0.6f * w, 0.4f * h, 100, game),
                        new Planet(0.5f * w, 0.65f * h, 400, game) }, // 3

                { new Planet(0.5f * w, 0.5f * h, 1000, game) }, // 4
                { new Planet(0.4f * w, 0.5f * h, 500, game),
                        new Planet(0.6f * w, 0.5f * h, 500, game) }, // 5
                { new Planet(0.3f * w, 0.3f * h, 200, game),
                        new Planet(0.6f * w, 0.4f * h, 100, game),
                        new Planet(0.5f * w, 0.65f * h, 400, game) }, // 6

                { new Planet(0.4f * w, 0.5f * h, 500, game),
                        new Planet(0.6f * w, 0.5f * h, 500, game) }, // 7
                { new Planet(0.4f * w, 0.5f * h, 500, game),
                        new Planet(0.6f * w, 0.5f * h, 500, game) }, // 8
                { new Planet(0.4f * w, 0.5f * h, 500, game),
                        new Planet(0.6f * w, 0.5f * h, 500, game) }, // 9
        };
        this.levelGoals = new Goal[][] {
                { new CircularGoal(0.5f * w, 0.3f * h, 30, 1, radiusK),
                        new CircularGoal(0.5f * w, 0.7f * h, 30, 1, radiusK) }, // 1
                { new CircularGoal(0.5f * w, 0.2f * h, 30, 1, radiusK),
                        new CircularGoal(0.5f * w, 0.8f * h, 30, 1, radiusK) }, // 2
                { new CircularGoal(0.3f * w, 0.3f * h, 60, 1, radiusK),
                        new CircularGoal(0.5f * w, 0.65f * h, 100, 1, radiusK) }, // 3

                { new CircularGoal(0.5f * w, 0.2f * h, 30, 1, radiusK),
                        new CircularGoal(0.5f * w, 0.8f * h, 30, 1, radiusK),
                        new CircularGoal(0.2f * w, 0.5f * h, 30, 1, radiusK)}, // 4
                { new CircularGoal(0.3f * w, 0.5f * h, 30, 1, radiusK),
                        new CircularGoal(0.7f * w, 0.5f * h, 30, 1, radiusK) }, // 5
                { new CircularGoal(0.3f * w, 0.3f * h, 60, 1, radiusK),
                        new CircularGoal(0.6f * w, 0.4f * h, 40, 1, radiusK),
                        new CircularGoal(0.5f * w, 0.65f * h, 100, 1, radiusK)}, // 6

                { new CircularGoal(0.3f * w, 0.5f * h, 30, 1, radiusK),
                        new CircularGoal(0.5f * w, 0.5f * h, 30, 1, radiusK),
                        new CircularGoal(0.7f * w, 0.5f * h, 30, 1, radiusK) }, // 7
                { new CircularGoal(0.5f * w, 0.2f * h, 30, 2, radiusK),
                        new CircularGoal(0.5f * w, 0.5f * h, 30, 4, radiusK),
                        new CircularGoal(0.5f * w, 0.8f * h, 30, 2, radiusK) }, // 8
                { new CircularGoal(0.5f * w, 0.2f * h, 30, 1, radiusK),
                        new CircularGoal(0.5f * w, 0.5f * h, 30, 5, radiusK),
                        new CircularGoal(0.5f * w, 0.8f * h, 30, 1, radiusK),
                        new CircularGoal(0.3f * w, 0.5f * h, 30, 1, radiusK),
                        new CircularGoal(0.7f * w, 0.5f * h, 30, 1, radiusK) }, // 9
        };
    }

    public int levelCount() {
        return levelPlanets.length;
    }

    public PlanetSystem getLevelPlanets(int levelNo) {
        Planet[] planets = levelPlanets[levelNo];

        return new PlanetSystem(planets);
    }

    public Goal[] getLevelGoals(int levelNo) {
        return levelGoals[levelNo];
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
