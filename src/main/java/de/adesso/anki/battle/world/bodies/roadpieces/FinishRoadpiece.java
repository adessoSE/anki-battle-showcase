package de.adesso.anki.battle.world.bodies.roadpieces;

public class FinishRoadpiece extends StraightRoadpiece {

    private static final double LENGTH = 340;

    @Override
    public double getLength() {
        return LENGTH;
    }

    @Override
    public String toString() {
        return "F";
    }
}
