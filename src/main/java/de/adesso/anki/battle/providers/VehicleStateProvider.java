package de.adesso.anki.battle.providers;

import com.states.*;
import de.adesso.anki.battle.util.Position;
import de.adesso.anki.battle.world.Body;
import de.adesso.anki.battle.world.World;
import de.adesso.anki.battle.world.bodies.Mine;
import de.adesso.anki.battle.world.bodies.Rocket;
import de.adesso.anki.battle.world.bodies.Vehicle;
import de.adesso.anki.battle.world.bodies.roadpieces.Roadpiece;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class VehicleStateProvider {
	
	
	public VehicleStateProvider() {

	}
	
	public List<GameState> getRoadFacts(  Vehicle vehicle ){
		ArrayList<GameState> facts = new ArrayList<>();
		Roadpiece nextPiece = vehicle.getRoadPiece().getNext() ;
		if (nextPiece.isRightCurved()){
	    	RightCurveAhead rCurve = new RightCurveAhead(150); 
	     	facts.add(rCurve);
		}
		if (nextPiece.isLeftCurved()) {
			LeftCurveAhead lCurve = new LeftCurveAhead(150) ; 
			facts.add(lCurve); 
		}
		return facts;
	}


	public List<GameState> getInventoryFacts( Vehicle vehicle )
	{
		ArrayList<GameState> facts = new ArrayList<>();
		if (vehicle.isRocketReady()){
	    	InventoryRocket rocketFact = new InventoryRocket();
	    	facts.add(rocketFact); 
		}
		if (vehicle.isMineReady()) {
			InventoryMine mineFact = new InventoryMine() ; 
			facts.add(mineFact); 
		}
		if (vehicle.isShieldReady()) {
			InventoryShield shieldFact = new InventoryShield() ; 
			facts.add(shieldFact); 
		}
		if (vehicle.isReflectorReady()) {
			InventoryReflector reflectorFact = new InventoryReflector() ; 
			facts.add(reflectorFact); 
		}

		return facts;
	}

	
	public List<GameState> getObstacleFacts(Vehicle vehicle ){
		ArrayList<GameState> facts = new ArrayList<>();	
		World world = vehicle.getWorld();
		for (Body body : world.getBodies()){
			if (vehicle == body) {
				continue;
			}
			String type = "";
			if ( body instanceof Rocket) {
				type = "Rocket";
			}
			if ( body instanceof Mine) {
				type = "Mine";
			}
			if (body instanceof Vehicle) {
				type = "Vehicle";
			}
			Position position1 = vehicle.getPosition();
			Position position2 = body.getPosition();
			double distance = position1.distance(position2);
			log.debug("distance="+distance);
			double angle = position1.angle();
			if( angle < 180 ) {
				ObjectInFront objInFront = new ObjectInFront(distance, "type");
				facts.add(objInFront);
			}
			else {
				ObjectBehind objBehind = new ObjectBehind(distance, "type");
				facts.add(objBehind);
			}
		}
		return facts;
	}
		
		
		
		//if vehicle.nextRoadPiece.containsRocket()
/*		if (nextPiece) {
			int meters = 100;
			String type = "";
			RocketInFront rocketInFront  = new RocketInFront(100, type);
			facts.add(rocketInFront); 
		}
		if (prevPiece) {
			int meters = 100;
			String type = "";
			RocketBehind obstacleBehind  = new RocketBehind(100, type);
			facts.add(obstacleBehind); 
		}
		
		if (nextPiece) {
			int meters = 100;
			String type = "";
			MineInFront mineInFront  = new MineInFront(100);
			facts.add(mineInFront); 
		}
		if (nextPiece.containsRocket()) {
			int meters = 100;
			String type = "";
			RocketInFront obstacleInFront  = new RocketInFront(100, type);
				    	facts.add(rocketFact); 
		}
		if (prevPiece) {
			int meters = 100;
			String type = "";
			RocketInFront obstacleInFront  = new RocketInFront(100, type);
			facts.add(); 
		}
		
		
*/


	
	private boolean objectInNeighbourhood(Vehicle vehicle, Body body2 , double neighbourhoodDistance){
		Position position1 = vehicle.getPosition();
		Position position2 = body2.getPosition();
		double distance = position1.distance(position2);
		double angle1 = position1.angle();
		double angle2  = position2.angle();
		if (distance  < neighbourhoodDistance) {
			
		}
		return false;
	}
	
	
	

}
