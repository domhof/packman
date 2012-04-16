package at.ac.tuwien.foop.game.levels;

import at.ac.tuwien.foop.basic.Position;
import at.ac.tuwien.foop.game.Direction;

/**
 * Simple square level.
 * 
 * @author dominik
 * 
 */
public class Level02 extends Level {

    private static final int SIDELENGTH = 17;

    public Level02() throws InvalidLevelConfigurationException {
        super("Level 2");

        addWall(new Position(0, 0), Direction.RIGHT, SIDELENGTH);
        addWall(new Position(SIDELENGTH, 0), Direction.DOWN, SIDELENGTH);
        addWall(new Position(SIDELENGTH, SIDELENGTH), Direction.LEFT, SIDELENGTH);
        addWall(new Position(0, SIDELENGTH), Direction.UP, SIDELENGTH);

        addWall(new Position(2, 2), Direction.RIGHT, 5);
        addWall(new Position(2, 3), Direction.DOWN, 4);
        // addWall(new Position(SIDELENGTH - 2, SIDELENGTH - 2), Direction.LEFT,
        // 5);
        // addWall(new Position(2, SIDELENGTH - 2), Direction.UP, 5);

        addPoint(new Position(13, 13));
        addPoint(new Position(12, 12));
        addPoint(new Position(11, 11));
        addPoint(new Position(10, 10));

        addPlayerPosition(new Position(1, 1));
        addPlayerPosition(new Position(SIDELENGTH - 1, 1));
        addPlayerPosition(new Position(SIDELENGTH - 1, SIDELENGTH - 1));

        super.finishLevelConfiguration();
    }
}