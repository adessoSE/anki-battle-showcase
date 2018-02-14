package com.commands;

import com.domain.Vehicle;

public class BrakeCommand extends Command {
	private int desiredSpeed; 
	
	public BrakeCommand(int desiredSpeed) {
		this.desiredSpeed = desiredSpeed; 
	}
	
	public void execute(Vehicle vehicle){
		vehicle.setSpeed(this.desiredSpeed);
	}
	
}
