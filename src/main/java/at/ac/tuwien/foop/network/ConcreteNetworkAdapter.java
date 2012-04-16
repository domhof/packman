package at.ac.tuwien.foop.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import at.ac.tuwien.foop.common.DefaultSettings;

public class ConcreteNetworkAdapter implements NetworkAdapter, Disposable {

	private ServerSocket serverSocket;
	private final int port;
	
	// tracked for disposal only!
	private ArrayList<ConcreteClientHandle> returnedHandles;
	private int livingClients = 0;
	
	public ConcreteNetworkAdapter(int port)
	{
		this.port = port;
		this.returnedHandles = new ArrayList<ConcreteClientHandle>();

		Runtime.getRuntime().addShutdownHook(
				new Thread(new Runnable()
				{
					@Override
					public void run() {
						dispose();
					}
				}));
	}
	
	public ConcreteNetworkAdapter()
	{
		this(DefaultSettings.DEFAULT_SERVER_PORT);
	}
	
	@Override
	public List<ClientHandle> awaitConnections(int clients)
			throws NetworkSetupException {
		
		ArrayList<ClientHandle> result = new ArrayList<ClientHandle>();
		
		try {
			System.out.println("Creating Packman server.");
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			throw new NetworkSetupException("Could not create server socket", e);
		}
		
		try {
			for(int i=0;i<clients;i++)
			{
				System.out.println("Waiting for player: " + i + "/" + clients);
					Socket client = serverSocket.accept();
				System.out.println("Got player");
					ConcreteClientHandle handle = registerClient(client);
					result.add(handle);
					returnedHandles.add(handle);
				livingClients++;
			}
		} catch (IOException e) {
			throw new NetworkSetupException("Accep failed. ", e); 
		}
		
		return result;
	}
	
	private ConcreteClientHandle registerClient(Socket client)
	{
		return new ConcreteClientHandle(client, this);
	}

	@Override
	public void tick() throws NetworkException {
		
		// nop for now
		for(ConcreteClientHandle h: returnedHandles)
		{
			h.tick();
		}
	}
	
	public void clientDied(ClientHandle handle)
	{
		livingClients--;
		if(livingClients <= 0)
		{
			System.out.println("closing server socket");
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void dispose() {
		
		// first dispose all exposed connections
		for(ConcreteClientHandle h : returnedHandles)
		{
			h.dispose();
		}
		
		// now also dispose serversock
		try {
			serverSocket.close();
		} catch (IOException e) {
			// well again nop!
		}
	}

}
