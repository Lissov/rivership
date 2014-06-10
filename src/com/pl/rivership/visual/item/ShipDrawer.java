package com.pl.rivership.visual.item;
import com.pl.rivership.visual.*;
import android.content.*;
import android.graphics.*;
import com.pl.rivership.data.model.items.*;

public class ShipDrawer extends ItemDrawerBase<Ship>
{
	public ShipDrawer(Context context){
		super(context);
	}

	public void draw(Canvas canvas, Ship item, CoordsTranslator trans){
		Paint p = new Paint();
		p.setColor(Color.GREEN);
		p.setStyle(Paint.Style.FILL_AND_STROKE);
		
		/*PointF pnt = trans.toScreen(item.position.x - 5, item.position.y - 20);
		float l = pnt.x;
		float t = pnt.y;
		pnt = trans.toScreen(item.position.x + 5, item.position.y + 20);
		float r = pnt.x;
		float b = pnt.y;
		canvas.drawRect(l, t, r, b, p);
		
		drawTriangle(canvas, p, trans,
					 item.position.x - 5, item.position.y + 20,
					 item.position.x    , item.position.y + 25,
					 item.position.x + 5, item.position.y + 20);*/
		drawTriangleC(canvas, p, trans, item,
					  -5, -20,
					  -5, 20,
					  5, -20);
		drawTriangleC(canvas, p, trans, item,
					  -5, 20,
					  5, 20,
					  5, -20);
		drawTriangleC(canvas, p, trans, item,
					 -5, 20,
					 0, 25,
					 5, 20);
	}
}
