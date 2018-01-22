package de.adesso.anki.battle.world;

public abstract class Body {
	
	private World world ; 
	
	public Body() {
		
	}
	
	public World getWorld() {
		return world;
	}

	public void setWorld (World world){
		this.world = world ; 
	}
	
    public abstract void evaluateBehavior();

}
