package de.adesso.anki.battle;

import de.adesso.anki.battle.renderers.Renderer;
import de.adesso.anki.battle.world.Body;
import de.adesso.anki.battle.world.DynamicBody;
import de.adesso.anki.battle.world.World;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

    @Scheduled(fixedDelay = 1000)
    public void gameLoop() {
        if (running) {
            //log.info("Entering game loop");

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
            evaluateBehavior();

            // Step 5: Render world
            renderWorld();
        }
    }

    private void updateSimulation(long deltaNanos) {
        for (DynamicBody body : world.getDynamicBodies()) {
           body.updatePosition(deltaNanos);
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
