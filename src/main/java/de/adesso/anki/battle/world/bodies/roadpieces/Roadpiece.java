package de.adesso.anki.battle.world.bodies.roadpieces;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.adesso.anki.battle.util.Position;

public abstract class Roadpiece {

    private Roadpiece prev;
    private Roadpiece next;

    private Position position;

    private int roadpieceId;

    public abstract Position relativeEntry();
    public abstract Position relativeExit();

    public abstract double getLength();

    public double getWidth() {
        return 220;
    }

    public void connect(Roadpiece next) {
        this.setNext(next);
        next.setPrev(this);

        if (next.getPosition() == null) {
            next.setPosition(this.getPosition().transform(this.relativeExit())
                    .invTransform(next.relativeEntry()));
        }
    }

    public Position getEntry() {
        if (getPosition() == null)
            return null;

        return getPosition().transform(relativeEntry());
    }

    public Position getExit() {
        if (getPosition() == null)
            return null;

        return getPosition().transform(relativeExit());
    }

    @JsonIgnore
    public Roadpiece getNext() {
        return next;
    }

    @JsonIgnore
    public Roadpiece getPrev() {
        return prev;
    }

    void setPrev(Roadpiece prev) {
        this.prev = prev;
    }

    void setNext(Roadpiece next) {
        this.next = next;
    }

    public Roadpiece reverse() {
        return new ReverseRoadpiece(this);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public abstract Position followTrack(Position origin, double travel);
    public abstract double findMaximumTravel(Position origin);

    public boolean isStraight() { return false; }
    public boolean isLeftCurved() { return false; }
    public boolean isRightCurved() { return false; }

    public String getType() {
        return getClass().getSimpleName();
    }

    public int getRoadpieceId() {
        return roadpieceId;
    }

    public void setRoadpieceId(int roadpieceId) {
        this.roadpieceId = roadpieceId;
    }
}
