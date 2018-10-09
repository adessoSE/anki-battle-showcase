package de.adesso.anki.battle.world;

import de.adesso.anki.battle.world.bodies.Roadmap;
import de.adesso.anki.battle.world.bodies.Vehicle;
import de.adesso.anki.battle.world.bodies.collisionHandling.Collision;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class World {

    private Roadmap roadmap;

    private List<Body> bodies = new ArrayList<>();
    private List<Collision> collisions = new ArrayList<>();

    public void setRoadmap(Roadmap roadmap) {
        this.roadmap = roadmap;
    }

    public Roadmap getRoadmap() {
        return roadmap;
    }

    public void addBody(Body body) {
        bodies.add(body);
        body.setWorld(this);
    }

    public void addCollision(Collision collision)
    {
        this.collisions.add(collision);
    }

    public List<Collision> getCollisions()
    {
        return this.collisions;
    }
    public List<Body> getBodies() {
        return Collections.unmodifiableList(bodies);
    }
    
    public List<Body> getBodiesModifiable() {
        return this.bodies;
    }


    public List<DynamicBody> getDynamicBodies() {
        return bodies.stream()
                .filter(x -> x instanceof DynamicBody)
                .map(x -> (DynamicBody) x)
                .collect(Collectors.toList());
    }


    public List<Vehicle> getVehicles() {
        return bodies.stream()
                .filter(x -> x instanceof Vehicle)
                .map(x -> (Vehicle) x)
                .collect(Collectors.toList());
    }

    
    @Override
    public String toString() {
        return "World{" +
                "roadmap=" + roadmap +
                ", bodies=" + bodies +
                '}';
    }
}
