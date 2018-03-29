package com.commands;

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
	
	
	@Override
	public void execute(Vehicle vehicle) {
		// TODO: set Positions
		if (!vehicle.isRocketReady()) {
			return;
		}
		World world = vehicle.getWorld();		
		Rocket rocket  = new Rocket (this.direction);
		//TODO speed of rocket
		rocket.setTargetSpeed(300);
		rocket.setPosition(vehicle.getPosition());
		rocket.setWorld(world);
		world.addBody(rocket);
		vehicle.setRocketReady(false);
	}
	
}



