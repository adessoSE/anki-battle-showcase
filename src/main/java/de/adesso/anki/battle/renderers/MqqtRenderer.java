package de.adesso.anki.battle.renderers;

import de.adesso.anki.battle.mqtt.MqttService;
import de.adesso.anki.battle.transfer.BodyDTO;
import de.adesso.anki.battle.transfer.RoadmapDTO;
import de.adesso.anki.battle.world.World;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


@Component
@Profile("render-websockets")
public class MqqtRenderer implements Renderer {

	@Autowired 
	private MqttService mqtt;
    @Autowired
    private SimpMessagingTemplate messenger;

    @Override
    @Async
    public void render(World world) {
    	try {
    	RoadmapDTO road = new RoadmapDTO(world.getRoadmap());
    	ObjectMapper mapper = new ObjectMapper();
    	String roadJSON = mapper.writeValueAsString(road);
        mqtt.publish("ext/roadmap",roadJSON );
    	
    	String bodies = mapper.writeValueAsString(world.getBodies().stream().map(BodyDTO::new).collect(Collectors.toList()));
        mqtt.publish("ext/bodies", bodies);
        System.out.println("JO");
        System.out.println(roadJSON);
    	System.out.println(bodies);
    	}
    	catch(JsonMappingException e) {
    		System.out.println("Json generation failed");
    	}
    	catch(MqttException e) {
    		System.out.println("Mqqt ERROR");
    	} catch (JsonProcessingException e) {
			System.out.println("Something went wrong while mapping to json");
		}
    }
}
