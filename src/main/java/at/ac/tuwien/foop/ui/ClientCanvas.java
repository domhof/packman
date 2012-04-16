package at.ac.tuwien.foop.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import at.ac.tuwien.foop.Client;
import at.ac.tuwien.foop.basic.GameObject;
import at.ac.tuwien.foop.basic.GameState;
import at.ac.tuwien.foop.common.InputProvider;
import at.ac.tuwien.foop.game.Direction;

public class ClientCanvas extends Canvas implements InputProvider {

    private int fieldSize;
    private int tileSize, pointSize;
    private int height;
    private int width;

    private BufferStrategy strategy;

    private GameState gameState = null;
    private Client client;
    private CanvasContext cc; 
    
    private Direction currentDirection = Direction.DOWN;
    
    private JFrame container;

    public ClientCanvas(int width, int height, int fieldSize, int tileSize) {


        pointSize = 8;
        cc = new CanvasContext(width, height, fieldSize, tileSize, pointSize);

        
        
        setBackground(Color.BLACK);
        setSize(width, height);

        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {


                switch (e.getKeyCode()) {

                case KeyEvent.VK_LEFT:
                	currentDirection = Direction.LEFT;
                    break;

                case KeyEvent.VK_RIGHT:
                	currentDirection = Direction.RIGHT;
                    break;

                case KeyEvent.VK_UP:
                	currentDirection = Direction.UP;
                    break;

                case KeyEvent.VK_DOWN:
                	currentDirection = Direction.DOWN;
                    break;

                default:
                    break;

                }

            }
        });

        // create Frame

        container = new JFrame("PacMan");

        JPanel panel = (JPanel) container.getContentPane();
        panel.setPreferredSize(new Dimension(width, height));
        panel.setLayout(null);

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        container.setLocation((screenWidth / 2) - (width / 2), (screenHeight / 2) - (height / 2));

        setBounds(0, 0, width, height);
        panel.add(this);

       // setIgnoreRepaint(true);

        container.pack();
        container.setResizable(false);
        container.setVisible(true);
        container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        requestFocus();
        
    }

    public void refreshGame(List<GameObject> list) {

    	/*createBufferStrategy(1);
    	strategy = getBufferStrategy();

    	Graphics2D g = (Graphics2D) getGraphics();

    	
    	for (GameObject go: gameObjects){
    		
    		go.draw(g, cc);
    	}
    	
    	//g.dispose();
    	strategy.show();
   */
    	synchronized(this)
    	{
    		objs = new HashSet<GameObject>(list);
    	}
    }
    Set<GameObject> objs ;
    
    @Override
    public void paint(Graphics g)
    {
    	Graphics2D g2 = (Graphics2D)g;
    	
    	synchronized(this)
    	{
	    	if(queue!=null)
	    	{
		    	for (GameObject go: queue){
		    		go.draw(g2, cc, go.getPosition().getX(), go.getPosition().getY());
		    	}
		    	queue.clear();
	    	}
    	}
    	g2.dispose();
    }
    
    public void update(Graphics g) 
    { 
         paint(g); 
    } 


	public Direction getCurrentDirection() {
		return currentDirection;
	}
	
	//Object lock = new Object();
	Set<GameObject> queue = new HashSet<GameObject>();

	public void addPendingObjectsForDrawing(List<GameObject> list) {

		synchronized(queue)
		{
			for(GameObject obj : list)
			{
				queue.add(obj);
			}
		}
	}
	
	public void showDialog(String msg){
		
		JOptionPane.showMessageDialog(container, msg);

		container.dispose();
	}
}
