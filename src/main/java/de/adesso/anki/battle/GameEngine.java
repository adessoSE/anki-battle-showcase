package de.adesso.anki.battle;

import de.adesso.anki.battle.providers.VehicleStateProvider;
import de.adesso.anki.battle.renderers.Renderer;
import de.adesso.anki.battle.world.Body;
import de.adesso.anki.battle.world.DynamicBody;
import de.adesso.anki.battle.world.World;
import lombok.extern.slf4j.Slf4j;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.commands.Command;
import com.states.GameState;
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
        if (running) {
            log.info("Entering game loop");
            // Step 1: Synchronize with real world
            // TODO: Synchronize with Anki vehicles

            // Step 2: Simulate movement
            updateSimulation();

          
            // Step 3: Process input
            // TODO: Process input from frontend

            // Step 4: Evaluate behavior
            ArrayList<GameState> facts = new ArrayList<>();
            RightCurveAhead rCurve = new RightCurveAhead(150); 

            facts.add(rCurve);  
            
            setFacts(facts);
            evaluateBehavior();

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
