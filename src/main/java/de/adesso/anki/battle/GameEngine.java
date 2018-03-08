package de.adesso.anki.battle;

import com.states.GameState;
import de.adesso.anki.battle.mqtt.MqttService;
import de.adesso.anki.battle.providers.VehicleStateProvider;
import de.adesso.anki.battle.renderers.Renderer;
import de.adesso.anki.battle.sync.AnkiSynchronization;
import de.adesso.anki.battle.world.Body;
import de.adesso.anki.battle.world.DynamicBody;
import de.adesso.anki.battle.world.World;
import de.adesso.anki.battle.world.bodies.Vehicle;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
                    List<GameState> allFacts = new ArrayList<>();
                    List<GameState> factsRoad = vehicleStateProvider.getRoadFacts((Vehicle) body);
                    List<GameState> factsInventory = vehicleStateProvider.getInventoryFacts((Vehicle) body);
                    List<GameState> factsObstacles = vehicleStateProvider.getObstacleFacts((Vehicle) body);


                    //allFacts.addAll(factsRoad);
                    //allFacts.addAll(factsInventory);
                    //allFacts.addAll(factsObstacles);
                    body.setFacts(factsRoad, factsInventory, factsObstacles);
                }

            
				evaluateBehavior();


                stepCount = 0;
            }

            

  
            // Step 5: Render world
            renderWorld();
            calculateLaptime();
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

    

    private void collisionHandling() {
    	// TODO 
    }
    
  /*
    private void setFacts(List<GameState> facts) {
    	//all vehicles have same facts, prototype
        for (DynamicBody body : world.getDynamicBodies()) {
           body.setFacts(facts);
        }
    }
    */
    
    private void evaluateBehavior()  {
    	List<Body> oldBodies= new ArrayList<Body>(world.getBodies());
        for (Body body : oldBodies) {
            if (body instanceof Vehicle) {
    			try {
					body.evaluateBehavior(mqtt);
				} catch (MqttException e) {
					mqtt.connect();
					subscribeAllVehicles();
				}
            }
        }
    }

    private void renderWorld() {
        for (Renderer renderer : renderers) {
            renderer.render(world);
        }
    }

}
