package com.ohanhi.into_orbit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    private int levelAnimationFrames = 0;
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
        int lvl = (currentLevel+1) % levelPack.levelCount();
        selectLevel(lvl);
    }

    public void selectLevel(int n) {
        currentLevel = n;
        planetSystem = levelPack.getLevelPlanets(n);
        goals = levelPack.getLevelGoals(n);
        satellite = null;
        resetLevel();
        showLevelAnimation();
    }

    private void showLevelAnimation() {
        levelAnimationFrames = 60;
    }

    private void drawLevelAnimationFrame() {
        float alpha = (float)Math.cos((levelAnimationFrames / (60 * 2)) * Math.PI);
        renderText("Level " + (currentLevel+1), alpha);
    }

    @Override
    public void create() {
        Gdx.input.setInputProcessor(gameInputProcessor);

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        TextureRegion region = null;
        font = new BitmapFont(
                new BitmapFont.BitmapFontData(Gdx.files.internal("open-sans.fnt"), false),
                region,
                false
        );
        camera = new OrthographicCamera();
    }



    @Override
    public void render() {
        gameTick++;
        final double delta = Gdx.graphics.getDeltaTime() * Const.TIME_SPEED;
        final boolean won = checkWin();

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        shapeRenderer.setProjectionMatrix(camera.combined);

        // begin a new render
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // set up alpha blending
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        // draw background gradient
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(0,0, screenWidth, screenHeight,
                Const.BG_COLOR, Const.BG_COLOR, Const.BG_COLOR_2, Const.BG_COLOR_2);
        shapeRenderer.end();


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Goal goal : goals) {
            goal.drawToRenderer(shapeRenderer);
        }
        if (satellite != null) {
            satellite.drawToRenderer(shapeRenderer);
        }
        if (touchX >= 0) {
            drawLaunch(shapeRenderer);
        }
        shapeRenderer.end();

        batch.begin();
        // draw planets
        planetSystem.drawToBatch(batch);

        if (won) renderText("Nice! Tap to continue.", 1);
        batch.end();

        // set the input processor based on whether the level is won
        if (won) {
            Gdx.input.setInputProcessor(buttonInputProcessor);
        } else {
            Gdx.input.setInputProcessor(gameInputProcessor);
        }

        // move satellite for the next frame
        if (satellite != null && !satellite.hasCollided()) {
            satellite.move(delta, gameTick, 20);
        }

        if (levelAnimationFrames > 0) {
            Gdx.input.setInputProcessor(null);
            batch.begin();
            // set up alpha blending
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            
            drawLevelAnimationFrame();
            batch.end();
            levelAnimationFrames--;
        }
    }

    private void drawLaunch(ShapeRenderer renderer) {
        renderer.setColor(Const.HERO_COLOR);
        renderer.circle(touchDownX, touchDownY, Const.SATELLITE_RADIUS * radiusK);
        renderer.setColor(Color.WHITE);
        renderer.rectLine(touchDownX, touchDownY, touchDownX*2 - touchX, touchDownY*2 - touchY, 2);
    }

    private void renderText(String text) {
        BitmapFont.TextBounds bounds = font.getBounds(text);
        float x = screenWidth / 2 - bounds.width / 2;
        float y = 2 * screenHeight / 3 + bounds.height / 2;

        font.draw(batch, text, x, y);
    }

    private void renderText(String text, float alpha) {
        font.setColor(1,1,1, alpha);
        renderText(text);
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
        satellite = null;
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
        batch.dispose();
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
        // ???
        this.dispose();
    }

    @Override
    public void resume() {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
    }
}