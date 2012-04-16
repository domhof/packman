package at.ac.tuwien.foop;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import at.ac.tuwien.foop.basic.Game;
import at.ac.tuwien.foop.basic.GameObject;
import at.ac.tuwien.foop.basic.GameState;
import at.ac.tuwien.foop.basic.Position;
import at.ac.tuwien.foop.common.DefaultSettings;
import at.ac.tuwien.foop.game.Direction;
import at.ac.tuwien.foop.game.gameobjects.Player;
import at.ac.tuwien.foop.game.gameobjects.PlayerColor;
import at.ac.tuwien.foop.game.gameobjects.Point;
import at.ac.tuwien.foop.network.GameStateDTO;
import at.ac.tuwien.foop.network.GameStatusDTO;
import at.ac.tuwien.foop.network.InputCommand;
import at.ac.tuwien.foop.ui.ClientCanvas;

public class Client  {
	
	private final ClientCanvas frame;
	private final int serverPort;
	
	// TODO remove
	GameState gameState;
	
	public Client()
	{
		this(DefaultSettings.DEFAULT_SERVER_PORT);
	}
	
	public Client(int port)
	{
		serverPort = port;
        frame = new ClientCanvas(40 * 18, 40 * 18, 5, 40);
	}
	
	public void run(String hostname) throws IOException
	{
		// ugly piece of code. just for tesing purpose
		System.out.println("Creating client socket");
		final Socket socket = new Socket(hostname, serverPort);
		System.out.println("Created socket");
		
		final OutputStream out = socket.getOutputStream();
		final InputStream in = socket.getInputStream();
		
		final ObjectOutputStream oos = new ObjectOutputStream(out);
		final ObjectInputStream ois = new ObjectInputStream(in);

		
		// fire up network
        //final Timer timer = new Timer();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
            	
            	for(;;){
            	// send to server
            	Direction currentDirection = frame.getCurrentDirection();
            	InputCommand command = new InputCommand(currentDirection);
            	GameStateDTO newState = null; 
            	try
            	{
            	
            		//System.out.println("Writing current input");
					oos.writeObject(command);
					//System.out.println("Wrote input command");
            	
	            	try {
	            		//System.out.println("Reading gameState");
						newState = (GameStateDTO)ois.readObject();
						
						frame.addPendingObjectsForDrawing(newState.getGameObjects());
						
						GameStatusDTO status = (GameStatusDTO)ois.readObject();
						
						if(status.getGameFinished())
						{
							// frame.SetFinished()
							frame.showDialog(status.getMessage());
							
							oos.close();
							socket.close();
							
							if(status.getAnotherLevelLeft())
							{
								return;		
							}
							else
							{
								frame.showDialog("Keine Levels mehr in diesem Turnier. Bye.");
								System.exit(0);
							}
						}
						
						//System.out.println("Got new game state");
					
						//Game.printGameState(newState.getGameObjects());
						
	            	} catch (ClassNotFoundException e) {
						System.exit(2);
					}
						
					if(newState == null)
					{
						System.out.println("FUU. received state is null");
					}
					else 
					{
						frame.refreshGame(newState.getGameObjects());
						frame.repaint();
					}
            	} catch(EOFException e)
            	{
            		System.out.println("Server ended the game. Exiting");
            		///timer.cancel();
            	} catch(IOException e)
            	{
            		e.printStackTrace();
            		System.exit(3);
            	}
            	}
            }
        });
        t.start();
        try {
			t.join();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    public static void main(String[] args) throws IOException {

    	String hostname = "localhost";
    	if(args.length == 1)
    	{
    		 hostname = args[0];
    		 System.out.println("using: " + hostname);
    	}  
    	
    	for(;;)
    	{
    		System.out.println("Running new client");
    		(new Client()).run(hostname);
    	}
    }

    
	public void showDialog(String msg){
		
		frame.showDialog(msg);
	}
}
