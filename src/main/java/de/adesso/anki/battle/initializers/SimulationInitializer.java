package de.adesso.anki.battle.initializers;


import de.adesso.anki.battle.GameEngine;
import de.adesso.anki.battle.util.Position;
import de.adesso.anki.battle.world.World;
import de.adesso.anki.battle.world.bodies.Roadmap;
import de.adesso.anki.battle.world.bodies.Vehicle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("simulation")
public class SimulationInitializer implements ApplicationRunner {

    @Autowired
    private World world;

    @Autowired
    private GameEngine engine;

    @Override
    public void run(ApplicationArguments args) {
        log.info("Initializing game with simulated environment");

        buildRoadmap();
        addVehicles();

        startEngine();
    }

    private void buildRoadmap() {
        Roadmap map = Roadmap.builder()
                        .start().left().left().straight().left().left().finish()
                        .build();

        world.setRoadmap(map);
    }

    private void addVehicles() {
        Vehicle vehicle = new Vehicle();
        vehicle.setCurrentRoadpiece(world.getRoadmap().getAnchor());
        vehicle.setPosition(world.getRoadmap().getAnchor().getEntry());
        vehicle.setTargetSpeed(500);

        world.addBody(vehicle);
    }

    private void startEngine() {
        engine.start();
    }
}
