package de.adesso.anki.battle.initializers;


import de.adesso.anki.battle.GameEngine;
import de.adesso.anki.battle.world.World;
import de.adesso.anki.battle.world.bodies.Mine;
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
                        .left().left().left().straight().right().right().right().straight().build();
    	world.setRoadmap(map);
    }

    private void addVehicles() {
        Vehicle vehicle = new Vehicle();
        vehicle.setName("vehicle1");
        vehicle.setWorld(world);
        vehicle.setCurrentRoadpiece(world.getRoadmap().getAnchor().reverse());
        vehicle.setPosition(world.getRoadmap().getAnchor().getEntry().reverse());
        vehicle.setTargetSpeed(250);
        vehicle.setMineReady(true);
        vehicle.setRocketReady(true);
        world.addBody(vehicle);
        
        /*Mine mine = new Mine();
        mine.setWorld(world);
        mine.setTargetSpeed(0);
        mine.setCurrentRoadpiece(world.getRoadmap().getAnchor().getNext().getNext().getNext().getNext().getNext().reverse());
        mine.setPosition(world.getRoadmap().getAnchor().getNext().getNext().getNext().getNext().getNext().getEntry().reverse());
        world.addBody(mine);
        
        Mine mine2 = new Mine(); 
        mine2.setWorld(world);
        mine2.setTargetSpeed(0);
        mine2.setCurrentRoadpiece(world.getRoadmap().getAnchor().getNext().getNext().getNext().reverse());
        mine2.setPosition(world.getRoadmap().getAnchor().getNext().getNext().getNext().getEntry().reverse());
        world.addBody(mine2);*/
    }

    private void startEngine() {
        engine.start();
    }
}
