package de.adesso.anki.battle.transfer;

import de.adesso.anki.battle.util.Position;
import de.adesso.anki.battle.world.Body;
import de.adesso.anki.battle.world.DynamicBody;
import de.adesso.anki.battle.world.bodies.Vehicle;
import de.adesso.anki.battle.world.bodies.roadpieces.Roadpiece;


import de.adesso.anki.sdk.AnkiGateway;
import de.adesso.anki.sdk.AnkiVehicle;
import de.adesso.anki.sdk.messages.*;

public class BodyDTO {

    private final Body body;
    
    private double speed; 
    
    private String BTAdress;

    private AnkiVehicle anki; 
    
    
    
    public BodyDTO(Body body) {
        this.body = body;
    }
    
    // find corresponding Anki vehicle
    public BodyDTO(Body body, AnkiVehicle anki) {
        this.body = body;
        this.speed = ((DynamicBody)body).getSpeed();
        if (this.getType().equals("Vehicle")) {
        	this.anki = anki;
            this.BTAdress = ((Vehicle)body).getAnkiReference().toString();
        }
        else {
        	//Rockets and mines should have no BT Adress
        	this.BTAdress = "NULL";
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
 //   public AnkiVehicle getAnkiReference() {
 //   	return this.anki;
 //  }
    
    public String getBTAdress() {
    	return this.BTAdress;
    }
    
    

}


	
