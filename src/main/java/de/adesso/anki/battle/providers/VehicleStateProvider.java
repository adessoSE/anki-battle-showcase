package de.adesso.anki.battle.providers;

import java.util.ArrayList;
import java.util.List;

import com.states.GameState;
import com.states.InventoryMine;
import com.states.InventoryReflector;
import com.states.InventoryRocket;
import com.states.InventoryShield;
import com.states.LeftCurveAhead;
import com.states.ObjectInFront;
import com.states.RightCurveAhead;
import com.states.RocketInFront;

import de.adesso.anki.battle.world.DynamicBody;
import de.adesso.anki.battle.world.bodies.Roadmap;
import de.adesso.anki.battle.world.bodies.Vehicle;
import de.adesso.anki.battle.world.bodies.roadpieces.Roadpiece;

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

	
	public List<GameState> getObstacleFacts(Vehicle vehicle )
	{
		ArrayList<GameState> facts = new ArrayList<>();
		Roadmap map = vehicle.getWorld().getRoadmap();
		Roadpiece nextPiece = vehicle.getRoadPiece().getNext();
		Roadpiece prevPiece = vehicle.getRoadPiece().getPrev();
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
		return facts;
	}

	
	
	
	
	

}
