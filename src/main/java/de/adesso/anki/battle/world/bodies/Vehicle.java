package de.adesso.anki.battle.world.bodies;

import com.commands.Command;
import com.domain.RuleEngine;
import com.states.GameState;
import de.adesso.anki.battle.util.Position;
import de.adesso.anki.battle.world.DynamicBody;
import de.adesso.anki.battle.world.bodies.roadpieces.Roadpiece;
import de.adesso.anki.sdk.AnkiVehicle;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

@Slf4j
public class Vehicle extends DynamicBody {

	private Roadpiece currentRoadpiece;
	private RuleEngine re ;
	private List<GameState> facts;
	private int track =3 ; 			// 0-6 (l nach r)
	private Command nextCommand;
	private boolean rocketReady;
	private boolean mineReady;
	private boolean shieldReady;
	private boolean reflectorReady;
    private AnkiVehicle ankiReference;


	public double getOffset() {
		return offsetFromCenter;
	}

	private double offsetFromCenter;
	private double targetOffset;

	private double horizontalSpeed = 80;

    public boolean isMineReady() {
		return mineReady;
	}

	public void setMineReady(boolean mineReady) {
		this.mineReady = mineReady;
	}

	public boolean isShieldReady() {
		return shieldReady;
	}

	public void setShieldReady(boolean shieldReady) {
		this.shieldReady = shieldReady;
	}

	public boolean isReflectorReady() {
		return reflectorReady;
	}

	public void setReflectorReady(boolean reflectorReady) {
		this.reflectorReady = reflectorReady;
	}

	public boolean isRocketReady() {
		return rocketReady;
	}

	public void setRocketReady(boolean rocketReady) {
		this.rocketReady = rocketReady;
	}

	public Vehicle() {	
	}
	
	public Vehicle(String pathDRL) {
		this.re = new RuleEngine(pathDRL);		
	}
	
	public void setRuleEngine(String pathDRL) {
		this.re = new RuleEngine(pathDRL);
	}
	

	public int getTrack() {
		return this.track;
	}
	
	
	public void setTrack (int track) {
		this.track = track;
	}

	public void setTargetOffset(double offsetFromCenter) {
		targetOffset = offsetFromCenter;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
		// TODO Auto-generated method stub
	}

	public void setCalibrationOffset(double newOffset) {
    	val deltaOffset = newOffset - offsetFromCenter;
    	if (deltaOffset != 0) {
    		if (position != null)
				position = position.transform(Position.at(0, -deltaOffset));
			offsetFromCenter += deltaOffset;
		}
	}

    @Override
    public void updatePosition(long deltaNanos) {
        if (position != null) {
        	updateOffset(deltaNanos);
			updateForwardPosition(deltaNanos);
        }
    }

    private void updateOffset(long deltaNanos) {
		double deltaOffset = 0;

		if (offsetFromCenter < targetOffset && speed > horizontalSpeed) {
			deltaOffset = horizontalSpeed * deltaNanos / 1_000_000_000;
			deltaOffset = Math.min(deltaOffset, targetOffset - offsetFromCenter);
		}

		if (offsetFromCenter > targetOffset && speed > horizontalSpeed) {
			deltaOffset = -horizontalSpeed * deltaNanos / 1_000_000_000;
			deltaOffset = Math.max(deltaOffset, targetOffset - offsetFromCenter);
		}

		if (deltaOffset != 0) {
			position = position.transform(Position.at(0, -deltaOffset));
			offsetFromCenter += deltaOffset;
		}
	}

	private void updateForwardPosition(long deltaNanos) {
		/*/
    	if (speed < targetSpeed) {
            speed += acceleration * deltaNanos / 1_000_000_000;
            speed = Math.min(speed, targetSpeed);
        }

		if (speed > targetSpeed) {
            speed -= acceleration * deltaNanos / 1_000_000_000;
            speed = Math.max(speed, targetSpeed);
        }
        /**/

		double travel = speed * deltaNanos / 1_000_000_000;

		val oldRoadpiece = currentRoadpiece;

		if (currentRoadpiece != null && position != null) {
            while (travel > 0) {
                double maxTravel = currentRoadpiece.findMaximumTravel(position);
                if (travel <= maxTravel) {
                    position = currentRoadpiece.followTrack(position, travel);
                    travel = 0;
                } else {
                    position = currentRoadpiece.followTrack(position, maxTravel);
                    travel -= maxTravel;
                    currentRoadpiece = currentRoadpiece.getNext();
                }
            }
        }

        currentRoadpiece = oldRoadpiece;
	}

	@Override
    public void setFacts(List <GameState> facts)
    {
    	
    	this.facts = facts;
    	this.re.insertFacts(facts);
    }


	@Override
	public void evaluateBehavior() {
		Collection<? extends Command> allCommands = this.re.evaluateRules();
		//Command command = allCommands.iterator().next();
		//if (command != null ){
		//	command.execute(this);
		//}
		for (Command command : allCommands) {
			if (command != null) {
				command.execute(this);
			} else {
				log.debug("No Command");
			}
		}
		//clean Ruleengine after execution of Command
		for (Object fact : facts) {
			this.re.retractFact(fact);
		}
		for (Object commands : allCommands) {
			this.re.retractFact(commands);
		}

	}

    @Override
    public String toString() {
        return "Vehicle{" +
                "roadpiece=" + currentRoadpiece +
                ", position=" + position +
				", speed=" + String.format(Locale.ROOT, "%.1f", speed) +
				", offset=" + String.format(Locale.ROOT, "%.1f", offsetFromCenter) +
                '}';
    }

    public void setCurrentRoadpiece(Roadpiece roadpiece) {
        currentRoadpiece = roadpiece;
    }

    public Roadpiece getRoadPiece () {
    	return this.currentRoadpiece;
    }


    public void setAnkiReference(AnkiVehicle ankiReference) {
        this.ankiReference = ankiReference;
    }

	public AnkiVehicle getAnkiReference() {
		return ankiReference;
	}

	public double getTargetOffset() {
		return targetOffset;
	}

    public Roadpiece getCurrentRoadpiece() {
        return currentRoadpiece;
    }

	public double getHorizontalSpeed() {
		return horizontalSpeed;
	}
}
