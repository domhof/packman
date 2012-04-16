package at.ac.tuwien.foop.game.levels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import at.ac.tuwien.foop.basic.GameObject;
import at.ac.tuwien.foop.basic.Position;
import at.ac.tuwien.foop.game.Direction;
import at.ac.tuwien.foop.game.gameobjects.EmptyField;
import at.ac.tuwien.foop.game.gameobjects.Point;
import at.ac.tuwien.foop.game.gameobjects.Wall;

public abstract class Level {
    private final String name;

    private final Set<Position> playerPositions = new HashSet<Position>();

    private final ArrayList<Wall> walls = new ArrayList<Wall>();
    private final ArrayList<Point> points = new ArrayList<Point>();
    private final ArrayList<EmptyField> emptyFields = new ArrayList<EmptyField>();

    private int levelWidth;
    private int levelHeight;

    private boolean levelConfigurationFinished = false;

    Level(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getLevelWidth() {
        if (!levelConfigurationFinished) {
            throw new LevelConfigurationNotFinishedException();
        }
        return this.levelWidth;
    }

    /**
     * Each level is designed for a specific amount of players.
     * 
     * @return Number of players.
     */
    public int numberOfPlayers() {
        if (!levelConfigurationFinished) {
            throw new LevelConfigurationNotFinishedException();
        }
        return playerPositions.size();
    }

    /**
     * Each level has defined positions for the players.
     * 
     * @return The defined player positions.
     */
    public Set<Position> getPlayerPositions() {
        return playerPositions;
    }

    public int getLevelHeight() {
        if (!levelConfigurationFinished) {
            throw new LevelConfigurationNotFinishedException();
        }
        return this.levelHeight;
    }

    public void drawLevel() {
        if (!levelConfigurationFinished) {
            throw new LevelConfigurationNotFinishedException();
        }

        Collection<GameObject> allGameObjects = getAllGameObjects();

        for (int y = 0; y <= this.levelHeight; y++) {
            for (int x = 0; x <= this.levelWidth; x++) {
                boolean found = false;
                for (GameObject go : allGameObjects) {
                    if (go.getPosition().equals(new Position(x, y))) {
                        go.draw();
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.print("F");
                }
            }
            System.out.println();
        }
    }

    public Set<GameObject> getAllGameObjects() {
        if (!levelConfigurationFinished) {
            throw new LevelConfigurationNotFinishedException();
        }

        HashSet<GameObject> allGameObjects = new HashSet<GameObject>();
        allGameObjects.addAll(walls);
        allGameObjects.addAll(points);
        allGameObjects.addAll(emptyFields);

        return allGameObjects;
    }

    private void generateEmptyFields(Set<Position> nonEmptyPositions) {
        for (int yCurrent = 0; yCurrent <= this.levelWidth; yCurrent++) {
            boolean xInside = false;
            boolean onObject = false;
            int x = 0;
            for (int xCurrent = 0; xCurrent <= this.levelWidth; xCurrent++) {
                Position currentPosition = new Position(xCurrent, yCurrent);

                if (!xInside) {
                    if (onObject && !nonEmptyPositions.contains(currentPosition)) {
                        x = xCurrent;
                        xInside = true;
                        onObject = false;
                    } else {
                        onObject = (onObject || nonEmptyPositions.contains(currentPosition));
                    }
                } else { // If inside.
                    if (nonEmptyPositions.contains(currentPosition)) {
                        onObject = true;
                        xInside = false;
                        do {
                            int yFrom = yCurrent;
                            int yTo = yCurrent;

                            while (yFrom > 0 && !nonEmptyPositions.contains(new Position(x, yFrom - 1))) {
                                yFrom -= 1;
                            }
                            while (yTo < this.levelWidth && !nonEmptyPositions.contains(new Position(x, yTo + 1))) {
                                yTo += 1;
                            }

                            if (yFrom != 0 && yTo != this.levelHeight) {
                                for (int y = yFrom; y <= yTo; y++) {
                                    Position emptyPosition = new Position(x, y);
                                    if (!playerPositions.contains(emptyPosition)) {
                                        nonEmptyPositions.add(emptyPosition);
                                        this.emptyFields.add(new EmptyField(emptyPosition));
                                    }
                                }
                            }
                        } while (++x < xCurrent);
                    }
                }
            }
        }
    }

    private Set<Position> getGameObjectsPositions(Set<GameObject> allGameObjects)
            throws InvalidLevelConfigurationException {
        Set<Position> gameObjectsPositions = new HashSet<Position>(allGameObjects.size());
        for (GameObject go : allGameObjects) {
            if (!gameObjectsPositions.add(go.getPosition())) {
                throw new InvalidLevelConfigurationException("Multiple game objects for one position defined.");
            }
        }
        return gameObjectsPositions;
    }

    private void calcLevelWidthAndHeight(Collection<GameObject> allGameObjects) {
        for (GameObject gameObject : allGameObjects) {
            allGameObjects.add(gameObject);

            int x = gameObject.getPosition().getX();
            if (this.levelWidth < x) {
                this.levelWidth = x;
            }
            int y = gameObject.getPosition().getY();
            if (this.levelHeight < y) {
                this.levelHeight = y;
            }
        }
    }

    protected void finishLevelConfiguration() throws InvalidLevelConfigurationException {
        HashSet<GameObject> allGameObjects = new HashSet<GameObject>();
        allGameObjects.addAll(walls);
        allGameObjects.addAll(points);

        calcLevelWidthAndHeight(allGameObjects);

        Set<Position> gameObjectsPositions = getGameObjectsPositions(allGameObjects);
        generateEmptyFields(gameObjectsPositions);

        this.levelConfigurationFinished = true;
    }

    /**
     * Adds a wall to the level.
     * 
     * @param position Start position of the wall.
     * @param direction Direction of the wall.
     * @param length Length of the wall.
     */
    protected void addWall(Position position, Direction direction, int length) {
        while (0 < length--) {
            walls.add(new Wall(position));
            position = direction.getNextPosition(position);
        }
    }

    /**
     * Adds a single wall element.
     * 
     * @param position Position of the wall element.
     */
    protected void addWall(Position position) {
        walls.add(new Wall(position));
    }

    protected void addPlayerPosition(Position position) {
        playerPositions.add(position);
    }

    /**
     * Adds a point to the level.
     * 
     * @param position Position of the point.
     */
    protected void addPoint(Position position) {
        Point point = new Point(position);
        points.add(point);
    }
}
