package at.ac.tuwien.foop.network;

import java.io.Serializable;

import at.ac.tuwien.foop.game.Direction;

/**
 * Represents a data transfer object for commands.
 *
 */
public class InputCommand implements Serializable {
	
	private static final long serialVersionUID = 3423903046892710116L;
	private final Direction direction;
	
	public InputCommand(Direction direction)
	{
		this.direction = direction;
	}
	
	public Direction getDirection()
	{
		return direction;
	}
}
