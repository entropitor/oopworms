package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of worms with position, direction, radius and a name.
 * 
 * @author Jens Claes
 * @author Tomas Fiers
 * @version 1.0
 *
 * @invar 	The position of the worm is a valid position. 
 * 		 	| isValidPosition(getXPosition(),getYPosition())
 */
public class Worm {

	/**
	 * Initialize the new worm with the given position, direction, radius and name.
	 * 
	 * @param x
	 * 			The x-position of the worm.
	 * @param y
	 * 			The y-position of the worm.
	 * @param direction
	 * 			The direction of the worm
	 * @param radius
	 * 			The radius of the worm.
	 * @param name
	 * 			The name of the worm.
	 * @throws IllegalArgumentException When the given position is not a valid position.
	 * 									| !isValidPosition(x,y)
	 */
	@Raw
	public Worm(double x, double y, double direction, double radius, String name) throws IllegalArgumentException{
		//TODO complete.
		//TODO complete documentation. (throwables, pre-condities, ...)
		
		//Next check is implicit in setXPosition() and setYPosition()
		//if(!isValidPosition(x,y))
		//		throw new IllegalArgumentException("Illegal Position");

		setXPosition(x);
		setYPosition(y);
	}
	
	/**
	 * Check whether a position (with x- and y-coordinates) is a valid position.
	 * 
	 * @param x The x-position to check.
	 * @param y The y-position to check.
	 * @effect 	Check whether the x-position and y-position both are valid.
	 * 			| isValidXPosition(x) && isValidYPosition(y)
	 */
	public boolean isValidPosition(double x, double y){
		return isValidXPosition(x) && isValidYPosition(y);
	}
	
	/**
	 * Return the x-position of this worm.
	 */
	@Basic @Raw
	public double getXPosition(){
		return this.xPosition;
	}
	
	/**
	 * Check whether the given x-position is a valid x-position.
	 * @param x
	 * 			The x-position to check.
	 * @return 	Whether or not x is a valid number.
	 * 			| !Double.isNan(x)
	 */
	public static boolean isValidXPosition(double x){
		return !Double.isNaN(x);
	}
	
	/**
	 * Set the x-position of this worm.
	 * @param x 
	 * 			The new x-position of this worm.
	 * @post The x-position of this worm equals the given position
	 * 		 | new.getXPosition() == x
	 * @throws IllegalArgumentException The given position is not a valid (x-)position.
	 * 									| !isValidXPosition(x)
	 */
	@Raw
	private void setXPosition(double x) throws IllegalArgumentException{
		if (!isValidXPosition(x))
			throw new IllegalArgumentException("The given x-position is not a valid x-position.");
		this.xPosition = x;
	}
	private double xPosition;
	
	/**
	 * Return the y-position of this worm.
	 */
	@Basic @Raw
	public double getYPosition(){
		return this.yPosition;
	}
	
	/**
	 * Check whether the given y-position is a valid y-position.
	 * @param x
	 * 			The x-position to check.
	 * @return 	Whether or not y is a valid number.
	 * 			| !Double.isNan(y)
	 */
	public static boolean isValidYPosition(double y){
		return !Double.isNaN(y);
	}
	
	/**
	 * Set the y-position of this worm.
	 * @param y
	 * 			The new y-position of this worm.
	 * @post The y-position of this worm equals the given position
	 * 		 | new.getYPosition() == y
	 * @throws IllegalArgumentException The given position is not a valid (y-)position.
	 * 									| !isValidYPosition(y)
	 */
	@Raw
	private void setYPosition(double y){
		if (!isValidYPosition(y))
			throw new IllegalArgumentException("The given y-position is not a valid y-position.");
		this.yPosition = y;
	}
	private double yPosition;
	
}
