package de.adesso.anki.battle.transfer;

import de.adesso.anki.battle.world.bodies.collisionHandling.Collision;

public class CollisionDTO {

    private final Collision collision;

    public CollisionDTO(Collision collision)
    {
        this.collision = collision;
    }

    public String getType()
    {
        return collision.getClass().getSimpleName();
    }

    public String getWeaponType()
    {
        return collision.getWeaponType();
    }

    public String getWeaponId()
    {
        return collision.getWeaponId();
    }

    public String getVehicleAddress()
    {
        return collision.getVehicleAddress();
    }
}
