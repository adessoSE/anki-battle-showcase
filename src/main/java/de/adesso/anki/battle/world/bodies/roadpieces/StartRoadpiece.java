package de.adesso.anki.battle.world.bodies.roadpieces;

public class StartRoadpiece extends StraightRoadpiece {

    private static final double LENGTH = 220;

    @Override
    public double getLength() {
        return LENGTH;
    }

    @Override
    public String toString() {
        return "S";
    }
}
