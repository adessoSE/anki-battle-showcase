package de.adesso.anki.battle.providers;

import com.states.*;
import de.adesso.anki.battle.util.Position;
import de.adesso.anki.battle.world.Body;
import de.adesso.anki.battle.world.World;
import de.adesso.anki.battle.world.bodies.Vehicle;
import de.adesso.anki.battle.world.bodies.roadpieces.Roadpiece;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class VehicleStateProvider {

	public List<GameState> getRoadFacts(  Vehicle vehicle ){
		ArrayList<GameState> facts = new ArrayList<>();
		if (vehicle.getCurrentRoadpiece() == null)
			return facts;

		Roadpiece nextPiece = vehicle.getCurrentRoadpiece().getNext();

		if (nextPiece.isRightCurved()){
	    	RightCurveAhead rCurve = new RightCurveAhead(150); 
	     	facts.add(rCurve);
		}
		if (nextPiece.isLeftCurved()) {
			LeftCurveAhead lCurve = new LeftCurveAhead(150) ; 
			facts.add(lCurve); 
		}
		if (nextPiece.isStraight()) {
			StraightPieceAhead straight = new StraightPieceAhead();
			facts.add(straight);
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
				//skip self
				continue;
			}
			//generating  facts for vehicle 
			//body all other in the current world
			String obstacleType = body.getClass().getSimpleName() ;
			Position position1 = vehicle.getPosition();
			Position position2 = body.getPosition();
			double distance = position1.distance(position2);
			double angle1 = position1.angle();


			
			//blickrichtung x cos(angle)  y sine (angle)
			double angleRad = Math.toRadians(angle1);
			double viewVectorX = Math.cos(angleRad);
			double viewVectorY = Math.sin(angleRad);
			
			double transX = position2.getX() - position1.getX();
			double transY = position2.getY() - position1.getY();
			
			double dotProduct = viewVectorX * transX + viewVectorY * transY;
			
			if (dotProduct < 0 ) {
				ObjectBehind obstacle = new ObjectBehind(distance, obstacleType);
				facts.add(obstacle);
			}
			else {
				ObjectInFront obstacle = new ObjectInFront(distance, obstacleType);
				facts.add(obstacle);
			}		
		}
		return facts;
	}

}
