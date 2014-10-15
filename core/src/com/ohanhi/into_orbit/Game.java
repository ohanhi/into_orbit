package com.ohanhi.into_orbit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


/**
 * The game.
 *
 * @author ohanhi
 */


public class Game extends ApplicationAdapter {

    public static final double GRAVITATIONAL_CONSTANT = 2.0d;
    public static final double COLLISION_DISTANCE = 2.0d;
//    public static final double TIME_STEP = 15.0d;

    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private int screenWidth = 1980;
    private int screenHeight = 1080;
    private long gameTick = 0L;
    private long lastTime;

    private PlanetSystem planetSystem;
    private Satellite satellite;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();

        camera = new OrthographicCamera();
        camera.setToOrtho(true, screenWidth, screenHeight);

        lastTime = TimeUtils.nanoTime();

        planetSystem = new PlanetSystem(10, screenWidth, screenHeight);
        satellite = new Satellite(screenWidth/2, screenHeight/2, 0.01f, 3, 0, planetSystem);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameTick++;
        final double delta = calculateDelta();

        // move satellite
        if (!satellite.hasCollided())
            satellite.move(delta, gameTick);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        shapeRenderer.setProjectionMatrix(camera.combined);

        // begin a new render and draw the objects
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        planetSystem.drawToRenderer(shapeRenderer);
        satellite.drawToRenderer(shapeRenderer);
        shapeRenderer.end();
    }

    private double calculateDelta() {
        final long now = TimeUtils.nanoTime();
        final double delta = (double) (now - lastTime) * 0.0000001;
        lastTime = now;
        return delta;
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
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}