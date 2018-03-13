package com.commands;

import de.adesso.anki.battle.world.World;
import de.adesso.anki.battle.world.bodies.Vehicle;

public class ShieldCommand extends Command {
	public ShieldCommand () {
	}
	
	public void execute(Vehicle vehicle) {
		// TODO:destroy Rocket, mine 
		
		World world = vehicle.getWorld();
		// amount that the shield absorbs
		// vehicle.setEnergy(vehicle.getEnergy()+25);
		vehicle.setShieldReady(false);
	}
}
