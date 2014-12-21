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

                // single planet

                { new Planet(0.5f * w, 0.5f * h, 1000, game) }, // simple
                { new Planet(0.5f * w, 0.5f * h, 1000, game) }, // simple, 3 goals
                { new Planet(0.5f * w, 0.5f * h, 3000, game) }, // ellipse


                // two planets

                { new Planet(0.4f * w, 0.5f * h, 500, game),
                        new Planet(0.6f * w, 0.5f * h, 500, game) }, // left-right
                { new Planet(0.4f * w, 0.5f * h, 500, game),
                        new Planet(0.6f * w, 0.5f * h, 500, game) }, // up-down
                { new Planet(0.4f * w, 0.5f * h, 500, game),
                        new Planet(0.6f * w, 0.5f * h, 500, game) }, // left-center-right
                { new Planet(0.4f * w, 0.5f * h, 500, game),
                        new Planet(0.6f * w, 0.5f * h, 500, game) }, // up-center-down
                { new Planet(0.4f * w, 0.5f * h, 500, game),
                        new Planet(0.6f * w, 0.5f * h, 500, game) }, // easy "fan"
                { new Planet(0.4f * w, 0.5f * h, 500, game),
                        new Planet(0.6f * w, 0.5f * h, 500, game) }, // harder fan
                { new Planet(0.4f * w, 0.5f * h, 500, game),
                        new Planet(0.6f * w, 0.5f * h, 500, game) }, // tricky fan
                { new Planet(0.4f * w, 0.5f * h, 500, game),
                        new Planet(0.6f * w, 0.5f * h, 500, game) }, // four directions

                // one planet, one moon

                { new Planet(0.4f * w, 0.5f * h, 100, game),
                        new Planet(0.5f * w, 0.5f * h, 1000, game) }, // x
                { new Planet(0.4f * w, 0.5f * h, 100, game),
                        new Planet(0.5f * w, 0.5f * h, 1000, game) }, // y

                // three planets

                { new Planet(0.3f * w, 0.3f * h, 200, game),
                        new Planet(0.6f * w, 0.4f * h, 100, game),
                        new Planet(0.5f * w, 0.65f * h, 400, game) }, // 3
                { new Planet(0.3f * w, 0.3f * h, 200, game),
                        new Planet(0.6f * w, 0.4f * h, 100, game),
                        new Planet(0.5f * w, 0.65f * h, 400, game) }, // 6

        };
        this.levelGoals = new Goal[][] {

                // single planet

                { new CircularGoal(0.5f * w, 0.3f * h, 30, 1, radiusK),
                        new CircularGoal(0.5f * w, 0.7f * h, 30, 1, radiusK) }, // simple
                { new CircularGoal(0.5f * w, 0.2f * h, 30, 2, radiusK),
                        new CircularGoal(0.5f * w, 0.8f * h, 30, 1, radiusK),
                        new CircularGoal(0.2f * w, 0.5f * h, 30, 1, radiusK)}, // simple, 3 goals
                { new CircularGoal(0.1f * w, 0.5f * h, 30, 2, radiusK),
                        new CircularGoal(0.5f * w, 0.5f * h, 80, 2, radiusK) }, // ellipse


                // two planets

                { new CircularGoal(0.5f * w, 0.2f * h, 30, 1, radiusK),
                        new CircularGoal(0.5f * w, 0.8f * h, 30, 1, radiusK) }, // left-right
                { new CircularGoal(0.3f * w, 0.5f * h, 30, 1, radiusK),
                        new CircularGoal(0.7f * w, 0.5f * h, 30, 1, radiusK) }, // up-down
                { new CircularGoal(0.3f * w, 0.5f * h, 30, 1, radiusK),
                        new CircularGoal(0.5f * w, 0.5f * h, 30, 1, radiusK),
                        new CircularGoal(0.7f * w, 0.5f * h, 30, 1, radiusK) }, // left-center-right
                { new CircularGoal(0.5f * w, 0.2f * h, 30, 2, radiusK),
                        new CircularGoal(0.5f * w, 0.5f * h, 30, 4, radiusK),
                        new CircularGoal(0.5f * w, 0.8f * h, 30, 2, radiusK) }, // up-center-down
                { new CircularGoal(0.5f * w, 0.2f * h, 40, 1, radiusK),
                        new CircularGoal(0.5f * w, 0.5f * h, 40, 5, radiusK),
                        new CircularGoal(0.5f * w, 0.8f * h, 40, 1, radiusK),
                        new CircularGoal(0.7f * w, 0.5f * h, 40, 1, radiusK) }, // easy "fan"
                { new CircularGoal(0.5f * w, 0.2f * h, 40, 1, radiusK),
                        new CircularGoal(0.5f * w, 0.5f * h, 40, 1, radiusK),
                        new CircularGoal(0.5f * w, 0.8f * h, 40, 1, radiusK),
                        new CircularGoal(0.65f * w, 0.3f * h, 40, 1, radiusK),
                        new CircularGoal(0.65f * w, 0.7f * h, 40, 1, radiusK),
                        new CircularGoal(0.7f * w, 0.5f * h, 40, 1, radiusK) }, // harder fan
                { new CircularGoal(0.5f * w, 0.2f * h, 40, 1, radiusK),
                        new CircularGoal(0.5f * w, 0.5f * h, 40, 5, radiusK),
                        new CircularGoal(0.5f * w, 0.8f * h, 40, 1, radiusK),
                        new CircularGoal(0.65f * w, 0.3f * h, 40, 1, radiusK),
                        new CircularGoal(0.65f * w, 0.7f * h, 40, 1, radiusK),
                        new CircularGoal(0.7f * w, 0.5f * h, 40, 5, radiusK) }, // tricky fan
                { new CircularGoal(0.5f * w, 0.2f * h, 30, 2, radiusK),
                        new CircularGoal(0.5f * w, 0.8f * h, 30, 2, radiusK),
                        new CircularGoal(0.3f * w, 0.5f * h, 30, 2, radiusK),
                        new CircularGoal(0.7f * w, 0.5f * h, 30, 2, radiusK) }, // four directions

                // one planet, one moon

                { new CircularGoal(0.5f * w, 0.3f * h, 30, 1, radiusK),
                        new CircularGoal(0.5f * w, 0.7f * h, 30, 1, radiusK) }, // x
                { new CircularGoal(0.5f * w, 0.2f * h, 30, 1, radiusK),
                        new CircularGoal(0.5f * w, 0.8f * h, 30, 1, radiusK) }, // y

                // three planets

                { new CircularGoal(0.3f * w, 0.3f * h, 60, 1, radiusK),
                        new CircularGoal(0.5f * w, 0.65f * h, 100, 1, radiusK) }, // 3
                { new CircularGoal(0.3f * w, 0.3f * h, 60, 1, radiusK),
                        new CircularGoal(0.6f * w, 0.4f * h, 40, 1, radiusK),
                        new CircularGoal(0.5f * w, 0.65f * h, 100, 1, radiusK)}, // 6

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
