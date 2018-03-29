package de.adesso.anki.battle.world.bodies;

import com.states.GameState;
import de.adesso.anki.battle.mqtt.MqttService;
import de.adesso.anki.battle.util.Position;
import de.adesso.anki.battle.world.Body;
import de.adesso.anki.battle.world.DynamicBody;
import de.adesso.anki.battle.world.World;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.List;

public class Mine extends DynamicBody {
	private long timer ;
	
	
	public Mine(){
		timer = System.currentTimeMillis();
	}

	
	
	// TODO adjust timers
	public boolean isActive () {
		return System.currentTimeMillis() > timer + 500 ;
	}

	

	//@Override
	public void setFacts(List<GameState> facts) {
		// TODO Auto-generated method stub
		
	}


	//@Override
	public void evaluateBehavior() {
		// TODO Auto-generated method stub

	}


	@Override
	public void evaluateBehavior(MqttService mqtt) throws MqttException {
		World world = this.getWorld();
		if ( checkCollision(this,world)) {
			world.getBodiesModifiable().remove(this);
		}
	}
		private boolean checkCollision(Body weapon, World world) {
			
			//merge into weapon superclass
	    	if (weapon instanceof Mine && !((Mine) weapon).isActive()) {
	    		return false;
	    	}

	    	List<Vehicle> vehicles = world.getVehicles();
			Position pos1 = weapon.getPosition();
			boolean destroy = false;
			//TODO find damage values for weapon types
			int damage = 20;
	    	for (Vehicle vehicle : vehicles) {
				Position pos2 = vehicle.getPosition();
				double distance = pos1.distance(pos2);
				//TODO find distance value that indicates a collision
				double dummyValue = 30; 
				if (distance < dummyValue) {
					vehicle.setEnergy(vehicle.getEnergy() - damage);
					destroy = true;
				}
	    	}

			return destroy;
	    }
	


}
