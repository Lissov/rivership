package com.pl.rivership.visual.item;
import com.pl.rivership.visual.*;
import com.pl.rivership.*;
import android.content.*;
import android.graphics.*;
import com.pl.rivership.data.model.items.*;
import com.pl.rivership.data.physics.*;

public class StickDrawer extends ItemDrawerBase<Stick>
{
	Paint stickP = new Paint();
	public StickDrawer(Context context){
		super(context);
		setColor(stickP, R.color.stick);
		stickP.setStyle(Paint.Style.FILL_AND_STROKE);
	}
	
	public void draw(Canvas canvas, Stick item, CoordsTranslator trans){
		Movement p = item.position;
		float l = item.length;
		float r = item.radius;
		drawTriangleC(canvas, stickP, trans, item,
			-l, r, l, r, l, -r);
		drawTriangleC(canvas, stickP, trans, item,
					  -l, r, l, -r, -l, -r);
	}
}
