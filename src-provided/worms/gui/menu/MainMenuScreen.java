package worms.gui.menu;

import worms.gui.GameState;
import worms.gui.WormsGUI;
import worms.gui.game.PlayGameScreen;

enum MainMenuOption {
	Play("Play worms"), PlayDebug("Play worms (debug mode)"), Exit("Exit");

	private final String displayString;

	MainMenuOption(String displayString) {
		this.displayString = displayString;
	}

	public String getDisplayString() {
		return displayString;
	}
}

public class MainMenuScreen extends AbstractMenuScreen<MainMenuOption> {

	public MainMenuScreen(WormsGUI gui) {
		super(gui);
	}

	@Override
	protected MainMenuOption[] getChoices() {
		return MainMenuOption.values();
	}

	@Override
	protected String getDisplayName(MainMenuOption option) {
		return option.getDisplayString();
	}

	@Override
	protected String getInstructions() {
		return "Please make your choice";
	}

	@Override
	protected void screenStarted() {
		MainMenuOption option = select();
		switch (option) {
		case Play:
			startGame(false);
			break;
		case PlayDebug:
			startGame(true);
			break;
		case Exit:
			getGUI().exit();
		}
	}

	private void startGame(boolean debugMode) {
		WormsGUI gui = getGUI();
		GameState gameState = new GameState(gui.getFacade(),
				gui.getOptions().randomSeed, gui.getWidth(), gui.getHeight());

		PlayGameScreen playGameScreen = PlayGameScreen.create(gui, gameState,
				debugMode);

		gameState.startGame();
		getGUI().switchToScreen(playGameScreen);
	}

}
