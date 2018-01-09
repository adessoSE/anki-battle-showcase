package de.adesso.anki.battle.renderers;

import de.adesso.anki.battle.world.World;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("render-console")
public class ConsoleRenderer implements Renderer {

    @Override
    @SneakyThrows
    @Async
    public void render(World world) {
        Thread.sleep(2000);
        log.info(world.toString());
    }

}
