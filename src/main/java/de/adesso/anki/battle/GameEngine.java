package de.adesso.anki.battle;

import de.adesso.anki.battle.providers.VehicleStateProvider;
import de.adesso.anki.battle.renderers.Renderer;
import de.adesso.anki.battle.world.Body;
import de.adesso.anki.battle.world.DynamicBody;
import de.adesso.anki.battle.world.World;
import de.adesso.anki.battle.world.bodies.Roadmap;
import lombok.extern.slf4j.Slf4j;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.commands.Command;
import com.states.GameState;
import com.states.InventoryRocket;
import com.states.LeftCurveAhead;
import com.states.RightCurveAhead;

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

    
    public void start() {
        running = true;
    }

    @Scheduled(fixedRate = 1000)
    public void gameLoop() {
    	VehicleStateProvider vehicleStateProvider = new VehicleStateProvider();
        if (running) {
            
        	
        	//prototype
            ArrayList<GameState> mapprototype = new ArrayList<>();
            RightCurveAhead rCurve = new RightCurveAhead(150); 
            mapprototype.add(rCurve);  
            RightCurveAhead rCurve2 = new RightCurveAhead(150); 
            mapprototype.add(rCurve2);  
            InventoryRocket rocketFact = new InventoryRocket();
            mapprototype.add(rocketFact);
            LeftCurveAhead lCurve = new LeftCurveAhead(150); 
            mapprototype.add(lCurve);  
            LeftCurveAhead lCurve2 = new LeftCurveAhead(150); 
            mapprototype.add(lCurve2);  
            
        	
            log.info("Entering game loop");
            // Step 1: Synchronize with real world
            // TODO: Synchronize with Anki vehicles


            
            
            // Step 2: Simulate movement
            updateSimulation();

          
            // Step 3: Process input
            // TODO: Process input from frontend

            // Step 4: Evaluate behavior
            
            
            //Roadmap.builder()
            //.start().right().right().straight().right().right().finish()
            //.build()
            
            Roadmap map =  world.getRoadmap();
     
            List<DynamicBody> dynBodies = world.getDynamicBodies();
            
     
/*            for (DynamicBody body : dynBodies) {
                List<GameState> factsRoad = vehicleStateProvider.getRoadFacts( body);
                List<GameState> factsObstacles= vehicleStateProvider.getObstacleFacts(map, body);
                List<GameState> factsInventory = vehicleStateProvider.getInventoryFacts(body);
                factsRoad.addAll(factsObstacles);
                factsRoad.addAll(factsInventory);
                body.setFacts(factsRoad);
            	//setFacts(facts, body);
            }
           evaluateBehavior();
*/
            
            
 /*           ArrayList<GameState> facts = new ArrayList<>();
            RightCurveAhead rCurve = new RightCurveAhead(150); 
            facts.add(rCurve);  

            RightCurveAhead rCurve2 = new RightCurveAhead(150); 
            facts.add(rCurve2);  
              
            setFacts(facts);
            evaluateBehavior();
            */
            for( GameState state :  mapprototype ) {
            	ArrayList <GameState> oneFactTest = new ArrayList<>() ;
            	oneFactTest.add(state);
            	setFacts(oneFactTest);
                evaluateBehavior();
            }


            // Step 5: Render world
            renderWorld();
        }
    }

    private void updateSimulation() {
        for (DynamicBody body : world.getDynamicBodies()) {
           body.updatePosition();
        }
    }

    

    
    
    
    private void setFacts(List<GameState> facts) {
    	//all vehicles have same facts, prototype
        for (DynamicBody body : world.getDynamicBodies()) {
           body.setFacts(facts);
        }
    }
    
    
    private void evaluateBehavior() {
        for (Body body : world.getBodies()) {
            body.evaluateBehavior();
        }
    }

    private void renderWorld() {
        for (Renderer renderer : renderers) {
            renderer.render(world);
        }
    }

}
