package worms.gui.game;

import java.awt.Color;
import java.awt.Shape;

import worms.gui.GUIUtils;
import worms.gui.game.sprites.WormSprite;
import worms.model.ModelException;

public class PlayGameScreenDebugPainter extends PlayGameScreenPainter {

	private static final int LOCATION_MARKER_SIZE = 4;

	public PlayGameScreenDebugPainter(PlayGameScreen screen) {
		super(screen);
	}

	@Override
	protected void paintWorm(WormSprite sprite) {
		super.paintWorm(sprite);

		if (getState().getSelectedWorm() != sprite.getWorm()) {
			drawOutline(sprite);
			drawJumpMarkers(sprite); // also draw for other worms
		}

		drawDirectionLine(sprite);

		drawLocationMarker(sprite);

	}

	@Override
	protected void drawJumpMarkers(WormSprite sprite) {
		try {
			double time = getFacade().getJumpTime(sprite.getWorm());
			double[] prevXY = getFacade().getJumpStep(sprite.getWorm(), 0);
			int n = 1 + (int) (time / JUMP_MARKER_TIME_DISTANCE * 2);
			for (int i = 1; i <= n; i++) {
				double dt = i * time / n;
				double[] xy = getFacade().getJumpStep(sprite.getWorm(), dt);
				if (xy != null && prevXY != null) {
					double jumpX = getScreenX(xy[0]);
					double jumpY = getScreenY(xy[1]);
					graphics.setColor(JUMP_MARKER_COLOR);
					graphics.drawLine((int) getScreenX(prevXY[0]),
							(int) getScreenY(prevXY[1]), (int) jumpX,
							(int) jumpY);
					prevXY = xy;
					drawCrossMarker(jumpX, jumpY, JUMP_MARKER_SIZE,
							JUMP_MARKER_COLOR);
				}
			}
		} catch (ModelException e) {
			// cannot jump; draw nothing
		}
	}

	/**
	 * Draw a marker at the current location of the worm (which is not
	 * necessarily equal to the sprite's location)
	 */
	protected void drawLocationMarker(WormSprite worm) {
		double x = getFacade().getX(worm.getWorm());
		double y = getFacade().getY(worm.getWorm());

		drawCrossMarker(getScreenX(x), getScreenY(y), LOCATION_MARKER_SIZE,
				Color.YELLOW);
	}

	protected void drawOutline(WormSprite sprite) {
		double r = getFacade().getRadius(sprite.getWorm());
		double x = sprite.getCenterX();
		double y = sprite.getCenterY();

		graphics.setColor(Color.YELLOW);
		Shape circle = GUIUtils.circleAt(x, y, GUIUtils.meterToPixels(r));
		graphics.draw(circle);

	}

	protected void drawDirectionLine(WormSprite sprite) {
		double r = GUIUtils.meterToPixels(getFacade().getRadius(
				sprite.getWorm()));
		double x = sprite.getCenterX();
		double y = sprite.getCenterY();
		double direction = getFacade().getOrientation(sprite.getWorm());

		graphics.setColor(Color.YELLOW);
		graphics.drawLine((int) x, (int) y,
				(int) (x + r * Math.cos(direction)),
				(int) (y - r * Math.sin(direction)));
	}

}
