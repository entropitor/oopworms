package worms.gui.game.sprites;

import worms.gui.GUIUtils;
import worms.gui.game.ImageSprite;
import worms.gui.game.PlayGameScreen;
import worms.model.Worm;

public class WormSprite extends ImageSprite<Worm> {

	private static final double MAX_SCALE = 100;
	private static final double MIN_SCALE = 0.05;
	private final Worm worm;

	public WormSprite(PlayGameScreen screen, Worm worm) {
		super(screen, "images/worm.png");
		this.worm = worm;
	}

	@Override
	public Worm getObject() {
		return getWorm();
	}

	public Worm getWorm() {
		return worm;
	}

	public void setDirection(double newDirection) {
		double direction = GUIUtils.restrictDirection(newDirection);

		if (Math.PI / 2 > direction || 3 * Math.PI / 2 < direction) {
			setHflipped(true);
		} else {
			setHflipped(false);
		}
	}

	/**
	 * @param radius
	 *            (in worm-meter)
	 */
	public void setRadius(double radius) {
		/*
		 * Height of the image (when drawn at native size) in worm-meters, given
		 * the scale at which the world is drawn to screen
		 */
		double imageHeightInMeters = getScreen().screenToWorldDistance(
				getImageHeight());

		/*
		 * scale factor to nicely fit the image in a circle with diameter equal
		 * to the image height (value determined experimentally)
		 */
		double fitFactor = 0.8;

		double scaleFactor = fitFactor * 2 * radius / imageHeightInMeters;

		// limit scaling
		scaleFactor = Math.max(MIN_SCALE, Math.min(scaleFactor, MAX_SCALE));

		setScale(scaleFactor);
	}

	public boolean hitTest(double screenX, double screenY) {
		double radius = getScale()
				* Math.max(getImageWidth(), getImageHeight()) / 2.0;
		double dx = screenX - getCenterX();
		double dy = screenY - getCenterY();
		return dx * dx + dy * dy <= radius * radius;
	}

	@Override
	public boolean isObjectAlive() {
		return getFacade().isAlive(getWorm());
	}

	@Override
	public void update() {
		// don't update the location here, because it may differ from the
		// location in the model
		setRadius(getFacade().getRadius(getWorm()));
		setDirection(getFacade().getOrientation(getWorm()));
	}
}
