package com.ohanhi.into_orbit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.TimeUtils;


/**
 * The game.
 *
 * @author ohanhi
 */


public class Game extends ApplicationAdapter {


    public int getTouchDownX() {
        return touchDownX;
    }

    public int getTouchDownY() {
        return touchDownY;
    }

    public int getTouchX() {
        return touchX;
    }

    public int getTouchY() {
        return touchY;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public float getRadiusK() {
        return radiusK;
    }

    public long getGameTick() {
        return gameTick;
    }

    public Goal[] getGoals() {
        return goals;
    }

    public Satellite getSatellite() {
        return satellite;
    }

    public PlanetSystem getPlanetSystem() {
        return planetSystem;
    }

    public InputProcessor getGameInputProcessor() {
        return gameInputProcessor;
    }

    public InputProcessor getButtonInputProcessor() {
        return buttonInputProcessor;
    }

    private int touchDownX = -1;
    private int touchDownY = -1;
    private int touchX = -1;
    private int touchY = -1;
    private int currentLevel = 0;
    private LevelPack levelPack;
    private SaveManager saveManager;
    private WorldRenderer worldRenderer;

    // values that can be used elsewhere
    protected int screenWidth = 1280;
    protected int screenHeight = 800;
    protected float radiusK = 1.2f;
    protected long gameTick = 0L;
    protected PlanetSystem planetSystem;
    protected Goal[] goals;
    protected Satellite satellite;

    private InputProcessor gameInputProcessor = new InputAdapter() {
        @Override
        public boolean touchDown(int x, int y, int pointer, int button) {
            touchDownX = x;
            touchDownY = y;
            return true;
        }

        @Override
        public boolean touchDragged (int screenX, int screenY, int pointer) {
            touchX = screenX;
            touchY = screenY;
            return true;
        }

        @Override
        public boolean touchUp(int x, int y, int pointer, int button) {
            launchSatellite(touchDownX, touchDownY, touchDownX*2 - x, touchDownY*2 - y);

            touchDownX = -1;
            touchDownY = -1;
            touchX = -1;
            touchY = -1;
            return true;
        }
    };

    private InputProcessor buttonInputProcessor = new InputAdapter() {
        @Override
        public boolean touchDown(int x, int y, int pointer, int button) {
            nextLevel();
            return true;
        }
    };

    public void nextLevel() {
        // TODO: make this smarter
        int lvl = (currentLevel+1) % levelPack.levelCount();
        selectLevel(lvl);
    }

    public void selectLevel(int n) {
        if (Const.DEBUG) n = 0;
        if (planetSystem != null) planetSystem.dispose();
        planetSystem = levelPack.getLevelPlanets(n);
        goals = levelPack.getLevelGoals(n);
        satellite = null;
        resetLevel();
        worldRenderer.startLevelAnimation();
        currentLevel = n;
        saveManager.saveDataValue(Const.SAVE_LEVEL, n);
    }


    private void init() {
        worldRenderer = new WorldRenderer(this);
        saveManager = new SaveManager(true);
        Integer level = saveManager.loadDataValue(Const.SAVE_LEVEL, Integer.class);
        if (level != null) currentLevel = level.intValue();
    }

    @Override
    public void create() {
        Gdx.input.setInputProcessor(gameInputProcessor);

        init();
    }



    @Override
    public void render() {
        gameTick++;
        final double delta = Gdx.graphics.getDeltaTime() * Const.TIME_SPEED;


        // render world
        worldRenderer.render();

        // set the input processor based on whether the level is won
        if (isWon()) {
            Gdx.input.setInputProcessor(buttonInputProcessor);
        } else {
            Gdx.input.setInputProcessor(gameInputProcessor);
        }

        // move satellite for the next frame
        if (satellite != null && !satellite.hasCollided()) {
            satellite.move(delta, gameTick, 20);
        }
    }


    public Boolean isWon() {
        Boolean allDone = true;
        for (Goal goal : goals) {
            allDone = allDone && goal.isAchieved();
        }

        return allDone;
    }

    private void resetLevel() {
        for (Goal goal : goals) {
            goal.reset();
        }
        satellite = null;
    }

    public void resetIfNotWon() {
        if (!isWon()) {
            resetLevel();
        }
    }

    private void launchSatellite(int x1, int y1, int x2, int y2) {
        resetIfNotWon();
        float k = 0.02f / radiusK;
        float vx = k * (x2 - x1);
        float vy = k * (y2 - y1);
        satellite = new Satellite(x1, y1, vx, vy, this);
    }

    @Override
    public void dispose() {
        // dispose of all the native resources
        if (planetSystem != null) {
            planetSystem.dispose();
            planetSystem = null;
        }
        if (levelPack != null) {
            levelPack.dispose();
            levelPack = null;
        }
        if (worldRenderer != null) {
            worldRenderer.dispose();
            worldRenderer = null;
        }
    }

    @Override
    public void resize(final int width, final int height) {
        radiusK = radiusK * width / screenWidth;
        screenWidth = width;
        screenHeight = height;

        if (levelPack != null) {
            levelPack.dispose();
        }
        goals = new Goal[0];
        levelPack = new LevelPack(this);
        planetSystem = levelPack.getLevelPlanets(currentLevel);
        satellite = null;
        if(worldRenderer != null) worldRenderer.resize(width, height);

        selectLevel(currentLevel);
    }

    @Override
    public void pause() {
        dispose();
    }

    @Override
    public void resume() {
        dispose();
        init();
        levelPack = new LevelPack(this);
        selectLevel(currentLevel);
    }
}