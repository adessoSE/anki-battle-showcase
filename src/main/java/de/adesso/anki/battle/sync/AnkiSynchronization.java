package de.adesso.anki.battle.sync;

import de.adesso.anki.battle.world.bodies.Vehicle;
import de.adesso.anki.sdk.messages.ChangeLaneMessage;
import de.adesso.anki.sdk.messages.LocalizationPositionUpdateMessage;
import de.adesso.anki.sdk.messages.LocalizationTransitionUpdateMessage;
import de.adesso.anki.sdk.messages.SetSpeedMessage;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public class AnkiSynchronization {

    public void synchronizeState(Vehicle vehicle) {
        if (vehicle.getAnkiReference() != null) {
            val anki = vehicle.getAnkiReference();

            val speed = new SetSpeedMessage((int) vehicle.getTargetSpeed(), (int) vehicle.getAcceleration());
            anki.sendMessage(speed);

            val lane = new ChangeLaneMessage(
                    (float) vehicle.getTargetOffset(),
                    80,
                    150);
            anki.sendMessage(lane);
        }
    }

    public void setupHandlers(Vehicle vehicle) {
        if (vehicle.getAnkiReference() != null) {
            val anki = vehicle.getAnkiReference();

            anki.addMessageListener(LocalizationPositionUpdateMessage.class, m -> updatePosition(vehicle, m));
            anki.addMessageListener(LocalizationTransitionUpdateMessage.class, m -> updateTransition(vehicle, m));
        }
    }

    private void updatePosition(Vehicle vehicle, LocalizationPositionUpdateMessage m) {

    }

    private void updateTransition(Vehicle vehicle, LocalizationTransitionUpdateMessage m) {

    }


}
