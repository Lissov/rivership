package com.pl.rivership.data.physics;
import com.pl.rivership.data.model.*;
import com.pl.rivership.data.model.items.*;

public class ForceCalcWhiteDot extends ForceCalcBase<WhiteDot>
{
	@Override
	public Movement calculate(WhiteDot item, River river)
	{
		return getForce(river, item.position.x, item.position.y, false);
	}
}
