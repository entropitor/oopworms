package worms.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import worms.gui.game.commands.Command;
import worms.gui.game.commands.CommandProcessor;
import worms.model.IFacade;
import worms.model.Worm;

public class GameState {

	private final Random random;
	private final IFacade facade;
	private final Collection<Worm> worms = new ArrayList<Worm>();
	private final CommandProcessor commandProcessor = new CommandProcessor();

	private Iterator<Worm> selection;
	private Worm selectedWorm;

	private final int width;
	private final int height;

	public GameState(IFacade facade, long randomSeed, int width, int height) {
		this.random = new Random(randomSeed);
		this.facade = facade;
		this.width = width;
		this.height = height;
	}

	private List<String> wormNames = Arrays.asList("Shari", "Shannon",
			"Willard", "Jodi", "Santos", "Ross", "Cora", "Jacob", "Homer",
			"Kara");
	private int nameIndex = 0;

	private void createRandomWorms() {
		for (int i = 0; i < wormNames.size(); i++) {
			String name = wormNames.get(nameIndex++);
			double radius = 0.25 + random.nextDouble() / 4;
			double worldWidth = GUIUtils.pixelToMeter(width);
			double worldHeight = GUIUtils.pixelToMeter(height);

			double x = -worldWidth / 2 + radius + random.nextDouble()
					* (worldWidth - 2 * radius);
			double y = -worldHeight / 2 + radius + random.nextDouble()
					* (worldHeight - 2 * radius);
			double direction = random.nextDouble() * 2 * Math.PI;
			Worm worm = facade.createWorm(x, y, direction, radius, name);
			if (worm != null) {
				worms.add(worm);
			} else {
				throw new NullPointerException("Created worm must not be null");
			}
		}
	}

	public void startGame() {
		createRandomWorms();
		selectNextWorm();
	}

	public Worm getSelectedWorm() {
		return selectedWorm;
	}

	public void selectNextWorm() {
		if (selection == null || !selection.hasNext()) {
			selection = worms.iterator();
		}
		if (selection.hasNext()) {
			selectWorm(selection.next());
		} else {
			selectWorm(null);
		}
	}

	public void selectWorm(Worm worm) {
		selectedWorm = worm;
	}

	public IFacade getFacade() {
		return facade;
	}

	public Collection<Worm> getWorms() {
		return Collections.unmodifiableCollection(worms);
	}

	public void evolve(double timeDelta) {
		commandProcessor.advanceCommandQueue(timeDelta);
	}

	public void enqueueCommand(Command cmd) {
		commandProcessor.enqueueCommand(cmd);
	}

}
