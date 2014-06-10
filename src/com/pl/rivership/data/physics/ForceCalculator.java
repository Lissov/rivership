package com.pl.rivership.data.physics;
import com.pl.rivership.data.model.*;
import com.pl.rivership.data.model.items.*;

public class ForceCalculator extends ForceCalcBase<Item>
{
	private ForceCalcBase dotCalc = new ForceCalcWhiteDot();
	private ForceCalcBase stickCalc = new ForceCalcStick();
	private ForceCalcBase shipCalc = new ForceCalcShip();
	
	@Override
	public Movement calculate(Item item, River river){
		switch (item.type){
			case Stick:
				return stickCalc.calculate(item, river);
			case Ship:
				return shipCalc.calculate(item, river);
			case WhiteDot:
				return dotCalc.calculate(item, river);
			default:
				return null;
		}
	}
}
