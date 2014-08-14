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
	
	public static final float MaxRudderAngle = 0.8f;
	public static final float RudderSquare = 2f; // square meters
	public static final float RudderEnginedSquare = 0.5f; // square meters
	public static final float MaxPower = 200000f; // maximum power of engine - let it be 5% of weight 
	public static final float MaxPowerFlow = 3f; // flow of water from engine at max power, m/s (relative to river, only for rudder force)
	public static final float EngineInertion = 0.2f;
	public static final float RudderInertion = 0.8f;
	
	public float rudderAngle;
	public float enginePower;
	
	public int thrustAheadSteps = 5;
	public int thrustAsternSteps = 3;
	public String[] thrustStepNames = {"Full", "Half", "Slow", "Stop", "Slow", "1/3", "Half", "2/3", "Full"};
	public float[] thrustSteps = {-0.8f, -0.4f, -0.1f, 0f, 0.1f, 0.3f, 0.5f, 0.65f, 1f};
	public int thrustPosition = 3; // Stop
	public float rudderPosition = 0; // 0 angle
	
	public void setThrustPosition(int value){
		if (value < 0 || value > thrustAsternSteps + thrustAheadSteps)
			return;
		thrustPosition = value;
	}
	
	public void setRudderPosition(float value){
		if (value < -MaxRudderAngle || value > MaxRudderAngle)
			return;
		rudderPosition = value;
	}
	
	public Ship(Movement position)
	{
		super(ItemType.Ship, position, ShipMass, 
			  (1f/12f) * ShipMass * (ShipLength * ShipLength + ShipWidth * ShipWidth));
	}
	
	public Movement debugForce;
	
	public void updateControls(float dtime){
		float diff = (thrustSteps[thrustPosition] - enginePower);
		enginePower += (diff > 0 ? 1 : -1) * (float)Math.sqrt(Math.abs(diff)) * dtime * EngineInertion;
		
		float rDiff = rudderPosition - rudderAngle;
		rudderAngle += (rDiff > 0 ? 1 : -1) * (float)Math.sqrt(Math.abs(rDiff)) * dtime * RudderInertion;
	}
}
