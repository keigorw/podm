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
import com.badlogic.gdx.utils.TimeUtils;
import java.util.Iterator;

public class podmGame extends ApplicationAdapter {
    
        // Declare Rectangles
	private Rectangle playerSprite;
        
        // Declare Batch
	private SpriteBatch batch;
        
        // Declare counters
        private int enBulletCount;
        private int frBulletCount;
        
        // Declare timers
        private static int enemyCounter=0;
        private int wave=0;
        private long lastPlayerBullet;
        private long lastEnemyBullet;
        private double counter;
        
        // Declare Textures
        private Texture playerShipTexture;
        private Texture enemyShip1;
        private Texture background;
        private Texture bulletImage;
        
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
                background = new Texture ("bg01.png");
                
                bulletImage = new Texture ("bullet.png");
                
                enemies = new Array<Enemy>();
                
                friendlyBullets = new Array<Rectangle>();
                enemyBullets = new Array<Rectangle>();
                
                enBulletCount = 0;
                frBulletCount = 0;
                
                playerSprite = new Rectangle();
                playerSprite.x = WINDOWWIDTH / 2 - 64 / 2;
                playerSprite.y = 20;
                playerSprite.width = 64;
                playerSprite.height = 64;
                
                batch = new SpriteBatch();
                enemyShip1 = new Texture ("enemyship1.png");
                playerShipTexture = new Texture ("mainship01.png");
                
                maryStageTheme = Gdx.audio.newMusic(Gdx.files.internal("bloodymaryfightthememaster2_16bit.wav"));
                maryStageTheme.setLooping(true);
                maryStageTheme.play();
                                
                orthoCam = new OrthographicCamera();
                orthoCam.setToOrtho(false, WINDOWWIDTH, WINDOWHEIGHT);
	}

	@Override
	public void render () {
            // Bullet Spawning

            System.out.println(frBulletCount);

            // Enemy spawning

            if (enemyCounter==0) {
                wave++;
                spawnEnemy(100,864);
                spawnEnemy(300,864);
            }

            // Enemy movement

            Iterator<Enemy> enemyiter = enemies.iterator();
            while(enemyiter.hasNext()) {
                Rectangle enemy = enemyiter.next();
                if (enemy.y >= WINDOWHEIGHT - 200){
                    enemy.y -= 200 * Gdx.graphics.getDeltaTime();
                }
            }

            // Enemy bullets

            for(Enemy enemy: enemies){
                if(TimeUtils.nanoTime() - enemy.getTimeSinceBullet() > 200000000){
                    enemy.setTimeSinceBullet(TimeUtils.nanoTime());
                    fireEnemyBullet((int)enemy.x,(int)enemy.y);
                }
            }

            // Bullet tracking & cleanup

            Iterator<Rectangle> bulletiter = friendlyBullets.iterator();
            if(bulletiter.hasNext()){
                while(bulletiter.hasNext()) {
                    Rectangle curBullet = bulletiter.next();
                    if (curBullet.y < WINDOWHEIGHT + 4){
                        curBullet.y += 200 * Gdx.graphics.getDeltaTime();
                    } else {
                        bulletiter.remove();
                        frBulletCount--;
                    }
                }
            }

            Iterator<Rectangle> enemybulletiter = enemyBullets.iterator();
            if(enemybulletiter.hasNext()){
                while(enemybulletiter.hasNext()) {
                    Rectangle curBullet = enemybulletiter.next();
                    if (curBullet.y < WINDOWHEIGHT){
                        curBullet.y -= 200 * Gdx.graphics.getDeltaTime();
                    } else {
                        enemybulletiter.remove();
                    }
                }
            }
            
            // Bullet Collision
            
            for(int j = 0; j < friendlyBullets.size ; j++){
                for(int i = 0; i < enemies.size ; i++){
                    Enemy enemyCheck = enemies.get(i);
                    if (enemyCheck.overlaps(friendlyBullets.get(j))){
                        enemies.removeIndex(i);
                        enemyCounter--;
                    }
                }
            }
            
            for(int j = 0; j < enemyBullets.size ; j++){
                Rectangle bulletCheck = enemyBullets.get(j);
                if (bulletCheck.overlaps(playerSprite)){
                    
                }
            }
            
            // Player actions

            if(Gdx.input.isKeyPressed(Keys.LEFT)) playerSprite.x -= 200 * Gdx.graphics.getDeltaTime();
            if(playerSprite.x < 0) playerSprite.x = 0;
            if(Gdx.input.isKeyPressed(Keys.RIGHT)) playerSprite.x += 200 * Gdx.graphics.getDeltaTime();
            if(playerSprite.x > WINDOWWIDTH - PLAYERWIDTH) playerSprite.x = WINDOWWIDTH - 64;
            if(Gdx.input.isKeyPressed(Keys.UP)) playerSprite.y += 200 * Gdx.graphics.getDeltaTime();
            if(playerSprite.y > WINDOWHEIGHT - PLAYERHEIGHT) playerSprite.y = WINDOWHEIGHT -64;
            if(Gdx.input.isKeyPressed(Keys.DOWN)) playerSprite.y -= 200 * Gdx.graphics.getDeltaTime();
            if(playerSprite.y < 20) playerSprite.y = 20;
            if(Gdx.input.isKeyPressed(Keys.SPACE) && (TimeUtils.nanoTime() - lastPlayerBullet) > 200000000) firePlayerBullet();

            // Collision Detection

            

            // Draw

            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.setProjectionMatrix(orthoCam.combined);
            batch.begin();
            batch.draw(background, 0, 0);
            batch.draw(playerShipTexture, playerSprite.x, playerSprite.y);
            for(Rectangle enemy: enemies){
                batch.draw(enemyShip1, enemy.x, enemy.y);
            }
            for(Rectangle bullet: friendlyBullets){
                batch.draw(bulletImage, bullet.x, bullet.y);
            }
            for(Rectangle enemyBullet: enemyBullets){
                batch.draw(bulletImage, enemyBullet.x, enemyBullet.y);
            }
            batch.end();

	}
        
        @Override
        public void dispose() {
            playerShipTexture.dispose();
            enemyShip1.dispose();
            maryStageTheme.dispose();
            background.dispose();
            batch.dispose();
            
        }
        
        private void spawnEnemy(int cX, int cY) {
            Enemy en = new Enemy(cX,cY);
            en.width = 64;
            en.height = 64;
            en.setTimeSinceBullet(TimeUtils.nanoTime());
            enemies.add(en);
            enemyCounter++;
        }
        
        private void firePlayerBullet(){
            Rectangle bullet = new Rectangle();
            bullet.x = playerSprite.x + (PLAYERWIDTH / 2);
            bullet.y = playerSprite.y + 40;
            bullet.width=4;
            bullet.height=4;
            friendlyBullets.add(bullet);
            lastPlayerBullet = TimeUtils.nanoTime();
            frBulletCount++;
        }
        
        private void fireEnemyBullet(int coordX, int coordY){
            Rectangle bullet = new Rectangle();
            bullet.x = coordX + (PLAYERWIDTH / 2);
            bullet.y = coordY + 40;
            bullet.width=4;
            bullet.height=4;
            enemyBullets.add(bullet);
        }
        
//        private void waveLoader(){
//            enemies.clear();
//            switch (wave){
//                case 0:
//                    Enemy enemy1 = new Enemy();
//                    enemies.add(enemy1);
//                    Enemy enemy2 = new Enemy();
//                    enemies.add(enemy2);
//
//            }
//        }
        
}
