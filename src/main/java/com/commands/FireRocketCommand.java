package com.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.adesso.anki.battle.world.World;
import de.adesso.anki.battle.world.bodies.Rocket;
import de.adesso.anki.battle.world.bodies.Vehicle;
import lombok.extern.slf4j.Slf4j;



@Slf4j
public class FireRocketCommand extends Command {
	private String direction;  // forward or backwards

	
	public FireRocketCommand(String direction) {
		this.direction = direction;
	}
	
	
	public void execute(Vehicle vehicle) {
		// TODO: set Positions
		World world = vehicle.getWorld();		
		Rocket rocket  = new Rocket (this.direction);
		rocket.setTargetSpeed(100);
		rocket.setWorld(world);
		world.addBody(rocket);
		vehicle.setRocketReady(false);
	}
	
}



