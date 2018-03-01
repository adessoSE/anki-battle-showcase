package com.commands;

import de.adesso.anki.battle.world.bodies.Vehicle;
public class ChangeLaneCommand extends Command {
	
	private int track;
	public  ChangeLaneCommand(int track) {
		this.track = track;
	}
		
	@Override
	public void execute(Vehicle vehicle) {
		vehicle.setTrack(this.track);
	}

}
