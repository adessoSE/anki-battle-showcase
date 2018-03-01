package de.adesso.anki.battle.transfer;

import de.adesso.anki.battle.world.bodies.Roadmap;
import de.adesso.anki.battle.world.bodies.roadpieces.Roadpiece;
import lombok.val;

import java.util.LinkedList;
import java.util.List;

public class RoadmapDTO {

    private Roadmap roadmap;

    public RoadmapDTO(Roadmap roadmap) {
        this.roadmap = roadmap;
    }

    public List<Roadpiece> getRoadpieces() {
        val list = new LinkedList<Roadpiece>();
        val anchor = roadmap.getAnchor();
        list.add(anchor);

        for (Roadpiece i = anchor.getNext(); i.getNext() != null && i != anchor; i = i.getNext()) {
            list.add(i);
        }

        return list;
    }

}
