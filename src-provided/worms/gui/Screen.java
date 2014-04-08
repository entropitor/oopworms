package worms.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import worms.gui.messages.Message;
import worms.gui.messages.MessageDisplay;
import worms.gui.messages.MessagePainter;
import worms.gui.messages.MessageType;

public abstract class Screen {

	private MessageDisplay messageDisplay = new MessageDisplay();

	private final WormsGUI gui;
	private final Component contents;
	private final MessagePainter messagePainter;

	protected Screen(WormsGUI gui) {
		this.gui = gui;
		
		this.contents = createContents();
		contents.setFocusable(true);
		contents.setFocusTraversalKeysEnabled(false);
		
		this.messagePainter = createMessagePainter();
		
		switchInputMode(createDefaultInputMode());
	}

	protected MessagePainter createMessagePainter() {
		return new MessagePainter(this);
	}

	public Component getContents() {
		return contents;
	}

	protected Component createContents() {
		@SuppressWarnings("serial")
		Component result = new JPanel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				Graphics2D graphics = (Graphics2D) g;
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);

				Screen.this.paintScreen(graphics);

				Screen.this.paintMessage(graphics);

				getCurrentInputMode().paintOverlay(graphics);
			}
		};
		result.setBackground(Color.BLACK);
		return result;
	}

	public WormsGUI getGUI() {
		return gui;
	}

	protected abstract InputMode<? extends Screen> createDefaultInputMode();

	private InputMode<? extends Screen> currentInputMode;

	public InputMode<? extends Screen> getCurrentInputMode() {
		return currentInputMode;
	}

	public <ST extends Screen> void switchInputMode(InputMode<ST> newMode) {
		if (currentInputMode != null) {
			contents.removeKeyListener(currentInputMode);
			contents.removeMouseListener(currentInputMode);
			contents.removeMouseMotionListener(currentInputMode);
		}
		currentInputMode = newMode;
		if (newMode != null) {
			contents.addKeyListener(newMode);
			contents.addMouseListener(newMode);
			contents.addMouseMotionListener(newMode);
		}
	}

	protected void paintScreen(Graphics2D g) {
	}

	protected void paintMessage(Graphics2D g) {
		Message message = messageDisplay.getMessage();
		if (message != null && messagePainter != null) {
			messagePainter.paintMessage(g, message);
		}
	}

	public void addMessage(String message, MessageType type) {
		messageDisplay.addMessage(message, type);
		getContents().repaint();
	}

	public void screenStarted() {
	}

	public int getScreenHeight() {
		return getContents().getHeight();
	}

	public int getScreenWidth() {
		return getContents().getWidth();
	}

	public void repaint() {
		getContents().repaint();
	}

	public void screenStopped() {
		switchInputMode(null);
	}


}
