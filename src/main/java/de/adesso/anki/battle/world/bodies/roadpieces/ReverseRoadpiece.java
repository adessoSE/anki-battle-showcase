package de.adesso.anki.battle.world.bodies.roadpieces;

import de.adesso.anki.battle.util.Position;

import java.util.Objects;

public class ReverseRoadpiece extends Roadpiece {

    private final Roadpiece original;

    public ReverseRoadpiece(Roadpiece original) {
        this.original = original;
    }

    @Override
    public Roadpiece reverse() {
        return original;
    }

    @Override
    public Roadpiece getNext() {
        return original.getPrev() != null ? original.getPrev().reverse() : null;
    }

    @Override
    public Roadpiece getPrev() {
        return original.getNext() != null ? original.getNext().reverse() : null;
    }

    @Override
    public Position getPosition() {
        return original.getPosition();
    }

    @Override
    void setNext(Roadpiece next) {
        original.setPrev(next.reverse());
    }

    @Override
    void setPrev(Roadpiece prev) {
        original.setNext(prev.reverse());
    }

    @Override
    public void setPosition(Position position) {
        original.setPosition(position);
    }

    @Override
    public Position relativeEntry() {
        return original.relativeExit().reverse();
    }

    @Override
    public Position relativeExit() {
        return original.relativeEntry().reverse();
    }

    @Override
    public double getLength() {
        return original.getLength();
    }

    @Override
    public Position followTrack(Position origin, double travel) {
        return original.followTrack(origin, travel);
    }

    @Override
    public double findMaximumTravel(Position origin) {
        return original.findMaximumTravel(origin);
    }

    @Override
    public String toString() {
        return original.toString() == "L" ?
                "R" : original.toString() + "!";
    }

    @Override
    public boolean isStraight() { return original.isStraight(); }

    @Override
    public boolean isLeftCurved() { return original.isRightCurved(); }

    @Override
    public boolean isRightCurved() { return original.isLeftCurved(); }

    @Override
    public int getRoadpieceId() {
        return original.getRoadpieceId();
    }

    @Override
    public void setRoadpieceId(int roadpieceId) {
        original.setRoadpieceId(roadpieceId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        final ReverseRoadpiece other = (ReverseRoadpiece) obj;
        return Objects.equals(this.original, other.original);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.original);
    }
}
