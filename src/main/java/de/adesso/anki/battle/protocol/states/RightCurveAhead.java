package de.adesso.anki.battle.protocol.states;

public class RightCurveAhead extends GameState{
	private int metersAhead; 
	
	
	public RightCurveAhead() { }
	
	public RightCurveAhead(int meters) {
		this.metersAhead = meters;
	}

	public int getMeters() {
		return metersAhead;
	}

	public void setMeters(int meters) {
		this.metersAhead = meters;
	}
}
