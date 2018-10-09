package de.adesso.anki.battle.world.bodies;

import de.adesso.anki.battle.mqtt.MqttService;
import de.adesso.anki.battle.util.Position;
import de.adesso.anki.battle.world.Body;
import de.adesso.anki.battle.world.DynamicBody;
import de.adesso.anki.battle.world.World;
import de.adesso.anki.battle.world.bodies.collisionHandling.Collision;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.List;

public class Mine extends DynamicBody {
	private long timer ;
	private double offsetFromCenter;
	private String id;
	private static long mineIdCounter = 0;

	public double getOffset() {
		return offsetFromCenter;
	}

	public String getId()
	{
		return id;
	}

	public void setOffsetFromCenter(double offsetFromCenter) {
		this.offsetFromCenter = offsetFromCenter;
	}

	public Mine(){
		timer = System.currentTimeMillis();
		id = "Mine_" + mineIdCounter++;
	}
	
	// TODO adjust timers
	public boolean isActive () {
		return System.currentTimeMillis() > timer + 500 ;
	}

	@Override
	public void evaluateBehavior(MqttService mqtt) throws MqttException {
		World world = this.getWorld();
		if ( checkCollision(this,world)) {
			world.getBodiesModifiable().remove(this);
		}
	}
	@SuppressWarnings("Duplicates")
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
			if(pos2 != null && pos1 != null){
				double distance = pos1.distance(pos2);
				//TODO find distance value that indicates a collisionHandling
				double dummyValue = 80;

				if (distance < dummyValue) {
					//vehicle.setEnergy(vehicle.getEnergy() - damage);
					destroy = true;
					world.addCollision(new Collision(this.getClass(), this.id, vehicle.getAnkiReference().getAddress()));

					break;
				}
			}
		}

		return destroy;
	}
	


}
