package de.adesso.anki.battle.initializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.adesso.anki.battle.GameEngine;
import de.adesso.anki.battle.mqtt.MqttService;
import de.adesso.anki.battle.sync.AnkiSynchronization;
import de.adesso.anki.battle.transfer.RoadmapDTO;
import de.adesso.anki.battle.world.World;
import de.adesso.anki.battle.world.bodies.Roadmap;
import de.adesso.anki.battle.world.bodies.Vehicle;
import de.adesso.anki.roadmap.roadpieces.CurvedRoadpiece;
import de.adesso.anki.roadmap.roadpieces.FinishRoadpiece;
import de.adesso.anki.roadmap.roadpieces.Roadpiece;
import de.adesso.anki.roadmap.roadpieces.StartRoadpiece;
import de.adesso.anki.sdk.AnkiGateway;
import de.adesso.anki.sdk.AnkiVehicle;
import de.adesso.anki.sdk.messages.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Profile("anki")
@Component
public class AnkiInitializer implements ApplicationRunner {

    // Autowired Spring beans
    private World world;
    private GameEngine engine;
    private AnkiSynchronization sync;
    private MqttService mqtt;
    private ObjectMapper mapper;

    // Anki references
    private String gatewayIp;
    private List<AnkiVehicle> vehicles = new ArrayList<>();
    private AnkiVehicle scanningVehicle;
    private Vehicle myVehicle;
    private List<Vehicle> activeVehicles;

    private String preferredId;

    // Message listeners
    private MessageListener<LocalizationPositionUpdateMessage> positionListener;
    private MessageListener<LocalizationTransitionUpdateMessage> transitionListener;

    // Scanning state
    private Roadmap.RoadmapBuilder builder = Roadmap.builder();
    private boolean newPiece = false;

    public AnkiInitializer(@Autowired World world,
                           @Autowired GameEngine engine,
                           @Autowired AnkiSynchronization sync,
                           @Autowired MqttService mqtt,
                           @Autowired ObjectMapper mapper,
                           @Value("${anki.gateway.ip}") String gatewayIp,
                           @Value("${anki.preferred_vehicle}") String preferredId) {
        this.world = world;
        this.engine = engine;
        this.mqtt = mqtt;
        this.mapper = mapper;
        this.sync = sync;
        this.gatewayIp = gatewayIp;
        this.preferredId = preferredId;
    }

    @Async
    @Override
    public void run(ApplicationArguments args) {
        log.info("Initializing game with Anki environment");

        connectToAnki();
        scanRoadmap();
    }

    @SneakyThrows
    private void addMoreVehicle()
    {
        short itemCount = 2;
        for(Vehicle item: activeVehicles) {
            item.setTargetSpeed(400);
            item.setName("vehicle" + itemCount++);

            //item.setCurrentRoadpiece(world.getRoadmap().getAnchor().getPrev());

            LightsPatternMessage lights = new LightsPatternMessage();
            lights.add(new LightsPatternMessage.LightConfig(
                    LightsPatternMessage.LightChannel.FRONT_RED,
                    LightsPatternMessage.LightEffect.FLASH,
                    0,
                    1,
                    20
            ));

            item.getAnkiReference().sendMessage(lights);

            Thread.sleep(2000);

            item.getAnkiReference().sendMessage(new SetSpeedMessage(400, 5000));

            lights = new LightsPatternMessage();
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

            item.getAnkiReference().sendMessage(lights);

            sync.setupHandlers(item);
        }


    }

    @SneakyThrows
    private void scanRoadmap() {
        // select a Vehicle to scan the roadmap
        scanningVehicle = findPreferredVehicle(vehicles);

        // flash headlights

        LightsPatternMessage lights = new LightsPatternMessage();
        lights.add(new LightsPatternMessage.LightConfig(
                LightsPatternMessage.LightChannel.FRONT_RED,
                LightsPatternMessage.LightEffect.FLASH,
                0,
                1,
                20
                ));
        scanningVehicle.sendMessage(lights);

        log.info("Selected vehicle to scan the track: " + scanningVehicle.toString());
        // wait until roadmap is complete

        Thread.sleep(2000);

        positionListener = scanningVehicle.addMessageListener(LocalizationPositionUpdateMessage.class, this::handlePosition);
        transitionListener = scanningVehicle.addMessageListener(LocalizationTransitionUpdateMessage.class, m -> handleTransition());
        scanningVehicle.sendMessage(new SetSpeedMessage(400, 5000));
    }

    private AnkiVehicle findPreferredVehicle(List<AnkiVehicle> vehicles) {
        Optional<AnkiVehicle> preferredVehicle = vehicles.stream()
                .filter(v -> v.toString().equals(preferredId))
                .findFirst();

        return preferredVehicle.orElse(vehicles.get(0));
    }

    private void handleTransition() {
        newPiece = true;
    }

    private void handlePosition(LocalizationPositionUpdateMessage m) {
        if (newPiece) {
            val ankiPiece = Roadpiece.createFromId(m.getRoadPieceId());

            if (ankiPiece instanceof CurvedRoadpiece) {
                builder.curve(m.isParsedReverse());
                builder.setRoadpieceId(m.getRoadPieceId());
            } else if (ankiPiece instanceof StartRoadpiece) {
                builder.start(m.isParsedReverse());
                builder.setRoadpieceId(m.getRoadPieceId());
            } else if (ankiPiece instanceof FinishRoadpiece) {
                builder.finish(m.isParsedReverse());
                builder.setRoadpieceId(m.getRoadPieceId());
            } else { // will fall back to StraightRoadpiece
                builder.straight(m.isParsedReverse());
                builder.setRoadpieceId(m.getRoadPieceId());
            }

            val segment = ankiPiece.getSegmentByLocation(m.getLocationId(), m.isParsedReverse());
            val offset = segment.getOffsetByLocation(m.getLocationId());
            scanningVehicle.sendMessage(new SetOffsetFromRoadCenterMessage((float) offset));

            log.info(builder.build().toString());

            if (builder.isComplete()) {
                world.setRoadmap(builder.build());
                publishRoadmapToMqtt(world.getRoadmap());

                scanningVehicle.removeMessageListener(LocalizationPositionUpdateMessage.class, positionListener);
                scanningVehicle.removeMessageListener(LocalizationTransitionUpdateMessage.class, transitionListener);
                scanningVehicle.sendMessage(new SetSpeedMessage(0, 5000));

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

                scanningVehicle.sendMessage(lights);
                startEngine();
            }

            newPiece = false;
        }
    }

    private void publishRoadmapToMqtt(Roadmap roadmap) {
        try {
            RoadmapDTO road = new RoadmapDTO(world.getRoadmap());
            String roadJSON = mapper.writeValueAsString(road);
            System.out.println(roadJSON);
            mqtt.publishRetained("ext/roadmap", roadJSON);
        } catch(Exception e) {
            log.error("MQTT error", e);
        }
    }

    @SneakyThrows
    private void connectMoreVehicleToAnki(String impossibleAddress)
    {
        if(activeVehicles == null)
            activeVehicles = new ArrayList<>(vehicles.size() - 1);

        for (AnkiVehicle item : vehicles) {
            if(!item.getAddress().equals(impossibleAddress)) {
                Vehicle toAdd = new Vehicle();
                toAdd.setAnkiReference(item);
                activeVehicles.add(toAdd);
                item.connect();
                item.sendMessage(new SdkModeMessage());
                world.addBody(toAdd);
            }
        }
    }

    @SneakyThrows
    private void connectToAnki() {
        log.info("Connecting to Anki Gateway at " + gatewayIp + "...");
        val gateway = new AnkiGateway(gatewayIp, 5000);
        vehicles = gateway.findVehicles();

        log.info("Found Vehicle count: " + vehicles.size());

        AnkiVehicle anki = findPreferredVehicle(vehicles);
        myVehicle = new Vehicle();
        myVehicle.setAnkiReference(anki);

        anki.connect();
        anki.sendMessage(new SdkModeMessage());
        world.addBody(myVehicle);

        connectMoreVehicleToAnki(anki.getAddress());
    }

    private void startEngine() {
        myVehicle.setCurrentRoadpiece(world.getRoadmap().getAnchor().getPrev());
        myVehicle.setTargetSpeed(400);
        myVehicle.setName("vehicle1");
        sync.setupHandlers(myVehicle);


        engine.start();

        addMoreVehicle();
    }
}
