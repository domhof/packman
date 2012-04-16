package at.ac.tuwien.foop.network;

import java.io.Serializable;

public class GameStatusDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2372367230708935471L;

	
	private boolean gameFinished;
	private String message;
	private boolean anotherLevelLeft;
	
	public GameStatusDTO(boolean finished, String message, boolean anotherLevelLeft)
	{
		this.gameFinished = finished;
		this.message = message;
		this.anotherLevelLeft = anotherLevelLeft;
	}
	
	public boolean getGameFinished()
	{
		return gameFinished;
	}
	
	public boolean getAnotherLevelLeft()
	{
		return anotherLevelLeft;
	}
	
	public String getMessage()
	{
		return message;
	}
}
