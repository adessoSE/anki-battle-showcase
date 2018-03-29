package com.states;

public class LeftCurveAhead extends GameState {
	private int metersAhead; 
	
	
	public LeftCurveAhead() { }
	
	public LeftCurveAhead(int meters) {
		this.metersAhead = meters ; 
	}


	public int getMeters() {
		return metersAhead;
	}


	public void setMeters(int meters) {
		this.metersAhead = meters;
	}
}
