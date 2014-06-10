package com.pl.rivership.data.physics;
import com.pl.rivership.data.model.*;
import com.pl.rivership.data.model.items.*;

public class ForceCalcShip extends ForceCalcBase<Ship>
{
	@Override
	public Movement calculate(Ship item, River river)
	{
		_force.x = 0;
		_force.y = 0;
		_force.rotation = 10000f;
		//J = 1/12 m(a2 + b2)
		// TODO: Implement this method
		
		
		/*for (float y = -Ship.ShipLength/2f; y < Ship.ShipLength/2f; y+=0.5f){
			float x = Ship.ShipWidth / 2f;
			
			item.position.rotation
		}*/
		
		for (float x = -Ship.ShipWidth/2f; x<0; x+=0.5f){
			
		}
		/*for (float y = -Ship.ShipLength/2f; y < Ship.ShipLength/2f; y+=0.5f){
			float x = Ship.ShipWidth / 2f;

			item.position.rotation
		}*/

		return _force;
		
	}
}
