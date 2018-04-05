package de.adesso.anki.battle.protocol.commands;

import de.adesso.anki.battle.world.bodies.Vehicle;
public class ChangeLaneCommand extends Command {
	
	private double offset;

	public ChangeLaneCommand(double offset) {
		if (offset > 67.5) {
			offset = 67.5;
		}
		if (offset < -67.5) {
			offset = -67.5;
		}
		this.offset = offset;
	}
		
	@Override
	public void execute(Vehicle vehicle) {
		vehicle.setTargetOffset(offset);
	}

}
