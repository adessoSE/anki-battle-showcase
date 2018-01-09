package com.domain;

import java.util.Collection;

import org.kie.api.KieServices;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import com.commands.Command;
import com.states.GameState;
import java.util.List;

public class RuleEngine {
	private KieSession kSession ; 
	//private KieContainer kContainer;
	
	
	public RuleEngine(String PathDRL){
	    KieServices ks = KieServices.Factory.get();
	    KieContainer kContainer = ks.getKieClasspathContainer();
	    this.kSession = kContainer.newKieSession(PathDRL);
	   
	}
	
	public KieSession getSession() {
		return this.kSession;
	}


	public void insertFacts (List<GameState> facts) {
		for (GameState fact: facts) {
			kSession.insert(fact);
		}
	}
	
	
	
	/// just for testing, delete when finished
	public void insertFacts2 (List<Vehicle> facts) {
		for (Vehicle fact: facts) {
			kSession.insert(fact);
		}
	}
	
	public void retractFact(Object fact) {
		FactHandle fhandle = kSession.getFactHandle(fact);
		this.kSession.delete(fhandle);
		//this.kSession.retract(fhandle);
	}
	
	public void clear () {
		/**
		 * needs to be called after session is not needed anymore to avoid memory leaks
		 */
		kSession.dispose();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<? extends Command> evaluateRules () {
	    kSession.fireAllRules();  
	    Collection<? extends Object> test = kSession.getObjects();
	    System.out.println("Size" + test.size());
	    Collection<? extends Object> allCommands = kSession.getObjects(x -> x instanceof Command);
	    return (Collection<? extends Command>) allCommands;
	}
	
	
	
	
	
}
