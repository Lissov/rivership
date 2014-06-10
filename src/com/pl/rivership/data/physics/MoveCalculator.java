package com.pl.rivership.data.physics;
import com.pl.rivership.data.model.items.*;
import com.pl.rivership.data.model.*;

public class MoveCalculator
{
	private ForceCalculator forceCalc = new ForceCalculator();
	public void calculate(Item item, River river, float deltaT){
		float inertR = deltaT / item.momentI;
		
		Movement f = forceCalc.calculate(item, river);
		if (item.mass == 0){
			item.movement.x = f.x;
			item.movement.y = f.y;
		} else{
			float inert = deltaT / item.mass;
			item.movement.x += f.x * inert;
			item.movement.y += f.y * inert;
		}
		item.movement.rotation += f.rotation * inertR;

		item.position.x += item.movement.x * deltaT;
		item.position.y += item.movement.y * deltaT;
		item.position.rotation += item.movement.rotation * deltaT;
	}
}
