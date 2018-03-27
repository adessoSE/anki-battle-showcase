package com.commands;

import de.adesso.anki.battle.world.bodies.Vehicle;

import java.time.LocalTime;


public class Command {
	private LocalTime time ;
	
	public Command () {
		time = LocalTime.now();
	}
	public LocalTime getTime () {
		return time;
	} 
	
	
	public void execute(Vehicle vehicle) {
		
	}
	
}
