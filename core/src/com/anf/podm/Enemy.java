
package com.anf.podm;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

class Enemy extends Rectangle{
    static int enemyCount;

    int enemyX = 0;
    int enemyY = 0;
    Texture enemyTexture;
    
    void Enemy(int enemyType){
        
        this.enemyTexture = new Texture ("enemyship1.png");
        
    }
    
}
