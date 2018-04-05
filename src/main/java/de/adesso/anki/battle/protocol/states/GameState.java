package de.adesso.anki.battle.protocol.states;

import java.time.LocalTime;

public class GameState {
	private LocalTime time ;
	
	public GameState() {
 
		time = LocalTime.now();
	}
	public LocalTime getTime () {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	} 
}
