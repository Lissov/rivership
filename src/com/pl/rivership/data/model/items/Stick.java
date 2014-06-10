package com.pl.rivership.data.model.items;
import android.graphics.*;
import com.pl.rivership.data.physics.*;

public class Stick extends Item
{
	public static float radius = 0.1f;
	public static float density = 800;
	
	public float length;
	
	public Stick(Movement position, float length)
	{
		super(ItemType.Stick, position, getMass(length),
			(1f/12f) * (getMass(length)) * (length + radius));
		this.length = length;
	}
	
	private static float getMass(float l){
		return (float)(Math.pow(l, 3) * Math.pow(radius, 2) * density);
	}
}
