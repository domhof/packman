package at.ac.tuwien.foop.network;

import java.util.List;

/**
 * Provides network setup services.
 * Connects to Clients and returns ClientHandles as witness for each connection.
 * Synchronously pulls inputs from each open connection.
 * 
 */
public interface NetworkAdapter {
	
	/**
	 * Requested clients which should be awaited.
	 * Acutally this count is mandatory and can be 
	 * built on (synchronously)
	 * 
	 * @param clients Number of ClientHandles which will be returned if success.
	 * @return
	 */
	List<ClientHandle> awaitConnections(int clients) throws NetworkSetupException;

	/**
	 * Pulls Inputs (e.g. move up, down) from connected clients (synchronously)
	 */
	void tick() throws NetworkException;
	
	void clientDied(ClientHandle handle);
	
	void dispose();
	
}
