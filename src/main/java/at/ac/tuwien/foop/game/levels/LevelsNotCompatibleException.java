package at.ac.tuwien.foop.game.levels;

public class LevelsNotCompatibleException extends Exception {
    private static final long serialVersionUID = 5802041337310836116L;

    public LevelsNotCompatibleException(String msg) {
        super(msg);
    }
}
