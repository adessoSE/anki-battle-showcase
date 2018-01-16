package de.adesso.anki.battle.world.bodies.roadpieces;

import de.adesso.anki.battle.util.Position;

public class StraightRoadpiece extends Roadpiece {

    private static final double LENGTH = 560;

    @Override
    public Position relativeEntry() {
        return Position.at(-getLength() / 2, 0, 0);
    }

    @Override
    public Position relativeExit() {
        return Position.at(getLength() / 2, 0, 0);
    }

    @Override
    public double getLength() {
        return LENGTH;
    }

    @Override
    public Position followTrack(Position origin, double travel) {
        double maxTravel = origin.distance(this.getExit()); // maximum travel on this piece
        if (travel <= maxTravel) {
            return origin.transform(Position.at(travel, 0)); // follow track on this piece
        }
        else if (next != null) {
            return next.followTrack(this.getExit(), travel - maxTravel);
        } else {
            return this.getExit();
        }
    }

    @Override
    public Roadpiece followTrackRoadpiece(Position origin, double travel) {
        double maxTravel = origin.distance(this.getExit()); // maximum travel on this piece
        if (next == null || travel <= maxTravel) {
            return this;
        }
        else {
            return next.followTrackRoadpiece(this.getExit(), travel - maxTravel);
        }
    }

    @Override
    public String toString() {
        return "G";
    }

    @Override
    public boolean isStraight() {
        return true;
    }
}
