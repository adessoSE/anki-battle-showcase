package com.commands;

import de.adesso.anki.battle.world.bodies.Vehicle;

public class DriveLeftCommand extends Command{
	public DriveLeftCommand() {
	}
	
	@Override
	public void execute(Vehicle vehicle){
		vehicle.setTargetOffset(-67.5);
	}
}
