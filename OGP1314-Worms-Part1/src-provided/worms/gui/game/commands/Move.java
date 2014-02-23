package worms.gui.game.commands;

import worms.gui.GUIConstants;
import worms.gui.game.PlayGameScreen;
import worms.gui.game.sprites.WormSprite;
import worms.model.IFacade;
import worms.model.Worm;

public class Move extends Command {
	private final Worm worm;
	private final int nbSteps;

	private double startX;
	private double startY;

	private double finalX;
	private double finalY;

	public Move(IFacade facade, Worm worm, int nbSteps, PlayGameScreen screen) {
		super(facade, screen);
		this.worm = worm;
		this.nbSteps = nbSteps;
	}

	public Worm getWorm() {
		return worm;
	}

	@Override
	protected boolean canExecute() {
		return worm != null && getFacade().canMove(worm, nbSteps);
	}

	private double getTotalDuration() {
		return nbSteps * GUIConstants.MOVE_DURATION;
	}

	@Override
	protected void doUpdate(double dt) {
		WormSprite sprite = getScreen().getWormSprite(getWorm());
		if (getElapsedTime() < getTotalDuration()) {
			double t = getElapsedTime() / getTotalDuration();
			t = t * t * (3 - 2 * t); // smooth-step interpolation
			double x = (1.0 - t) * startX + t * finalX;
			double y = (1.0 - t) * startY + t * finalY;
			sprite.setCenterLocation(x, y);
		} else {
			sprite.setCenterLocation(finalX, finalY);
		}
	}

	@Override
	protected boolean isDoneExecuting() {
		return getElapsedTime() > getTotalDuration();
	}

	@Override
	protected void executionCancelled() {
		getScreen().addMessage("This worm cannot move :(");
	}

	@Override
	protected void doStartExecution() {
		this.startX = getScreen().getScreenX(getFacade().getX(worm));
		this.startY = getScreen().getScreenY(getFacade().getY(worm));
		getFacade().move(worm, nbSteps);
		this.finalX = getScreen().getScreenX(getFacade().getX(worm));
		this.finalY = getScreen().getScreenY(getFacade().getY(worm));
	}
}