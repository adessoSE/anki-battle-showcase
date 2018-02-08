package de.adesso.anki.battle.world.bodies.roadpieces;

import de.adesso.anki.battle.util.Position;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CurvedRoadpiece extends Roadpiece {

    private static final double RADIUS = 280;

    @Override
    public Position relativeEntry() {
        return Position.at(0, -RADIUS, 0);
    }

    @Override
    public Position relativeExit() {
        return Position.at(RADIUS, 0, 90);
    }

    @Override
    public double getLength() {
        return RADIUS * Math.PI / 2;
    }

    @Override
    public Position followTrack(Position origin, double travel) {
        if (getExit().rotate(-origin.angle()).angle() <= 90) {
            return origin.invTranslate(getPosition().x(), getPosition().y())
                    .rotate(Math.toDegrees(travel / findRadius(origin)))
                    .translate(getPosition().x(), getPosition().y());
        } else {
            return origin.invTranslate(getPosition().x(), getPosition().y())
                    .rotate(Math.toDegrees(-travel / findRadius(origin)))
                    .translate(getPosition().x(), getPosition().y());
        }
    }

    public double findMaximumTravel(Position origin) {
        if (getExit().rotate(-origin.angle()).angle() <= 90) {
            double theta = Math.toRadians(getExit().angle() - origin.angle());
            theta = theta < 0 ? theta + 2 * Math.PI : theta;
            return theta * findRadius(origin);
        } else {
            double theta = Math.toRadians(origin.angle() - getEntry().reverse().angle());
            theta = theta < 0 ? theta + 2 * Math.PI : theta;
            return theta * findRadius(origin);
        }
    }

    private double findRadius(Position origin) {
        return getPosition().distance(origin);
    }

    @Override
    public boolean isLeftCurved() { return true; }

    @Override
    public String toString() {
        return "L";
    }
}
