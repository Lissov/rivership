package com.pl.rivership.data.model;
import com.pl.rivership.data.model.items.*;
import java.util.*;
import android.graphics.*;
import com.pl.rivership.data.physics.*;

public class Game
{
	public River river;
	public List<Item> items;
	
	public Movement cameraPoint;

	public Game(River river)
	{
		this.river = river;
		this.items = new LinkedList<Item>();
	}
}
