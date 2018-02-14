package de.adesso.anki.battle.world.bodies;

import de.adesso.anki.battle.util.Position;
import de.adesso.anki.battle.world.bodies.roadpieces.*;

public class Roadmap {

    private Roadpiece anchor;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (anchor != null) {
            Roadpiece current = anchor;
            sb.append(anchor.toString());
            while (current.getNext() != null && current.getNext() != anchor) {
                current = current.getNext();
                sb.append(current.toString());
            }
            return sb.toString();
        }
        else {
            return "empty";
        }
    }

    public static RoadmapBuilder builder() {
        return new RoadmapBuilder();
    }

    public Roadpiece getAnchor() {
        return anchor;
    }

    public static class RoadmapBuilder {
        private Roadmap map;
        private Roadpiece last;

        RoadmapBuilder() {
            map = new Roadmap();
        }

        private void addRoadpiece(Roadpiece next) {
            if (map.anchor == null) {
                map.anchor = next;
                last = next;
                next.setPosition(Position.at(0,0));
            }
            else {
                last.connect(next);
                last = next;
                if (last.getExit().distance(map.anchor.getEntry()) < 1) {
                    last.connect(map.anchor);
                }
            }
        }

        public Roadmap build() {
            return map;
        }

        public RoadmapBuilder straight() {
            Roadpiece next = new StraightRoadpiece();
            addRoadpiece(next);

            return this;
        }

        public RoadmapBuilder left() {
            Roadpiece next = new CurvedRoadpiece();
            addRoadpiece(next);

            return this;
        }

        public RoadmapBuilder right() {
            Roadpiece next = new CurvedRoadpiece().reverse();
            addRoadpiece(next);

            return this;
        }

        public RoadmapBuilder start() {
            Roadpiece next = new StartRoadpiece();
            addRoadpiece(next);

            return this;
        }

        public RoadmapBuilder finish() {
            Roadpiece next = new FinishRoadpiece();
            addRoadpiece(next);

            return this;
        }
    }
}
