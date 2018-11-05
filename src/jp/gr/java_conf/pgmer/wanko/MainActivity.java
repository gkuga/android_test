package jp.gr.java_conf.pgmer.wanko;

import jp.gr.java_conf.pgmer.wanko.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    private GameView view;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        view = new GameView(this);
	}
	
    public void onClickStart(View v) {
        setContentView(view); // ゲーム画面呼び出し
    }

}
