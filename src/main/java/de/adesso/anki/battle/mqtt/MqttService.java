package de.adesso.anki.battle.mqtt;

import com.commands.*;
import de.adesso.anki.battle.world.DynamicBody;
import de.adesso.anki.battle.world.World;
import de.adesso.anki.battle.world.bodies.Vehicle;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MqttService {

    private MqttClient mqttClient;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired 
    private World world ; 
    
    public MqttService() {
        try {
            mqttClient = new MqttClient("tcp://localhost:1883", "anki-battle", new MemoryPersistence());
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(false);
            log.info("Connecting to MQTT broker: localhost:1883");
            mqttClient.connect(connOpts);
            log.info("Connected to MQTT broker");
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {
                    log.info("MQTT connection lost");
                }

                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) {
                    try {
                        log.info("MQTT message arrived: topic=" + s + "; message=" + mqttMessage.toString());
                        String temp = mqttMessage.toString();
                        byte[] hm = mqttMessage.getPayload();
                        JSONObject json = new JSONObject(mqttMessage.toString());

                        //get topic, where topic is identifier for vehicle
                        //get corresponding vehicle v
                        // v.exexute command
                        String topic = s.split("_")[0];
                        List<DynamicBody> vehicles = world.getDynamicBodies();
                        Vehicle wantedVehicle = null;
                        for (int i = 0; i < vehicles.size(); i++) {
                            Vehicle currentVehicle = ((Vehicle) vehicles.get(i));
                            if (topic.equals(currentVehicle.getName())) {
                                wantedVehicle = currentVehicle;
                                break;
                            }
                        }
                        String commandType = (String) json.get("type");
                        int speed;
                        Command command = null;
                        int track;
                        switch (commandType) {
                            case ("accelerate"):
                                speed = Integer.parseInt((String) json.get("velocity"));
                                command = new AccelerateCommand(speed);
                                break;
                            case ("brake"):
                                speed = Integer.parseInt((String) json.get("velocity"));
                                command = new BrakeCommand(speed);
                                break;
                            case ("change track"):
                                val lane = Double.parseDouble((String) json.get("track"));
                                command = new ChangeLaneCommand(lane);
                                break;
                            case ("uTurn"):
                                command = new UTurnCommand();
                                break;
                            case ("fireRocket"):
                                command = new FireRocketCommand("");
                                break;
                            case ("putMine"):
                                command = new PutMineCommand();
                                break;
                        }
                        if (command != null) {
                            command.execute(wantedVehicle);
                        } else {
                            log.debug("No Command");
                        }
                    } catch (Exception e) {
                        log.error("Error while parsing MQTT message", e);
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publish(String topic, String message) throws MqttException {
        mqttClient.publish(topic, new MqttMessage(message.getBytes()));
        log.info("MQTT message published: topic="+topic+"; message="+message);
    }

    public void subscribe(String topicFilter) throws MqttException {
        mqttClient.subscribe(topicFilter);
    }



}
