package com.pl.rivership.data.model;

public class River
{
	public int currentTile;

	public RiverTile[] tiles;
	
	public RiverTile getCurrentTile(){
		return tiles[currentTile];
	}
	
	public River(int tilesCount, int tileSize){
		tiles = new RiverTile[tilesCount];
		for (int i = 0; i < tilesCount; i++){
			tiles[i] = new RiverTile(tileSize);
		}
	}
}
