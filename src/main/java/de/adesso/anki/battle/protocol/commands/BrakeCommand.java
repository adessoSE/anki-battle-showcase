package de.adesso.anki.battle.protocol.commands;

import de.adesso.anki.battle.world.bodies.Vehicle;

public class BrakeCommand extends Command {
	private int desiredSpeed; 
	
	public BrakeCommand(int desiredSpeed) {
		this.desiredSpeed = desiredSpeed; 
	}
	
	@Override
	public void execute(Vehicle vehicle){
		vehicle.setTargetSpeed(desiredSpeed);
	}
	
}
