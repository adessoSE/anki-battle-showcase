package com.commands;

import java.time.LocalTime;

import de.adesso.anki.battle.world.bodies.Vehicle;


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
