package at.ac.tuwien.foop.network;

import java.util.Observable;

import at.ac.tuwien.foop.game.Direction;

/**
 * Provides an Input State (e.g. Player is in Move-Up state)
 * Furthermore changes of this state may be observed.
 *
 */
public class InputEvents extends Observable {

	private Direction direction;
	
	/**
	 * Gets gurrent Direction
	 * @return
	 */
	public Direction getDirection(){
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
		setChanged();
		notifyObservers();
	}
	
}
