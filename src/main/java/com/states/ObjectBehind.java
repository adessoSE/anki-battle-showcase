package com.states;

public class ObjectBehind extends GameState {
	private double metersBehind;
	private String type; 
	
	

	public ObjectBehind(double distance, String type) {
		super();
		this.metersBehind = distance;
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getMetersBehind() {
		return metersBehind;
	}
	public void setMetersBehind(double meters) {
		this.metersBehind = meters;
	}
}
