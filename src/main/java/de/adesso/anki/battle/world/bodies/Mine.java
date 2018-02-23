package de.adesso.anki.battle.world.bodies;

import com.states.GameState;
import de.adesso.anki.battle.mqtt.MqttService;
import de.adesso.anki.battle.world.DynamicBody;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.List;

public class Mine extends DynamicBody {

	
	public Mine(){
	}

	@Override
	public void evaluateBehavior(MqttService mqtt) throws MqttException {

	}


	@Override
	public void setFacts(List<GameState> facts) {
		// TODO Auto-generated method stub
		
	}


}
