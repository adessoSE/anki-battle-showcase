package de.adesso.anki.battle.initializers;

import de.adesso.anki.battle.GameEngine;
import de.adesso.anki.battle.sync.AnkiSynchronization;
import de.adesso.anki.battle.world.World;
import de.adesso.anki.battle.world.bodies.Roadmap;
import de.adesso.anki.battle.world.bodies.Vehicle;
import de.adesso.anki.roadmap.roadpieces.*;
import de.adesso.anki.sdk.AnkiGateway;
import de.adesso.anki.sdk.AnkiVehicle;
import de.adesso.anki.sdk.messages.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Profile("anki")
@Component
public class AnkiInitializer implements ApplicationRunner {

    private AnkiGateway gateway;

    @Autowired
    private World world;

    @Autowired
    private GameEngine engine;

    private List<AnkiVehicle> ankiVehicles = new ArrayList<>();

    private MessageListener<LocalizationPositionUpdateMessage> listener;
    private MessageListener<LocalizationTransitionUpdateMessage> listener2;
    private boolean newPiece = false;

    private Roadmap.RoadmapBuilder builder = Roadmap.builder();
    private AnkiVehicle vehicle;
    private Vehicle myVehicle;

    @Autowired
    private AnkiSynchronization sync;

    @Async
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Initializing game with Anki environment");

        connectToAnki();
        scanRoadmap();

        //startEngine();
    }

    @SneakyThrows
    private void scanRoadmap() {
        // select a Vehicle to scan the roadmap
        vehicle = ankiVehicles.get(0);
        // flash headlights

        LightsPatternMessage lights = new LightsPatternMessage();
        lights.add(new LightsPatternMessage.LightConfig(
                LightsPatternMessage.LightChannel.FRONT_RED,
                LightsPatternMessage.LightEffect.FLASH,
                0,
                1,
                20
                ));
        vehicle.sendMessage(lights);
        log.info("Selected vehicle to scan the track: " + vehicle.toString());
        // wait until roadmap is complete

        Thread.sleep(5000);

        listener = vehicle.addMessageListener(LocalizationPositionUpdateMessage.class, m -> handlePosition(m));
        listener2 = vehicle.addMessageListener(LocalizationTransitionUpdateMessage.class, m -> handleTransition(m));
        vehicle.sendMessage(new SetSpeedMessage(400, 5000));
    }

    private void handleTransition(LocalizationTransitionUpdateMessage m) {
        newPiece = true;
    }

    private void handlePosition(LocalizationPositionUpdateMessage m) {
        if (newPiece) {
            val ankiPiece = Roadpiece.createFromId(m.getRoadPieceId());

            if (ankiPiece instanceof StraightRoadpiece) {
                builder.straight(m.isParsedReverse());
            } else if (ankiPiece instanceof CurvedRoadpiece) {
                builder.curve(m.isParsedReverse());
            } else if (ankiPiece instanceof StartRoadpiece) {
                builder.start(m.isParsedReverse());
            } else if (ankiPiece instanceof FinishRoadpiece) {
                builder.finish(m.isParsedReverse());
            } else {
                builder.straight(m.isParsedReverse());
            }

            val segment = ankiPiece.getSegmentByLocation(m.getLocationId(), m.isParsedReverse());
            val offset = segment.getOffsetByLocation(m.getLocationId());
            vehicle.sendMessage(new SetOffsetFromRoadCenterMessage((float) offset));

            log.info(builder.build().toString());

            if (builder.isComplete()) {
                world.setRoadmap(builder.build());
                vehicle.removeMessageListener(LocalizationPositionUpdateMessage.class, listener);
                vehicle.removeMessageListener(LocalizationTransitionUpdateMessage.class, listener2);
                vehicle.sendMessage(new SetSpeedMessage(0, 5000));
                LightsPatternMessage lights = new LightsPatternMessage();
                lights.add(new LightsPatternMessage.LightConfig(
                        LightsPatternMessage.LightChannel.FRONT_RED,
                        LightsPatternMessage.LightEffect.STEADY,
                        15,
                        0,
                        1
                ));
                lights.add(new LightsPatternMessage.LightConfig(
                        LightsPatternMessage.LightChannel.FRONT_GREEN,
                        LightsPatternMessage.LightEffect.STEADY,
                        15,
                        0,
                        1
                ));
                vehicle.sendMessage(lights);

                startEngine();
            }

            newPiece = false;
        }
    }

    @SneakyThrows
    private void connectToAnki() {
        log.info("Connecting to Anki Gateway at 10.200.100.12...");
        gateway = new AnkiGateway("10.200.100.12", 5000);
        ankiVehicles = gateway.findVehicles();

        for (AnkiVehicle anki : ankiVehicles.subList(0,1)) {
            myVehicle = new Vehicle();
            myVehicle.setAnkiReference(anki);
            anki.connect();
            anki.sendMessage(new SdkModeMessage());
            world.addBody(myVehicle);
        }
    }

    private void startEngine() {
        myVehicle.setCurrentRoadpiece(world.getRoadmap().getAnchor().getPrev());
        myVehicle.setTargetSpeed(500);
        sync.setupHandlers(myVehicle);
        engine.start();
    }
}
