package de.adesso.anki.battle.transfer;

import de.adesso.anki.battle.util.Position;
import de.adesso.anki.battle.world.Body;
import de.adesso.anki.battle.world.DynamicBody;
import de.adesso.anki.battle.world.bodies.Vehicle;

import de.adesso.anki.sdk.AnkiVehicle;
import de.adesso.anki.sdk.messages.*;

public class BodyDTO {

    private final Body body;
    
    private double speed; 
    
    private String btAdress;

    private AnkiVehicle anki; 
    
    
    
    
    // find corresponding Anki vehicle
    public BodyDTO(Body body) {
        this.body = body;
        this.speed = ((DynamicBody)body).getSpeed();
        if (this.getType().equals("Vehicle")) {
        	if ( ((Vehicle)body).getAnkiReference() != null) {
            	this.btAdress = ((Vehicle)body).getAnkiReference().toString();
        	}
        }
        else {
        	//Rockets and mines should or in simulation have no BT Adress
        	this.btAdress = "NULL";
        }
    }

    public double getSpeed() {
    	return this.speed;
    }
    

    
    public String getType() {
        return body.getClass().getSimpleName();
    }

    public Position getPosition() {
        return body.getPosition();
    }

    
    public String getBTAdress() {
    	return this.btAdress;
    }
    
    

}


	
