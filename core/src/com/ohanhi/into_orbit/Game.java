package com.ohanhi.into_orbit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


/**
 * The game.
 *
 * @author ohanhi
 */


public class Game extends ApplicationAdapter {

    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font;
    private long lastTime;
    private int touchDownX = -1;
    private int touchDownY = -1;
    private int touchX = -1;
    private int touchY = -1;
    private int currentLevel = 0;
    private LevelPack levelPack;

    // values that can be used elsewhere
    protected int screenWidth = 1280;
    protected int screenHeight = 800;
    protected float radiusK = 1;
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
        selectLevel( (currentLevel+1) % levelPack.levelCount() );
    }

    public void selectLevel(int n) {
        currentLevel = n;
        planetSystem = levelPack.getLevelPlanets(n);
        goals = levelPack.getLevelGoals(n);
        satellite = null;
        resetLevel();
    }

    @Override
    public void create() {
        Gdx.input.setInputProcessor(gameInputProcessor);

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();
        camera = new OrthographicCamera();
    }

    @Override
    public void render() {
        gameTick++;
        final double delta = Gdx.graphics.getDeltaTime() * Const.TIME_SPEED;

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        shapeRenderer.setProjectionMatrix(camera.combined);

        // begin a new render and draw the objects
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        // set up alpha blending
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(0,0, screenWidth, screenHeight,
                Const.BG_COLOR, Const.BG_COLOR, Const.BG_COLOR_2, Const.BG_COLOR_2);

        for (int i = 0; i < goals.length; i++) {
            goals[i].drawToRenderer(shapeRenderer);
        }
        if (satellite != null) {
            satellite.drawToRenderer(shapeRenderer);
        }
        if (touchDownX >= 0 && touchX >= 0) {
            shapeRenderer.setColor(Const.HERO_COLOR);
            shapeRenderer.circle(touchDownX, touchDownY, Const.SATELLITE_RADIUS * radiusK);
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.rectLine(touchDownX, touchDownY, touchDownX*2 - touchX, touchDownY*2 - touchY, 2);
        }
        shapeRenderer.end();

        batch.begin();
        // draw planets
        planetSystem.drawToBatch(batch);
        batch.end();

        batch.begin();
        if (satellite != null && satellite.hasCollided()) {
            String text = "Traveled " + satellite.getTraveledRound()
                    + " with max velocity of " + satellite.getMaxVelocityRound();
            font.draw(batch, text, 30, 30);
        }

        // set the input processor based on whether the level is won
        if (checkWin()) {
            font.draw(batch, "Well done! Tap screen for next level >",
                    screenWidth*0.5f - 120, screenHeight*0.4f);
            Gdx.input.setInputProcessor(buttonInputProcessor);
        } else {
            Gdx.input.setInputProcessor(gameInputProcessor);
        }
        batch.end();

        // move satellite for the next frame
        if (satellite != null && !satellite.hasCollided()) {
            satellite.move(delta, gameTick, 20);
        }
    }

    public Boolean checkWin() {
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
    }

    public void resetIfNotWon() {
        if (!checkWin()) {
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
        shapeRenderer.dispose();
    }

    @Override
    public void resize(final int width, final int height) {
        radiusK = radiusK * width / screenWidth;
        screenWidth = width;
        screenHeight = height;
        camera.setToOrtho(true, screenWidth, screenHeight);
        lastTime = TimeUtils.nanoTime();

        if (levelPack != null) {
            levelPack.dispose();
        }
        goals = new Goal[0];
        levelPack = new LevelPack(this);
        planetSystem = levelPack.getLevelPlanets(currentLevel);
        satellite = null;

        selectLevel(currentLevel);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}