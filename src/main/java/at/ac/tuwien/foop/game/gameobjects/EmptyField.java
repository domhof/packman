package at.ac.tuwien.foop.game.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;

import at.ac.tuwien.foop.basic.GameObject;
import at.ac.tuwien.foop.basic.Position;
import at.ac.tuwien.foop.ui.CanvasContext;

public class EmptyField extends GameObject {

    public EmptyField(Position position) {
        super(position);
    }

    @Override
    public void moveOntoMe(Player o) {
        o.setPosition(this.getPosition());
    }

    @Override
    public void tick() {
        // Do nothing.
    }

    @Override
    public void draw() {
        System.out.print("-");
    }

	@Override
	public void draw(Graphics2D g, CanvasContext cs, int x, int y) {
    	g.setColor(Color.black);
        g.fillRect(getPosition().getX() * cs.getTileSize(), getPosition().getY() * cs.getTileSize(), cs.getTileSize(), cs.getTileSize());
        //g.setColor(Color.black);
       // g.drawRect(getPosition().getX() * cs.getTileSize(), getPosition().getY() * cs.getTileSize(), cs.getTileSize(), cs.getTileSize());
    	
	}
}
