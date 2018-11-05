package jp.gr.java_conf.pgmer.wanko;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Wanko {

    public static final int WANKO_TOUCH_WIDTH = 100; // タッチ可能幅
    public static final int WANKO_TOUCH_HEIGHT = 100; // タッチ可能高さ
    protected static final double STOP_PROB = 0.2;
    protected static final int WALK_TIME = 30;
    protected static final int WALK = 3;
    
    protected WankoNumber wankonum;
    
    protected Bitmap wanko;
    protected float density; // density
    protected float posX; // 設置位置のX座標
    protected float posY; // 設置位置のY座標
    protected int walk_time = 0;
    protected int walkX = WALK;
    protected int walkY = WALK;
    protected final int width;
    protected final int height;
    
    public Wanko(int number, Bitmap wanko, float posX, float posY, float density, int width, int height) {
    	this.wanko = wanko;
    	this.wankonum = new WankoNumber(number);
        this.posX = posX;
        this.posY = posY;
        this.density = density;
        this.width = width;
        this.height = height;
    }
    
    public boolean checkHit(float hitX, float hitY) {
        if (this.posX > hitX || hitX > this.posX + WANKO_TOUCH_WIDTH * density
                || this.posY > hitY
                || hitY > this.posY + WANKO_TOUCH_HEIGHT * density) {
            // 領域外をタッチされた
            return false;
        }
        if (!wankonum.check()) {
        	return false;
        }
        return true;
    }
    
    public void walk() {
    	if (walk_time < 0) {
    		Random r = new Random();
    		if (r.nextDouble() < STOP_PROB) walkX = 0;
    		else walkX = r.nextDouble() < 0.5 ? WALK : -WALK;
    		if (r.nextDouble() < STOP_PROB) walkY = 0;
    		else walkY = r.nextDouble() < 0.5 ? WALK : -WALK;
    		walk_time = new Random().nextInt(WALK_TIME);
    	}
    	
		if ( (posX < 0 && walkX < 0) || (posX+WANKO_TOUCH_WIDTH*density> width && walkX > 0) ) walkX *= -1;
		if ( (posY < 0 && walkY < 0) || (posY+WANKO_TOUCH_HEIGHT*density > height && walkY > 0) ) walkY *= -1;
		
    	posX += walkX;
    	posY += walkY;
    	walk_time--;
    }
    
    public void kill() {
    	wankonum.setNextNumber();
    }
    
    public void draw(Canvas c) {
    	c.drawBitmap(wanko, this.posX, this.posY, null);
    }

}
