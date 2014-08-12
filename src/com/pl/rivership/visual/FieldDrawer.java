package com.pl.rivership.visual;
import com.pl.rivership.*;
import android.graphics.*;
import com.pl.rivership.data.model.*;
import android.content.*;
import com.pl.rivership.data.*;

public class FieldDrawer
{
	Paint fieldP = new Paint();
	
	public FieldDrawer(Context context){
		fieldP.setColor(context
				.getResources()
				.getColor(R.color.field));
	}
	
	public void draw(Canvas canvas, River river, CoordsTranslator trans){
		
		float vr = trans.getVisionRadius() / 2;
		RiverTile tile = river.getCurrentTile();
			
		for (int x = (int)(trans.camera.x - vr); x < (int)(trans.camera.x + vr); x+=3){
			for (int y = (int)(trans.camera.y - vr); y < (int)(trans.camera.y + vr); y+=3){
				int xt = x - (int)tile.southwestCorner.x;
				int yt = y - (int)tile.southwestCorner.y;
				if (xt < 0 || xt >= RiverHolder.TileSize
					|| yt < 0 || yt >= RiverHolder.TileSize)
				{ 
					continue;
				}
				
				PointF start = trans.toScreen(x, y);
				float x1 = start.x;
				float y1 = start.y;
				
				drawArrow(canvas, x1, y1, tile.flowX[xt][yt] * trans.scale, - tile.flowY[xt][yt] * trans.scale);
			}
		}
	}
	
	private void drawArrow(Canvas canvas, float sx, float sy, float x, float y){
		float l = 0.8f;
		float al = 0.25f * l;
		float tx = sx + x*l;
		float ty = sy + y*l;
		canvas.drawLine(sx, sy, tx, ty, fieldP);
		canvas.drawLine(tx, ty, tx - y*al - x*al, ty + x*al - y*al, fieldP);
		canvas.drawLine(tx, ty, tx + y*al - x*al, ty - x*al - y*al, fieldP);
	}
}
