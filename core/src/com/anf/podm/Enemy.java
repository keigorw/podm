
package com.anf.podm;

import com.badlogic.gdx.math.Rectangle;

@SuppressWarnings("serial")
public class Enemy extends Rectangle {
    
    long timeSinceBullet;
    
    Enemy (int cX, int cY){
        this.timeSinceBullet = 0;
        this.x = cX;
        this.y = cY;
    }

    public long getTimeSinceBullet() {
        return timeSinceBullet;
    }

    public void setTimeSinceBullet(long timeSinceBullet) {
        this.timeSinceBullet = timeSinceBullet;
    }
    
}
