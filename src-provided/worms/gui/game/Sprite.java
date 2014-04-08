package worms.gui.game;

import java.awt.Graphics2D;

import worms.model.IFacade;

public abstract class Sprite<T> {

	private double x;
	private double y;
	private final PlayGameScreen screen;

	protected Sprite(PlayGameScreen screen) {
		this.screen = screen;
	}
	
	public PlayGameScreen getScreen() {
		return screen;
	}
	
	public abstract T getObject();
	
	public abstract boolean isObjectAlive();
	
	protected IFacade getFacade() {
		return getScreen().getFacade();
	}

	public abstract void draw(Graphics2D g);

	/**
	 * Height (in pixels) of this sprite, when drawn to the given graphics object
	 * @return
	 */
	public abstract double getHeight(Graphics2D g);

	/**
	 * Width (in pixels) of this sprite, when drawn to the given graphics object
	 * @return
	 */
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

	/**
	 * Update attributes of this sprite with values from the object
	 */
	public void update() {		
	}

}