package at.ac.tuwien.foop.network;

/**
 * Something went wrong during network transfer steps.
 *
 */
public class NetworkException extends RuntimeException {

	private static final long serialVersionUID = 428325417418945340L;

	public NetworkException(String message)
	{
		super(message);
	}
	
	public NetworkException(String message, Throwable throwable)
	{
		super(message,throwable);
	}
}
