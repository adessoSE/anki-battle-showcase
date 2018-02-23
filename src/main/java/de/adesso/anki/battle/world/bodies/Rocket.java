package de.adesso.anki.battle.world.bodies;

import com.states.GameState;
import de.adesso.anki.battle.mqtt.MqttService;
import de.adesso.anki.battle.world.DynamicBody;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.List;

public class Rocket extends DynamicBody {

	private String direction; 
	
	
	public Rocket (String direction) {
		this.direction = direction;
	}
	
	
	@Override
	public void evaluateBehavior(MqttService mqtt) throws MqttException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFacts(List<GameState> facts) {
		// Facts for rockets? 
	}

}
