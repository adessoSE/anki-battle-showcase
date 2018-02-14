package de.adesso.anki.battle.world;

import de.adesso.anki.battle.util.Position;

public abstract class Body {
	
	private World world ; 
	private Position position;
	
	public Body() {
		
	}
	
	public World getWorld() {
		return world;
	}

	public void setWorld (World world){
		this.world = world ; 
	}
	public Position getPosition () {
		return this.position;
	}
	public void setPosition (Position pos) {
		this.position = pos;
	}
	
	
    public abstract void evaluateBehavior();

}
