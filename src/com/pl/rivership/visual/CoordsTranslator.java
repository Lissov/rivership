package com.pl.rivership.visual;
import com.pl.rivership.data.physics.*;
import android.graphics.*;

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
}
