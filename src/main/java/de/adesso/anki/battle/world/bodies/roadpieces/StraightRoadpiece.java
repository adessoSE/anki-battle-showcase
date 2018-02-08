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
        return origin.transform(Position.at(travel, 0));
    }

    @Override
    public String toString() {
        return "G";
    }

    @Override
    public boolean isStraight() {
        return true;
    }

    @Override
    public double findMaximumTravel(Position origin) {
        Position relativeOrigin = origin.invTransform2(getPosition());

        if (relativeOrigin.angle() == 0) {
            Position normalizedOrigin = Position.at(relativeOrigin.x(), 0);
            return normalizedOrigin.distance(relativeExit());
        } else {
            Position normalizedOrigin = Position.at(relativeOrigin.x(), 0);
            return normalizedOrigin.distance(relativeEntry());
        }
    }
}
