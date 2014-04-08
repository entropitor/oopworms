package worms.gui.game.commands;

import worms.gui.game.PlayGameScreen;
import worms.gui.game.sprites.WormSprite;
import worms.model.IFacade;
import worms.model.ModelException;
import worms.model.Worm;

public class Jump extends Command {
	private final Worm worm;
	private boolean finished = false;

	public Jump(IFacade facade, Worm worm, PlayGameScreen screen) {
		super(facade, screen);
		this.worm = worm;
	}

	public Worm getWorm() {
		return worm;
	}

	@Override
	protected boolean canExecute() {
		return worm != null;
	}

	@Override
	protected void doStartExecution() {

	}
	
	@Override
	protected void executionCancelled() {
		getScreen().addMessage("This worm cannot jump :(");
	}

	private double getTotalDuration() {
		return getFacade().getJumpTime(getWorm());
	}

	@Override
	protected void doUpdate(double dt) {
		WormSprite sprite = getScreen().getWormSprite(getWorm());

		try {
			if (getElapsedTime() >= getTotalDuration()) {
				getFacade().jump(worm);
				finished = true;
				double x = getScreen().getScreenX(getFacade().getX(getWorm()));
				double y = getScreen().getScreenY(getFacade().getY(getWorm()));

				sprite.setCenterLocation(x, y);
			} else {
				double[] xy = getFacade().getJumpStep(getWorm(),
						getElapsedTime());

				sprite.setCenterLocation(getScreen().getScreenX(xy[0]),
						getScreen().getScreenY(xy[1]));
			}
		} catch (ModelException e) {
			finished = true;
			executionCancelled();
		}
	}

	@Override
	protected boolean isDoneExecuting() {
		return finished;
	}
}