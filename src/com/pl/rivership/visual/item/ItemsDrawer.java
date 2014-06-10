package com.pl.rivership.visual.item;
import com.pl.rivership.visual.*;
import android.content.*;
import android.graphics.*;
import com.pl.rivership.data.model.items.*;

public class ItemsDrawer
{
	private WhiteDotDrawer dot;
	private StickDrawer stick;
	private ShipDrawer ship;
	public ItemsDrawer(Context context){
		dot = new WhiteDotDrawer(context);
		stick = new StickDrawer(context);
		ship = new ShipDrawer(context);
	}
	
	public void draw(Canvas canvas, Item item, CoordsTranslator trans){
		switch (item.type){
			case WhiteDot:
				dot.draw(canvas, (WhiteDot)item, trans);
				return;
			case Stick:
				stick.draw(canvas, (Stick)item, trans);
				return;
			case Ship:
				ship.draw(canvas, (Ship)item, trans);
				return;
		}
	}
}
