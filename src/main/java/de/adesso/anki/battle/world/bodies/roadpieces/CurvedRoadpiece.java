package de.adesso.anki.battle.world.bodies.roadpieces;

import de.adesso.anki.battle.util.Position;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CurvedRoadpiece extends Roadpiece {

    private static final double RADIUS = 280;

    private boolean reversed;

    @Override
    public Position relativeEntry() {
        return Position.at(-RADIUS, 0, 0);
    }

    @Override
    public Position relativeExit() {
        return Position.at(0, RADIUS, 90);
    }

    @Override
    public double getLength() {
        return RADIUS * Math.PI / 2;
    }

    @Override
    public Position followTrack(Position origin, double travel) {
        double maxTravel = findMaximumTravel(origin); // maximum travel on this piece
        log.info(String.format("%.1f", maxTravel));
        if (travel <= maxTravel) {
            return origin.rotate(Math.toDegrees(travel / RADIUS)); // follow track on this piece
        }
        else if (next != null) {
            return next.followTrack(this.getExit(), travel - maxTravel);
        } else {
            return this.getExit();
        }
    }

    private double findMaximumTravel(Position origin) {
        // winkel zwischen center-origin und center-exit
        Position co = Position.at(origin.x() - position.x(), origin.y() - position.y());
        Position ce = relativeExit();
        double theta = Math.acos((co.x()*ce.x() + co.y()+ce.y()) / (RADIUS*RADIUS));
        return theta * RADIUS;
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
