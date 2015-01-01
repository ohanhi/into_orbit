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

import java.util.ArrayList;

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

    private void renderSatellite() {
        // draw path
        Satellite sat = game.getSatellite();
        ArrayList<float[]> pathVertices = sat.getPathVertices();
        for (int i = 0; i < pathVertices.size(); i++) {
            float point[] = pathVertices.get(i);
            Color color = Const.pathVertexColor(point[2], i);
            shapeRenderer.setColor(color);
            shapeRenderer.circle(point[0], point[1], Const.PATH_RADIUS);
        }
        // draw self
        if (!sat.hasCollided()) {
            shapeRenderer.setColor(Const.HERO_REACH_COLOR);
            shapeRenderer.circle(sat.getX(), sat.getY(), Const.SATELLITE_REACH_RADIUS * game.radiusK);
            shapeRenderer.setColor(Const.HERO_COLOR);
            shapeRenderer.circle(sat.getX(), sat.getY(), sat.getRadius());
            //if (pathVertices.size() > 2) drawTriangle(renderer, pathVertices.get(1));
        }
    }

    private void renderPlanets(Planet[] planets) {
        // colored circles
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Planet planet : planets) {
            int animationFrames = planet.animationFramePop();
            shapeRenderer.setColor(planet.getColor());
            shapeRenderer.circle(planet.getX(), planet.getY(), planet.getRadius() + animationFrames);
        }
        shapeRenderer.end();

        // translucent textures
        batch.begin();
        for (Planet planet :planets) {
            int animationFrames = planet.getAnimationFrames();
            Texture texture = planet.getTexture();
            int textureWidth = texture.getWidth();
            int textureHeight = texture.getHeight();
            float scale = 2 * ((planet.getRadius() + animationFrames) * 2) / textureWidth;
            float x = planet.getX() - textureWidth * 0.5f * scale;
            float y = game.screenHeight - planet.getY() - textureHeight * 0.5f * scale; // flipped
            batch.draw(texture,
                    x,
                    y,
                    0, 0,
                    textureWidth + animationFrames, textureHeight + animationFrames, // width,height
                    scale, scale, // scale
                    0f, // rotation
                    0, 0, // source anchor
                    textureWidth, textureHeight, // source size
                    false, false
            );
        }
        batch.end();
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
            renderSatellite();
        }
        if (game.getTouchX() >= 0) {
            drawLaunch();
        }
        shapeRenderer.end();


        // draw planets
        setUpAlphaBlending();
        renderPlanets(game.planetSystem.getPlanets());


        batch.begin();
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
