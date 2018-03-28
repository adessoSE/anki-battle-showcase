package com.commands;

import de.adesso.anki.battle.world.DynamicBody;
import de.adesso.anki.battle.world.World;
import de.adesso.anki.battle.world.bodies.Vehicle;

public class UseReflectorCommand extends Command {
	
	@Override
    public void execute(Vehicle vehicle) {
		/*TODO implement  reflection 
		*/

		World world = vehicle.getWorld();
		for (DynamicBody dbody : world.getDynamicBodies()) {
			//reverse  when near
			double distance = vehicle.getPosition().distance(dbody.getPosition());
			if (distance< 100){
				// reverse roadmap
			}
		}
		vehicle.setReflectorReady(false);
	}

}
