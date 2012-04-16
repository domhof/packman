package at.ac.tuwien.foop.game.levels;

import java.util.Random;

import at.ac.tuwien.foop.basic.Position;
import at.ac.tuwien.foop.game.Direction;

/**
 * Simple square level.
 * 
 * @author dominik
 * 
 */
public class Level03 extends Level {

    private static final int SIDELENGTH = 17;

    public Level03() throws InvalidLevelConfigurationException {
        super("Level 3");

        addWall(new Position(0, 0), Direction.RIGHT, SIDELENGTH);
        addWall(new Position(SIDELENGTH, 0), Direction.DOWN, SIDELENGTH);
        addWall(new Position(SIDELENGTH, SIDELENGTH), Direction.LEFT, SIDELENGTH);
        addWall(new Position(0, SIDELENGTH), Direction.UP, SIDELENGTH);

        Random randomGenerator = new Random();
        for (int i = 2; i < SIDELENGTH - 1; i++) {
            for (int j = 2; j < SIDELENGTH - 1; j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    addWall(new Position(i, j));
                } else {
                    if (randomGenerator.nextInt(2) == 0) {
                        addPoint(new Position(i, j));
                    }
                }
            }
        }

        addPlayerPosition(new Position(1, 1));
        addPlayerPosition(new Position(SIDELENGTH - 1, 1));
        addPlayerPosition(new Position(SIDELENGTH - 1, SIDELENGTH - 1));

        super.finishLevelConfiguration();
    }
}