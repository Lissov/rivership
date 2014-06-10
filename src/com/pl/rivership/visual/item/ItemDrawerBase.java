package com.pl.rivership.visual.item;
import com.pl.rivership.visual.*;
import android.graphics.*;
import com.pl.rivership.data.model.items.*;
import android.content.*;

public abstract class ItemDrawerBase<TItem>
{
	Context _context;
	public ItemDrawerBase(Context context){
		_context = context;
	}
	
	public abstract void draw(Canvas canvas, TItem item, CoordsTranslator trans);
	
	protected void setColor(Paint paint, int resId){
		paint.setColor(_context.getResources().getColor(resId));
	}
	
	protected void drawTriangle(Canvas canvas, Paint paint,
								CoordsTranslator trans,
								float x1, float y1,
								float x2, float y2,
								float x3, float y3){

		Path path = new Path();
		PointF pf = trans.toScreen(x1, y1);
		path.moveTo(pf.x, pf.y);
		pf = trans.toScreen(x2, y2);
		path.lineTo(pf.x, pf.y);
		pf = trans.toScreen(x3, y3);
		path.lineTo(pf.x, pf.y);
		canvas.drawPath(path, paint);
	}
	
	protected void drawTriangleC(Canvas canvas, Paint paint,
								CoordsTranslator trans,
								Item item,
								float x1, float y1,
								float x2, float y2,
								float x3, float y3){
		
		float cos = (float)Math.cos(item.position.rotation);
	 	float sin = (float)Math.sin(item.position.rotation);
		
		drawTriangle(canvas, paint, trans,
					item.position.x + x1 * cos - y1 * sin,
					item.position.y + x1 * sin + y1 * cos,
			
					item.position.x + x2 * cos - y2 * sin,
					item.position.y + x2 * sin + y2 * cos,
	   
					item.position.x + x3 * cos - y3 * sin,
					item.position.y + x3 * sin + y3 * cos
	   	);
	}	
}
