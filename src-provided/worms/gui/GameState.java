package worms.gui;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import worms.gui.game.commands.Command;
import worms.gui.game.commands.CommandProcessor;
import worms.model.IFacade;
import worms.model.World;
import worms.model.Worm;

public class GameState {

	private final Random random;
	private final IFacade facade;
	private final CommandProcessor commandProcessor = new CommandProcessor();

	private World world;

	private final Level level;

	public GameState(IFacade facade, long randomSeed, Level level) {
		this.random = new Random(randomSeed);
		this.facade = facade;
		this.level = level;
	}

	public void createWorld() {
		level.load();
		world = facade.createWorld(level.getWorldWidth(),
				level.getWorldHeight(), level.getPassableMap(), random);
	}

	public IFacade getFacade() {
		return facade;
	}

	public Collection<Worm> getWorms() {
		return getFacade().getWorms(world);
	}

	public void evolve(double timeDelta) {
		commandProcessor.advanceCommandQueue(timeDelta);
	}

	public void enqueueCommand(Command cmd) {
		commandProcessor.enqueueCommand(cmd);
	}

	public Level getLevel() {
		return level;
	}

	public World getWorld() {
		return world;
	}

	public List<Command> getEnqueuedCommands() {
		return commandProcessor.getCommandStack();
	}

}
