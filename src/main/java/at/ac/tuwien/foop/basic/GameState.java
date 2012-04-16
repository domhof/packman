package at.ac.tuwien.foop.basic;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import at.ac.tuwien.foop.game.gameobjects.EmptyField;

public class GameState implements Serializable {

	private static final long serialVersionUID = -6976789641519721309L;
	
	private HashMap<Position, GameObject> gameObjects = new HashMap<Position, GameObject>();
	private Set<GameObject> gameStateDelta = new HashSet<GameObject>();

	void gameObjectPositionChanged(Position newPosition, GameObject gameObject) {
		EmptyField emptyField = new EmptyField(gameObject.getPosition());
		gameObjects.put(gameObject.getPosition(), emptyField);
		this.updateGameObject(emptyField);

		gameObjects.put(newPosition, gameObject);
		this.updateGameObject(gameObject);
	}

	public GameObject getGameObject(Position position) {
		GameObject gameObject = gameObjects.get(position);
		// return (gameObject != null) ? gameObject : new EmptyField(position);
		return gameObject;
	}

	void updateGameObject(GameObject gameObject) {
		gameStateDelta.add(gameObject);
	}

	/**
	 * 
	 * @param initialGameObjects
	 *            The game objects for this game.
	 */
	public void initGameObjects(Collection<GameObject> initialGameObjects) {
		this.gameObjects.clear();
		this.gameStateDelta.clear();

		for (GameObject o : initialGameObjects) {
			o.setGameState(this);
			Position position = o.getPosition();
			this.gameObjects.put(position, o);
			this.gameStateDelta.add(o);
		}
	}

	/**
	 * Returns the the whole game state. Should be called only once per client.
	 * 
	 * @return The game objects in this game.
	 */
	public Set<GameObject> getCurrentState() {
		return new HashSet<GameObject>(gameObjects.values());
	}

	/**
	 * To be called once in each game loop cycle.
	 * 
	 * @return The game state delta caused by this tick.
	 */
    public Set<GameObject> tick() {
        for (GameObject go : new HashSet<GameObject>(gameObjects.values())) {
            if (!go.isRemoved()) {
                go.tick();
            }
        }
        HashSet<GameObject> result = new HashSet<GameObject>(this.gameStateDelta);
        this.gameStateDelta.clear();
        return result;
    }
}
