package com.pl.rivership;
import android.app.*;
import android.os.*;
import android.widget.*;
import com.pl.rivership.visual.*;

public class GameActivity extends Activity
{
	GameView gameView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.game);
		
		gameView = new GameView(this);
		LinearLayout llMain = (LinearLayout)findViewById(R.id.game_llMain);
		llMain.addView(gameView);
	}
}
