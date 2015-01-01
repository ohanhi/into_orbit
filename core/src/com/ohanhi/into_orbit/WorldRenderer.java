package com.ohanhi.into_orbit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by ohan on 1.1.2015.
 */
public class WorldRenderer {

    private OrthographicCamera camera;
    private Game game;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture backgroundTexture;

    private int levelAnimationFrames = 0;

    public WorldRenderer(Game game) {
        this.game = game;
        this.camera = new OrthographicCamera(16, 9);
        init();
    }
    
    public void init() {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont(
                new BitmapFont.BitmapFontData(Gdx.files.internal("open-sans.fnt"), false),
                (TextureRegion)null,
                false
        );
        camera = new OrthographicCamera();
        backgroundTexture = new Texture(Gdx.files.internal("grain.png"));
    }

    private void setUpAlphaBlending() {
        // set up alpha blending
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    private void drawLaunch() {
        setUpAlphaBlending();
        shapeRenderer.setColor(Const.HERO_COLOR);
        shapeRenderer.circle(game.getTouchDownX(), game.getTouchDownY(), Const.SATELLITE_RADIUS * game.radiusK);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rectLine(game.getTouchDownX(), game.getTouchDownY(),
                game.getTouchDownX()*2 - game.getTouchX(), game.getTouchDownY()*2 - game.getTouchY(), 2);
    }

    public void startLevelAnimation() {
        levelAnimationFrames = 60;
    }

    private void drawLevelAnimationFrame() {
        //float alpha = (float)Math.cos((levelAnimationFrames / (60f * 2)) * Math.PI - Math.PI);
        float alpha = 1;
        renderText("Level " + (game.getCurrentLevel() + 1), alpha);
    }
    
    public void renderText(String text) {
        BitmapFont.TextBounds bounds = font.getBounds(text);
        float x = game.screenWidth / 2 - bounds.width / 2;
        float y = 2 * game.screenHeight / 3 + bounds.height / 2;
        setUpAlphaBlending();
        font.draw(batch, text, x, y);
    }

    public void renderText(String text, float alpha) {
        font.setColor(1,1,1, alpha);
        renderText(text);
    }
    
    public void resize(int screenWidth, int screenHeight) {
        camera.setToOrtho(true, screenWidth, screenHeight);
    }

    public void render() {
        final boolean won = game.isWon();
        
        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        camera.setToOrtho(true);
        shapeRenderer.setProjectionMatrix(camera.combined);
        camera.setToOrtho(false);
        batch.setProjectionMatrix(camera.combined);

        // begin a new render
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, game.screenWidth, game.screenHeight);
        batch.end();

        setUpAlphaBlending();

        // draw background gradient
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // draw goal circles
        for (Goal goal : game.goals) {
            goal.drawToRenderer(shapeRenderer);
        }
        shapeRenderer.end();

        batch.begin();
        // draw goal numbers
        for (Goal _goal : game.goals) {
            CircularGoal goal = (CircularGoal)_goal;
            goal.renderNumber(batch, font, game.screenHeight);
        }
        batch.end();

        setUpAlphaBlending();

        // draw satellite / launch
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if (game.satellite != null) {
            game.satellite.drawToRenderer(shapeRenderer);
        }
        if (game.getTouchX() >= 0) {
            drawLaunch();
        }
        shapeRenderer.end();

        batch.begin();
        // draw planets
        setUpAlphaBlending();
        game.planetSystem.drawToBatch(batch);


        if (won) renderText("Nice! Tap to continue.", 1);



        if (levelAnimationFrames > 0) {
            Gdx.input.setInputProcessor(null);
            setUpAlphaBlending();
            drawLevelAnimationFrame();
            levelAnimationFrames--;
        }
        batch.end();
    }

    public void dispose() {
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
            shapeRenderer = null;
        }
        if (batch != null) {
            batch.dispose();
            batch = null;
        }
        if (font != null) {
            font.dispose();
            font = null;
        }
    }
}
