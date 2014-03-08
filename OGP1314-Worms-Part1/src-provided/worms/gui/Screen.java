package worms.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public abstract class Screen {

	protected class InputMode implements KeyListener, MouseListener,
			MouseMotionListener {

		public void paintOverlay(Graphics2D g) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseDragged(MouseEvent e) {
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}
	}

	private final WormsGUI gui;
	private final Component contents;

	protected Screen(WormsGUI gui) {
		this.gui = gui;
		this.contents = createContents();
		contents.setFocusable(true);
		contents.setFocusTraversalKeysEnabled(false);
		switchInputMode(createDefaultInputMode());
	}

	public Component getPanel() {
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

				getCurrentInputMode().paintOverlay(graphics);
			}
		};
		result.setBackground(Color.BLACK);
		return result;
	}

	public WormsGUI getGUI() {
		return gui;
	}

	protected abstract InputMode createDefaultInputMode();

	private InputMode currentInputMode;

	protected InputMode getCurrentInputMode() {
		return currentInputMode;
	}

	protected void switchInputMode(InputMode newMode) {
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

	public final void startScreen() {
		getPanel().requestFocusInWindow();
		screenStarted();
	}
	
	protected abstract void screenStarted();

	public int getScreenHeight() {
		return getPanel().getHeight();
	}

	public int getScreenWidth() {
		return getPanel().getWidth();
	}

	public void repaint() {
		getPanel().repaint();
	}

	public double getScreenX(double x) {
		return getScreenWidth() / 2.0 + GUIUtils.meterToPixels(x);
	}

	public double getLogicalX(double screenX) {
		return GUIUtils.pixelToMeter(screenX - getScreenWidth() / 2.0);
	}

	public double getScreenY(double y) {
		return getScreenHeight() / 2.0 - GUIUtils.meterToPixels(y);
	}

	public double getLogicalY(double screenY) {
		return GUIUtils.pixelToMeter(getScreenHeight() / 2.0 - screenY);
	}

}
