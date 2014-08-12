package com.pl.rivership.data.model.items;
import android.graphics.*;
import com.pl.rivership.data.physics.*;

public class Ship extends Item
{
	// assume it sets 1m deep.
	// length = 40m, width = 10m -> 400m2 -> 400 tons
	
	public static final float ShipMass = 400000f; //mass in kilos
	public static final float ShipLength = 40;
	public static final float ShipWidth = 10;

	public static final float ShipLength2 = ShipLength / 2f;
	public static final float ShipWidth2 = ShipWidth / 2f;
	
	public static final float MaxRudderAngle = 1f;
	public static final float RudderSquare = 2f; // square meters
	public static final float RudderEnginedSquare = 1f; // square meters
	public static final float MaxPower = 200000f; // maximum power of engine - let it be 5% of weight 
	public static final float MaxPowerFlow = 10f; // flow of water from engine at max power, m/s
	
	public float rudderAngle;
	public float enginePower;
	

	public Ship(Movement position)
	{
		super(ItemType.Ship, position, ShipMass, 
			  (1f/12f) * ShipMass * (ShipLength * ShipLength + ShipWidth * ShipWidth));
	}
	
	
	public Movement debugForce;
}
