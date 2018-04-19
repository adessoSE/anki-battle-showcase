package de.adesso.anki.battle;

import de.adesso.anki.battle.mqtt.MqttService;
import de.adesso.anki.battle.protocol.states.GameState;
import de.adesso.anki.battle.providers.VehicleStateProvider;
import de.adesso.anki.battle.renderers.Renderer;
import de.adesso.anki.battle.sync.AnkiSynchronization;
import de.adesso.anki.battle.util.Position;
import de.adesso.anki.battle.world.Body;
import de.adesso.anki.battle.world.DynamicBody;
import de.adesso.anki.battle.world.World;
import de.adesso.anki.battle.world.bodies.Mine;
import de.adesso.anki.battle.world.bodies.Rocket;
import de.adesso.anki.battle.world.bodies.Vehicle;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Component
public class GameEngine {

    @Autowired
    private World world;

    @Autowired
    private List<Renderer> renderers;

    @Autowired
    private MqttService mqtt;


    @Autowired
    private AnkiSynchronization anki;


    private boolean running = false;
    private long lastStep;

    private long stepCount = 0;


    public void start() {
        lastStep = System.nanoTime();
        running = true;
        subscribeAllVehicles();
    }

    @Scheduled(fixedRate = 50)
    public void gameLoop() {
        if (running) {
            // Step 0: Calculate elapsed nanoseconds since last loop
            long step = System.nanoTime();
            long deltaNanos = step - lastStep;
            lastStep = step;
            
            // Step 1: Synchronize with real world
			if (stepCount % 4 == 0) {
				synchronizeAnki();
			}

			// Step 2: Simulate movement
            updateSimulation(deltaNanos);

            // Step 3: Evaluate behavior
            if (stepCount % 4 == 0) {
                generateFacts();
				evaluateBehavior();
            }

            collisionHandling();
            collectOrphanedWeapons();
  
            // Step 4: Render world
            renderWorld();

            calculateLaptime();

            stepCount++;
        }
    }

    private void generateFacts() {
        VehicleStateProvider vehicleStateProvider = new VehicleStateProvider();
        List<DynamicBody> dynBodies = world.getDynamicBodies();

        for (DynamicBody body : dynBodies) {
            log.debug(body.toString());
            if (!(body instanceof Vehicle)) {
                continue;
            }

            final Vehicle vehicle = (Vehicle) body;

            List<GameState> factsRoad = vehicleStateProvider.getRoadFacts(vehicle);
            List<GameState> factsInventory = vehicleStateProvider.getInventoryFacts(vehicle);
            List<GameState> factsObstacles = vehicleStateProvider.getObstacleFacts(vehicle);

            vehicle.setFacts(factsRoad, factsInventory, factsObstacles);
        }
    }

    private void synchronizeAnki() {
		for (Vehicle v : world.getVehicles()) {
			anki.synchronizeState(v);
		}
	}


	private void calculateLaptime() {
		for(DynamicBody body : world.getDynamicBodies()) {
			if (body instanceof Vehicle) {
				((Vehicle)body).updateLapTime();
			}
		}
	}

	private void subscribeAllVehicles()  {
        for (DynamicBody body : world.getDynamicBodies()) {
        	if (body instanceof Vehicle)
        	{
        		try {
					mqtt.subscribe(((Vehicle) body).getName() + "_sub");
				} catch (MqttException e) {
					mqtt.connect();
					subscribeAllVehicles();
				}
        	}
        }
	}

	private void updateSimulation(long deltaNanos) {
        for (DynamicBody body : world.getDynamicBodies()) {
           body.updatePosition(deltaNanos);
        }
    }
	
	
    private void collectOrphanedWeapons() {
    	Iterator<Body> it = world.getBodiesModifiable().iterator();
        while(it.hasNext()){
        	Body weapon = it.next();
        	if (weapon instanceof Rocket && ((Rocket) weapon).shouldExplode()) {
        		it.remove();
        		log.debug("Delete orphaned rocket");
        	}
        }
	}

	
	//merge though filter predicate with method above
	private void collisionHandling() {
        world.getBodiesModifiable().removeIf(this::checkCollision);
    }

	private boolean checkCollision(Body weapon) {
		if ( weapon instanceof Vehicle) {
			return false;
		}
		// merge into new superclass weapon ? 
    	if (weapon instanceof Rocket && !((Rocket) weapon).isActive()) {
    		return false;
    	}
    	if (weapon instanceof Mine && !((Mine) weapon).isActive()) {
    		return false;
    	}
    	
    	List<Vehicle> vehicles = world.getVehicles();
		Position pos1 = weapon.getPosition();
		boolean succesfulHit = false;
		//TODO find damage values for weapon types
		int damage = ((weapon instanceof Rocket ) ? 10 : 20);
    	for (Vehicle vehicle : vehicles) {
			Position pos2 = vehicle.getPosition();
			double distance = pos1.distance(pos2);
			//TODO find distance value that indicates a collision
			double dummyValue = 30; 
			if (distance < dummyValue) {
				log.debug("BOOM: " + weapon.getClass().getSimpleName());
				vehicle.setEnergy(vehicle.getEnergy() - damage);
				log.debug("energy=" + vehicle.getEnergy());
				succesfulHit = true;
			}
    	}
		return succesfulHit;
    }
    
 
    
    private void evaluateBehavior()  {
    	List<Body> oldBodies= new ArrayList<>(world.getBodies());
        for (Body body : oldBodies) {
        	try {
				body.evaluateBehavior(mqtt);
			} catch (MqttException e) {
				mqtt.connect();
				subscribeAllVehicles();
			}
        }
        
    }

    private void renderWorld() {
        for (Renderer renderer : renderers) {
            renderer.render(world);
        }
    }

}
