package com.pl.rivership.data;
import com.pl.rivership.data.model.*;
import android.graphics.*;
import com.pl.rivership.data.physics.*;
import com.pl.rivership.data.model.items.*;

public class GameHolder
{
	public static Game getCurrentGame(){
		return createGame();
	}
	
	private static Game createGame(){
		Game game = new Game(RiverHolder.getRiver(1));
		
		game.cameraPoint = new Movement(0, 0, 0);
		
		addItems(game);
		
		return game;
	}
	
	private static void addItems(Game game){

		Ship s = new Ship(
			new Movement(
				game.cameraPoint.x + 10f,
				game.cameraPoint.y + 5f,
				-(float)Math.PI /2f
			)
		);
		s.movement.y = 0;
		s.movement.x = 0;
		s.rudderAngle = 0f;
		s.thrustPosition = 3;

		game.items.add(s);
		game.ship = s;
	}
}
