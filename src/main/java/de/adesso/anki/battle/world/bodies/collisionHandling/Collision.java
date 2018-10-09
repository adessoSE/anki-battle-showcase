package de.adesso.anki.battle.world.bodies.collisionHandling;

public class Collision {
    private String weaponType;
    private String weaponId;
    private String vehicleAddress;

    public Collision(Class weaponType, String weaponId, String vehicleAddress)
    {
        this.weaponType = weaponType.getSimpleName();
        this.weaponId = weaponId;
        this.vehicleAddress = vehicleAddress;
    }

    public String getWeaponType()
    {
        return this.weaponType;
    }

    public String getWeaponId()
    {
        if(weaponType.toLowerCase().equals("rocket") || weaponType.toLowerCase().equals("mine"))
            return this.weaponId;

        return "NO_WEAPON";
    }

    public String getVehicleAddress()
    {
        return this.vehicleAddress;
    }
}
