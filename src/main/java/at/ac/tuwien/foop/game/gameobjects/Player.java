package at.ac.tuwien.foop.game.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;

import at.ac.tuwien.foop.basic.GameObject;
import at.ac.tuwien.foop.basic.Position;
import at.ac.tuwien.foop.game.Direction;
import at.ac.tuwien.foop.ui.CanvasContext;

public class Player extends GameObject {

    private static final long serialVersionUID = 5900794114975423868L;

    private Integer points = 0;
    private PlayerColor color;
    private Direction direction;
    private boolean alive = true;

    public Player(PlayerColor color, Position position, Direction direction) {
        super(position);
        this.color = color;
        this.direction = direction;
    }

    public Player(PlayerColor color, Position position) {
        super(position);
        this.color = color;
        this.direction = Direction.NONE;
    }

    public Player(PlayerColor color) {
        super();
        this.color = color;
        this.direction = Direction.NONE;
    }

    public void preparePlayer() {
        alive = true;
        direction = Direction.NONE;
        removed = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public void incrementPoints() {
        this.points++;
    }

    private void addPoints(Integer points) {
        this.points += points;
    }

    private void decreasePoints(Integer points) {
        this.points -= points;
    }

    public Integer getPoints() {
        return points;
    }

    public PlayerColor getColor() {
        return color;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void moveTo(Position position) {
        if (!position.equals(this.getPosition())) {
            super.getGameObject(position).moveOntoMe(this);
        }
    }

    @Override
    public void moveOntoMe(Player o) {
        Player winner;
        Player loser;

        if (color.wins(o.getColor())) {
            winner = this;
            loser = o;
        } else {
            winner = o;
            loser = this;
        }
        winner.addPoints(loser.points);
        loser.decreasePoints(loser.points);
        loser.die();
        winner.setPosition(this.getPosition());
    }

    private void die() {
        this.alive = false;
        super.removeFromGameState();
    }

    @Override
    public void tick() {
        this.moveTo(getDirection().getNextPosition(this.getPosition()));
    }

    @Override
    public void draw() {
        switch (color) {
        case BLUE:
            System.out.print("B");
            break;
        case GREEN:
            System.out.print("G");
            break;
        case RED:
            System.out.print("R");
            break;
        }

    }

    @Override
    public void draw(Graphics2D g, CanvasContext cc, int x, int y) {

        if (color == PlayerColor.BLUE) {
            g.setColor(Color.blue);

        } else if (color == PlayerColor.GREEN) {
            g.setColor(Color.green);

        } else if (color == PlayerColor.RED) {
            g.setColor(Color.red);
        }

        int startAngle = 45;
        int arcAngle = 270;

        if (direction == Direction.RIGHT) {

            startAngle = 45;

        } else if (direction == Direction.LEFT) {

            startAngle = 235;

        } else if (direction == Direction.UP) {

            startAngle = 135;
        } else if (direction == Direction.DOWN) {

            startAngle = 315;
        }

        g.fillArc(x * cc.getTileSize() + (cc.getTileSize() / 2 - cc.getTileSize() / 2),
                y * cc.getTileSize() + (cc.getTileSize() / 2 - cc.getTileSize() / 2), cc.getTileSize(),
                cc.getTileSize(), startAngle, arcAngle);

    }
}
