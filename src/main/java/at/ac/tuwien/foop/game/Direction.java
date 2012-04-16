package at.ac.tuwien.foop.game;

import at.ac.tuwien.foop.basic.Position;

public enum Direction {
    UP {

        @Override
        public Position getNextPosition(Position currentPosition) {
            return currentPosition.getNextUp();
        }

    },
    DOWN {

        @Override
        public Position getNextPosition(Position currentPosition) {
            return currentPosition.getNextDown();
        }

    },
    LEFT {

        @Override
        public Position getNextPosition(Position currentPosition) {
            return currentPosition.getNextLeft();
        }

    },
    RIGHT {

        @Override
        public Position getNextPosition(Position currentPosition) {
            return currentPosition.getNextRight();
        }

    },
    NONE {

        @Override
        public Position getNextPosition(Position currentPosition) {
            return currentPosition;
        }

    };

    public abstract Position getNextPosition(Position currentPosition);
}
