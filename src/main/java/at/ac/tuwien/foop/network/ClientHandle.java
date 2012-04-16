package at.ac.tuwien.foop.network;


/**
 * Describes an end point. Sends GameStates to Physical Players
 * and provides an interface for querying player inputs.
 * 
 *
 */
public interface ClientHandle {
	
	/**
	 * Transfers game state to the client connected with this
	 * handle.
	 * @param o The state to transfer
	 */
	void transferGameState(GameStateDTO o) throws NetworkException;
	
	/**
	 * Reads inputs from Client in order to bring InputEvents up to date.
	 * @throws NetworkException
	 */
	void tick() throws NetworkException;
	
	/**
	 * Gets an Observable `input device`.
	 * @return
	 */
	InputEvents getInputEvents();

	/**
	 * Transfers game state - i.e. if game ended
	 * @param gameEnds
	 */
	void transferStatus(boolean gameEnds, String message, boolean hasMoreLevels);
	
}
