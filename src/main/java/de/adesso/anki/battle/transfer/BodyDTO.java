package de.adesso.anki.battle.transfer;

import de.adesso.anki.battle.util.Position;
import de.adesso.anki.battle.world.Body;

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
}
