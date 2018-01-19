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
        double maxTravel = findMaximumTravel(origin); // maximum travel on this piece
        //log.debug(String.format("maxTravel=%.1f", maxTravel));
        if (travel <= maxTravel) {

            return origin.invTranslate(position.x(), position.y())
                    .rotate(Math.toDegrees(travel / RADIUS))
                    .translate(position.x(), position.y()); // follow track on this piece
        }
        else if (next != null) {
            return next.followTrack(this.getExit(), travel - maxTravel);
        } else {
            return this.getExit();
        }
    }

    private double findMaximumTravel(Position origin) {
        /*// winkel zwischen center-origin und center-exit
        Position co = Position.at(origin.x() - position.x(), origin.y() - position.y());
        Position ce = relativeExit();
        // double theta = Math.acos((co.x()*ce.x() + co.y()+ce.y()) / (RADIUS*RADIUS));
        double theta = Math.atan2(ce.y(), ce.x()) - Math.atan2(co.y(), co.x());
        theta = theta < 0 ? theta + 2*Math.PI : theta;*/

        double theta = Math.toRadians(getExit().angle() - origin.angle());
        theta = theta < 0 ? theta + 2*Math.PI : theta;
        log.debug(String.format("theta=%.1f, maxTravel=%.1f", Math.toDegrees(theta), theta * RADIUS));
        return theta * RADIUS;
    }

    @Override
    public Roadpiece followTrackRoadpiece(Position origin, double travel) {
        double maxTravel = findMaximumTravel(origin); // maximum travel on this piece
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
