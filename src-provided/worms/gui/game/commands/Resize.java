package worms.gui.game.commands;

import worms.gui.game.PlayGameScreen;
import worms.gui.game.sprites.WormSprite;
import worms.model.IFacade;
import worms.model.ModelException;
import worms.model.Worm;

public class Resize extends InstantaneousCommand {
	private final Worm worm;
	private final double factor;

	public Resize(IFacade facade, Worm worm, double factor,
			PlayGameScreen screen) {
		super(facade, screen);
		this.worm = worm;
		this.factor = factor;
	}

	@Override
	protected boolean canExecute() {
		return worm != null;
	}

	@Override
	protected void doStartExecution() {
		try {
			double newRadius = factor * getFacade().getRadius(worm);
			getFacade().setRadius(worm, newRadius);
			WormSprite sprite = getScreen().getWormSprite(worm);
			sprite.setRadius(newRadius);
		} catch (ModelException e) {
			// an invalid radius
			getScreen().addMessage(
					"Cannot " + (factor > 1.0 ? "grow" : "shrink")
							+ " that worm anymore :(");
		}
	}
}