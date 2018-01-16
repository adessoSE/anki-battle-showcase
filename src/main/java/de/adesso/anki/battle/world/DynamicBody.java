package de.adesso.anki.battle.world;

import java.util.List;

import com.states.GameState;


public interface DynamicBody extends Body {

	
    void updatePosition();

	void setFacts(List<GameState>facts);

}
