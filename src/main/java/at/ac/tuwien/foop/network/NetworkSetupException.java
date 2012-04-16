package at.ac.tuwien.foop.network;


/**
 * Something went wrong during setup step.
 *
 */
public class NetworkSetupException extends NetworkException {

	private static final long serialVersionUID = -4950795280288005580L;
	
	public NetworkSetupException(String message)
	{
		super(message);
	}

	public NetworkSetupException(String string, Throwable e) {
		super(string,e);
	}
}
