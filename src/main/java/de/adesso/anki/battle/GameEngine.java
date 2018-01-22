package de.adesso.anki.battle;

import com.states.GameState;
import com.states.InventoryRocket;
import com.states.ObjectInFront;

import de.adesso.anki.battle.providers.VehicleStateProvider;
import de.adesso.anki.battle.renderers.Renderer;
import de.adesso.anki.battle.world.Body;
import de.adesso.anki.battle.world.DynamicBody;
import de.adesso.anki.battle.world.World;
import de.adesso.anki.battle.world.bodies.Vehicle;
import lombok.extern.slf4j.Slf4j;
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

    
    private boolean running = false;
    private long lastStep;

    
    public void start() {
        lastStep = System.nanoTime();
        running = true;
    }

    @Scheduled(fixedRate = 200)
    public void gameLoop() {
    	VehicleStateProvider vehicleStateProvider = new VehicleStateProvider();
        if (running) {
            
        	 
           
            // Step 0: Calculate elapsed nanoseconds since last loop
            long step = System.nanoTime();
            long deltaNanos = step - lastStep;
            lastStep = step;
            
            // Step 1: Synchronize with real world
            // TODO: Synchronize with Anki vehicles


            
            
            // Step 2: Simulate movement
            updateSimulation(deltaNanos);

          
            // Step 3: Process input
            // TODO: Process input from frontend

            // Step 4: Evaluate behavior
            
            
            
            //Roadmap map =  world.getRoadmap();
     
            List<DynamicBody> dynBodies = world.getDynamicBodies();
            
            for (DynamicBody body : dynBodies) {
            	log.debug(body.toString());
            	if (!(body instanceof Vehicle))
            	{
            		continue;
            	}
            	List <GameState> allFacts = new ArrayList<>();
            	List<GameState> factsRoad = vehicleStateProvider.getRoadFacts((Vehicle) body);
            	List<GameState> factsInventory = vehicleStateProvider.getInventoryFacts((Vehicle)body);
            	List<GameState> factsObstacles = vehicleStateProvider.getObstacleFacts((Vehicle)body);
   
            	
            	allFacts.addAll(factsRoad);
            	allFacts.clear();
            	allFacts.addAll(factsInventory);
            	allFacts.addAll(factsObstacles);
                body.setFacts(allFacts);
            }
           
            evaluateBehavior();

            
            
 /*           ArrayList<GameState> facts = new ArrayList<>();
            RightCurveAhead rCurve = new RightCurveAhead(150); 
            facts.add(rCurve);  

            RightCurveAhead rCurve2 = new RightCurveAhead(150); 
            facts.add(rCurve2);  
              
            setFacts(facts);
            evaluateBehavior();
            */

            // Step 5: Render world
            renderWorld();
        }
    }

    private void updateSimulation(long deltaNanos) {
        for (DynamicBody body : world.getDynamicBodies()) {
           body.updatePosition(deltaNanos);
        }
    }

    

    
    
    
    private void setFacts(List<GameState> facts) {
    	//all vehicles have same facts, prototype
        for (DynamicBody body : world.getDynamicBodies()) {
           body.setFacts(facts);
        }
    }
    
    
    private void evaluateBehavior() {
    	List<Body> oldBodies= new ArrayList<Body>(world.getBodies());
        for (Body body : oldBodies) {
            body.evaluateBehavior();
        }
    }

    private void renderWorld() {
        for (Renderer renderer : renderers) {
            renderer.render(world);
        }
    }

}
