package at.ac.tuwien.foop.game.levels;

public class InvalidLevelConfigurationException extends Exception {
    private static final long serialVersionUID = -5210124586090146616L;

    public InvalidLevelConfigurationException(String msg) {
        super(msg);
    }
}
