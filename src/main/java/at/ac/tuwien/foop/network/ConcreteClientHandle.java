package at.ac.tuwien.foop.network;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;


public class ConcreteClientHandle implements ClientHandle, Disposable {
	
	private final InputEvents inputEvents;
	private final NetworkAdapter networkAdapter;
	private Socket clientSocket;
	
	private boolean dead = false;
	
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	
	public ConcreteClientHandle(Socket clientSocket, NetworkAdapter adapter)
	{
		inputEvents = new InputEvents();
		this.clientSocket = clientSocket;
		this.networkAdapter = adapter;
		
		getInitializedInputStream();
		getInitializedOutputStream();
	}

	@Override
	public void transferGameState(GameStateDTO o) throws NetworkException {

		if(dead) return;
		
		ObjectOutputStream oos = getInitializedOutputStream();
		
		try
		{			
			//System.out.println("Transfering Game State to client");
			oos.writeObject(o);
			oos.reset();
			//System.out.println("Transfered game state");
		} 
		catch(EOFException e)
		{
			System.out.println("Client seemed to ended the game. nopping this one out.");
			dead = true;
			dispose();
		}
		catch(IOException e)
		{
			System.out.println("Client seemed to ended the game. nopping this one out.");
			dead = true;
			dispose();
		} 
	}
	
	public void tick() throws NetworkException
	{
		if(dead) return;
		
		ObjectInputStream inputStream = getInitializedInputStream();
		
		try
		{
			//System.out.println("Processing InputCommand");
			InputCommand command = (InputCommand)inputStream.readObject();
			//System.out.println("Got InputCommand");
			inputEvents.setDirection(command.getDirection());
		}
		catch(EOFException e)
		{
			System.out.println("Client seemed to ended the game. nopping this one out.");
			dead = true;
			dispose();
		}
		catch(IOException e)
		{
			System.out.println("Client seemed to ended the game. nopping this one out.");
			dead = true;
			dispose();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1); // fatal ;-).
		}
	}

	@Override
	public InputEvents getInputEvents() {
		
		return inputEvents;
		
	}
	
	private ObjectOutputStream getInitializedOutputStream() throws NetworkSetupException
	{
		if(oos == null)
		{
			try
			{
				OutputStream stream = this.clientSocket.getOutputStream();
				oos = new ObjectOutputStream(stream);
			} catch(IOException e)
			{
				throw new NetworkSetupException("Could not initialize outputstream for client handle");
			}
		}
		
		return oos;
	}
	
	
	private ObjectInputStream getInitializedInputStream() throws NetworkSetupException
	{
		if(ois == null)
		{
			try
			{
				InputStream stream = this.clientSocket.getInputStream();
				ois = new ObjectInputStream(stream);
			} catch(IOException e)
			{
				throw new NetworkSetupException("Could not initialize outputstream for client handle");
			}
		}
		
		return ois;
	}
	
	public void transferStatus(boolean gameEnds, String message, boolean anotherGameLeft)
	{
		GameStatusDTO status = new GameStatusDTO(gameEnds, message, anotherGameLeft);
		
		ObjectOutputStream oos = getInitializedOutputStream();
		
		try
		{			
			oos.writeObject(status);
			oos.reset();
		} 
		catch(IOException e)
		{
			System.out.println("Client seemed to ended the game. nopping this one out.");
			dead = true;
			dispose();
		} 
	}

	public void dispose() {
		
		// TODO dispose streams
		networkAdapter.clientDied(this);
		
		try {
			this.clientSocket.close();
		} catch (IOException e) {
			// now tell me how to recover? find someting smarter than nop !?!!1?!
		}
	}

}
