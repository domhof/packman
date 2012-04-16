package at.ac.tuwien.foop.game.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;

import at.ac.tuwien.foop.basic.GameObject;
import at.ac.tuwien.foop.basic.Position;
import at.ac.tuwien.foop.ui.CanvasContext;

public class Point extends GameObject {

	private static final long serialVersionUID = 6080121163102758559L;

	public Point(Position position) {
        super(position);
    }

    @Override
    public void moveOntoMe(Player o) {
        o.incrementPoints();
        o.setPosition(this.getPosition());
    }

    @Override
    public void tick() {
        // Do nothing.
    }

    @Override
    public void draw() {
        System.out.print("x");
    }

	@Override
	public void draw(Graphics2D g, CanvasContext cc, int x, int y) {

		g.setColor(Color.white);
		g.fillOval(getPosition().getX() * cc.getTileSize() + (cc.getTileSize() / 2 - cc.getPointSize() / 2), getPosition().getY() * cc.getTileSize()
               + (cc.getTileSize() / 2 - cc.getPointSize() / 2), cc.getPointSize(), cc.getPointSize());
	}
}
