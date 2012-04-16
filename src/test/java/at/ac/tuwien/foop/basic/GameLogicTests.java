package at.ac.tuwien.foop.basic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import at.ac.tuwien.foop.game.Direction;
import at.ac.tuwien.foop.game.gameobjects.Player;
import at.ac.tuwien.foop.game.gameobjects.PlayerColor;
import at.ac.tuwien.foop.game.gameobjects.Point;
import at.ac.tuwien.foop.game.gameobjects.Wall;
import at.ac.tuwien.foop.game.levels.InvalidLevelConfigurationException;
import at.ac.tuwien.foop.game.levels.Level01;

public class GameLogicTests {

    private Point point;

    private Player bluePlayer;
    private Player greenPlayer;
    private Player redPlayer;

    private GameState gameState;

    @Before
    public void setup() {
        this.gameState = new GameState();
        Collection<GameObject> gameObjects = new ArrayList<GameObject>();

        point = new Point(new Position(2, 1));

        bluePlayer = new Player(PlayerColor.BLUE, new Position(1, 1), Direction.RIGHT);
        greenPlayer = new Player(PlayerColor.GREEN, new Position(3, 1), Direction.LEFT);
        redPlayer = new Player(PlayerColor.RED, new Position(2, 2), Direction.DOWN);

        gameObjects.add(new Wall(new Position(0, 0)));
        gameObjects.add(new Wall(new Position(1, 0)));
        gameObjects.add(new Wall(new Position(2, 0)));
        gameObjects.add(new Wall(new Position(3, 0)));
        gameObjects.add(new Wall(new Position(4, 0)));
        gameObjects.add(new Wall(new Position(5, 0)));
        gameObjects.add(new Wall(new Position(5, 1)));
        gameObjects.add(new Wall(new Position(5, 2)));
        gameObjects.add(new Wall(new Position(5, 3)));
        gameObjects.add(new Wall(new Position(5, 4)));
        gameObjects.add(new Wall(new Position(5, 5)));
        gameObjects.add(new Wall(new Position(5, 6)));
        gameObjects.add(new Wall(new Position(4, 6)));
        gameObjects.add(new Wall(new Position(3, 6)));
        gameObjects.add(new Wall(new Position(3, 5)));
        gameObjects.add(new Wall(new Position(3, 4)));
        gameObjects.add(new Wall(new Position(3, 3)));
        gameObjects.add(new Wall(new Position(3, 3)));
        gameObjects.add(new Wall(new Position(2, 3)));
        gameObjects.add(new Wall(new Position(1, 3)));
        gameObjects.add(new Wall(new Position(0, 3)));
        gameObjects.add(new Wall(new Position(0, 2)));
        gameObjects.add(new Wall(new Position(0, 1)));

        gameObjects.add(new Wall(new Position(8, 0)));
        gameObjects.add(new Wall(new Position(8, 1)));
        gameObjects.add(new Wall(new Position(8, 2)));
        gameObjects.add(new Wall(new Position(8, 3)));
        gameObjects.add(new Wall(new Position(8, 4)));
        gameObjects.add(new Wall(new Position(8, 5)));

        gameObjects.add(point);
        gameObjects.add(bluePlayer);
        gameObjects.add(greenPlayer);
        gameObjects.add(redPlayer);

        this.gameState.initGameObjects(gameObjects);
    }

    @Test
    public void defineLevel() {
        Set<GameObject> currentState = gameState.getCurrentState();
        for (int y = 0; y <= 6; y++) {
            for (int x = 0; x <= 8; x++) {
                boolean found = false;
                for (GameObject go : currentState) {
                    if (go.getPosition().equals(new Position(x, y))) {
                        assertFalse(found);
                        go.draw();
                        found = true;
                    }
                }
                if (!found) {
                    System.out.print("F");
                }
            }
            System.out.println();
        }
    }

    @Test
    public void level01() throws InvalidLevelConfigurationException {
        Level01 level = new Level01();
        level.drawLevel();
    }

    @Test
    public void redMovesAndEatsBlue() {
        redPlayer.moveTo(new Position(1, 1));
        GameObject gameObject = gameState.getGameObject(new Position(1, 1));
        assertTrue(gameObject.equals(redPlayer));
        assertTrue(bluePlayer.isAlive() == false);
    }

    @Test
    public void greenMovesAndGetsEatenByBlue() {
        greenPlayer.moveTo(new Position(1, 1));
        GameObject gameObject = gameState.getGameObject(new Position(1, 1));
        assertTrue(gameObject.equals(bluePlayer));
        assertTrue(greenPlayer.isAlive() == false);
    }

    @Test
    public void greenMovesAndEatsRed() {
        greenPlayer.moveTo(new Position(2, 2));
        GameObject gameObject = gameState.getGameObject(new Position(2, 2));
        assertTrue(gameObject.equals(greenPlayer));
        assertTrue(redPlayer.isAlive() == false);
    }

    @Test
    public void callTickRedWins() {
        gameState.tick();
        assertTrue(bluePlayer.getPosition().equals(new Position(2, 1)));
        assertTrue(bluePlayer.isAlive() == true);
        assertTrue(greenPlayer.isAlive() == false);
        assertTrue(redPlayer.isAlive() == true);

        gameState.tick();

    }
}
