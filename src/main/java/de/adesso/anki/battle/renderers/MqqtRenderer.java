package de.adesso.anki.battle.renderers;

import de.adesso.anki.battle.mqtt.MqttService;
import de.adesso.anki.battle.transfer.BodyDTO;
import de.adesso.anki.battle.transfer.CollisionDTO;
import de.adesso.anki.battle.transfer.RoadmapDTO;
import de.adesso.anki.battle.world.World;

import de.adesso.anki.battle.world.bodies.collisionHandling.Collision;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Instant;
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

    @Autowired
	private ObjectMapper mapper;

    private long lastRender = 0;

    @Override
    @Async
    public void render(World world) {
    	long now = System.nanoTime();
    	//if (now - lastRender > 25 * 1000000) {
			renderMqtt(world);
			lastRender = now;
		//}
	}

	private void renderMqtt(World world) {
		try {
			String bodies = mapper.writeValueAsString(world.getBodies().stream().map(BodyDTO::new).collect(Collectors.toList()));
			//mqtt.publish("ext/bodies", bodies);
			mqtt.publish("ext/bodiess", bodies);
			//System.out.println(bodies + "\n");

			if(world.getCollisions().size() > 0)
			{
				String collisions = mapper.writeValueAsString(world.getCollisions().stream().map(CollisionDTO::new).collect(Collectors.toList()));
				//mqtt.publish("ext/collisions", collisions);
				System.out.println(collisions);
				world.getCollisions().clear();
			}

		}catch(JsonMappingException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}catch(MqttException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
