package at.ac.tuwien.foop.basic;

import java.awt.Graphics2D;
import java.io.Serializable;

import at.ac.tuwien.foop.ui.CanvasContext;

public abstract class GameObject implements MoveOntoMe, Serializable {

	private static final long serialVersionUID = 4395612635706406723L;
	
	private GameState gameState;
    private Position position;

    protected boolean removed = false;

    public GameObject(Position position) {
        this.position = position;
    }

    public GameObject() {
    }

    protected void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    protected GameObject getGameObject(Position position) {
        return gameState.getGameObject(position);
    }

    protected void removeFromGameState() {
        removed = true;
        gameState.updateGameObject(this);
    }

    boolean isRemoved() {
        return removed;
    }

    public final Position getPosition() {
        return this.position;
    }

    public final void setPosition(Position position) {
        if (gameState != null) {
            gameState.gameObjectPositionChanged(position, this);
        }
        this.position = position;
    }

    /**
     * Called in every game loop cycle via tick method in GameState. Implements
     * automatic behavior of the game object.
     */
    public abstract void tick();

    public abstract void draw();
    
    public abstract void draw(Graphics2D g, CanvasContext cc, int x, int y);

}
