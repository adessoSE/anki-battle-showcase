package com.commands;

import de.adesso.anki.battle.world.bodies.Vehicle;
public class ChangeLaneCommand extends Command {
	
	private double offset;

	public ChangeLaneCommand(double offset) {
		this.offset = offset;
	}
		
	@Override
	public void execute(Vehicle vehicle) {
		vehicle.setTargetOffset(offset);
	}

}
