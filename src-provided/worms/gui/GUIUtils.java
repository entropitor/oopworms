package worms.gui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import worms.util.Util;

public class GUIUtils {

	public static Ellipse2D.Double circleAt(double centerX, double centerY,
			double r) {
		return new Ellipse2D.Double(centerX - r, centerY - r, 2 * r, 2 * r);
	}

	public static void drawCenteredString(Graphics2D g2d, String text,
			double width, double y) {
		Rectangle2D bounds = g2d.getFontMetrics().getStringBounds(text, g2d);
		g2d.drawString(text, (int) (width / 2 - bounds.getCenterX()), (int) y);
	}

	public static double restrictDirection(double direction) {
		return restrictAngle(direction, 0);
	}

	/**
	 * Restrict angle to [min, min+2pi)
	 */
	public static double restrictAngle(double angle, double min) {
		while (angle < min) {
			angle += 2 * Math.PI;
		}
		double max = min + 2 * Math.PI;
		while (Util.fuzzyGreaterThanOrEqualTo(angle, max)) {
			angle -= 2 * Math.PI;
		}
		return angle;
	}

	public static double distance(double x1, double y1, double x2, double y2) {
		double dx = x1 - x2;
		double dy = y1 - y2;
		return Math.sqrt(dx * dx + dy * dy);
	}

	public static Image scaleTo(BufferedImage image, int screenWidth,
			int screenHeight, int hints) {
		double ratio = Math.min((double) screenHeight / image.getHeight(),
				(double) screenWidth / image.getWidth());
		return image.getScaledInstance((int) (ratio * image.getWidth()),
				(int) (ratio * image.getHeight()), hints);
	}
}
