package worms.gui.game.commands;

import worms.gui.game.PlayGameScreen;
import worms.gui.game.sprites.WormSprite;
import worms.model.IFacade;
import worms.model.Worm;

public class Turn extends InstantaneousCommand {
	private final Worm worm;
	private final double angle;

	public Turn(IFacade facade, Worm worm, double angle, PlayGameScreen screen) {
		super(facade, screen);
		this.worm = worm;
		this.angle = angle;
	}

	@Override
	protected boolean canExecute() {
		return getFacade().canTurn(worm, angle);
	}
	
	@Override
	protected void executionCancelled() {
		getScreen().addMessage("This worm cannot perform that turn :(");
	}
	
	@Override
	protected void doStartExecution() {
		getFacade().turn(worm, angle);
		WormSprite sprite = getScreen().getWormSprite(worm);
		sprite.setDirection(getFacade().getOrientation(worm));
	}
}