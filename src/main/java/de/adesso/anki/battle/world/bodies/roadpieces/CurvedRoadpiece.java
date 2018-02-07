package de.adesso.anki.battle.world.bodies.roadpieces;

import de.adesso.anki.battle.util.Position;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CurvedRoadpiece extends Roadpiece {

    private static final double RADIUS = 280;

    private boolean reversed;

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
        return origin.invTranslate(position.x(), position.y())
                    .rotate(Math.toDegrees(travel / findRadius(origin)))
                    .translate(position.x(), position.y());
    }

    public double findMaximumTravel(Position origin) {
        double theta = Math.toRadians(getExit().angle() - origin.angle());
        theta = theta < 0 ? theta + 2*Math.PI : theta;
        return theta * findRadius(origin);
    }

    private double findRadius(Position origin) {
        return position.distance(origin);
    }

    public Roadpiece reverse() {
        reversed = !reversed;
        return this;
    }

    @Override
    public boolean isLeftCurved() {
        return !reversed;
    }

    @Override
    public boolean isRightCurved() {
        return reversed;
    }

    @Override
    public String toString() {
        return reversed ? "R" : "L";
    }
}
