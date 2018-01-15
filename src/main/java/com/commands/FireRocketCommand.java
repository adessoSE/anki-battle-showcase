package com.commands;

import de.adesso.anki.battle.world.bodies.Vehicle;

public class FireRocketCommand extends Command {
	private String direction;  // forward or backwards

	public FireRocketCommand(String direction) {
		this.direction = direction;
	}
	
	
	public void execute(Vehicle vehicle) {
		// create new Dynamic Body
		//remove Rocket from Inventory
	}
	
}



