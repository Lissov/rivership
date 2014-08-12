package com.pl.rivership.visual;
import com.pl.rivership.data.physics.*;
import android.graphics.*;
import com.pl.rivership.data.model.items.*;

public class CoordsTranslator
{
	float transCos;
	float transSin;
	float screenW;
	float screenH;
	float csX;
	float csY;
	float scale;
	Movement camera;
	public void setup(float screenW, float screenH, Movement camera){
		this.screenW = screenW;
		this.screenH = screenH;
		csX = screenW / 2f;
		csY = screenH / 2f;
		
		float ms = Math.min(screenW, screenH);
		scale = ms / 100f;
		this.camera = camera;
		
		transCos = (float)Math.cos(camera.rotation);
		transSin = (float)Math.sin(camera.rotation);
	}

	private PointF _cached = new PointF(0,0);
	public PointF toScreen(Movement m){
		return toScreen(m.x, m.y);
	}

	public PointF toScreen(float x, float y){
		float dx = (x - camera.x);
		float dy = (y - camera.y);
		_cached.x = csX + (dx * transCos - dy * transSin) * scale;
		_cached.y = csY - (dx * transSin + dy * transCos) * scale;
		return _cached;
	}
	
	public float getVisionRadius(){
		float mx = Math.max(csX, screenW - csX);
		float my = Math.max(csY, screenH - csY);
		float r = (float)Math.sqrt(mx * mx + my * my);
		
		return r / scale;
	}
	
	//relative to item
	private Item relItem;
	float relCx;
	float relCy;
	float relSin;
	float relCos;
	public void setMasterItem(Item item){
		relItem = item;
		PointF c = toScreen(item.position.x, item.position.y);
		relCx = c.x;
		relCy = c.y;
		relCos = (float)Math.cos(camera.rotation + item.position.rotation);
		relSin = (float)Math.sin(camera.rotation + item.position.rotation);
	}
	
	public PointF toScreenRel(float x, float y){
		_cached.x = relCx + (x * relCos - y * relSin) * scale;
		_cached.y = relCy - (x * relSin + y * relCos) * scale;
		return _cached;
	}
	
}
