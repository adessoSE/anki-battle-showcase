package de.adesso.anki.battle.schedules;

import de.adesso.anki.battle.world.World;
import de.adesso.anki.battle.world.bodies.Vehicle;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Resets the vehicles' inventory state after a cooldown timer has elapsed.
 */
@Component
public class WeaponScheduler {

    /**
     * The time in milliseconds a weapon has to cool down
     * after it has been fired.
     */
    private static final int WEAPON_COOLDOWN_MS = 10_000;

    private World world;

    public WeaponScheduler(World world) {
        this.world = world;
    }

    @Scheduled(fixedRate = WEAPON_COOLDOWN_MS)
    public void resetRocketReady() {
        for (Vehicle vehicle : world.getVehicles() ) {
            //if ((vehicle.getAnkiReference() != null && !vehicle.getAnkiReference().getAdvertisement().isCharging())
            //        || (vehicle.getPosition() != null && vehicle.getCurrentRoadpiece() != null)) {
                vehicle.setRocketReady(true);
            //}
        }
    }

    @Scheduled(fixedRate = WEAPON_COOLDOWN_MS)
    public void resetMineReady() {
        for (Vehicle vehicle : world.getVehicles() ) {
            //if ((vehicle.getAnkiReference() != null && !vehicle.getAnkiReference().getAdvertisement().isCharging())
            //        || (vehicle.getPosition() != null && vehicle.getCurrentRoadpiece() != null)) {
                vehicle.setMineReady(true);
            //}
        }
    }

    @Scheduled(fixedRate = WEAPON_COOLDOWN_MS)
    public void resetShieldReady() {
        for (Vehicle vehicle : world.getVehicles() ) {
            //if ((vehicle.getAnkiReference() != null && !vehicle.getAnkiReference().getAdvertisement().isCharging())
            //        || (vehicle.getPosition() != null && vehicle.getCurrentRoadpiece() != null)) {
                vehicle.setShieldReady(true);
            //}
        }
    }

    @Scheduled(fixedRate = WEAPON_COOLDOWN_MS)
    public void resetReflectorReady() {
        for (Vehicle vehicle : world.getVehicles() ) {
            //if ((vehicle.getAnkiReference() != null && !vehicle.getAnkiReference().getAdvertisement().isCharging())
            //        || (vehicle.getPosition() != null && vehicle.getCurrentRoadpiece() != null)) {
                vehicle.setReflectorReady(true);
            //}
        }
    }
}
