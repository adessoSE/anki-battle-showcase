package de.adesso.anki.battle;

import com.states.GameState;
import de.adesso.anki.battle.mqtt.MqttService;
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

    private int stepCount = 0;


    public void start() {
        lastStep = System.nanoTime();
        running = true;
        subscribeAllVehicles ();

    	 
    }
    //TODO set ready at the same time, 
    // cool ideas like random sampling can be also used
    
	@Scheduled(fixedRate= 10000)
	public void RocketReadySchedule() {
		for (Vehicle vehicle: world.getVehicles() ) {
			vehicle.setRocketReady(true);
		}
	}
	@Scheduled(fixedRate= 10000)
	public void MineReadySchedule() {
		for (Vehicle vehicle: world.getVehicles() ) {
			vehicle.setMineReady(true);
		}
	}
    
	@Scheduled(fixedRate= 10000)
	public void ShieldSchedule() {
		for (Vehicle vehicle: world.getVehicles() ) {
			vehicle.setShieldReady(true);
		}
	}
    
	@Scheduled(fixedRate= 10000)
	public void ReflectorSchedule() {
		for (Vehicle vehicle: world.getVehicles() ) {
			vehicle.setReflectorReady(true);
		}
	}
    
	
    

    @Scheduled(fixedRate = 50)
    public void gameLoop() {
    	VehicleStateProvider vehicleStateProvider = new VehicleStateProvider();
        if (running) {
           
            // Step 0: Calculate elapsed nanoseconds since last loop
            long step = System.nanoTime();
            long deltaNanos = step - lastStep;
            lastStep = step;
            
            // Step 1: Synchronize with real world
            // TODO: Synchronize with Anki vehicles
            if (stepCount == 0) {
                for (DynamicBody body : world.getDynamicBodies()) {
                    if (body instanceof Vehicle)
                        anki.synchronizeState((Vehicle) body);
                }
            }

            
            
            // Step 2: Simulate movement
            updateSimulation(deltaNanos);

          
            // Step 3: Process input
            // TODO: Process input from frontend

            // Step 4: Evaluate behavior

            stepCount++;
            if (stepCount > 3) {

                List<DynamicBody> dynBodies = world.getDynamicBodies();

                for (DynamicBody body : dynBodies) {
                    log.debug(body.toString());
                    if (!(body instanceof Vehicle)) {
                        continue;
                    }
                    List<GameState> factsRoad = vehicleStateProvider.getRoadFacts((Vehicle) body);
                    List<GameState> factsInventory = vehicleStateProvider.getInventoryFacts((Vehicle) body);
                    List<GameState> factsObstacles = vehicleStateProvider.getObstacleFacts((Vehicle) body);

                    body.setFacts(factsRoad, factsInventory, factsObstacles);
                }

            
				evaluateBehavior();


                stepCount = 0;
            }

            
            // Remove while iterating, leads to exception 
            //java.util.ConcurrentModificationException: in renderer?
            //collisionHandling();
            collectOrphanedWeapons();
  
            // Step 5: Render world
            renderWorld();

            
            
            calculateLaptime();
            checkForGameEnding();
        }
        
    }
    
    
    private void checkForGameEnding() {
    	if (world.getVehicles().size() == 1) {
    		log.debug("Winner: " + world.getVehicles().get(0).getName());
    	}
    	updateVehicleList();
    }

    private void updateVehicleList() {
    	Iterator<Body> it = world.getBodiesModifiable().iterator();
        while(it.hasNext()){
        	Body vehicle = it.next();
        	if (vehicle instanceof Vehicle && vehicleDeafeated((Vehicle) vehicle)) {
        		it.remove();
        		log.debug("Delete orphaned rocket");
        	}
        }
    	
    	
    	/*for (Vehicle vehicle : world.getVehicles()) {
    		System.out.println(world.getVehicles().size());
    		if  (vehicle.getEnergy() <= 0 ) {
            	boolean aa  = world.getVehicles().remove(vehicle);
            	System.out.println(aa);
    		}
    	}*/
    }
    	//world.getVehicles().removeIf(this::vehicleDeafeated);

    
    private boolean vehicleDeafeated(Vehicle vehicle) {
    	return vehicle.getEnergy() <= 0 ; 
    }

	private void calculateLaptime() {
		for(DynamicBody body : world.getDynamicBodies()) {
			if (body instanceof Vehicle) {
				((Vehicle)body).updateLapTime();
			}
		}
	}

	public void subscribeAllVehicles()  {
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
        		System.out.println("Delete orphaned rocket");
        	}
        }
	}

	
	//merge though filter predicate with method above
	private void collisionHandling() {
		Iterator<Body> it = world.getBodiesModifiable().iterator();
        while(it.hasNext()){
        	Body weapon = it.next();
        	if (checkCollision(weapon)) {
        		it.remove();
        	}
        }
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
				System.out.println("BOOM: " + weapon.getClass().getSimpleName()); 
				vehicle.setEnergy(vehicle.getEnergy() - damage);
				System.out.println(vehicle.getEnergy());
				succesfulHit = true;
			}
    	}
		return succesfulHit;
    }
    
 
    
    private void evaluateBehavior()  {
    	List<Body> oldBodies= new ArrayList<Body>(world.getBodies());
        for (Body body : oldBodies) {
        	try {
				body.evaluateBehavior(mqtt);
			} catch (MqttException e) {
				mqtt.connect();
				subscribeAllVehicles();
			}
/*            if (body instanceof Vehicle) {
    			try {
					body.evaluateBehavior(mqtt);
				} catch (MqttException e) {
					mqtt.connect();
					subscribeAllVehicles();
				}*/
        }
        
    }

    private void renderWorld() {
        for (Renderer renderer : renderers) {
            renderer.render(world);
        }
    }

}
