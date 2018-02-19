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
                if (last.getExit().distance(map.anchor.getEntry()) < 10) {
                    last.connect(map.anchor);
                }
            }
        }

        public Roadmap build() {
            return map;
        }

        public boolean isComplete() {
            return map.anchor != null && map.anchor.getPrev() != null;
        }

        public RoadmapBuilder straight() {
            return straight(false);
        }

        public RoadmapBuilder straight(boolean reverse) {
            Roadpiece next = new StraightRoadpiece();
            if (reverse) next = next.reverse();

            addRoadpiece(next);
            return this;
        }

        public RoadmapBuilder left() {
            return curve(false);
        }

        public RoadmapBuilder right() {
            return curve(true);
        }

        public RoadmapBuilder curve(boolean reverse) {
            Roadpiece next = new CurvedRoadpiece();
            if (reverse) next = next.reverse();

            addRoadpiece(next);
            return this;
        }

        public RoadmapBuilder start() {
            return start(false);
        }

        public RoadmapBuilder start(boolean reverse) {
            Roadpiece next = new StartRoadpiece();
            if (reverse) next = next.reverse();

            addRoadpiece(next);
            return this;
        }

        public RoadmapBuilder finish() {
            return finish(false);
        }

        public RoadmapBuilder finish(boolean reverse) {
            Roadpiece next = new FinishRoadpiece();
            if (reverse) next = next.reverse();

            addRoadpiece(next);
            return this;
        }
    }
}
