package com.states;

public class ObjectInFront extends GameState {
	private int metersInFront;
	
	public int getMetersInFront() {
		return metersInFront;
	}

	public void setMetersInFront(int metersInFront) {
		this.metersInFront = metersInFront;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private String type;
	
	public ObjectInFront(int meters, String type) {
		this.metersInFront = meters;
		this.type = type;
	}
}
