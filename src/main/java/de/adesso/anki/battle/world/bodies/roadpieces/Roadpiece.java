package de.adesso.anki.battle.world.bodies.roadpieces;

import de.adesso.anki.battle.util.Position;

public abstract class Roadpiece {

    protected Roadpiece prev;
    protected Roadpiece next;

    protected Position position;

    public abstract Position relativeEntry();
    public abstract Position relativeExit();

    public abstract double getLength();

    public double getWidth() {
        return 220;
    }

    public void connect(Roadpiece next) {
        this.next = next;
        next.prev = this;

        if (next.position == null) {
            next.position = position.transform(this.relativeExit())
                    .invTransform(next.relativeEntry());
        }
    }

    public Position getEntry() {
        return position.transform(relativeEntry());
    }

    public Position getExit() {
        return position.transform(relativeExit());
    }

    public Roadpiece getNext() {
        return next;
    }

    public Roadpiece getPrev() {
        return prev;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public abstract Position followTrack(Position origin, double travel);
    public abstract double findMaximumTravel(Position position);

    public boolean isStraight() { return false; }
    public boolean isLeftCurved() { return false; }
    public boolean isRightCurved() { return false; }

}
