package com.commands;

import de.adesso.anki.battle.world.bodies.Vehicle;

public class AccelerateCommand extends Command {
	private int desiredSpeed;
	
	public AccelerateCommand(int desiredSpeed) {
		this.desiredSpeed = desiredSpeed;
	}
	
	
	@Override
	public void execute(Vehicle vehicle){
		vehicle.setTargetSpeed(desiredSpeed);
	}
	
}
