package com.states;

public class ObjectInFront extends GameState {
	private double metersInFront;
	private String type;
	
	public double getMetersInFront() {
		return metersInFront;
	}

	public void setMetersInFront(double metersInFront) {
		this.metersInFront = metersInFront;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public ObjectInFront(double meters, String type) {
		this.metersInFront = meters;
		this.type = type;
	}
}
