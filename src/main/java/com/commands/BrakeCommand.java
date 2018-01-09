package com.commands;

public class BrakeCommand extends Command {
	private int desiredSpeed; 
	
	public BrakeCommand(int desiredSpeed) {
		this.desiredSpeed = desiredSpeed; 
	}
}
