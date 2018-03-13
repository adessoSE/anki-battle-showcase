package de.adesso.anki.battle.renderers;

import de.adesso.anki.battle.transfer.BodyDTO;
import de.adesso.anki.battle.transfer.RoadmapDTO;
import de.adesso.anki.battle.world.World;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@Profile("render-websockets")
public class WebsocketRenderer implements Renderer {

    @Autowired
    private SimpMessagingTemplate messenger;

    @Override
    @Async
    public void render(World world) {
        messenger.convertAndSend("/topic/roadmap", new RoadmapDTO(world.getRoadmap()));
        messenger.convertAndSend("/topic/bodies", world.getBodies().stream().map(BodyDTO::new).collect(Collectors.toList()));
    }

}
