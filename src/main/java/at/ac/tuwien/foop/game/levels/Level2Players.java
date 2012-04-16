package at.ac.tuwien.foop.game.levels;

import at.ac.tuwien.foop.basic.Position;
import at.ac.tuwien.foop.game.Direction;

/**
 * Simple square level.
 * 
 * @author dominik
 * 
 */
public class Level2Players extends Level {

    private static final int SIDELENGTH = 17;

    public Level2Players() throws InvalidLevelConfigurationException {
        super("Level 1");

        addWall(new Position(0, 0), Direction.RIGHT, SIDELENGTH);
        addWall(new Position(SIDELENGTH, 0), Direction.DOWN, SIDELENGTH);
        addWall(new Position(SIDELENGTH, SIDELENGTH), Direction.LEFT, SIDELENGTH);
        addWall(new Position(0, SIDELENGTH), Direction.UP, SIDELENGTH);

        addPoint(new Position(SIDELENGTH / 2, SIDELENGTH / 2));
        addPoint(new Position(SIDELENGTH / 2 + 1, SIDELENGTH / 2));
        addPoint(new Position(SIDELENGTH / 2, SIDELENGTH / 2 + 1));
        addPoint(new Position(SIDELENGTH / 2 + 1, SIDELENGTH / 2 + 1));

        addPlayerPosition(new Position(1, 1));
        addPlayerPosition(new Position(SIDELENGTH - 1, 1));
        //addPlayerPosition(new Position(SIDELENGTH - 1, SIDELENGTH - 1));

        super.finishLevelConfiguration();
    }
}