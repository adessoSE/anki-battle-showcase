package de.adesso.anki.battle.world;

import de.adesso.anki.battle.mqtt.MqttService;
import de.adesso.anki.battle.util.Position;
import de.adesso.anki.battle.world.bodies.roadpieces.Roadpiece;
import org.eclipse.paho.client.mqttv3.MqttException;

public abstract class Body {
	
	private World world ; 
	private Position position;
	private Roadpiece currentRoadpiece;
	
	public Body() {
		
	}
	
	public World getWorld() {
		return world;
	}

	public void setWorld (World world){
		this.world = world ; 
	}
	public Position getPosition () {
		return this.position;
	}
	public void setPosition (Position pos) {
		this.position = pos;
	}
	
    public void setCurrentRoadpiece(Roadpiece roadpiece) {
        currentRoadpiece = roadpiece;
    }

    public Roadpiece getRoadPiece () {
    	return this.currentRoadpiece;
    }
	

    
    
    public abstract void evaluateBehavior(MqttService mqtt) throws MqttException;


}
