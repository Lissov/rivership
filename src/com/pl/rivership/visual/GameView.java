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
import com.pl.rivership.data.physics.*;

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
		}, 1000, 20);
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
		
		drawDebugInfo(canvas);
	}
	
	private void drawDebugInfo(Canvas canvas){
		Paint debugP = new Paint();
		debugP.setColor(Color.WHITE);
		debugP.setTextSize(16);
		canvas.drawText("Update interval: " + gameContr.debugInfo.updateInterval.get(), 5,15, debugP);
		canvas.drawText("Model calc time: " + gameContr.debugInfo.modelCalcTime.get(), 5,30, debugP);
		
		if (gameContr.game != null && gameContr.game.ship != null && gameContr.game.ship.debugForce != null)
		{ 
			canvas.drawText("Force X: " + gameContr.game.ship.debugForce.x, 5,45, debugP);
			canvas.drawText("Force Y: " + gameContr.game.ship.debugForce.y, 5,60, debugP);
			Movement m = gameContr.game.ship.movement;
			canvas.drawText("Speed: " + Math.sqrt(m.x*m.x + m.y*m.y), 5, 75, debugP);
		}
	}
}
