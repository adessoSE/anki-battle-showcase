package com.commands;

import de.adesso.anki.battle.world.bodies.Vehicle;

public class ShieldCommand extends Command {
	
	@Override
    public void execute(Vehicle vehicle) {
		// TODO:destroy Rocket, mine

		// amount that the shield absorbs
		// vehicle.setEnergy(vehicle.getEnergy()+25);
		vehicle.setShieldReady(false);
	}

}
