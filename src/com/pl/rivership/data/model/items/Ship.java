package com.pl.rivership.data.model.items;
import android.graphics.*;
import com.pl.rivership.data.physics.*;

public class Ship extends Item
{
	public static float ShipMass = 10000f;
	public static float ShipLength = 40;
	public static float ShipWidth = 5;
	
	private float angle;

	public Ship(Movement position, float angle)
	{
		super(ItemType.Ship, position, ShipMass, 
			  (1f/12f) * ShipMass * (ShipLength * ShipLength + ShipWidth * ShipWidth));
		this.angle = angle;
	}	
}
