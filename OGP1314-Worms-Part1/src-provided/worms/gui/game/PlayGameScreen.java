package worms.gui.game;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import javax.swing.Timer;

import worms.gui.GUIConstants;
import worms.gui.GUIUtils;
import worms.gui.GameState;
import worms.gui.Screen;
import worms.gui.WormsGUI;
import worms.gui.game.commands.Jump;
import worms.gui.game.commands.Move;
import worms.gui.game.commands.Rename;
import worms.gui.game.commands.Resize;
import worms.gui.game.commands.Turn;
import worms.gui.game.sprites.Sprite;
import worms.gui.game.sprites.WormSprite;
import worms.model.IFacade;
import worms.model.Worm;

public class PlayGameScreen extends Screen {

	private class DefaultInputMode extends InputMode {

		@Override
		public void mouseClicked(MouseEvent e) {
			Point point = e.getPoint();
			for (WormSprite sprite : getSpritesOfType(WormSprite.class)) {
				Worm worm = sprite.getWorm();
				double[] xy = sprite.getCenterLocation();
				double radius = GUIUtils.meterToPixels(getFacade().getRadius(
						worm));
				if (GUIUtils.distance(xy[0], xy[1], point.getX(), point.getY()) <= radius) {
					getGameState().selectWorm(worm);
					return;
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			switchInputMode(new TurningMode());
			getCurrentInputMode().mouseDragged(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_RIGHT:
				switchInputMode(new TurningMode());
				getCurrentInputMode().keyPressed(e);
				break;
			case KeyEvent.VK_UP:
				move(GUIConstants.DEFAULT_NB_STEPS);
				break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				getGUI().exit();
				break;
			case KeyEvent.VK_TAB:
				getGameState().selectNextWorm();
				break;
			case KeyEvent.VK_J:
				jump();
				break;
			case KeyEvent.VK_N:
				switchInputMode(new EnteringNameMode());
				break;
			case KeyEvent.VK_PLUS:
			case KeyEvent.VK_ADD:
				resizeWorm(true);
				break;
			case KeyEvent.VK_MINUS:
			case KeyEvent.VK_SUBTRACT:
				resizeWorm(false);
				break;
			default:
				// System.out.println("Unhandled key: " + e);
			}
		}

	}

	private class EnteringNameMode extends InputMode {
		private String enteredName = "";

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				changeName(enteredName);
				switchInputMode(new DefaultInputMode());
			} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				switchInputMode(new DefaultInputMode());
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			if (e.getKeyChar() == '\b') {
				enteredName = enteredName.substring(0,
						Math.max(0, enteredName.length() - 1));
			} else if (!Character.isISOControl(e.getKeyChar())
					&& e.getKeyChar() != KeyEvent.CHAR_UNDEFINED) {
				enteredName += e.getKeyChar();
			}
			repaint();
		}

		@Override
		public void paintOverlay(Graphics2D g) {
			super.paintOverlay(g);
			painter.paintNameEntry(g, enteredName);
		}

	}

	private class TurningMode extends InputMode {
		private double angle = 0;

		private long pressedSince = 0; // 0 if not turning
		private boolean clockwise;

		private void startTurning(boolean clockwise) {
			if (!isTurning()) {
				pressedSince = System.currentTimeMillis();
				this.clockwise = clockwise;
			}
		}

		private void stopTurning() {
			angle = getCurrentAngle();
			pressedSince = 0;
		}

		private boolean isTurning() {
			return pressedSince != 0;
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			double[] wormXY = getWormSprite(getSelectedWorm())
					.getCenterLocation();
			double currentOrientation = getFacade().getOrientation(
					getSelectedWorm());
			this.angle = Math.PI
					- currentOrientation
					+ Math.atan2((e.getY() - wormXY[1]), (wormXY[0] - e.getX()));
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			finishTurn();
		}

		private void finishTurn() {
			if (angle != 0) {
				turn(angle);
				switchInputMode(new DefaultInputMode());
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				switchInputMode(new DefaultInputMode());
				break;
			case KeyEvent.VK_ENTER:
				finishTurn();
				break;
			case KeyEvent.VK_LEFT: // no-break
			case KeyEvent.VK_RIGHT:
				stopTurning();
				break;
			}
		}

		private double getCurrentAngle() {
			double delta = 0;
			if (isTurning()) {
				long now = System.currentTimeMillis();
				delta = Math.max(GUIConstants.MIN_TURN_ANGLE,
						(now - pressedSince) / 1000.0
								* GUIConstants.ANGLE_TURNED_PER_SECOND);
				if (clockwise) {
					delta = -delta;
				}
				return GUIUtils.restrictAngle(angle + delta, -Math.PI);
			} else {
				return angle;
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_RIGHT:
				startTurning(true);
				break;
			case KeyEvent.VK_LEFT:
				startTurning(false);
				break;
			}
		}

		@Override
		public void paintOverlay(Graphics2D g) {
			super.paintOverlay(g);
			painter.drawTurnAngleIndicator(g, getWormSprite(getSelectedWorm()),
					getCurrentAngle());
		}
	}

	private final PlayGameScreenPainter painter;
	private final GameState gameState;

	private final Set<Sprite> sprites = new HashSet<Sprite>();

	private static class MessageDisplay {
		private LinkedList<String> messages = new LinkedList<String>();
		private long currentMessageDisplayedSince;

		public MessageDisplay() {
		}

		public void addMessage(String message) {
			if (messages.isEmpty() || !messages.getLast().equals(message))
				this.messages.add(message);
		}

		private boolean isDisplayingMessage() {
			return currentMessageDisplayedSince > 0;
		}

		private double currentDisplayTime() {
			return (System.currentTimeMillis() - currentMessageDisplayedSince) / 1000.0;
		}

		private String currentMessage() {
			return messages.peek();
		}

		private void gotoNextMessage() {
			if (!messages.isEmpty()) {
				currentMessageDisplayedSince = System.currentTimeMillis();
			} else {
				currentMessageDisplayedSince = 0;
			}
		}

		public String getMessage() {
			if (isDisplayingMessage()) {
				if (currentDisplayTime() >= GUIConstants.MESSAGE_DISPLAY_TIME) {
					messages.remove();
					gotoNextMessage();
				}
			} else {
				gotoNextMessage();
			}
			return currentMessage();
		}
	}

	private MessageDisplay messageDisplay = new MessageDisplay();

	public PlayGameScreen(WormsGUI gui, GameState state) {
		super(gui);
		this.gameState = state;
		this.painter = createPainter();
	}

	protected InputMode createDefaultInputMode() {
		return new DefaultInputMode();
	}

	@Override
	protected void screenStarted() {
		createSprites();
		runGameLoop();
	}

	private void runGameLoop() {
		final AtomicLong lastUpdateTimestamp = new AtomicLong();

		final Timer timer = new Timer(1000 / GUIConstants.FRAMERATE,
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						long now = System.currentTimeMillis();
						long delta = now - lastUpdateTimestamp.getAndSet(now);
						double dt = delta / 1000.0 * GUIConstants.TIME_SCALE;
						gameState.evolve(dt);
						repaint();
					}
				});
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				timer.stop();
				e.printStackTrace();
				getGUI().showError(
						e.getClass().getName() + ": " + e.getMessage());
			}
		});
		lastUpdateTimestamp.set(System.currentTimeMillis());
		timer.start();
	}

	public void createSprites() {
		for (Worm worm : getGameState().getWorms()) {
			WormSprite sprite = createWormSprite(worm);
			sprites.add(sprite);
		}
	}

	private WormSprite createWormSprite(Worm worm) {
		double x = getScreenX(getFacade().getX(worm));
		double y = getScreenY(getFacade().getY(worm));
		WormSprite sprite = new WormSprite(worm);
		sprite.setCenterLocation(x, y);
		sprite.setDirection(getFacade().getOrientation(worm));
		sprite.setRadius(getFacade().getRadius(worm));
		return sprite;
	}

	public GameState getGameState() {
		return gameState;
	}

	protected IFacade getFacade() {
		return getGameState().getFacade();
	}

	protected PlayGameScreenPainter createPainter() {
		return new PlayGameScreenPainter(this);
	}

	public <T extends Sprite> Set<T> getSpritesOfType(Class<T> type) {
		Set<T> result = new HashSet<T>();
		for (Sprite sprite : sprites) {
			if (type.isInstance(sprite)) {
				result.add(type.cast(sprite));
			}
		}
		return result;
	}

	public WormSprite getWormSprite(Worm worm) {
		for (WormSprite sprite : getSpritesOfType(WormSprite.class)) {
			if (worm != null && worm.equals(sprite.getWorm())) {
				return sprite;
			}
		}
		return null;
	}

	public void move(int nbSteps) {
		Worm worm = getSelectedWorm();

		if (worm != null) {
			getGameState().enqueueCommand(
					new Move(getFacade(), worm, nbSteps, this));
		}
	}

	public void jump() {
		Worm worm = getSelectedWorm();
		if (worm != null) {
			getGameState().enqueueCommand(new Jump(getFacade(), worm, this));
		}

	}

	public void turn(double angle) {
		Worm worm = getSelectedWorm();
		angle = GUIUtils.restrictAngle(angle, -Math.PI);

		if (worm != null) {
			getGameState().enqueueCommand(
					new Turn(getFacade(), worm, angle, this));
		}
	}

	public void changeName(String newName) {
		Worm worm = getSelectedWorm();

		if (worm != null) {
			getGameState().enqueueCommand(
					new Rename(getFacade(), worm, newName, this));
		}
	}

	public void resizeWorm(boolean makeLarger) {
		Worm worm = getSelectedWorm();

		if (worm != null) {
			double factor = 1.0 + (makeLarger ? GUIConstants.RESIZE_FACTOR
					: -GUIConstants.RESIZE_FACTOR);
			getGameState().enqueueCommand(
					new Resize(getFacade(), worm, factor, this));
		}
	}

	private Worm getSelectedWorm() {
		return getGameState().getSelectedWorm();
	}

	@Override
	protected void paintScreen(Graphics2D g) {
		painter.paint(g);
		String message = messageDisplay.getMessage();
		if (message != null) {
			painter.paintMessage(g, message);
		}
	}

	public void addMessage(String message) {
		messageDisplay.addMessage(message);
	}

	public static PlayGameScreen create(WormsGUI gui, GameState gameState,
			boolean debugMode) {
		if (!debugMode) {
			return new PlayGameScreen(gui, gameState);
		} else {
			return new PlayGameScreen(gui, gameState) {
				@Override
				protected PlayGameScreenPainter createPainter() {
					return new PlayGameScreenDebugPainter(this);
				}
			};
		}
	}

}
