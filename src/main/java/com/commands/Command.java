package com.commands;

import de.adesso.anki.battle.world.bodies.Vehicle;

import java.time.LocalTime;


public abstract class Command {

	private LocalTime time;
	
	protected Command() {
		time = LocalTime.now();
	}

	public LocalTime getTime() {
		return time;
	}
	
	public abstract void execute(Vehicle vehicle);
}
