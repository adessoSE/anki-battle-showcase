package de.adesso.anki.battle.world.bodies;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.MqttException;

import com.states.GameState;

import de.adesso.anki.battle.mqtt.MqttService;
import de.adesso.anki.battle.world.DynamicBody;

public class Rocket extends DynamicBody {
//TODO should rockets follow the roadmap ? 
	// else explode after x seconds for garbage collector
	private String direction; 
	
	// hotfix for driving into own rockets
	private long timer ; 
	private boolean active;
	
	public Rocket (String direction) {
		this.direction = direction;
		timer = System.currentTimeMillis();
	}
	
	
	
	// TODO adjust timers
	public boolean isActive () {
		return System.currentTimeMillis() > timer + 1000 ;
	}
	public boolean shouldExplode () {
		return System.currentTimeMillis() > timer + 8000 ;
	}
	
	//@Override
	public void evaluateBehavior() {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void setFacts(List<GameState> facts) {
		// Facts for rockets? 
	}


	@Override
	public void evaluateBehavior(MqttService mqtt) throws MqttException {
		// TODO Auto-generated method stub
		
	}

}
