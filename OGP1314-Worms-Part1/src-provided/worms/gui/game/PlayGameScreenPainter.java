package worms.gui.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import worms.gui.GUIUtils;
import worms.gui.GameState;
import worms.gui.game.sprites.WormSprite;
import worms.model.IFacade;
import worms.model.ModelException;

public class PlayGameScreenPainter {

	protected static final Color SELECTION_FILL_COLOR = new Color(0xaa84b6cc,
			true);
	protected static final Color SELECTION_OUTLINE_COLOR = new Color(
			0xaaffffff, true);
	protected static final Color DIRECTION_MARKER_COLOR = new Color(0xcc84b6cc,
			true);
	protected static final Color TURN_ANGLE_MARKER_COLOR = new Color(
			0xcccc84b6, true);
	protected static final Color INVALID_TURN_ANGLE_MARKER_COLOR = Color.RED;
	protected static final Color ACTION_POINTS_COLOR = new Color(0xcc00cc00,
			true);

	protected static final double ACTION_BAR_WIDTH = 30;
	protected static final double ACTION_BAR_HEIGHT = 5;

	protected static final Color HIT_POINTS_COLOR = new Color(0xccff6a00, true);
	protected static final Color BAR_OUTLINE_COLOR = Color.WHITE;
	protected static final Color NAME_BAR_BACKGROUND = new Color(0x40ffffff,
			true);
	protected static final Color NAME_BAR_TEXT = Color.WHITE;

	protected static final double TEXT_BAR_H_MARGIN = 4;
	protected static final double TEXT_BAR_V_MARGIN = 3;
	protected static final double TEXT_BAR_V_OFFSET = 2;

	protected static final Color RENAME_BACKGROUND_COLOR = new Color(
			0x600e53a7, true);
	protected static final Color RENAME_TEXT_COLOR = Color.WHITE;
	protected static final Color MESSAGE_BACKGROUND_COLOR = new Color(
			0x60a7130e, true);
	protected static final Color MESSAGE_TEXT_COLOR = Color.WHITE;
	protected static final Color JUMP_MARKER_COLOR = Color.GRAY;

	protected static final int JUMP_MARKER_SIZE = 1;
	protected static final double JUMP_MARKER_TIME_DISTANCE = 0.1; // worm-seconds
	protected static final double DIRECTION_INDICATOR_SIZE = 10;

	protected Graphics2D graphics;
	private final PlayGameScreen screen;

	public PlayGameScreenPainter(PlayGameScreen screen) {
		this.screen = screen;
	}

	public PlayGameScreen getScreen() {
		return screen;
	}

	protected GameState getState() {
		return screen.getGameState();
	}

	protected IFacade getFacade() {
		return getState().getFacade();
	}

	public void paint(Graphics2D g) {
		this.graphics = g;

		for (WormSprite sprite : getScreen().getSpritesOfType(WormSprite.class)) {
			if (sprite.getWorm() == getState().getSelectedWorm()) {
				drawSelection(sprite);
			}
			paintWorm(sprite);
		}

		this.graphics = null;
	}

	protected double getScreenX(double x) {
		return getScreen().getScreenX(x);
	}

	protected double getScreenY(double y) {
		return getScreen().getScreenY(y);
	}

	protected void paintWorm(WormSprite sprite) {

		sprite.draw(graphics);

		drawName(sprite);

		drawActionBar(sprite);

		if (getState().getSelectedWorm() == sprite.getWorm()) {
			drawDirectionIndicator(sprite);
			drawJumpMarkers(sprite);
		}
	}

	protected void drawName(WormSprite sprite) {
		final double radius = GUIUtils.meterToPixels(getFacade().getRadius(
				sprite.getWorm()));
		String name = getFacade().getName(sprite.getWorm());

		if (name == null) {
			name = "(null)";
		}

		Rectangle2D bounds = graphics.getFontMetrics().getStringBounds(name,
				graphics);
		final double stringWidth = bounds.getWidth();
		final double stringHeight = bounds.getHeight();

		final double x = sprite.getCenterX() - stringWidth / 2;
		final double y = sprite.getCenterY() - radius - TEXT_BAR_V_OFFSET;

		RoundRectangle2D nameBarFill = new RoundRectangle2D.Double(x
				- TEXT_BAR_H_MARGIN, y - stringHeight - TEXT_BAR_V_MARGIN,
				stringWidth + 2 * TEXT_BAR_H_MARGIN, stringHeight + 2
						* TEXT_BAR_V_MARGIN, 5, 5);
		graphics.setColor(NAME_BAR_BACKGROUND);
		graphics.fill(nameBarFill);

		graphics.setColor(NAME_BAR_TEXT);

		graphics.drawString(name, (float) x, (float) (y));
	}

	protected void drawActionBar(WormSprite sprite) {
		double x = sprite.getCenterX();
		double y = sprite.getCenterY();
		double radius = GUIUtils.meterToPixels(getFacade().getRadius(
				sprite.getWorm()));

		double actionPoints = getFacade().getActionPoints(sprite.getWorm());
		double maxActionPoints = getFacade().getMaxActionPoints(
				sprite.getWorm());

		RoundRectangle2D actionBarFill = new RoundRectangle2D.Double(x
				- ACTION_BAR_WIDTH / 2, y + radius, actionPoints
				* ACTION_BAR_WIDTH / maxActionPoints, ACTION_BAR_HEIGHT, 5, 5);
		graphics.setColor(ACTION_POINTS_COLOR);
		graphics.fill(actionBarFill);

		RoundRectangle2D actionBar = new RoundRectangle2D.Double(x
				- ACTION_BAR_WIDTH / 2, y + radius, ACTION_BAR_WIDTH,
				ACTION_BAR_HEIGHT, 5, 5);
		graphics.setColor(BAR_OUTLINE_COLOR);
		graphics.draw(actionBar);
	}

	protected void drawSelection(WormSprite sprite) {
		double x = sprite.getCenterX();
		double y = sprite.getCenterY();
		double r = getFacade().getRadius(sprite.getWorm());

		graphics.setColor(SELECTION_FILL_COLOR);

		Shape circle = GUIUtils.circleAt(x, y, GUIUtils.meterToPixels(r));
		graphics.fill(circle);
	}

	protected void drawDirectionIndicator(WormSprite sprite) {
		double x = sprite.getCenterX();
		double y = sprite.getCenterY();
		double r = GUIUtils.meterToPixels(getFacade().getRadius(
				sprite.getWorm()));
		r += DIRECTION_INDICATOR_SIZE / 2;
		double direction = GUIUtils.restrictDirection(getFacade()
				.getOrientation(sprite.getWorm()));

		graphics.setColor(DIRECTION_MARKER_COLOR);

		Shape directionIndicator = new Ellipse2D.Double(x + r
				* Math.cos(direction) - DIRECTION_INDICATOR_SIZE / 2, y - r
				* Math.sin(direction) - DIRECTION_INDICATOR_SIZE / 2,
				DIRECTION_INDICATOR_SIZE, DIRECTION_INDICATOR_SIZE);
		graphics.fill(directionIndicator);
	}

	void drawTurnAngleIndicator(Graphics2D graphics, WormSprite sprite,
			double angle) {
		double x = sprite.getCenterX();
		double y = sprite.getCenterY();
		double r = GUIUtils.meterToPixels(getFacade().getRadius(
				sprite.getWorm()));
		r += DIRECTION_INDICATOR_SIZE / 2;
		double direction = GUIUtils.restrictDirection(getFacade()
				.getOrientation(sprite.getWorm()) + angle);

		if (getFacade().canTurn(sprite.getWorm(), angle)) {
			graphics.setColor(TURN_ANGLE_MARKER_COLOR);
		} else {
			graphics.setColor(INVALID_TURN_ANGLE_MARKER_COLOR);
		}

		Shape directionIndicator = new Ellipse2D.Double(x + r
				* Math.cos(direction) - DIRECTION_INDICATOR_SIZE / 2, y - r
				* Math.sin(direction) - DIRECTION_INDICATOR_SIZE / 2,
				DIRECTION_INDICATOR_SIZE, DIRECTION_INDICATOR_SIZE);
		graphics.fill(directionIndicator);
	}

	protected void drawJumpMarkers(WormSprite sprite) {
		try {
			double time = getFacade().getJumpTime(sprite.getWorm());
			int n = 1 + (int) (time / JUMP_MARKER_TIME_DISTANCE);
			for (int i = 1; i <= n; i++) {
				double dt = i * time / n;
				double[] xy = getFacade().getJumpStep(sprite.getWorm(), dt);
				if (xy != null) {
					double jumpX = getScreenX(xy[0]);
					double jumpY = getScreenY(xy[1]);
					drawCrossMarker(jumpX, jumpY, JUMP_MARKER_SIZE,
							JUMP_MARKER_COLOR);
				}
			}
		} catch (ModelException e) {
			// cannot jump; draw nothing
		}
	}

	protected void drawCrossMarker(double x, double y, int size, Color color) {
		graphics.setColor(color);
		graphics.drawLine((int) (x - size), (int) y, (int) (x + size), (int) y);
		graphics.drawLine((int) x, (int) (y - size), (int) x, (int) (y + size));
	}

	void paintNameEntry(Graphics2D g, String enteredName) {
		g.setColor(RENAME_BACKGROUND_COLOR);
		g.fillRect(0, 0, getScreen().getScreenWidth(), 120);
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		g.setColor(RENAME_TEXT_COLOR);
		GUIUtils.drawCenteredString(g, "Enter new name for worm: "
				+ enteredName + "\u2502", getScreen().getScreenWidth(), 100);
	}

	void paintMessage(Graphics2D g, String message) {
		g.setColor(MESSAGE_BACKGROUND_COLOR);
		g.fillRect(0, 0, getScreen().getScreenWidth(), 120);
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		g.setColor(MESSAGE_TEXT_COLOR);
		GUIUtils.drawCenteredString(g, message, getScreen().getScreenWidth(),
				100);
	}
}
