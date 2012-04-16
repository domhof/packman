package at.ac.tuwien.foop.game.gameobjects;

public enum PlayerColor {
    RED {

        @Override
        public boolean wins(PlayerColor playerColor) {
            return (playerColor == PlayerColor.GREEN) ? false : true;
        }

        @Override
        public PlayerColor nextColor() {
            return GREEN;
        }
    },
    BLUE {

        @Override
        public boolean wins(PlayerColor playerColor) {
            return (playerColor == PlayerColor.RED) ? false : true;
        }

        @Override
        public PlayerColor nextColor() {
            return RED;
        }
    },
    GREEN {

        @Override
        public boolean wins(PlayerColor playerColor) {
            return (playerColor == PlayerColor.BLUE) ? false : true;
        }

        @Override
        public PlayerColor nextColor() {
            return BLUE;
        }

    };

    public abstract boolean wins(PlayerColor playerColor);

    public abstract PlayerColor nextColor();
}
