package de.adesso.anki.battle.providers;

import java.util.ArrayList;
import java.util.List;

import com.states.GameState;
import com.states.ObjectInFront;
import com.states.RightCurveAhead;
import com.states.RocketInFront;

import de.adesso.anki.battle.world.DynamicBody;
import de.adesso.anki.battle.world.bodies.Roadmap;

public class VehicleStateProvider {
	
	
	public VehicleStateProvider() {

	}
	
	public List<GameState> getRoadFacts(  DynamicBody vehicle )
	{
		ArrayList<GameState> facts = new ArrayList<>();
		RightCurveAhead rCurve = new RightCurveAhead(150);
		/*if (vehicle.currentRoadPiece.nextPiece == "right"){
	    /	RightCurveAhead rCurve = new RightCurveAhead(150); 
	     	facts.add(rCurve);
		}
		if (vehicle.currentRoadPiece.nextPiece =="left") {
			LeftCurveAhead lCurve = new RightCurveAhead(150) ; 
			facts.add(lCurve); 
		}*/
	    facts.add(rCurve);  	
		return facts;
	}


	public List<GameState> getInventoryFacts( DynamicBody vehicle )
	{
		ArrayList<GameState> facts = new ArrayList<>();
		/*if (Vehicle.hasRocket()){
	    	InventoryRocket rocketFact = new InventoryRocket();
	    	facts.add(rocketFact); 
		}
		if (Vehicle.hasMine) {
			InventoryMine mineFact = new InventoryMine() ; 
			facts.add(MineFact); 
		}
		if (Vehicle.hasShield) {
			InventoryShield shieldFact = new InventoryShield() ; 
			facts.add(shieldFact); 
		}
		if (Vehicle.hasReflector) {
			InventoryReflector reflectorFact = new InventoryReflector() ; 
			facts.add(reflectorFact); 
		}
		
		}*/
	
		return facts;
	}

	
	public List<GameState> getObstacleFacts( Roadmap map, DynamicBody vehicle )
	{
		ArrayList<GameState> facts = new ArrayList<>();
		
		//if vehicle.nextRoadPiece.containsRocket()
/*		if (rocketInFront) {
			int meters = 100;
			String type = "";
			RocketInFront rocketInFront  = new RocketInFront(100, type);
			facts.add(rocketInFront); 
		}
		if (rocketBehind) {
			int meters = 100;
			String type = "";
			RocketBehind obstacleBehind  = new RocketBehind(100, type);
			facts.add(obstacleBehind); 
		}
		
		if (MineInFront) {
			int meters = 100;
			String type = "";
			MineInFront mineInFront  = new MineInFront(100);
			facts.add(mineInFront); 
		}
		if (VehicleInFront) {
			int meters = 100;
			String type = "";
			RocketInFront obstacleInFront  = new RocketInFront(100, type);
				    	facts.add(rocketFact); 
		}
		if (VehicleBehind) {
			int meters = 100;
			String type = "";
			RocketInFront obstacleInFront  = new RocketInFront(100, type);
			facts.add(); 
		}
		
		
*/
		return facts;
	}

	
	
	
	
	

}
