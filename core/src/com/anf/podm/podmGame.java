package com.anf.podm;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class podmGame extends ApplicationAdapter {
    
        // Declare Rectangles
	private Rectangle playerSprite;
        
        // Declare Batch
	SpriteBatch batch;

        // Declare Textures
        private Texture playerShipTexture;
        private Texture enemyShip1;
        
        // Declare Sounds
        private Music pirateSpaceTheme;
        private Music maryStageTheme;
        
        // Declare Camera
        private OrthographicCamera orthoCam;
        
        // Declare Window constants
        private final int WINDOWWIDTH = 480;
        private final int WINDOWHEIGHT = 800;
        
        // Declare Arrays
        private Array<Rectangle> enemies;
        private Array<Rectangle> enemyBullets;
        private Array<Rectangle> friendlyBullets;
        
	@Override
	public void create () {
                playerSprite = new Rectangle();
                playerSprite.x = WINDOWWIDTH / 2 - 64 / 2;
                playerSprite.y = 20;
                playerSprite.width = 64;
                playerSprite.height = 64;
                
		batch = new SpriteBatch();
                enemyShip1 = new Texture ("enemyship1.png");
                playerShipTexture = new Texture ("playership.png");
                maryStageTheme = Gdx.audio.newMusic(Gdx.files.internal("bloodymaryfightthememaster2_16bit.wav"));
                maryStageTheme.setLooping(true);
                maryStageTheme.play();
                
                orthoCam = new OrthographicCamera();
                orthoCam.setToOrtho(false, WINDOWWIDTH, WINDOWHEIGHT);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                batch.setProjectionMatrix(orthoCam.combined);
		batch.begin();
		batch.draw(playerShipTexture, playerSprite.x, playerSprite.y);
		batch.end();
                
                if(Gdx.input.isKeyPressed(Keys.LEFT)) playerSprite.x -= 200 * Gdx.graphics.getDeltaTime();
                if(Gdx.input.isKeyPressed(Keys.RIGHT)) playerSprite.x += 200 * Gdx.graphics.getDeltaTime();
                if(playerSprite.x < 0) playerSprite.x = 0;
                if(playerSprite.x > WINDOWWIDTH - 64) playerSprite.x = WINDOWHEIGHT - 64;
                
	}
}
