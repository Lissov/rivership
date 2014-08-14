package com.pl.rivership.data.physics;
import com.pl.rivership.data.model.*;
import com.pl.rivership.data.model.items.*;

public class ForceCalcShip extends ForceCalcBase<Ship>
{
	// force (in Newtons) that is applied by water flow of 1m/s on 1m^2 panel
	public static final float WaterPower = 6000f;
	public static final float WaterPowerOpposite = 4000f;
	
	@Override
	public Movement calculate(Ship item, River river)
	{
		_force.x = 0;
		_force.y = 0;
		_force.rotation = 0f;
		
		float invSqrt2 = 1f/1.414213f;
		float sinr = (float)Math.sin(item.position.rotation);
		float cosr = (float)Math.cos(item.position.rotation);
		
		//J = 1/12 m(a2 + b2)
		// TODO: Implement this method
		
		for (float x = -Ship.ShipWidth2 + 0.5f; x<0; x+=1f){
			float y = Ship.ShipLength2 + Ship.ShipWidth2 + x;
			addForce(_force, river, x, y, 
					invSqrt2, -invSqrt2, 0.1f, // decrease S to decrease frontal resistance
					item, sinr, cosr);
		}
		for (float x = 0.5f; x<Ship.ShipWidth2; x+=1f){
			float y = Ship.ShipLength2 + Ship.ShipWidth2 - x;
			addForce(_force, river, x, y, 
					 -invSqrt2, -invSqrt2, 0.1f, // decrease S to decrease frontal resistance
					 item, sinr, cosr);
		}
		
		for (float y = -Ship.ShipLength2 + 0.5f; y<Ship.ShipLength2; y+=1f){
			float s = (1 - Math.abs(y / Ship.ShipLength));
			s = s*s * 2;
			addForce(_force, river, -Ship.ShipWidth2, y, 
					 1f, 0f, s,
					 item, sinr, cosr);			
		}
		for (float y = -Ship.ShipLength2 + 0.5f; y<Ship.ShipLength2; y+=1f){
			float s = (1 - Math.abs(y / Ship.ShipLength));
			s = s*s * 2;
			addForce(_force, river, -Ship.ShipWidth2, y, 
					 -1f, 0f, s,
					 item, sinr, cosr);			
		}
		
		for (float x = -Ship.ShipWidth2 + 0.5f; x<Ship.ShipWidth2; x+=1f){
			addForce(_force, river, x, -Ship.ShipLength2, 
					 0f, 1f, 0.2f,	// decrease S to decrease frontal resistance
					 item, sinr, cosr);			
		}
		
		//rudder
		float rudderNx = (float)Math.cos(item.rudderAngle);
		float rudderNy = (float)Math.sin(item.rudderAngle);
		addForce(_force, river, 0, -Ship.ShipLength2,
			rudderNx, rudderNy, Ship.RudderSquare, item, sinr, cosr);
		addForce(_force, river, 0, -Ship.ShipLength2,
				 -rudderNx, -rudderNy, Ship.RudderSquare, item, sinr, cosr);
				 
		// engine power
		float engY = item.enginePower * Ship.MaxPower;
		_force.x += -engY * sinr;
		_force.y += engY * cosr;
		// causes no rotation
		
		//rudder from engine
		float engWaterSpeed = item.enginePower * Ship.MaxPowerFlow;
		Movement engFlow = new Movement(
			engWaterSpeed * sinr, -engWaterSpeed * cosr,
			0);
		addForceDiffFlow(_force, river, 0, -Ship.ShipLength2,
					     rudderNx, rudderNy, 
						 Ship.RudderEnginedSquare, item, sinr, cosr,
						 engFlow);
		addForceDiffFlow(_force, river, 0, -Ship.ShipLength2,
						 -rudderNx, -rudderNy, 
						 Ship.RudderEnginedSquare, item, sinr, cosr,
						 engFlow);
		
						 
		// debug info
		item.debugForce = _force;

		return _force;
		
	}
	
	// (bx, by) - ship border point
	// (nx, ny) - normale at that point
	// s - square at that point
	private void addForce(Movement force,
						  River river,
						  float bx, float by, float nx, float ny, float s,
						  Ship ship, float sinr, float cosr) // sin and cos precalculated (performance)
	{
		float rx = bx*cosr - by*sinr;
		float ry = bx*sinr + by*cosr;

		Movement flow = getForce(river, 
								 ship.position.x + rx, ship.position.y + ry,
								 false);

		// total flow relative to ship
		float flowx = flow.x - ship.movement.x + ry * ship.movement.rotation;
		float flowy = flow.y - ship.movement.y - rx * ship.movement.rotation;
		
		flow.x = flowx;
		flow.y = flowy;
		
		addForceDiffFlow(force,
						 river, bx, by, nx, ny, s,
						 ship, sinr, cosr,
						 flow);
	}
	
	private void addForceDiffFlow(Movement force,
						  River river,
						  float bx, float by, float nx, float ny, float s,
						  Ship ship, float sinr, float cosr,
						  Movement shipRelFlow)
	{
		// duplicate calculation!		
		float rx = bx*cosr - by*sinr;
		float ry = bx*sinr + by*cosr;
		
		// total flow relative to ship
		float flowx = shipRelFlow.x;
		float flowy = shipRelFlow.y;
		
		// rotated normale
		float nrx = nx * cosr - ny * sinr;
		float nry = nx * sinr + ny * cosr;
		
		// resulting force at point
		float smult = flowx * nrx + flowy * nry;
		float k = smult > 0 ? WaterPower : WaterPowerOpposite;
		float power = smult*Math.abs(smult) * s * k; // smult * abs(smult) to preserve direction 
		float fx = nrx * power;
		float fy = nry * power;
		
		// applying to gravity center and rotation
		force.x += fx;
		force.y += fy;
		force.rotation += rx*fy - ry*fx;
	}
}

/*
THEORY for a model
==================

Movements of ship are counted as application of force + inertion.
Force is calculated as:
a. Moving force to center of mass
b. Rotation force to center of mass

To get these 2, in every border point we calculate the force (Fx, Fy):
	at point (bx, by)
	
	0. point recalculated according to ship rotation (point is still relative to center of mass):
	(rx, ry) = (bx*cosr - by*sinr, bx*sinr + by*cosr)

Algorythm of force calculation
	1. calculating point speed:
	speed of ship (sx, sy) + rotary speed of point:
		Point has coords (bx, by) and rotation (r/sec)
		point rot. speed = (ry*r, -rx*r)
	so point speed is (sx + ry*r, sy - rx * r):
	2. calculating flow speed: natural flow (nx, ny) at river point (rx, ry):
		(fx, fy) = (nx - sx - ry*r, ny - sy + rx * r)
	3. Flow applied force is Square * Flowspeed^2 Normale projection * flow force K
		Normale usual (nx, ny) - is reverse normale, pointed to inside ship.
		Normale rotated: (nrx, nry) = (nx * cos(r) - ny * sin(r), nx*sin(r) + ny * cos(r))
		(Fx, Fy) = SCALAR((-nrx, -nry) * (fx, fy))^2 * S * fK
	   - if normale and flow are on same direction, other flow force is used (//TODO: is it correct?)
		
This force is summed to get force(a) (SUM Fx, SUM Fy)
Rotation force is calculated as:
	Projection of force * distance to gravity center: 
 	vector multiplication (rx, ry) * (Fx, Fy) = rx*fy - ry*fx

*/
