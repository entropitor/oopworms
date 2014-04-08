package worms.gui.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import worms.gui.AbstractPainter;
import worms.gui.GUIConstants;
import worms.gui.GUIUtils;
import worms.gui.GameState;
import worms.gui.Level;
import worms.gui.game.sprites.FoodSprite;
import worms.gui.game.sprites.ProjectileSprite;
import worms.gui.game.sprites.WormSprite;
import worms.model.IFacade;
import worms.model.ModelException;
import worms.model.World;
import worms.model.Worm;

public class PlayGameScreenPainter extends AbstractPainter<PlayGameScreen> {

	protected static final Color SELECTION_FILL_COLOR = new Color(0xaa84b6cc,
			true);
	protected static final Color SELECTION_IMPASSABLE_FILL_COLOR = new Color(
			0xaacc8484, true);
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
	protected static final Color WEAPON_BAR_BACKGROUND = new Color(0x806666ff,
			true);
	protected static final Color NAME_BAR_TEXT = Color.WHITE;

	protected static final double TEXT_BAR_H_MARGIN = 4;
	protected static final double TEXT_BAR_V_MARGIN = 3;
	protected static final double TEXT_BAR_V_OFFSET = 2;

	protected static final Color RENAME_BACKGROUND_COLOR = new Color(
			0x600e53a7, true);
	protected static final Color RENAME_TEXT_COLOR = Color.WHITE;
	protected static final Color JUMP_MARKER_COLOR = Color.GRAY;

	protected static final int JUMP_MARKER_SIZE = 1;
	protected static final double JUMP_MARKER_TIME_DISTANCE = 0.1; // worm-seconds
	protected static final double DIRECTION_INDICATOR_SIZE = 10;

	protected Graphics2D currentGraphics;
	private Image scaledImage;

	public PlayGameScreenPainter(PlayGameScreen screen) {
		super(screen);
	}

	private void createBackgroundImage() {
		if (scaledImage == null) {
			scaledImage = GUIUtils.scaleTo(getState().getLevel().getMapImage(),
					getScreen().getScreenWidth(),
					getScreen().getScreenHeight(), Image.SCALE_SMOOTH);
		}
	}

	protected GameState getState() {
		return getScreen().getGameState();
	}

	protected IFacade getFacade() {
		return getState().getFacade();
	}

	protected World getWorld() {
		return getState().getWorld();
	}

	protected Level getLevel() {
		return getState().getLevel();
	}

	public void paint(Graphics2D g) {
		this.currentGraphics = g;

		paintLevel();

		for (FoodSprite sprite : getScreen().getSpritesOfType(FoodSprite.class)) {
			paintFood(sprite);
		}

		for (WormSprite sprite : getScreen().getSpritesOfType(WormSprite.class)) {
			if (sprite.getWorm() == getScreen().getSelectedWorm()) {
				drawSelection(sprite);
			}
			paintWorm(sprite);
		}

		for (ProjectileSprite sprite : getScreen().getSpritesOfType(
				ProjectileSprite.class)) {
			paintProjectile(sprite);
		}

		this.currentGraphics = null;
	}

	protected void paintProjectile(ProjectileSprite sprite) {
		sprite.draw(currentGraphics);
	}

	protected void paintFood(FoodSprite sprite) {
		sprite.draw(currentGraphics);
	}

	protected void paintLevel() {
		createBackgroundImage();

		int x = (int) getScreenX(0);
		int y = (int) getScreenY(getLevel().getWorldHeight());
		currentGraphics.drawImage(scaledImage, x, y, null);
	}

	protected double getScreenX(double x) {
		return getScreen().getScreenX(x);
	}

	protected double getScreenY(double y) {
		return getScreen().getScreenY(y);
	}

	protected void paintWorm(WormSprite sprite) {

		sprite.draw(currentGraphics);

		drawName(sprite);

		drawActionBar(sprite);
		drawHitpointsBar(sprite);

		if (getScreen().getSelectedWorm() == sprite.getWorm()) {
			drawDirectionIndicator(sprite);
			drawJumpMarkers(sprite);
		}
	}

	protected void drawName(WormSprite sprite) {
		final double voffset = sprite.getHeight(currentGraphics) / 2;
		String name = getFacade().getName(sprite.getWorm());

		if (name == null) {
			name = "(null)";
		}

		String teamName = null;
		try {
			teamName = getFacade().getTeamName(sprite.getWorm());
		} catch (ModelException e) {
			// no team
		}

		if (teamName != null) {
			name += " (" + teamName + ")";
		}

		Rectangle2D bounds = currentGraphics.getFontMetrics().getStringBounds(
				name, currentGraphics);
		final double stringWidth = bounds.getWidth();
		final double stringHeight = bounds.getHeight();

		final double x = sprite.getCenterX() - stringWidth / 2;
		final double y = sprite.getCenterY() - voffset - TEXT_BAR_V_OFFSET;

		RoundRectangle2D nameBarFill = new RoundRectangle2D.Double(x
				- TEXT_BAR_H_MARGIN, y - stringHeight - TEXT_BAR_V_MARGIN,
				stringWidth + 2 * TEXT_BAR_H_MARGIN, stringHeight + 2
						* TEXT_BAR_V_MARGIN, 5, 5);
		currentGraphics.setColor(NAME_BAR_BACKGROUND);
		currentGraphics.fill(nameBarFill);

		currentGraphics.setColor(NAME_BAR_TEXT);

		currentGraphics.drawString(name, (float) x, (float) (y));
	}

	protected void drawActionBar(WormSprite sprite) {
		double x = sprite.getCenterX();
		double y = sprite.getCenterY();
		double spriteHeight = sprite.getHeight(currentGraphics);

		double actionPoints = getFacade().getActionPoints(sprite.getWorm());
		double maxActionPoints = getFacade().getMaxActionPoints(
				sprite.getWorm());

		RoundRectangle2D actionBarFill = new RoundRectangle2D.Double(x
				- ACTION_BAR_WIDTH / 2, y + spriteHeight / 2, actionPoints
				* ACTION_BAR_WIDTH / maxActionPoints, ACTION_BAR_HEIGHT, 5, 5);
		currentGraphics.setColor(ACTION_POINTS_COLOR);
		currentGraphics.fill(actionBarFill);

		RoundRectangle2D actionBar = new RoundRectangle2D.Double(x
				- ACTION_BAR_WIDTH / 2, y + spriteHeight / 2, ACTION_BAR_WIDTH,
				ACTION_BAR_HEIGHT, 5, 5);
		currentGraphics.setColor(BAR_OUTLINE_COLOR);
		currentGraphics.draw(actionBar);
	}

	protected void drawHitpointsBar(WormSprite sprite) {
		double x = sprite.getCenterX();
		double y = sprite.getCenterY();
		double spriteHeight = sprite.getHeight(currentGraphics);

		double hitPoints = getFacade().getHitPoints(sprite.getWorm());
		double maxHitPoints = getFacade().getMaxHitPoints(sprite.getWorm());

		RoundRectangle2D hitpointsBarFill = new RoundRectangle2D.Double(x
				- ACTION_BAR_WIDTH / 2, y + spriteHeight / 2
				+ ACTION_BAR_HEIGHT, hitPoints * ACTION_BAR_WIDTH
				/ maxHitPoints, ACTION_BAR_HEIGHT, 5, 5);
		currentGraphics.setColor(HIT_POINTS_COLOR);
		currentGraphics.fill(hitpointsBarFill);

		RoundRectangle2D hitpointsBar = new RoundRectangle2D.Double(x
				- ACTION_BAR_WIDTH / 2, y + spriteHeight / 2
				+ ACTION_BAR_HEIGHT, ACTION_BAR_WIDTH, ACTION_BAR_HEIGHT, 5, 5);
		currentGraphics.setColor(BAR_OUTLINE_COLOR);
		currentGraphics.draw(hitpointsBar);
	}

	protected void drawSelection(WormSprite sprite) {
		double x = sprite.getCenterX();
		double y = sprite.getCenterY();
		double spriteHeight = Math.max(sprite.getWidth(currentGraphics),
				sprite.getHeight(currentGraphics));

		Worm worm = sprite.getWorm();
		if (getFacade().isImpassable(getWorld(), getFacade().getX(worm),
				getFacade().getY(worm), getFacade().getRadius(worm))) {
			currentGraphics.setColor(SELECTION_IMPASSABLE_FILL_COLOR);
		} else {
			currentGraphics.setColor(SELECTION_FILL_COLOR);
		}

		Shape circle = GUIUtils.circleAt(x, y, spriteHeight / 2);
		currentGraphics.fill(circle);
	}

	protected void drawDirectionIndicator(WormSprite sprite) {
		double x = sprite.getCenterX();
		double y = sprite.getCenterY();
		double distance = Math.max(sprite.getWidth(currentGraphics),
				sprite.getHeight(currentGraphics)) / 2;
		distance += DIRECTION_INDICATOR_SIZE / 2;
		double direction = GUIUtils.restrictDirection(getFacade()
				.getOrientation(sprite.getWorm()));

		currentGraphics.setColor(DIRECTION_MARKER_COLOR);

		Shape directionIndicator = new Ellipse2D.Double(x + distance
				* Math.cos(direction) - DIRECTION_INDICATOR_SIZE / 2,
				y - distance * Math.sin(direction) - DIRECTION_INDICATOR_SIZE
						/ 2, DIRECTION_INDICATOR_SIZE, DIRECTION_INDICATOR_SIZE);
		currentGraphics.fill(directionIndicator);
	}

	void drawTurnAngleIndicator(Graphics2D graphics, WormSprite sprite,
			double angle) {
		if (sprite == null) {
			return;
		}
		double x = sprite.getCenterX();
		double y = sprite.getCenterY();
		double distance = Math.max(sprite.getWidth(graphics),
				sprite.getHeight(graphics)) / 2;
		distance += DIRECTION_INDICATOR_SIZE / 2;
		double direction = GUIUtils.restrictDirection(getFacade()
				.getOrientation(sprite.getWorm()) + angle);

		if (getFacade().canTurn(sprite.getWorm(), angle)) {
			graphics.setColor(TURN_ANGLE_MARKER_COLOR);
		} else {
			graphics.setColor(INVALID_TURN_ANGLE_MARKER_COLOR);
		}

		Shape directionIndicator = new Ellipse2D.Double(x + distance
				* Math.cos(direction) - DIRECTION_INDICATOR_SIZE / 2,
				y - distance * Math.sin(direction) - DIRECTION_INDICATOR_SIZE
						/ 2, DIRECTION_INDICATOR_SIZE, DIRECTION_INDICATOR_SIZE);
		graphics.fill(directionIndicator);
	}

	protected void drawJumpMarkers(WormSprite sprite) {
		try {
			double time = getFacade().getJumpTime(sprite.getWorm(),
					GUIConstants.JUMP_TIME_STEP);
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
		currentGraphics.setColor(color);
		currentGraphics.drawLine((int) (x - size), (int) y, (int) (x + size),
				(int) y);
		currentGraphics.drawLine((int) x, (int) (y - size), (int) x,
				(int) (y + size));
	}

	void paintTextEntry(Graphics2D g, String message, String enteredText) {
		g.setColor(RENAME_BACKGROUND_COLOR);
		g.fillRect(0, 0, getScreen().getScreenWidth(), 120);
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		g.setColor(RENAME_TEXT_COLOR);
		GUIUtils.drawCenteredString(g, message + enteredText + "\u2502",
				getScreen().getScreenWidth(), 100);
	}

	public void drawShootingInfo(Graphics2D currentGraphics, WormSprite sprite,
			double propulsionFraction) {
		String weaponName = getFacade().getSelectedWeapon(sprite.getWorm());

		if (weaponName == null) {
			// no weapon selected, so nothing to draw
			return;
		}

		double x = sprite.getCenterX();
		double y = sprite.getCenterY();
		double hoffset = sprite.getWidth(currentGraphics);
		double voffset = sprite.getHeight(currentGraphics);

		double PROPULSION_BAR_HEIGHT = ACTION_BAR_WIDTH;
		double PROPULSION_BAR_WIDTH = ACTION_BAR_HEIGHT;

		double fillHeight = propulsionFraction * PROPULSION_BAR_HEIGHT;

		RoundRectangle2D barFill = new RoundRectangle2D.Double(x + hoffset
				+ PROPULSION_BAR_WIDTH / 2, y - PROPULSION_BAR_HEIGHT / 2
				+ (PROPULSION_BAR_HEIGHT - fillHeight), PROPULSION_BAR_WIDTH,
				fillHeight, 5, 5);
		currentGraphics.setColor(ACTION_POINTS_COLOR);
		currentGraphics.fill(barFill);

		RoundRectangle2D actionBar = new RoundRectangle2D.Double(x + hoffset
				+ PROPULSION_BAR_WIDTH / 2, y - PROPULSION_BAR_HEIGHT / 2,
				PROPULSION_BAR_WIDTH, PROPULSION_BAR_HEIGHT, 5, 5);
		currentGraphics.setColor(BAR_OUTLINE_COLOR);
		currentGraphics.draw(actionBar);

		Rectangle2D bounds = currentGraphics.getFontMetrics().getStringBounds(
				weaponName, currentGraphics);
		final double stringWidth = bounds.getWidth();
		final double stringHeight = bounds.getHeight();

		x = sprite.getCenterX() - stringWidth / 2;
		y = sprite.getCenterY() + voffset + TEXT_BAR_V_OFFSET;

		RoundRectangle2D nameBarFill = new RoundRectangle2D.Double(x
				- TEXT_BAR_H_MARGIN, y + TEXT_BAR_V_MARGIN, stringWidth + 2
				* TEXT_BAR_H_MARGIN, stringHeight + 2 * TEXT_BAR_V_MARGIN, 5, 5);
		currentGraphics.setColor(WEAPON_BAR_BACKGROUND);
		currentGraphics.fill(nameBarFill);

		currentGraphics.setColor(NAME_BAR_TEXT);

		currentGraphics.drawString(weaponName, (float) x,
				(float) (y + stringHeight));

	}

	public void paintInstructions(Graphics2D g, String message) {
		int lineHeight = 25;
		Font oldFont = g.getFont();
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 2 * lineHeight / 3));

		StringTokenizer tok = new StringTokenizer(message, "\n");
		int nbLines = tok.countTokens();
		List<String> lines = new ArrayList<String>(nbLines);
		while (tok.hasMoreTokens()) {
			lines.add(tok.nextToken());
		}

		int maxWidth = 0;
		for (String line : lines) {
			Rectangle2D bounds = g.getFontMetrics().getStringBounds(line, g);
			maxWidth = Math.max(maxWidth, (int) (bounds.getWidth() + 0.5));
		}

		int width = 2 * lineHeight + maxWidth;
		int height = 2 * lineHeight + lineHeight * nbLines;
		int top = 0;
		int left = 0;

		g.setColor(new Color(0xa0565656, true));
		g.fillRect(left, top, width, height);
		g.setColor(Color.WHITE);

		int y = top + 2 * lineHeight;
		for (String line : lines) {
			g.drawString(line, left + lineHeight, y);
			y += lineHeight;
		}

		g.setFont(oldFont);
	}
}
