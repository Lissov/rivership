package com.pl.rivership.data.physics;
import com.pl.rivership.data.model.*;
import com.pl.rivership.data.model.items.*;

public class ForceCalcStick extends ForceCalcBase<Stick>
{
	@Override
	public Movement calculate(Stick item, River river)
	{
		Movement fp = getForce(river, item.position.x, item.position.y, true);
		
		float s = item.length * item.radius;
		// todo: include rotation
		_force.x = (fp.x - item.movement.x) * s;
		_force.y = (fp.y - item.movement.y) * s;
		_force.rotation = (fp.rotation - item.movement.rotation) * s / 3;
		
		return _force;
	}
}
