package com.domain;

public class Vehicle {
	private int speed; 
	private boolean hasRocket;
	private String nextSegment;
	private boolean rocketInPursuit;
	private int energy; 
	
	public int getEnergy() {
		return energy;
	}
	public void setEnergy(int energy) {
		this.energy = energy;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public boolean isHasRocket() {
		return hasRocket;
	}
	public void setHasRocket(boolean hasRocket) {
		this.hasRocket = hasRocket;
	}
	public String getNextSegment() {
		return nextSegment;
	}
	public void setNextSegment(String nextSegment) {
		this.nextSegment = nextSegment;
	}
	public boolean isRocketInPursuit() {
		return rocketInPursuit;
	}
	public void setRocketInPursuit(boolean rocketInPursuit) {
		this.rocketInPursuit = rocketInPursuit;
	}
	
}
