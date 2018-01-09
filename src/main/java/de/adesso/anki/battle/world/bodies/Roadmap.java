package de.adesso.anki.battle.world.bodies;

public class Roadmap {

    public static RoadmapBuilder builder() {
        return new RoadmapBuilder();
    }

    public static class RoadmapBuilder {
        private Roadmap map;

        RoadmapBuilder() {
            map = new Roadmap();
        }

        public Roadmap build() {
            return map;
        }

        public RoadmapBuilder straight() {
            // TODO: Add Roadpiece
            return this;
        }

        public RoadmapBuilder left() {
            // TODO: Add Roadpiece
            return this;
        }

        public RoadmapBuilder right() {
            // TODO: Add Roadpiece
            return this;
        }

        public RoadmapBuilder start() {
            // TODO: Add Roadpiece
            return this;
        }

        public RoadmapBuilder finish() {
            // TODO: Add Roadpiece
            return this;
        }
    }
}
