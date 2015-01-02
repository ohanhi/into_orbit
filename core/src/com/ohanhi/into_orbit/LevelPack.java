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

        if (!Const.DEBUG) {
            this.levelPlanets = new Planet[][]{

                    // single planet

                    {new Planet(0.5f * w, 0.5f * h, 1000, game)}, // simple
                    {new Planet(0.5f * w, 0.5f * h, 1000, game)}, // simple, 3 goals
                    {new Planet(0.5f * w, 0.5f * h, 3000, game)}, // ellipse


                    // two planets

                    {new Planet(0.4f * w, 0.5f * h, 500, game),
                            new Planet(0.6f * w, 0.5f * h, 500, game)}, // left-right
                    {new Planet(0.4f * w, 0.5f * h, 500, game),
                            new Planet(0.6f * w, 0.5f * h, 500, game)}, // up-down
                    {new Planet(0.4f * w, 0.5f * h, 500, game),
                            new Planet(0.6f * w, 0.5f * h, 500, game)}, // left-center-right
                    {new Planet(0.4f * w, 0.5f * h, 500, game),
                            new Planet(0.6f * w, 0.5f * h, 500, game)}, // up-center-down
                    {new Planet(0.4f * w, 0.5f * h, 500, game),
                            new Planet(0.6f * w, 0.5f * h, 500, game)}, // easy "fan"
                    {new Planet(0.4f * w, 0.5f * h, 500, game),
                            new Planet(0.6f * w, 0.5f * h, 500, game)}, // harder fan
                    {new Planet(0.4f * w, 0.5f * h, 500, game),
                            new Planet(0.6f * w, 0.5f * h, 500, game)}, // tricky fan
                    {new Planet(0.4f * w, 0.5f * h, 500, game),
                            new Planet(0.6f * w, 0.5f * h, 500, game)}, // four directions

                    // one planet, one moon

                    {new Planet(0.4f * w, 0.5f * h, 100, game),
                            new Planet(0.5f * w, 0.5f * h, 1000, game)}, // x
                    {new Planet(0.4f * w, 0.5f * h, 100, game),
                            new Planet(0.5f * w, 0.5f * h, 1000, game)}, // y

                    // three planets

                    {new Planet(0.3f * w, 0.3f * h, 200, game),
                            new Planet(0.6f * w, 0.4f * h, 100, game),
                            new Planet(0.5f * w, 0.65f * h, 400, game)}, // 3
                    {new Planet(0.3f * w, 0.3f * h, 200, game),
                            new Planet(0.6f * w, 0.4f * h, 100, game),
                            new Planet(0.5f * w, 0.65f * h, 400, game)}, // 6
            };
            this.levelGoals = new Goal[][]{

                    // single planet

                    {new CircularGoal(0.5f * w, 0.3f * h, 30, 1, radiusK),
                            new CircularGoal(0.5f * w, 0.7f * h, 30, 1, radiusK)}, // simple
                    {new CircularGoal(0.5f * w, 0.2f * h, 30, 2, radiusK),
                            new CircularGoal(0.5f * w, 0.8f * h, 30, 1, radiusK),
                            new CircularGoal(0.2f * w, 0.5f * h, 30, 1, radiusK)}, // simple, 3 goals
                    {new CircularGoal(0.1f * w, 0.5f * h, 30, 2, radiusK),
                            new CircularGoal(0.5f * w, 0.5f * h, 80, 2, radiusK)}, // ellipse


                    // two planets

                    {new CircularGoal(0.5f * w, 0.2f * h, 30, 1, radiusK),
                            new CircularGoal(0.5f * w, 0.8f * h, 30, 1, radiusK)}, // left-right
                    {new CircularGoal(0.3f * w, 0.5f * h, 30, 1, radiusK),
                            new CircularGoal(0.7f * w, 0.5f * h, 30, 1, radiusK)}, // up-down
                    {new CircularGoal(0.3f * w, 0.5f * h, 30, 1, radiusK),
                            new CircularGoal(0.5f * w, 0.5f * h, 30, 1, radiusK),
                            new CircularGoal(0.7f * w, 0.5f * h, 30, 1, radiusK)}, // left-center-right
                    {new CircularGoal(0.5f * w, 0.2f * h, 30, 2, radiusK),
                            new CircularGoal(0.5f * w, 0.5f * h, 30, 4, radiusK),
                            new CircularGoal(0.5f * w, 0.8f * h, 30, 2, radiusK)}, // up-center-down
                    {new CircularGoal(0.5f * w, 0.2f * h, 40, 1, radiusK),
                            new CircularGoal(0.5f * w, 0.5f * h, 40, 5, radiusK),
                            new CircularGoal(0.5f * w, 0.8f * h, 40, 1, radiusK),
                            new CircularGoal(0.7f * w, 0.5f * h, 40, 1, radiusK)}, // easy "fan"
                    {new CircularGoal(0.5f * w, 0.2f * h, 40, 1, radiusK),
                            new CircularGoal(0.5f * w, 0.5f * h, 40, 1, radiusK),
                            new CircularGoal(0.5f * w, 0.8f * h, 40, 1, radiusK),
                            new CircularGoal(0.65f * w, 0.3f * h, 40, 1, radiusK),
                            new CircularGoal(0.65f * w, 0.7f * h, 40, 1, radiusK),
                            new CircularGoal(0.7f * w, 0.5f * h, 40, 1, radiusK)}, // harder fan
                    {new CircularGoal(0.5f * w, 0.2f * h, 40, 1, radiusK),
                            new CircularGoal(0.5f * w, 0.5f * h, 40, 5, radiusK),
                            new CircularGoal(0.5f * w, 0.8f * h, 40, 1, radiusK),
                            new CircularGoal(0.65f * w, 0.3f * h, 40, 1, radiusK),
                            new CircularGoal(0.65f * w, 0.7f * h, 40, 1, radiusK),
                            new CircularGoal(0.7f * w, 0.5f * h, 40, 5, radiusK)}, // tricky fan
                    {new CircularGoal(0.5f * w, 0.2f * h, 30, 2, radiusK),
                            new CircularGoal(0.5f * w, 0.8f * h, 30, 2, radiusK),
                            new CircularGoal(0.3f * w, 0.5f * h, 30, 2, radiusK),
                            new CircularGoal(0.7f * w, 0.5f * h, 30, 2, radiusK)}, // four directions

                    // one planet, one moon

                    {new CircularGoal(0.5f * w, 0.3f * h, 30, 1, radiusK),
                            new CircularGoal(0.5f * w, 0.7f * h, 30, 1, radiusK)}, // x
                    {new CircularGoal(0.5f * w, 0.2f * h, 30, 1, radiusK),
                            new CircularGoal(0.5f * w, 0.8f * h, 30, 1, radiusK)}, // y

                    // three planets

                    {new CircularGoal(0.3f * w, 0.3f * h, 60, 1, radiusK),
                            new CircularGoal(0.5f * w, 0.65f * h, 100, 1, radiusK)}, // 3
                    {new CircularGoal(0.3f * w, 0.3f * h, 60, 1, radiusK),
                            new CircularGoal(0.6f * w, 0.4f * h, 40, 1, radiusK),
                            new CircularGoal(0.5f * w, 0.65f * h, 100, 1, radiusK)}, // 6
            };
        } else {
            this.levelPlanets = new Planet[][] {
                 {
                        new Planet(100, 100, 500, game),
                        new Planet(200, 100, 500, game),
                        new Planet(300, 100, 500, game),
                        new Planet(400, 100, 500, game),
                        new Planet(500, 100, 500, game),
                        new Planet(600, 100, 500, game),
                        new Planet(700, 100, 500, game),
                        new Planet(800, 100, 500, game),
                        new Planet(900, 100, 500, game),
                        new Planet(1000, 100, 500, game),
                        new Planet(1100, 100, 500, game),
                        new Planet(1200, 100, 500, game),
                        new Planet(1300, 100, 500, game),
                        new Planet(1400, 100, 500, game),
                        new Planet(1500, 100, 500, game),
                        new Planet(1600, 100, 500, game),
                        new Planet(1700, 100, 500, game),

                        new Planet(100, 300, 500, game),
                        new Planet(200, 300, 500, game),
                        new Planet(300, 300, 500, game),
                        new Planet(400, 300, 500, game),
                        new Planet(500, 300, 500, game),
                        new Planet(600, 300, 500, game),
                        new Planet(700, 300, 500, game),
                        new Planet(800, 300, 500, game),
                        new Planet(900, 300, 500, game),
                        new Planet(1000, 300, 500, game),
                        new Planet(1100, 300, 500, game),
                        new Planet(1200, 300, 500, game),
                        new Planet(1300, 300, 500, game),
                        new Planet(1400, 300, 500, game),
                        new Planet(1500, 300, 500, game),
                        new Planet(1600, 300, 500, game),
                        new Planet(1700, 300, 500, game),

                        new Planet(100, 500, 500, game),
                        new Planet(200, 500, 500, game),
                        new Planet(300, 500, 500, game),
                        new Planet(400, 500, 500, game),
                        new Planet(500, 500, 500, game),
                        new Planet(600, 500, 500, game),
                        new Planet(700, 500, 500, game),
                        new Planet(800, 500, 500, game),
                        new Planet(900, 500, 500, game),
                        new Planet(1000, 500, 500, game),
                        new Planet(1100, 500, 500, game),
                        new Planet(1200, 500, 500, game),
                        new Planet(1300, 500, 500, game),
                        new Planet(1400, 500, 500, game),
                        new Planet(1500, 500, 500, game),
                        new Planet(1600, 500, 500, game),
                        new Planet(1700, 500, 500, game),

                        new Planet(100, 700, 500, game),
                        new Planet(200, 700, 500, game),
                        new Planet(300, 700, 500, game),
                        new Planet(400, 700, 500, game),
                        new Planet(500, 700, 500, game),
                        new Planet(600, 700, 500, game),
                        new Planet(700, 700, 500, game),
                        new Planet(800, 700, 500, game),
                        new Planet(900, 700, 500, game),
                        new Planet(1000, 700, 500, game),
                        new Planet(1100, 700, 500, game),
                        new Planet(1200, 700, 500, game),
                        new Planet(1300, 700, 500, game),
                        new Planet(1400, 700, 500, game),
                        new Planet(1500, 700, 500, game),
                        new Planet(1600, 700, 500, game),
                        new Planet(1700, 700, 500, game),

                        new Planet(100, 900, 500, game),
                        new Planet(200, 900, 500, game),
                        new Planet(300, 900, 500, game),
                        new Planet(400, 900, 500, game),
                        new Planet(500, 900, 500, game),
                        new Planet(600, 900, 500, game),
                        new Planet(700, 900, 500, game),
                        new Planet(800, 900, 500, game),
                        new Planet(900, 900, 500, game),
                        new Planet(1000, 900, 500, game),
                        new Planet(1100, 900, 500, game),
                        new Planet(1200, 900, 500, game),
                        new Planet(1300, 900, 500, game),
                        new Planet(1400, 900, 500, game),
                        new Planet(1500, 900, 500, game),
                        new Planet(1600, 900, 500, game),
                        new Planet(1700, 900, 500, game),
                 }
            };
            this.levelGoals = new Goal[][] {
                    { new CircularGoal(0.5f * w, 0.5f * h, 40, 1, radiusK) }
            };
        }
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
