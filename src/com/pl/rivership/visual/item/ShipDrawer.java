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
		
		trans.setMasterItem(item);
		Paint rudderP = new Paint();
		float rudderT = 0.4f;
		rudderP.setColor(Color.BLACK);
		PointF rS = trans.toScreenRel(0, -Ship.ShipLength2);
		float rsx = rS.x;
		float rsy = rS.y;
		/*PointF rE = trans.toScreenRel(
			0 + 2f*(float)Math.sin(item.rudderAngle),
			-Ship.ShipLength2 - 2f*(float)Math.cos(item.rudderAngle));*/
		//float rex = rE.x;
		//float rey = rE.y;
		float rs1x = rudderT*(float)Math.cos(item.rudderAngle);
		float rs1y = -Ship.ShipLength2 + rudderT*(float)Math.sin(item.rudderAngle);
		float rs2x = -rs1x;
		float rs2y = -2 * Ship.ShipLength2 - rs1y;
		canvas.drawCircle(rsx, rsy, rudderT*trans.scale, rudderP);
		drawTriangleC(canvas, rudderP, trans, item, rs1x, rs1y, rs2x, rs2y, 
					 0 + 2f*(float)Math.sin(item.rudderAngle),
					 -Ship.ShipLength2 - 2f*(float)Math.cos(item.rudderAngle));
		//canvas.drawLine(rsx, rsy, rE.x, rE.y, rudderP);
		
		Paint p = new Paint();
		p.setColor(Color.GREEN);
		p.setStyle(Paint.Style.FILL_AND_STROKE);
		
		drawTriangleC(canvas, p, trans, item,
					  -Ship.ShipWidth2, -Ship.ShipLength2,
					  -Ship.ShipWidth2, Ship.ShipLength2,
					  Ship.ShipWidth2, -Ship.ShipLength2);
		drawTriangleC(canvas, p, trans, item,
					  -Ship.ShipWidth2, Ship.ShipLength2,
					  Ship.ShipWidth2, Ship.ShipLength2,
					  Ship.ShipWidth2, -Ship.ShipLength2);
		drawTriangleC(canvas, p, trans, item,
					  -Ship.ShipWidth2, Ship.ShipLength2,
					  0, Ship.ShipLength2 + Ship.ShipWidth2,
					  Ship.ShipWidth2, Ship.ShipLength2);
					  
		drawDebugForce(canvas, item, trans); 
	}
	
	public void drawDebugForce(Canvas canvas, Ship item, CoordsTranslator trans)
	{
		if (item.debugForce == null)
			return;
		
		Paint p = new Paint();
		p.setColor(Color.RED);
		//p.setStyle(Paint.Style.FILL_AND_STROKE);
		
		PointF p0 = trans.toScreen(item.position);
		float xc = p0.x;
		float yc = p0.y;
		canvas.drawCircle(xc, yc, 2, p);
		PointF p1 = trans.toScreen(
			item.position.x + item.debugForce.x * 10f / item.mass, 
			item.position.y + item.debugForce.y * 10f / item.mass);
		canvas.drawLine(xc, yc, p1.x, p1.y, p);
		
		Paint moveP = new Paint();
		moveP.setColor(Color.WHITE);
		PointF pM = trans.toScreen(
			item.position.x + item.movement.x, 
			item.position.y + item.movement.y);
		canvas.drawLine(xc, yc, pM.x, pM.y, moveP);
		
		
		
		
		float fr = item.debugForce.rotation * 100f / item.momentI;
		drawTriangleC(canvas, p, trans, item,
			0, Ship.ShipLength2 + Ship.ShipWidth2 + 0.2f,
			-fr, Ship.ShipLength2 + Ship.ShipWidth2,
			0, Ship.ShipLength2 + Ship.ShipWidth2 - 0.2f);

		float rx = -fr;
		float ry = Ship.ShipLength2 + Ship.ShipWidth2;
		float rsin = (float)Math.sin(item.position.rotation);
		float rcos = (float)Math.cos(item.position.rotation);
		PointF pr = trans.toScreen(
			item.position.x + rx * rcos - ry * rsin,
			item.position.y + rx * rsin + ry * rcos);
		canvas.drawCircle(pr.x, pr.y, 2, p);
	}
}
