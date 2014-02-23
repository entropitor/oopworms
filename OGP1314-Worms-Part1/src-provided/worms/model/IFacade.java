package worms.model;

/**
 * Implement this interface to connect your code to the user interface.
 * 
 * <ul>
 * <li>Your class for representing a worm should have the name 
 * <code>Worm</code> and be located in the package <code>worms.model</code>.
 * </li>
 * 
 * <li>Connect your class to the user interface by creating a class named
 * <code>Facade</code> in the package <code>worms.model</code> that implements <code>IFacade</code>.
 * <p>
 * The header of that class should look as follows:<br>
 * <code>class Facade implements IFacade { ... }</code>
 * </p>
 * Consult the <a href=
 * "http://docs.oracle.com/javase/tutorial/java/IandI/createinterface.html">
 * Java tutorial</a> for more information on interfaces, if necessary.
 * </li>
 * 
 * <li>Each method defined in the interface <code>IFacade</code> must be implemented
 * by the class <code>Facade</code>. For example, the implementation of
 * <code>getX</code> should call a method of the given <code>worm</code> to
 * retrieve its x-coordinate.</li>
 * 
 * <li>Methods in this interface are allowed to throw only
 * <code>ModelException</code>. No other exception types are allowed. This
 * exception can be thrown only if calling a method of your <code>Worm</code>
 * class with the given parameters would violate a precondition or if the method
 * of your <code>Worm</code> class throws an exception (if so, wrap the
 * exception in a <code>ModelException</code>). <b>ModelException should not be
 * used anywhere outside of your Facade implementation.</b></li>
 * 
 * <li>The rules described above and the documentation described below for each
 * method apply only to the class implementing IFacade. Your class for
 * representing worms should follow the rules described in the assignment.</li>
 * 
 * <li>Do not modify the signatures of the methods defined in this interface.
 * You can however add additional methods, as long as these additional methods
 * do not overload the existing ones. Each additional method must be implemented
 * in your class <code>Facade</code>.</li>
 * 
 * <li>Your class implementing <code>IFacade</code> should offer a default
 * constructor.</li>
 * </ul>
 */
public interface IFacade {

	/**
	 * Create a new worm that is positioned at the given location,
	 * looks in the given direction, has the given radius and the given name.
	 * 
	 * @param x
	 * The x-coordinate of the position of the new worm (in meter)
	 * @param y
	 * The y-coordinate of the position of the new worm (in meter)
	 * @param direction
	 * The direction of the new worm (in radians)
	 * @param radius 
	 * The radius of the new worm (in meter)
	 * @param name
	 * The name of the new worm
	 */
	Worm createWorm(double x, double y, double direction, double radius,
			String name);

	/**
	 * Returns whether or not the given worm can move a given number of steps.
	 */
	boolean canMove(Worm worm, int nbSteps);

	/**
	 * Moves the given worm by the given number of steps.
	 */
	void move(Worm worm, int nbSteps);

	/**
	 * Returns whether or not the given worm can turn by the given angle.
	 */
	boolean canTurn(Worm worm, double angle);

	/**
	 * Turns the given worm by the given angle.
	 */
	void turn(Worm worm, double angle);

	/**
	 * Makes the given worm jump.
	 */
	void jump(Worm worm);

	/**
	 * Returns the total amount of time (in seconds) that a
	 * jump of the given worm would take.
	 */
	double getJumpTime(Worm worm);

	/**
	 * Returns the location on the jump trajectory of the given worm
	 * after a time t.
	 *  
	 * @return An array with two elements,
	 *  with the first element being the x-coordinate and
	 *  the second element the y-coordinate
	 */
	double[] getJumpStep(Worm worm, double t);

	/**
	 * Returns the x-coordinate of the current location of the given worm.
	 */
	double getX(Worm worm);

	/**
	 * Returns the y-coordinate of the current location of the given worm.
	 */
	double getY(Worm worm);

	/**
	 * Returns the current orientation of the given worm (in radians).
	 */
	double getOrientation(Worm worm);

	/**
	 * Returns the radius of the given worm.
	 */
	double getRadius(Worm worm);
	
	/**
	 * Sets the radius of the given worm to the given value.
	 */
	void setRadius(Worm worm, double newRadius);
	
	/**
	 * Returns the minimal radius of the given worm.
	 */
	double getMinimalRadius(Worm worm);

	/**
	 * Returns the current number of action points of the given worm.
	 */
	int getActionPoints(Worm worm);
	
	/**
	 * Returns the maximum number of action points of the given worm.
	 */
	int getMaxActionPoints(Worm worm);
	
	/**
	 * Returns the name the given worm.
	 */
	String getName(Worm worm);

	/**
	 * Renames the given worm.
	 */
	void rename(Worm worm, String newName);

	/**
	 * Returns the mass of the given worm.
	 */
	double getMass(Worm worm);

}
