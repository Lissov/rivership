package com.pl.rivership.data.model;
import android.graphics.*;

public class RiverTile
{
	public PointF southwestCorner;
	
	public float[][] flowX;
	public float[][] flowY;
	
	public RiverTile(int size){
		flowX = new float[size][size];
		flowY = new float[size][size];
	}
}
