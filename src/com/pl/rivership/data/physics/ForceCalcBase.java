package com.pl.rivership.data.physics;
import com.pl.rivership.data.model.*;
import android.graphics.*;
import com.pl.rivership.data.model.items.*;

public abstract class ForceCalcBase<ItemT>
{
	protected Movement _force;
	private Movement _pointF;
	
	public ForceCalcBase(){
		_force = new Movement(0,0,0);
		_pointF = new Movement(0,0,0);
	}
	
	public abstract Movement calculate(ItemT item, River river);
	
	protected Movement getForce(River river, float x, float y, boolean calcRotation){
		RiverTile rt = river.getCurrentTile();
		float xtile = x - rt.southwestCorner.x;
		float ytile = y - rt.southwestCorner.y;
		int xp = (int)Math.floor(xtile);
		int yp = (int)Math.floor(ytile);
		
		if (xp < 0 || yp < 0 
			|| xp >= rt.flowX[0].length - 1
			|| yp >= rt.flowX[0].length - 1)
		{
			_pointF.x = 0;
			_pointF.y = 0;
			_pointF.rotation = 0;
			return _pointF;
		}

		float xdn = xtile - xp;
		float ydn = ytile - yp;
		float xd = 1f - xdn;
		float yd = 1f - ydn;
		
		_pointF.x = rt.flowX[xp][yp] * xd * yd
				+ rt.flowX[xp+1][yp] * xdn * yd
				+ rt.flowX[xp][yp+1] * xd * ydn
				+ rt.flowX[xp+1][yp+1] * xdn * ydn;
		_pointF.y = rt.flowY[xp][yp] * xd * yd
				+ rt.flowY[xp+1][yp] * xdn * yd
				+ rt.flowY[xp][yp+1] * xd * ydn
				+ rt.flowY[xp+1][yp+1] * xdn * ydn;
				
		
		if (!calcRotation){
			_pointF.rotation = 0;
			return _pointF;			
		}
		
		float m1 = -/*(xdn)**/rt.flowY[xp][yp] + /*(ydn)**/rt.flowX[xp][yp];
		float m2 = /*(xd)**/rt.flowY[xp+1][yp] + /*(ydn)**/rt.flowX[xp+1][yp];
		float m3 = -/*(xdn)**/rt.flowY[xp][yp+1] - /*(yd)**/rt.flowX[xp][yp+1];
		float m4 = /*(xd)**/rt.flowY[xp+1][yp+1] - /*(yd)**/rt.flowX[xp+1][yp+1];
		_pointF.rotation = (m1 + m2 + m3 + m4) / 4;
		
		return _pointF;
	} 
}
