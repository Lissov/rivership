package com.pl.rivership.data;
import com.pl.rivership.data.model.*;
import java.util.*;
import com.pl.rivership.data.model.items.*;
import com.pl.rivership.data.physics.*;

public class GameController
{
	public Game game;
	private MoveCalculator moveCalc;

	public GameController(Game game)
	{
		this.game = game;
		this.moveCalc = new MoveCalculator();
		
		this.game.items.add(
			new WhiteDot(
				new Movement(
					game.cameraPoint.x+1,
					game.cameraPoint.y,
					0))
		);

		this.game.items.add(
			new Stick(
				new Movement(
					game.cameraPoint.x,
					game.cameraPoint.y,
					0),
				0.5f)
				);
		this.game.items.add(
			new Ship(
				new Movement(
					game.cameraPoint.x + 10f,
					game.cameraPoint.y + 5f,
					0f),
				100000f)
		);
	}
	
	private long lastUpdated = 0;
	public void update(){
		
		long now = new Date().getTime();
		long diff = now - lastUpdated;
		
		if (diff == 0)
			return; // too often

		lastUpdated = now;
		if (diff  > 1000)
			return;  // lag - ignore
		
		float td = (float)diff / 1000f;
		
		updatePositions(td);
		updateCamera(td);
	}
	
	private void updatePositions(float td) {
		for (Item item : game.items){
			moveCalc.calculate(item, game.river, td);
		}
	}
	
	private void updateCamera(float td) {
		// to implement
	}
	
	private long lastPointsAdd = 0;
	public void addRemoveItems(float visibilityR, float removeR) {
		long now = new Date().getTime();
		long diff = now - lastUpdated;

		if (diff < 5000)
			return;
		
		addPoints(visibilityR);
		removeInvisibleItems(removeR);
	}
	private void addPoints(float visibilityR) {
		for (double angle = 0; angle < Math.PI * 2; angle += 0.1f){
			game.items.add(new Stick(
				new Movement(
					game.cameraPoint.x + (float)Math.cos(angle) * visibilityR,
					game.cameraPoint.y + (float)Math.sin(angle) * visibilityR,
					0),
				0.5f)
			);	
		}
	}
	
	private void removeInvisibleItems(float invisibilityR){
		int i = 0;
		float d2 = invisibilityR * invisibilityR;
		while (i < game.items.size()){
			float d = game.items.get(i).position.getDistanceTo2(game.cameraPoint);
			if (d > d2)
				game.items.remove(i);
			else
				i++;
		}
	}
}
