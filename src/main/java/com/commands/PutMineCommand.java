package com.commands;

import de.adesso.anki.battle.world.World;
import de.adesso.anki.battle.world.bodies.Mine;
import de.adesso.anki.battle.world.bodies.Vehicle;

public class PutMineCommand {
	private int track; 
	
	
	public PutMineCommand (int track) {
		this.track = track;
	}
	
	public void execute(Vehicle vehicle) {
		// TODO: set Positions
		
		World world = vehicle.getWorld();
		Mine mine = new Mine ();
		mine.setPosition(vehicle.getPosition());
		world.addBody(mine);
		mine.setWorld(world);
		vehicle.setMineReady(false);
	}
	
	
}
