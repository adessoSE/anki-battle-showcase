package de.adesso.anki.battle.world.bodies;

import de.adesso.anki.battle.util.Position;
import de.adesso.anki.battle.world.DynamicBody;
import de.adesso.anki.battle.world.bodies.roadpieces.Roadpiece;

import java.util.Locale;

public class Vehicle extends DynamicBody {

    private Roadpiece currentRoadpiece;

    @Override
    public void updatePosition(long deltaNanos) {
        if (position != null) {
            if (speed < targetSpeed) {
                speed += acceleration * deltaNanos / 1_000_000_000;
                speed = Math.min(speed, targetSpeed);
            }

            if (speed > targetSpeed) {
                speed -= acceleration * deltaNanos / 1_000_000_000;
                speed = Math.max(speed, targetSpeed);
            }

            double travel = speed * deltaNanos / 1_000_000_000;

            if (currentRoadpiece != null) {
                Position newPosition = currentRoadpiece.followTrack(position, travel);
                currentRoadpiece = currentRoadpiece.followTrackRoadpiece(position, travel);
                position = newPosition;
            }
        }
    }

    @Override
    public void evaluateBehavior() {

    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "roadpiece=" + currentRoadpiece +
                ", position=" + position +
                ", speed=" + String.format(Locale.ROOT, "%.1f", speed) +
                '}';
    }

    public void setCurrentRoadpiece(Roadpiece roadpiece) {
        currentRoadpiece = roadpiece;
    }

}
