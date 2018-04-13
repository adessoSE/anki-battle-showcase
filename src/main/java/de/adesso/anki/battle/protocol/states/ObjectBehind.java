package de.adesso.anki.battle.protocol.states;

public class ObjectBehind extends GameState {
	public double getOffSetFromCenter() {
		return offSetFromCenter;
	}
	private double metersBehind;
	private String type; 
	private double offSetFromCenter;
	

	public ObjectBehind(double distance, String type, double offSetFromCenter) {
		super();
		this.metersBehind = distance;
		this.type = type;
		this.offSetFromCenter = offSetFromCenter;
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
