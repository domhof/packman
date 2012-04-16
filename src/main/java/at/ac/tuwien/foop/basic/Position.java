package at.ac.tuwien.foop.basic;

import java.io.Serializable;

public class Position implements Serializable {

	private static final long serialVersionUID = 3028049182296342346L;
	
	private int x;
    private int y;

    /**
     * 
     * @param x Positive X coordinate 0
     * @param y Positive Y coordinate 0
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position getNextRight() {
        return new Position(this.x + 1, this.y);
    }

    public Position getNextLeft() {
        return new Position(this.x - 1, this.y);
    }

    public Position getNextUp() {
        return new Position(this.x, this.y - 1);
    }

    public Position getNextDown() {
        return new Position(this.x, this.y + 1);
    }

    @Override
    public String toString() {
        return "Position [x=" + x + ", y=" + y + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }
}
