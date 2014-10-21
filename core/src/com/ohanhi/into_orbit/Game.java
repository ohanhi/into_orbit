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
    private LevelPack levelPack;

    private GenericDialog dialog;
    private Stage stage;

    // values that can be used elsewhere
    protected int screenWidth = 1280;
    protected int screenHeight = 800;
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

    /**
     * Called when user taps a dialog button.
     */
    public void dialogInput(String title, String input) {
        if (title.equals("Select level")) {
            int n = Integer.parseInt(input, 10);
            selectLevel(n);
        }
    }

    public void createLevelSelectDialog() {
        String buttons[] = { "1", "2", "3" };

        dialog = new GenericDialog(this, "Select level", "", buttons);
        stage = new Stage();
        dialog.show(stage);

        Gdx.input.setInputProcessor(stage);
    }

    public void selectLevel(int n) {
        planetSystem = levelPack.getLevelPlanets(n);
        goals = levelPack.getLevelGoals(n);
    }

    @Override
    public void create() {
        Gdx.input.setInputProcessor(gameInputProcessor);

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();
        camera = new OrthographicCamera();

        createLevelSelectDialog();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameTick++;
        final double delta = Gdx.graphics.getDeltaTime() * Const.TIME_SPEED;

        // move satellite
        if (satellite != null && !satellite.hasCollided()) {
            satellite.move(delta, gameTick, 20);
        }

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        shapeRenderer.setProjectionMatrix(camera.combined);

        // begin a new render and draw the objects
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < goals.length; i++) {
            goals[i].drawToRenderer(shapeRenderer);
        }
        planetSystem.drawToRenderer(shapeRenderer);
        if (satellite != null) {
            satellite.drawToRenderer(shapeRenderer);
        }
        if (touchDownX >= 0 && touchX >= 0) {
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.circle(touchDownX, touchDownY, Const.SATELLITE_VISUAL_RADIUS);
            shapeRenderer.rectLine(touchDownX, touchDownY, touchDownX*2 - touchX, touchDownY*2 - touchY, 2);
        }
        shapeRenderer.end();

        if (satellite != null && satellite.hasCollided()) {
            String text = "Traveled " + satellite.getTraveledRound()
                    + " with max velocity of " + satellite.getMaxVelocityRound();
            batch.begin();
            font.draw(batch, text, 30, 30);
            batch.end();
        }

        // set the input processor based on whether there is a dialog open
        if (stage.getActors().size < 1) {
            Gdx.input.setInputProcessor(gameInputProcessor);
        } else {
            Gdx.input.setInputProcessor(stage);
        }
        stage.draw();
    }

    private void launchSatellite(int x1, int y1, int x2, int y2) {
        float k = 0.01f;
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
        screenWidth = width;
        screenHeight = height;
        camera.setToOrtho(true, screenWidth, screenHeight);
        lastTime = TimeUtils.nanoTime();

        if (levelPack != null) {
            levelPack.dispose();
        }
        goals = new Goal[0];
        levelPack = new LevelPack(screenWidth, screenHeight);
        planetSystem = levelPack.getLevelPlanets(3);
        satellite = null;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}