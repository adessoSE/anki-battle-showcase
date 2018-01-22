package com.commands;

import de.adesso.anki.battle.world.World;
import de.adesso.anki.battle.world.bodies.Mine;
import de.adesso.anki.battle.world.bodies.Vehicle;

public class PutMineCommando {
	private int track; 
	
	
	public PutMineCommando (int track) {
		this.track = track;
	}
	
	public void execute(Vehicle vehicle) {
		// TODO: set Positions
		
		World world = vehicle.getWorld();
		Mine mine = new Mine ();
		world.addBody(mine);
		mine.setWorld(world);
		vehicle.setMineReady(false);
	}
	
	
}
