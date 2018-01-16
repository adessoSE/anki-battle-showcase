package de.adesso.anki.battle.world.bodies;

import java.util.Collection;
import java.util.List;

import com.commands.Command;
import com.domain.RuleEngine;
import com.states.GameState;

import de.adesso.anki.battle.world.Body;
import de.adesso.anki.battle.world.DynamicBody;

public class Vehicle implements DynamicBody {
	private RuleEngine re ; 
	private List<GameState> facts;
	private int track =3 ; 			// 0-6 (l nach r) 
	private int speed ;
	private Command nextCommand;
	
	public Vehicle() {	
	}
	
	public Vehicle(String pathDRL) {
		this.re = new RuleEngine(pathDRL);		
	}
	
	public void setRuleEngine(String pathDRL) {
		this.re = new RuleEngine(pathDRL);
	}
	

	public int getTrack() {
		return this.track;
	}
	
	
	public void setTrack (int track) {
		this.track = track;
	}
	

	public void setSpeed(int speed) {
		this.speed = speed;
		// TODO Auto-generated method stub
	}
	
    @Override
    public void updatePosition() {
        // TODO: Simulate movement
    }

    @Override
    public void setFacts(List <GameState> facts)
    {
    	
    	this.facts = facts;
    	this.re.insertFacts(facts);
    }
    

    
    @Override
    public void evaluateBehavior() {
    	Collection<? extends Command> allCommands = this.re.evaluateRules(); 
    	Command command = allCommands.iterator().next();
    	if (command != null ){
    		command.execute(this);
    	}
    	System.out.println(this.hashCode() + " trackNr: " + this.track);
    	for (Object fact: facts) {
    		this.re.retractFact(fact);
    	}
    	for (Object commands: allCommands) {
    		this.re.retractFact(commands);
    	}

    }

	

}
