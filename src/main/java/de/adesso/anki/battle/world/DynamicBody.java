package de.adesso.anki.battle.world;

import de.adesso.anki.battle.util.Position;

public abstract class DynamicBody implements Body {

    protected Position position = Position.at(0,0);
    protected double speed;

    protected double targetSpeed;
    protected double acceleration = 50;

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
            position = position.transform(Position.at(travel, 0));
        }
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public double getSpeed() {
        return speed;
    }

    public double getTargetSpeed() {
        return targetSpeed;
    }

    public void setTargetSpeed(double targetSpeed) {
        this.targetSpeed = targetSpeed;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

}
