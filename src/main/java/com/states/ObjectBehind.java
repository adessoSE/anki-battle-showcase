package com.states;

public class ObjectBehind extends GameState {
	private int metersBehind;
	private String type; 
	private int track ;
	
	

	public ObjectBehind(int metersBehind, String type, int track) {
		super();
		this.metersBehind = metersBehind;
		this.type = type;
		this.track = track;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getTrack() {
		return track;
	}
	public void setTrack(int track) {
		this.track = track;
	}
	public int getMetersBehind() {
		return metersBehind;
	}
	public void setMetersBehind(int meters) {
		this.metersBehind = meters;
	}
}
