package com.pl.rivership.data;
import com.pl.rivership.data.model.*;
import android.graphics.*;
import java.util.*;

public class RiverHolder
{
	public static int TileSize = 1000;
	public static River getRiver(int id){
		return getRiver1();
	}
	
	private static River getRiver1(){
		Random r = new Random();
		
		River res = new River(1, TileSize);
		
		res.tiles[0].southwestCorner = new PointF(-TileSize/2, -200);
		for (int x = 0; x < TileSize; x++){
			for (int y = 0; y < TileSize; y++){
				res.tiles[0].flowX[x][y] = 0;//0f + 0.1f * (float)r.nextGaussian();
				res.tiles[0].flowY[x][y] = 0;//-1f + 0.3f * (float)r.nextGaussian();
			}
		}
		
		return res;
	}
}
