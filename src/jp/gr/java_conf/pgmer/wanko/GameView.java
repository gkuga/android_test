package jp.gr.java_conf.pgmer.wanko;

import java.util.ArrayList;
import java.util.Random;

import jp.gr.java_conf.pgmer.wanko.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private static final int MODE_SETTING = 0; // 開始前
    private static final int MODE_GAME = 1; // ゲーム中
    private static final int MODE_GAME_END = 2; // タイムアップ
    
    private Thread thread = null;
    private Canvas canvas;
    
    private Bitmap imageBack = BitmapFactory.decodeResource(getResources(), R.drawable.back); // 背景画像
    private Rect backSrcRect; // 背景画像のRect
    private Rect backRect; // 背景画像の表示Rect
    
    private float density;
    private ArrayList<Wanko> wankos = new ArrayList<Wanko>();
    private long time;
    private int mode; // 表示モード
    
    public GameView(Context context) {
        super(context);
        density = getContext().getResources().getDisplayMetrics().density;
        getHolder().addCallback(this);
    }

    //このメソッドを実行すればゲームがもう一度始められるようにする。
    private void init() {
    	Random rnd = new Random();
        wankos.clear();
        wankos.add( new Wanko(1, BitmapFactory.decodeResource(getResources(),R.drawable.wanko1),
        		rnd.nextInt( getWidth()-(int)(Wanko.WANKO_TOUCH_WIDTH*density) ),
        		rnd.nextInt( getHeight()-(int)(Wanko.WANKO_TOUCH_HEIGHT*density) ),
        		density, getWidth(), getHeight()) );
        wankos.add( new Wanko(2, BitmapFactory.decodeResource(getResources(),R.drawable.wanko2),
        		rnd.nextInt( getWidth()-(int)(Wanko.WANKO_TOUCH_WIDTH*density) ),
        		rnd.nextInt( getHeight()-(int)(Wanko.WANKO_TOUCH_HEIGHT*density) ),
        		density, getWidth(), getHeight()) );
        wankos.add( new Wanko(3, BitmapFactory.decodeResource(getResources(),R.drawable.wanko3),
        		rnd.nextInt( getWidth()-(int)(Wanko.WANKO_TOUCH_WIDTH*density) ),
        		rnd.nextInt( getHeight()-(int)(Wanko.WANKO_TOUCH_HEIGHT*density) ),
        		density, getWidth(), getHeight()) );
        wankos.add( new Wanko(4, BitmapFactory.decodeResource(getResources(),R.drawable.wanko4),
        		rnd.nextInt( getWidth()-(int)(Wanko.WANKO_TOUCH_WIDTH*density) ),
        		rnd.nextInt( getHeight()-(int)(Wanko.WANKO_TOUCH_HEIGHT*density) ),
        		density, getWidth(), getHeight()) );
        wankos.add( new Wanko(5, BitmapFactory.decodeResource(getResources(),R.drawable.wanko5),
        		rnd.nextInt( getWidth()-(int)(Wanko.WANKO_TOUCH_WIDTH*density) ),
        		rnd.nextInt( getHeight()-(int)(Wanko.WANKO_TOUCH_HEIGHT*density) ),
        		density, getWidth(), getHeight()) );
        wankos.add( new Wanko(6, BitmapFactory.decodeResource(getResources(),R.drawable.wanko6),
        		rnd.nextInt( getWidth()-(int)(Wanko.WANKO_TOUCH_WIDTH*density) ),
        		rnd.nextInt( getHeight()-(int)(Wanko.WANKO_TOUCH_HEIGHT*density) ),
        		density, getWidth(), getHeight()) );
        wankos.add( new Wanko(7, BitmapFactory.decodeResource(getResources(),R.drawable.wanko7),
        		rnd.nextInt( getWidth()-(int)(Wanko.WANKO_TOUCH_WIDTH*density) ),
        		rnd.nextInt( getHeight()-(int)(Wanko.WANKO_TOUCH_HEIGHT*density) ),
        		density, getWidth(), getHeight()) );
        wankos.add( new Wanko(8, BitmapFactory.decodeResource(getResources(),R.drawable.wanko8),
        		rnd.nextInt( getWidth()-(int)(Wanko.WANKO_TOUCH_WIDTH*density) ),
        		rnd.nextInt( getHeight()-(int)(Wanko.WANKO_TOUCH_HEIGHT*density) ),
        		density, getWidth(), getHeight()) );
        wankos.add( new Wanko(9, BitmapFactory.decodeResource(getResources(),R.drawable.wanko9),
        		rnd.nextInt( getWidth()-(int)(Wanko.WANKO_TOUCH_WIDTH*density) ),
        		rnd.nextInt( getHeight()-(int)(Wanko.WANKO_TOUCH_HEIGHT*density) ),
        		density, getWidth(), getHeight()) );
        wankos.add( new Wanko(10, BitmapFactory.decodeResource(getResources(),R.drawable.wanko10),
        		rnd.nextInt( getWidth()-(int)(Wanko.WANKO_TOUCH_WIDTH*density) ),
        		rnd.nextInt( getHeight()-(int)(Wanko.WANKO_TOUCH_HEIGHT*density) ),
        		density, getWidth(), getHeight()) );
        mode = MODE_SETTING;
    }
    
	@Override
	public void run() {
        while (thread != null) {
            try{
                update(); // 計算処理
            	doDraw(); // 描画処理
            }catch(NullPointerException ex){}
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
        init();	
        backSrcRect = new Rect(0, 0, imageBack.getWidth(), imageBack.getHeight());
        backRect = new Rect(0, 0, getWidth(), getHeight());
        
        thread = new Thread(this);
        thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
        thread = null;
	}
	
    private void update() {
    	if(mode == MODE_SETTING) {
    		mode = MODE_GAME;
        	time = System.currentTimeMillis();
    	}
    	else if(mode == MODE_GAME) {
    		for (int i = 0; i < this.wankos.size(); i++) {
    			this.wankos.get(i).walk();
    		}
    	}
    	else if(mode == MODE_GAME_END) {
    	}
    }
    
    private void doDraw() {
        canvas = getHolder().lockCanvas();
        // 背景
        canvas.drawBitmap(imageBack, backSrcRect, backRect, null);

        // 
        for (int i = wankos.size()-1; i >= 0; i--) {
            wankos.get(i).draw(canvas);
        }
        
        if (mode == MODE_GAME_END) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            Typeface typeFace = Typeface.createFromAsset(getContext().getAssets(), "foo.ttf");
            paint.setTypeface(typeFace);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setColor(Color.BLACK);
            paint.setTextSize(70);
            
        	canvas.drawText(String.format("%.3f", time*0.001) + " sec!!", getWidth()/2, getHeight()/2, paint);
        }

        getHolder().unlockCanvasAndPost(canvas);
    }
	
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        if (mode == MODE_SETTING) {
        }
        else if (mode == MODE_GAME) {
        	if (event.getAction() == MotionEvent.ACTION_DOWN) {
        		for (int i = 0; i < this.wankos.size(); i++) {
        			if (!this.wankos.get(i).checkHit(x, y)) {
        				continue;
        			}
        			this.wankos.remove(i).kill();
        			if (wankos.isEmpty()) {
        				time = System.currentTimeMillis() - time;
        				mode = MODE_GAME_END;
        			}
        			break;
        		}
        	}        	
        }
        else if(mode == MODE_GAME_END) {
        	if (event.getAction() == MotionEvent.ACTION_DOWN) {
        		init();	
        	}
        }
        return true;
     }
    
}
