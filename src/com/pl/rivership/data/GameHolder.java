package com.pl.rivership.data;
import com.pl.rivership.data.model.*;
import android.graphics.*;
import com.pl.rivership.data.physics.*;

public class GameHolder
{
	public static Game getCurrentGame(){
		return createGame();
	}
	
	private static Game createGame(){
		Game game = new Game(RiverHolder.getRiver(1));
		
		game.cameraPoint = new Movement(0, 0, 0);
		
		return game;
	}
}
