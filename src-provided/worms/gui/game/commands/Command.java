package worms.gui.game.commands;

import worms.gui.game.PlayGameScreen;
import worms.model.IFacade;
import worms.model.World;

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

	protected World getWorld() {
		return getScreen().getWorld();
	}

	public final void startExecution() {
		if (canStart()) {
			doStartExecution();
			started = true;
			afterExecutionStarted();
		} else {
			cancelExecution();
		}
	}

	protected final void cancelExecution() {
		cancelled = true;
		afterExecutionCancelled();
	}

	public final void update(double dt) {
		if (!isTerminated()) {
			elapsedTime += dt;
			doUpdate(dt);
			if (cancelled) {
				afterExecutionCancelled();
			} else if (isTerminated()) {
				afterExecutionCompleted();
				getScreen().updateSprites();
				if (getFacade().isGameFinished(getWorld())) {
					getScreen().gameFinished();
				}
			}
		}
	}

	/**
	 * Returns the total time that has elapsed while executing this command
	 */
	public double getElapsedTime() {
		return elapsedTime;
	}

	/**
	 * Returns whether or not this command has been started
	 */
	public boolean hasBeenStarted() {
		return started;
	}

	/**
	 * Returns whether or not the execution of this command is terminated,
	 * either by cancellation or by successful completion.
	 */
	public final boolean isTerminated() {
		return cancelled || (hasBeenStarted() && isExecutionCompleted());
	}

	/**
	 * Returns whether or not the execution of the command has been completed successfully.
	 */
	protected abstract boolean isExecutionCompleted();

	/**
	 * Returns whether or not the execution of the command can start
	 */
	protected abstract boolean canStart();

	/**
	 * Start executing the command
	 */
	protected abstract void doStartExecution();

	/**
	 * Called when the execution of the command has been completed successfully.
	 */
	protected void afterExecutionCompleted() {
	}

	/**
	 * Called when the execution of the command has been cancelled.
	 */
	protected void afterExecutionCancelled() {
	}

	/**
	 * Called when the execution of the command has been started.
	 */
	protected void afterExecutionStarted() {
	}

	/**
	 * Update the execution of the command by the given time interval
	 *  
	 * @param dt
	 */
	protected abstract void doUpdate(double dt);

	@Override
	public String toString() {
		return this.getClass().getSimpleName()
				+ " ("
				+ (hasBeenStarted() ? "elapsed: "
						+ String.format("%.2f", getElapsedTime()) + "s)"
						: "queued)");
	}
}