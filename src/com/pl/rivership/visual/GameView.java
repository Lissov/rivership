package com.pl.rivership.visual;
import android.view.*;
import android.app.*;
import android.graphics.*;
import com.pl.rivership.data.*;
import com.pl.rivership.data.model.*;
import java.util.*;
import com.pl.rivership.data.model.items.*;
import com.pl.rivership.visual.item.*;
import android.content.*;

public class GameView extends View
{
	private Context context;
	private GameController gameContr;
	private Timer updateTimer;
	Paint pntBackground = new Paint();
	CoordsTranslator trans = new CoordsTranslator();
	ItemsDrawer itemsDrawer;
	FieldDrawer fieldDrawer;
	
	public GameView(final Activity activity){
		super(activity);
		context = activity;
		
		pntBackground.setColor(Color.BLUE);
		pntBackground.setStyle(Paint.Style.FILL_AND_STROKE);
		
		gameContr = new GameController(GameHolder.getCurrentGame());
		itemsDrawer = new ItemsDrawer(context);
		fieldDrawer = new FieldDrawer(context);
				
		if (updateTimer == null)
			updateTimer = new Timer("updateTimer");
		else
			updateTimer.cancel();

		updateTimer.schedule(new TimerTask() {
			public void run() {
				activity.runOnUiThread(new Runnable(){
					public void run() {
						update();
					}
				});
			}
		}, 1000, 40);
	}

	private void update(){
		gameContr.update();
		float vr = trans.getVisionRadius();
		//gameContr.addRemoveItems(vr, 2 * vr);
		this.invalidate();
	}
	
	private int width;
	private int heigth;
	
	@Override
	public void draw(Canvas canvas)
	{
		width = this.getMeasuredWidth();
		heigth = this.getMeasuredHeight();
		
		trans.setup(width, heigth, gameContr.game.cameraPoint);
		
		canvas.drawRect(0,0, width, heigth, pntBackground);
		fieldDrawer.draw(canvas, gameContr.game.river, trans);
		
		for (Item item : gameContr.game.items){
			itemsDrawer.draw(canvas, item, trans);
		}
	}
}
