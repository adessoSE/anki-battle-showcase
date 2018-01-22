package com.commands;

import de.adesso.anki.battle.world.World;
import de.adesso.anki.battle.world.bodies.Vehicle;

public class UseReflectorCommand extends Command {

	public UseReflectorCommand () {
		
	}
	
	public void execute(Vehicle vehicle) {
		/*TODO implement  reflection 
		*/
		
		World world = vehicle.getWorld();
		vehicle.setReflectorReady(false);
	}
}
