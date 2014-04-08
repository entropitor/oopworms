package worms.gui.game.commands;

import worms.gui.game.PlayGameScreen;
import worms.model.IFacade;

public abstract class Command {

	private final IFacade facade;
	private final PlayGameScreen screen;

	private double elapsedTime;
	private boolean cancelled = false;
	private boolean started = false;

	protected Command(IFacade facade, PlayGameScreen screen) {
		this.facade = facade;
		this.screen = screen;
	}

	protected PlayGameScreen getScreen() {
		return screen;
	}

	protected IFacade getFacade() {
		return facade;
	}

	public final void startExecution() {
		if (canExecute()) {
			doStartExecution();
			started = true;
		} else {
			cancelled = true;
			executionCancelled();
		}
	}

	public final void update(double dt) {
		elapsedTime += dt;
		if (!isFinished()) {
			doUpdate(dt);
		}
	}

	public double getElapsedTime() {
		return elapsedTime;
	}

	public boolean isStarted() {
		return started;
	}

	public final boolean isFinished() {
		return cancelled || (isStarted() && isDoneExecuting());
	}

	protected abstract boolean isDoneExecuting();

	protected abstract boolean canExecute();

	protected abstract void doStartExecution();
	
	protected void executionCancelled() {
		
	}

	protected abstract void doUpdate(double dt);
}