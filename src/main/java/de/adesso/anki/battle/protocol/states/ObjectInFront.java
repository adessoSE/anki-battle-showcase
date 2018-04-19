package de.adesso.anki.battle.protocol.states;

public class ObjectInFront extends GameState {
	private double metersInFront;
	private double offSetFromCenter;
	private String type;
	
	
	public double getMetersInFront() {
		return metersInFront;
	}

	public double getOffSetFromCenter() {
		return offSetFromCenter;
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
	
	public ObjectInFront(double meters, String type, double offSetFromCenter) {
		this.metersInFront = meters;
		this.type = type;
		this.offSetFromCenter = offSetFromCenter;
	}
}
