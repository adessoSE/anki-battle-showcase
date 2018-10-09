package de.adesso.anki.battle.transfer;

import de.adesso.anki.battle.world.bodies.Roadmap;
import de.adesso.anki.battle.world.bodies.roadpieces.Roadpiece;
import de.adesso.anki.roadmap.segments.Segment;

import java.util.List;

public class RoadmapDTO {

    private Roadmap roadmap;

    public RoadmapDTO(Roadmap roadmap) {
        this.roadmap = roadmap;
    }

    public List<Roadpiece> getRoadpieces() {
        return roadmap.getRoadpieces();
    }



}
