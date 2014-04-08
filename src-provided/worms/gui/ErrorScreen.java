package worms.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class ErrorScreen extends Screen {

	private final String message;

	public ErrorScreen(WormsGUI gui, String message) {
		super(gui);
		this.message = message;
	}
	
	@Override
	public void screenStarted() {
	}

	@Override
	protected InputMode<ErrorScreen> createDefaultInputMode() {
		return new InputMode<ErrorScreen>(this, null) {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					getGUI().exit();
				}
			}
		};
	}

	@Override
	protected void paintScreen(Graphics2D g) {
		g.setColor(Color.RED);
		GUIUtils.drawCenteredString((Graphics2D) g, "An error has occurred",
				getScreenWidth(), 20);
		GUIUtils.drawCenteredString((Graphics2D) g, message, getScreenWidth(), 50);
	}

}
