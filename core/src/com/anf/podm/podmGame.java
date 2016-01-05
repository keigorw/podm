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
	private SpriteBatch batch;
        
        // Declare timers
        private int enemyCounter;
        private int wave;
        
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
        private final int PLAYERHEIGHT = 64;
        private final int PLAYERWIDTH = 64;
        
        // Declare Arrays
        private Array<Enemy> enemies;
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
                
                wave=0;
                
                orthoCam = new OrthographicCamera();
                orthoCam.setToOrtho(false, WINDOWWIDTH, WINDOWHEIGHT);
	}

	@Override
	public void render () {

                
                // Enemy spawning
                
                if (Enemy.enemyCount==0) {
                    wave++;
                    
                } else {
                    
                };
                
                // Player movement
                
                if(Gdx.input.isKeyPressed(Keys.LEFT)) playerSprite.x -= 200 * Gdx.graphics.getDeltaTime();
                if(playerSprite.x < 0) playerSprite.x = 0;
                if(Gdx.input.isKeyPressed(Keys.RIGHT)) playerSprite.x += 200 * Gdx.graphics.getDeltaTime();
                if(playerSprite.x > WINDOWWIDTH - PLAYERWIDTH) playerSprite.x = WINDOWWIDTH - 64;
                if(Gdx.input.isKeyPressed(Keys.UP)) playerSprite.y += 200 * Gdx.graphics.getDeltaTime();
                if(playerSprite.y > WINDOWHEIGHT - PLAYERHEIGHT) playerSprite.y = WINDOWHEIGHT -64;
                if(Gdx.input.isKeyPressed(Keys.DOWN)) playerSprite.y -= 200 * Gdx.graphics.getDeltaTime();
                if(playerSprite.y < 20) playerSprite.y = 20;
                
                // Draw
                
                Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                batch.setProjectionMatrix(orthoCam.combined);
		batch.begin();
		batch.draw(playerShipTexture, playerSprite.x, playerSprite.y);
		batch.end();
                
	}
        
        private void waveLoader(){
            enemies.clear();
            switch (wave){
                case 0:
                    Enemy enemy1 = new Enemy();
                    enemies.add(enemy1);
                    Enemy enemy2 = new Enemy();
                    enemies.add(enemy2);

            }
        }
        
}
