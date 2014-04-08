package worms.gui.game.sprites;

import worms.gui.GUIUtils;
import worms.model.Worm;

public class WormSprite extends ImageSprite {

	private final Worm worm;

	public WormSprite(Worm worm) {
		super("images/worm.png");
		this.worm = worm;
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
		double imageHeightInMeters = GUIUtils.pixelToMeter(getImageHeight());

		/*
		 * scale factor to nicely fit the image in a circle with diameter equal
		 * to the image height (value determined experimentally)
		 */
		double fitFactor = 0.8;

		double scaleFactor = fitFactor * 2 * radius / imageHeightInMeters;

		// limit scaling
		scaleFactor = Math.max(0.1, Math.min(scaleFactor, 100));
		
		setScale(scaleFactor);
	}
}
