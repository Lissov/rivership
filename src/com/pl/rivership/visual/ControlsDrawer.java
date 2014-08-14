package com.pl.rivership.visual;
import com.pl.rivership.*;
import android.graphics.*;
import android.content.*;
import com.pl.rivership.data.model.items.*;
import android.renderscript.*;
import android.content.res.*;

public class ControlsDrawer
{
	Paint pThrustFill = new Paint();
	Paint pThrustBorder = new Paint();
	Paint pThrustSector = new Paint();
	Paint pThrustFillBottomH = new Paint();
	Paint pThrustFillUnused = new Paint();
	Paint pThrustStop = new Paint();
	Paint pThrustText = new Paint();
	Paint pThrustStick = new Paint();
	Paint pThrustHandle = new Paint();
	Paint pThrustGField = new Paint();
	Paint pThrustGBorder = new Paint();
	Paint pThrustGMark = new Paint();
	Paint pThrustGDial = new Paint();
	
	Paint pRudderCircle = new Paint();
	Paint pRudderCenter = new Paint();
	Paint pRudderStick = new Paint();
	Paint pRudderHandle = new Paint();
	Paint pRudderHandleC = new Paint();
	
	Paint pRudderGBorder = new Paint();
	Paint pRudderGField = new Paint();
	Paint pRudderGMark = new Paint();
	Paint pRudderGDial = new Paint();
	
	private float thrBackA = 2.5f;
	private float thrForwA = 2.5f;
	private float thrStopA = 0.75f;
	private float thrGp1a = +2.7f;
	private float thrGstep = 0.1f;
	private float rudderMaxA = 6f;
	private int rudderHandleCount = 12;
	private Resources res;
	private static final float rudderGa = 0.5f;
	private static final float rudderGam = 0.55f;
	private static final float rudderGmc = 5;
	
	public ControlsDrawer(Context context){
		res = context.getResources();
		
		pThrustFill.setColor(Color.WHITE);
		pThrustFill.setStyle(Paint.Style.FILL);
		
		pThrustBorder.setColor(Color.BLACK);
		pThrustBorder.setStyle(Paint.Style.STROKE);
		
		pThrustSector.setColor(Color.BLACK);
		pThrustFillBottomH.setColor(Color.DKGRAY);
		
		pThrustFillUnused.setColor(Color.GRAY);
		pThrustFillUnused.setStyle(Paint.Style.FILL);
		
		pThrustStop.setColor(Color.GREEN);
		pThrustStop.setStyle(Paint.Style.FILL);
		
		pThrustText.setColor(Color.BLACK);
		pThrustText.setTypeface(Typeface.MONOSPACE);
		
		pThrustStick.setColor(Color.LTGRAY);
		pThrustStick.setStyle(Paint.Style.FILL_AND_STROKE);
	
		pThrustHandle.setColor(Color.RED);
		pThrustHandle.setStyle(Paint.Style.FILL);
		
		//thrust gauge
		pThrustGField.setColor(Color.WHITE);
		pThrustGField.setStyle(Paint.Style.FILL);
		
		pThrustGBorder.setColor(Color.GRAY);
		pThrustGBorder.setStyle(Paint.Style.STROKE);
		
		pThrustGMark.setColor(Color.BLACK);
		pThrustGMark.setStyle(Paint.Style.STROKE);
		pThrustGMark.setStrokeWidth(3);
		
		pThrustGDial.setColor(Color.RED);
		pThrustGDial.setStyle(Paint.Style.FILL_AND_STROKE);
		
		//rudder
		setupPaint(pRudderCircle, R.color.rudder_outer, Paint.Style.STROKE);
		setupPaint(pRudderCenter, R.color.rudder_inner, Paint.Style.FILL_AND_STROKE);
		setupPaint(pRudderStick, R.color.rudder_stick, Paint.Style.STROKE);
		setupPaint(pRudderHandle, R.color.rudder_handle, Paint.Style.FILL_AND_STROKE);
		setupPaint(pRudderHandleC, R.color.rudder_handleA, Paint.Style.FILL_AND_STROKE);
		
		//rudder gauge
		setupPaint(pRudderGBorder, R.color.rudder_g_border, Paint.Style.STROKE);
		setupPaint(pRudderGField, R.color.rudder_g_field, Paint.Style.FILL);
		setupPaint(pRudderGMark, R.color.rudder_g_mark, Paint.Style.FILL);
		setupPaint(pRudderGDial, R.color.rudder_g_dial, Paint.Style.FILL_AND_STROKE);
	}
	
	private void setupPaint(Paint target, int color, Paint.Style style){
		target.setColor(res.getColor(color));
		target.setStyle(style);
	}
	
	private float thrustCx;
	private float thrustCy;
	private float thrustR;
	private float prevW = 0;
	private float prevH = 0;
	private RectF thrustRect;
	private RectF thrustInnerRect;
	private RectF thrustOuterRect;
	private float thrustAngB;
	private float thrustAngF;
	private Path thrustPAhead;
	private Path thrustPAstern;
	private Path[] thrustLabels;
	private float[] thrustAngles;
	private float thrustGx;
	private float thrustGy;
	private float thrustGr;
	private static final float am = (float)(180f / Math.PI);
	private float rudderCx;
	private float rudderCy;
	private float rudderR;
	private float rudderGcx;
	private float rudderGcy;
	private float rudderGr;
	private Path rudderGpath;
	private float rudderGamult;
	private void calcSizes(float width, float height, Ship ship){
		if (prevW == width && prevH == height)
			return;
			
		prevW = width;
		prevH = height;

		thrustR = width > height ? height : width;
		thrustR /= 8;
		thrustCx = thrustR * 1.5f;
		thrustCy = height - thrustR * 1.5f;
		
		thrustRect = new RectF(thrustCx - thrustR, thrustCy - thrustR, thrustCx + thrustR, thrustCy + thrustR);
		thrustInnerRect = new RectF(thrustCx - 0.32f*thrustR, thrustCy - 0.32f*thrustR,
									thrustCx + 0.32f*thrustR, thrustCy + 0.32f*thrustR);
		thrustOuterRect = new RectF(thrustCx - 0.72f*thrustR, thrustCy - 0.72f*thrustR,
									thrustCx + 0.72f*thrustR, thrustCy + 0.72f*thrustR);
		
		thrustAngB = (thrBackA - thrStopA/2) / ship.thrustAsternSteps;
		thrustAngF = (thrForwA - thrStopA/2) / ship.thrustAheadSteps;
		
		pThrustText.setTextSize(thrustR * 0.16f);
		Typeface tf = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD);
		pThrustText.setTypeface(tf); 
		thrustPAhead = new Path();
		thrustPAhead.addArc(thrustInnerRect, 90f - am*thrForwA, am*(thrForwA - 0.5f*thrStopA));
		thrustPAstern = new Path();
		thrustPAstern.addArc(thrustInnerRect, 270 - am*thrBackA, am*(thrForwA - 0.5f*thrStopA));
		
		thrustLabels = new Path[ship.thrustAheadSteps + ship.thrustAsternSteps + 1];
		thrustAngles = new float[ship.thrustAheadSteps + ship.thrustAsternSteps + 1];
		for (int bs = 0; bs < ship.thrustAsternSteps; bs++){
			thrustLabels[bs] = new Path();
			//thrustLabels[bs].addArc(thrustOuterRect, 270f - am*(thrBackA - bs*thrustAngB), am*thrustAngB); 
			float a = -thrBackA + (bs+0.35f)*thrustAngB;
			thrustLabels[bs].moveTo(thrustCx + thrustR*0.9f*(float)Math.sin(a), thrustCy - thrustR*0.9f*(float)Math.cos(a));
			thrustLabels[bs].lineTo(thrustCx + thrustR*0.55f*(float)Math.sin(a), thrustCy - thrustR*0.55f*(float)Math.cos(a));
			thrustAngles[bs] = (float)(Math.PI/2f) - a;
		}
		thrustLabels[ship.thrustAsternSteps] = new Path();
		thrustLabels[ship.thrustAsternSteps].addArc(thrustOuterRect,
			270f - 0.4f*am*thrStopA, am*thrStopA);
		thrustAngles[ship.thrustAsternSteps] = (float)(Math.PI/2f);
		
		int s0 = ship.thrustAsternSteps + 1;
		for (int as = 0; as < ship.thrustAheadSteps; as++){
			thrustLabels[s0 + as] = new Path();
			float a = thrStopA + (as-0.1f)*thrustAngF;
			/*thrustLabels[s0 + as].addArc(thrustOuterRect, 
				270f + am*(0.5f*thrStopA + as*thrustAngF), am*thrustAngF);*/
			thrustLabels[s0+as].moveTo(thrustCx + thrustR*0.55f*(float)Math.sin(a), thrustCy - thrustR*0.55f*(float)Math.cos(a));
			thrustLabels[s0+as].lineTo(thrustCx + thrustR*0.9f*(float)Math.sin(a), thrustCy - thrustR*0.9f*(float)Math.cos(a));
			thrustAngles[s0+as] = (float)(Math.PI/2f) - a;
		}
		
		//thrust gauge
		thrustGx = thrustCx;
		thrustGy = thrustCy - thrustR*2.5f;
		thrustGr = thrustR*0.75f;
		
		pThrustBorder.setStrokeWidth(thrustR*0.02f);
		pThrustStick.setStrokeWidth(thrustR*0.1f);
		pThrustGBorder.setStrokeWidth(thrustGr*0.1f);
		
		
		// rudder
		rudderR = width > height ? height : width;
		rudderR /= 8f;
		rudderCx = width - rudderR * 1.5f;
		rudderCy = height - rudderR * 1.5f;
		pRudderCircle.setStrokeWidth(rudderR * 0.15f);
		pRudderStick.setStrokeWidth(rudderR * 0.1f);
		//rudder gauge
		rudderGcx = rudderCx;
		rudderGr = 3f*rudderR;
		rudderGcy = rudderCy - 1.8f*rudderR - rudderGr;
		float rgIn = 0.8f;
		RectF rudderGrect = new RectF(rudderGcx-rudderGr, rudderGcy-rudderGr,
									  rudderGcx+rudderGr, rudderGcy+rudderGr);
		RectF rudderGrectI = new RectF(rudderGcx-rgIn*rudderGr, rudderGcy-rgIn*rudderGr,
									   rudderGcx+rgIn*rudderGr, rudderGcy+rgIn*rudderGr);
		rudderGpath = new Path();
		rudderGpath.arcTo(rudderGrect, 90-am*rudderGam, 2*am*rudderGam, true);
		rudderGpath.lineTo(rudderGcx - rgIn*rudderGr * (float)Math.sin(rudderGam),
						   rudderGcy + rgIn*rudderGr * (float)Math.cos(rudderGam));
		rudderGpath.arcTo(rudderGrectI, 90+am*rudderGam, -2*am*rudderGam);
		rudderGpath.lineTo(rudderGcx + rudderGr * (float)Math.sin(rudderGam),
						   rudderGcy + rudderGr * (float)Math.cos(rudderGam));
		pRudderGBorder.setStrokeWidth(0.025f*rudderGr);
		pRudderGMark.setStrokeWidth(0.007f*rudderGr);
		rudderGamult = rudderGa / ship.MaxRudderAngle;
	}
	
	public void draw(Canvas canvas, Ship ship, float width, float height){
		// Thrust
		calcSizes(width, height, ship);
		
		canvas.drawRect(thrustCx - 0.5f*thrustR, thrustCy, 
						thrustCx + 0.5f*thrustR, thrustCy + 1.25f * thrustR,
						pThrustFillBottomH);
		canvas.drawCircle(thrustCx, thrustCy, thrustR, pThrustFill);
		
		canvas.drawArc(thrustRect, 270f - am*thrStopA/2f, am*thrStopA, true, pThrustStop);

		canvas.drawCircle(thrustCx, thrustCy, 0.3f*thrustR, pThrustFillUnused);
		
		canvas.drawCircle(thrustCx, thrustCy, thrustR*0.5f, pThrustBorder);
		
		canvas.drawArc(thrustRect,
					   -90f + am*thrForwA, 
					   360f - am*(thrForwA + thrBackA), 
					   true, pThrustFillUnused);
					   
		
		drawRLine(canvas, 0.5f * thrStopA, 0.3f, 1f, pThrustBorder);
		for (int n = 1; n<ship.thrustAheadSteps; n++){
			drawRLine(canvas, 0.5f * thrStopA + n * thrustAngF, 0.5f, 1f, pThrustBorder);
		}
		drawRLine(canvas, thrForwA, 0.3f, 1f, pThrustBorder);
		
		drawRLine(canvas, -0.5f * thrStopA, 0.3f, 1f, pThrustBorder);
		for (int n = 1; n<ship.thrustAsternSteps; n++){
			drawRLine(canvas, -0.5f * thrStopA - n * thrustAngB, 0.5f, 1f, pThrustBorder);
		}
		drawRLine(canvas, -thrBackA, 0.3f, 1f, pThrustBorder);

		canvas.drawCircle(thrustCx, thrustCy, thrustR, pThrustBorder);
		
		canvas.drawTextOnPath("AHEAD", thrustPAhead, 0, 0, pThrustText);
		canvas.drawTextOnPath("ASTERN", thrustPAstern, 0, 0, pThrustText);
		
		for (int i = 0; i < thrustLabels.length; i++){
			canvas.drawTextOnPath(ship.thrustStepNames[i], thrustLabels[i], 0, 0, pThrustText);			
		}
		
		float aThrHandle = thrustAngles[ship.thrustPosition];
		float thx = thrustCx + thrustR*1.2f* (float)Math.cos(aThrHandle);
		float thy = thrustCy - thrustR*1.2f* (float)Math.sin(aThrHandle);
		canvas.drawLine(thrustCx, thrustCy, thx, thy, pThrustStick);
		canvas.drawCircle(thrustCx, thrustCy, thrustR*0.2f, pThrustStick);
		canvas.drawCircle(thx, thy, thrustR*0.1f, pThrustHandle);
		
		//thrust gauge
		canvas.drawCircle(thrustGx, thrustGy, thrustGr, pThrustGField);
		canvas.drawCircle(thrustGx, thrustGy, thrustGr, pThrustGBorder);
		for (float m = -1f; m<=1.001f; m+=thrGstep){
			float s = (eq(m,-1f) || eq(m,-0.5f) || eq(m,0f) || eq(m,0.5f) || eq(m,1f))
				? 0.75f*thrustGr
				: 0.85f*thrustGr;
			float e = 0.9f*thrustGr;
			float sx = thrustGx + (float)(s*Math.sin(m*thrGp1a));
			float sy = thrustGy - (float)(s*Math.cos(m*thrGp1a));
			float ex = thrustGx + (float)(e*Math.sin(m*thrGp1a));
			float ey = thrustGy - (float)(e*Math.cos(m*thrGp1a));
			canvas.drawLine(sx, sy, ex, ey, pThrustGMark);
		}
		
		float arrA = ship.enginePower*thrGp1a;
		float ax = thrustGx + (float)(0.8f*thrustGr*Math.sin(arrA));
		float ay = thrustGy - (float)(0.8f*thrustGr*Math.cos(arrA));
		float aex = thrustGx - (float)(0.3f*thrustGr*Math.sin(arrA));
		float aey = thrustGy + (float)(0.3f*thrustGr*Math.cos(arrA));
		float arx = 0.05f*thrustGr*(float)(Math.cos(arrA));
		float ary = 0.05f*thrustGr*(float)(Math.sin(arrA));
		drawTriangle(canvas, pThrustGDial, 
					 aex + arx, aey + ary,
					 aex - arx, aey - ary,
					 ax,ay);
			
					 
		// rudder
		float angle = rudderMaxA * ship.rudderPosition;
		float da = (float)Math.PI * 2 / rudderHandleCount;
		for (int n = 0; n<rudderHandleCount;n++){
			float a = angle + da*n;
			float cx = rudderCx + rudderR*(float)Math.sin(a);
			float cy = rudderCy - rudderR*(float)Math.cos(a);
			float t1x = rudderCx + rudderR*(float)Math.sin(a + 0.05f);
			float t1y = rudderCy - rudderR*(float)Math.cos(a + 0.05f);
			float t2x = rudderCx + rudderR*(float)Math.sin(a - 0.05f);
			float t2y = rudderCy - rudderR*(float)Math.cos(a - 0.05f);
			float ex = rudderCx + 1.3f*rudderR*(float)Math.sin(a);
			float ey = rudderCy - 1.3f*rudderR*(float)Math.cos(a);
			float e2x = rudderCx + 1.4f*rudderR*(float)Math.sin(a);
			float e2y = rudderCy - 1.4f*rudderR*(float)Math.cos(a);

			canvas.drawLine(rudderCx, rudderCy, cx, cy, pRudderStick);
			drawTriangle(canvas, pRudderHandle, t1x, t1y, t2x, t2y, e2x, e2y);
			Paint phc = n == 0 ? pRudderHandleC : pRudderHandle;
			canvas.drawCircle(ex, ey, 0.1f*rudderR, phc);
		}

		canvas.drawCircle(rudderCx, rudderCy, rudderR, pRudderCircle);
		canvas.drawCircle(rudderCx, rudderCy, rudderR*0.3f, pRudderCenter);
		
		//rudder gauge
		canvas.drawPath(rudderGpath, pRudderGField);
		for (float a = -rudderGa; a <= rudderGa+0.001f; a += rudderGa/rudderGmc){
			float l = 0.90f;
			if (eq(a, -rudderGa) || eq(a, 0) || eq(a, +rudderGa))
				l = 0.95f;
			canvas.drawLine(
				rudderGcx + rudderGr*0.85f*(float)Math.sin(a),
				rudderGcy + rudderGr*0.85f*(float)Math.cos(a),
				rudderGcx + rudderGr*l*(float)Math.sin(a),
				rudderGcy + rudderGr*l*(float)Math.cos(a),
				pRudderGMark);
		}
		float ra = rudderGamult * ship.rudderAngle;
		float ex = rudderGcx + rudderGr*0.9f*(float)Math.sin(ra);
		float ey = rudderGcy + rudderGr*0.9f*(float)Math.cos(ra);
		float e1x = rudderGcx + rudderGr*0.8f*(float)Math.sin(ra+0.03f);
		float e1y = rudderGcy + rudderGr*0.8f*(float)Math.cos(ra+0.03f);
		float e2x = rudderGcx + rudderGr*0.8f*(float)Math.sin(ra-0.03f);
		float e2y = rudderGcy + rudderGr*0.8f*(float)Math.cos(ra-0.03f);
		drawTriangle(canvas, pRudderGDial, e1x, e1y, e2x, e2y, ex, ey);
		
		canvas.drawPath(rudderGpath, pRudderGBorder);
	}
	
	private boolean eq(float v1, float v2){
		return Math.abs(v1-v2)<0.000001f;
	}
	
	private void drawRLine(Canvas canvas, float angle, float from, float to, Paint paint)
	{
		canvas.drawLine(
			thrustCx + (float)Math.sin(angle)*from*thrustR,
			thrustCy - (float)Math.cos(angle)*from*thrustR,
			thrustCx + (float)Math.sin(angle)*to*thrustR,
			thrustCy - (float)Math.cos(angle)*to*thrustR,
			paint);
	}
	
	private void drawTriangle(Canvas canvas, Paint paint,
							  float x1, float y1,
							  float x2, float y2,
							  float x3, float y3){

		Path path = new Path();
		path.moveTo(x1, y1);
		path.lineTo(x2, y2);
		path.lineTo(x3, y3);
		canvas.drawPath(path, paint);
	}
}
