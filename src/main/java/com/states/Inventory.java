package com.states;

public class Inventory extends GameState {
	private String typeOfWeapon;
	
	public Inventory (String weaponType) {
		this.typeOfWeapon = weaponType;
	}

	public String getTypeOfWeapon() {
		return typeOfWeapon;
	}

	public void setTypeOfWeapon(String typeOfWeapon) {
		this.typeOfWeapon = typeOfWeapon;
	}
	
	
}
