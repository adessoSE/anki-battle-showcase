package de.adesso.anki.battle.transfer;

import de.adesso.anki.battle.util.Position;
import de.adesso.anki.battle.world.Body;
import de.adesso.anki.battle.world.DynamicBody;
import de.adesso.anki.battle.world.bodies.Mine;
import de.adesso.anki.battle.world.bodies.Rocket;
import de.adesso.anki.battle.world.bodies.Vehicle;
import de.adesso.anki.battle.world.bodies.roadpieces.Roadpiece;

public class BodyDTO {

    private final Body body;

    public BodyDTO(Body body) {
        this.body = body;
    }

    public String getType() {
        return body.getClass().getSimpleName();
    }

    public Position getPosition() {
        return body.getPosition();
    }

    public double getSpeed() {
        if (body instanceof Vehicle) {
            Vehicle vehicle = (Vehicle) body;
            return vehicle.getSpeed();
        }

        if (body instanceof Rocket) {
            Rocket rocket = (Rocket) body;
            return rocket.getTargetSpeed();
        }
        return 0;
    }

    public String getName() {
        if (body instanceof Vehicle) {
            Vehicle vehicle = (Vehicle) body;

            if (vehicle.getAnkiReference() != null) {
                return vehicle.getAnkiReference().toString();
            } else {
                return "SIMULATED";
            }
        }

        if(body instanceof Rocket)
        {
            return ((Rocket) body).getId();
        }

        if(body instanceof Mine)
        {
            return ((Mine)body).getId();
        }

        return null;
    }

    public String getAddress() {
        if (body instanceof Vehicle) {
            Vehicle vehicle = (Vehicle) body;

            if (vehicle.getAnkiReference() != null) {
                return vehicle.getAnkiReference().getAddress();
            } else {
                return "SIMULATED";
            }
        }

        if(body instanceof Rocket)
        {
            return ((Rocket) body).getId();
        }
        if(body instanceof Mine)
        {
            return ((Mine)body).getId();
        }

        return null;
    }

    public Roadpiece getCurrentRoadpiece()
    {
        if (body instanceof Vehicle) {
            Vehicle vehicle = (Vehicle) body;
            if (vehicle.getAnkiReference() != null) {
                return vehicle.getCurrentRoadpiece();
            }
        }

        if (body instanceof Rocket) {
            Rocket rocket = (Rocket) body;
            if (rocket.getRoadpiece() != null) {
                return rocket.getRoadpiece();
            }
        }

        return null;
    }

    public boolean isCharging()
    {
        if (body instanceof Vehicle) {
            Vehicle vehicle = (Vehicle) body;
            if (vehicle.getAnkiReference() != null) {
                return vehicle.getAnkiReference().getAdvertisement().isCharging();
            }
        }
        return false;
    }

    public long getTimeStamp()
    {
        if (body instanceof Vehicle) {
            return System.currentTimeMillis() / 1000;
        }

        return 0;
    }

    public float getTimeToExplode()
    {
        if (body instanceof Rocket) {
            return ((Rocket) body).getTimeToExplode();
        }

        return -1.0f;
    }

    /*public boolean isActive()
    {
        if (body instanceof Rocket) {
            return ((Rocket) body).isActive();
        }

        return false;
    }

    public boolean shouldExplode()
    {
        if (body instanceof Rocket) {
            return ((Rocket) body).shouldExplode();
        }

        return false;
    }*/
}
