package de.adesso.anki.battle.world.bodies;

import java.util.List;

import com.states.GameState;

import de.adesso.anki.battle.world.DynamicBody;

public class Rocket extends DynamicBody {

	private String direction; 
	
	
	public Rocket (String direction) {
		this.direction = direction;
	}
	
	
	@Override
	public void evaluateBehavior() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFacts(List<GameState> facts) {
		// Facts for rockets? 
	}

}
