package com.pl.rivership.data.model.items;
import android.graphics.*;
import com.pl.rivership.data.physics.*;

public abstract class Item
{
	public ItemType type;
	public Movement position;
	public float mass;
	public float momentI;
	public Movement movement;

	public Item(ItemType type, Movement position, float mass, float momentI)
	{
		this.type = type;
		this.position = position;
		this.mass = mass;
		this.momentI = momentI;
		this.movement = new Movement(0,0,0);
	}
}
