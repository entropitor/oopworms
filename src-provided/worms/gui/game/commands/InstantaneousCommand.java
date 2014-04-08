package worms.gui.game.commands;

import worms.gui.game.PlayGameScreen;
import worms.model.IFacade;

public abstract class InstantaneousCommand extends Command {
	protected InstantaneousCommand(IFacade facade, PlayGameScreen screen) {
		super(facade, screen);
	}

	@Override
	protected final boolean isDoneExecuting() {
		return true;
	}

	@Override
	protected final void doUpdate(double dt) {
	}
}