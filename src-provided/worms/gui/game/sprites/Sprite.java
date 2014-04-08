package worms.gui.game.sprites;

import java.awt.Graphics2D;

public abstract class Sprite {

	private double x;
	private double y;

	public Sprite() {
		super();
	}

	public abstract void draw(Graphics2D g);

	public abstract double getHeight(Graphics2D g);

	public abstract double getWidth(Graphics2D g);

	public double[] getCenterLocation() {
		return new double[] { getCenterX(), getCenterY() };
	}

	public void setCenterLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getCenterX() {
		return x;
	}

	public double getCenterY() {
		return y;
	}

}