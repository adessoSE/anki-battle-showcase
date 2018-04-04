package de.adesso.anki.battle.protocol.states;

public class DrivingStats extends GameState{
	private int velocity; 
	private int track;
	public int getVelocity() {
		return velocity;
	}
	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}
	public int getTrack() {
		return track;
	}
	public void setTrack(int track) {
		this.track = track;
	} 
	
}
