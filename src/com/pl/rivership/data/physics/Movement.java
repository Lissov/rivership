package com.pl.rivership.data.physics;

public class Movement
{
	public float x;
	public float y;
	public float rotation;

	public Movement(float x, float y, float rotation)
	{
		this.x = x;
		this.y = y;
		this.rotation = rotation;
	}
	
	public float getDistanceTo2(Movement m){
		return (m.x - x) * (m.x - x) + (m.y - y) * (m.y - y);
	}
	
	public float getDistanceTo(Movement m){
		return (float)Math.sqrt((m.x - x) * (m.x - x) + (m.y - y) * (m.y - y));
	}
}
