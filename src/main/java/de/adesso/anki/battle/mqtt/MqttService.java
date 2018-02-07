package de.adesso.anki.battle.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MqttService {

    private MqttClient mqttClient;

    @Autowired
    private ApplicationEventPublisher publisher;

    public MqttService() {
        try {
            mqttClient = new MqttClient("tcp://localhost:1883", "anki-battle", new MemoryPersistence());
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            log.info("Connecting to broker: tcp://localhost:1883");
            mqttClient.connect(connOpts);
            log.info("Connected");
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {
                    log.info("MQTT connection lost");
                }

                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                    log.info("MQTT message arrived: topic="+s+"; message="+mqttMessage.toString());
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
