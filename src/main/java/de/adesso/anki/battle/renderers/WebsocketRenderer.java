package de.adesso.anki.battle.renderers;

import de.adesso.anki.battle.world.World;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Profile("render-websockets")
public class WebsocketRenderer implements Renderer {

    @Override
    @Async
    public void render(World world) {
        System.out.println("Websockets!");
    }

}
