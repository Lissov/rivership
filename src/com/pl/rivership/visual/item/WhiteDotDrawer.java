package com.pl.rivership.visual.item;
import com.pl.rivership.visual.*;
import com.pl.rivership.*;
import android.content.*;
import android.graphics.*;
import com.pl.rivership.data.model.items.*;
import com.pl.rivership.data.physics.*;

public class WhiteDotDrawer extends ItemDrawerBase<WhiteDot>
{
	Paint dotP = new Paint();
	public WhiteDotDrawer(Context context){
		super(context);
		setColor(dotP, R.color.whiteDot);
		dotP.setStyle(Paint.Style.FILL_AND_STROKE);
	}

	public void draw(Canvas canvas, WhiteDot item, CoordsTranslator trans){
		PointF p = trans.toScreen(item.position);
		canvas.drawCircle(p.x, p.y, 2, dotP);
	}
}
