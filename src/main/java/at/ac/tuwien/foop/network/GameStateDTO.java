package at.ac.tuwien.foop.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import at.ac.tuwien.foop.basic.GameObject;

public class GameStateDTO implements Serializable {
	
	private static final long serialVersionUID = 5950619618824677321L;
	private ArrayList<GameObject> gameObjects;

	public GameStateDTO(List<GameObject> allGameObjects) {
		this.gameObjects = new ArrayList<GameObject>(allGameObjects);
	}

	public List<GameObject> getGameObjects()
	{
		return this.gameObjects;
	}
}
