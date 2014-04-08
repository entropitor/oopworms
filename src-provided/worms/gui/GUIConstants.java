package worms.gui;

public final class GUIConstants {

	/**
	 * Default width of the window, when not running in full-screen, in pixels
	 */
	public static final int DEFAULT_WINDOW_WIDTH = 1024;

	/**
	 * Default height of the window, when not running in full-screen, in pixels
	 */
	public static final int DEFAULT_WINDOW_HEIGHT = 768;

	/**
	 * Framerate at which to re-draw the screen, in frames per (real) second
	 */
	public static final int FRAMERATE = 30; // fps

	/**
	 * Time (in worm-seconds) that elapses in 1 real second
	 */
	public static final double TIME_SCALE = 2;

	/**
	 * Scale at which the game is drawn to the screen (in pixels per worm-meter)
	 */
	public static double WORLD_SCALE = 45;

	/**
	 * Minimal angle to turn when pressing the 'turn' key a single time
	 */
	public static final double MIN_TURN_ANGLE = Math.PI / 60.0;

	/**
	 * Angle that is turned per (real) second while keeping the 'turn' keys
	 * pressed.
	 */
	public static final double ANGLE_TURNED_PER_SECOND = Math.PI;

	/**
	 * Number of steps to take when pressing the 'move' key a single time
	 */
	public static final int DEFAULT_NB_STEPS = 1;

	/**
	 * Duration of the move animation for a single step (in worm-seconds)
	 */
	public static final double MOVE_DURATION = 1;

	/**
	 * Fraction by which to expand or shrink a worm's radius when resizing
	 */
	public static final double RESIZE_FACTOR = 0.1;

	/**
	 * Time to display messages on the screen (in real seconds)
	 */
	public static final double MESSAGE_DISPLAY_TIME = 1.0;

	/* disable instantiations */
	private GUIConstants() {
	}
}
