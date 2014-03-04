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
 * 		 	| isValidPosition(getXCoordinate(),getYCoordinate())
 * @invar	The direction of the worm is a valid direction.
 * 			| isValidDirection(getDirection())
 * @invar	The name of the worm is a valid name.
 * 			| isValidName(getName())
 */
public class Worm {

	/**
	 * Creates a new worm that is positioned at the given location, looks in the given direction, has the given radius and the given name.
	 * 
	 * @param x
	 * 			The x-coordinate of the position of the new worm (in meter)
	 * @param y
	 * 			The y-coordinate of the position of the new worm (in meter)
	 * @param direction
	 * 			The direction of the new worm (in radians)
	 * @param radius
	 * 			The radius of the new worm (in meter)
	 * @param name
	 * 			The name of the new worm
	 * @pre		The given direction is a valid direction.
	 * 			| isValidDirection(direction)
	 * @post 	The x-coordinate of the position of the new worm equals the given x-coordinate
	 * 			| new.getXCoordinate() == x
	 * @post 	The y-coordinate of the position of the new worm equals the given y-coordinate
	 * 			| new.getYCoordinate() == y
	 * @post	The direction of the new worm equals the given direction
	 * 			| new.getDirection() == direction
	 * @post	The name of the new worm equals the given name
	 * 			| new.getName() == name
	 * @throws IllegalArgumentException 
	 * 			When the given position is not a valid position.
	 * 			| !isValidPosition(x,y)
	 * @throws IllegalArgumentException
	 * 			When the given name is not a valid name.
	 * 			| !isValidName(name)
	 */
	@Raw
	public Worm(double x, double y, double direction, double radius, String name) throws IllegalArgumentException{
		//TODO complete.
		//TODO complete documentation. (throwables, pre-condities, ...)
		
		//Next check is implicit in setXPosition() and setYPosition()
		//if(!isValidPosition(x,y))
		//		throw new IllegalArgumentException("Illegal Position");

		//Implicit in setDirection:
		//assert isValidDirection(direction);
		
		//Implicit in setName:
		//if(!isValidName(name))
		//		throw new IllegalArgumentException("Illegal Name");

		setXCoordinate(x);
		setYCoordinate(y);

		setDirection(direction);
		
		setName(name);
	}
	
	/**
	 * Checks whether a position (with x- and y-coordinates) is a valid position.
	 * 
	 * @param x The x-coordinate of the location to check.
	 * @param y The y-coordinate of the location to check.
	 * @effect 	Check whether the x-coordinate and y-coordinate are both valid.
	 * 			| isValidXPCoordinate(x) && isValidYCoordinate(y)
	 */
	public static boolean isValidPosition(double x, double y){
		return isValidXCoordinate(x) && isValidYCoordinate(y);
	}
	
	/**
	 * Returns the x-coordinate of the current location of this worm.
	 */
	@Basic @Raw
	public double getXCoordinate(){
		return this.xCoordinate;
	}
	
	/**
	 * Checks whether the given x-coordinate is a valid x-coordinate.
	 * 
	 * @param x
	 * 			The x-coordinate to check.
	 * @return 	Whether or not x is a valid number.
	 * 			| result == !Double.isNan(x)
	 */
	public static boolean isValidXCoordinate(double x){
		return !Double.isNaN(x);
	}
	
	/**
	 * Sets the x-coordinate of the location of this worm.
	 * 
	 * @param x 
	 * 			The new x-coordinate of this worm.
	 * @post The x-coordinate of this worm equals the given x-coordinate
	 * 		 | new.getXCoordinate() == x
	 * @throws IllegalArgumentException The given position is not a valid x-coordinate.
	 * 									| !isValidXCoordinate(x)
	 */
	@Raw
	private void setXCoordinate(double x) throws IllegalArgumentException{
		if (!isValidXCoordinate(x))
			throw new IllegalArgumentException("The given x-coordinate is not a valid x-coordinate.");
		this.xCoordinate = x;
	}
	private double xCoordinate;
	
	/**
	 * Returns the y-coordinate of the current location of this worm.
	 */
	@Basic @Raw
	public double getYCoordinate(){
		return this.yCoordinate;
	}
	
	/**
	 * Checks whether the given y-coordinate is a valid y-coordinate.
	 * 
	 * @param y
	 * 			The y-coordinate to check.
	 * @return 	Whether or not y is a valid number.
	 * 			| !Double.isNan(y)
	 */
	public static boolean isValidYCoordinate(double y){
		return !Double.isNaN(y);
	}
	
	/**
	 * Sets the y-coordinate of the location of this worm.
	 * 
	 * @param y
	 * 			The new y-coordinate of the location of this worm.
	 * @post The y-coordinate of this worm equals the given y-coordinate
	 * 		 | new.getYCoordinate() == y
	 * @throws IllegalArgumentException The given position is not a valid y-coordinate.
	 * 									| !isValidYCoordinate(y)
	 */
	@Raw
	private void setYCoordinate(double y) throws IllegalArgumentException{
		if (!isValidYCoordinate(y))
			throw new IllegalArgumentException("The given y-coordinate is not a valid y-coordinate.");
		this.yCoordinate = y;
	}
	private double yCoordinate;
	
	/**
	 * Returns the direction of this worm (in radians).
	 */
	@Basic @Raw
	public double getDirection(){
		return direction;
	}
	
	/**
	 * Checks whether the given direction is a valid direction.
	 * 
	 * @param direction The direction to check
	 * @return 	Whether or not direction is a valid number between 0 and 2*Math.PI
	 * 			| result == (!double.isNaN(direction) && 0<= direction && direction < 2*Math.PI)
	 */
	public static boolean isValidDirection(double direction){
		if(Double.isNaN(direction))
			return false;
		return 0 <= direction && direction < 2*Math.PI;
	}
	
	/**
	 * Sets the direction of this worm to the given direction.
	 * 
	 * @param direction
	 * 			The new direction of the worm.
	 * @pre 	The given direction is a valid direction.
	 * 			| isValidDirection(direction)
	 * @post 	The new direction of this worm equals the given direction.
	 * 			| new.getDirection() == direction
	 */
	@Raw
	private void setDirection(double direction){
		assert isValidDirection(direction);
		this.direction = direction;
	}
	private double direction;
	
	
	/**
	 * Returns the name of this worm.
	 */
	@Basic @Raw
	public String getName(){
		return this.name;
	}
	
	/**
	 * Checks whether or not the given name is a valid name for a worm.
	 * 
	 * @param name The name to check.
	 * @return	Whether or not the name is at least 2 characters long, starts with an uppercase letter and only contains valid characters.
	 * 			|	result == (name.length() >= 2 && Character.isUpperCase(name.charAt(0)) 
	 * 			|				&& for all Character c in name:
	 * 			|						isValidCharacterForName(c))
	 */
	public static boolean isValidName(String name){
		if(name.length() < 2)
			return false;
		if(!Character.isUpperCase(name.charAt(0)))
			return false;
		for(Character c : name.toCharArray()){
			if(!isValidCharacterForName(c))
				return false;
		}
		return true;
	}
	
	/**
	 * Checks whether or not the given character is a valid character to be used in a name for a worm.
	 * 
	 * <p>In the current version, the valid characters consist of (latin) letters (both uppercase and lowercase), quotes (both single and double) and spaces.
	 * In short the list looks like this: [A-Za-z'" ].
	 * 
	 * @param c The character to check.
	 * @return	Whether or not the character is a (latin) letter, quote or space.
	 * 			| result == (c == 32 || c == 34 || c == 39 || (65 <= c && c <= 90) || (97 <= c && c <= 122))
	 */
	public static boolean isValidCharacterForName(char c){
		//Space is allowed.
		if(c == 32)
			return true;
		//Single and double quotes are allowed.
		if(c == 34 || c == 39)
			return true;
		
		//Uppercase characters are allowed.
		if(65 <= c && c <= 90)
			return true;
		//Lowercase characters are allowed.
		if(97 <= c && c <= 122)
			return true;
		
		//All the rest isn't allowed (at the moment).
		return false;
	}
	
	/**
	 * Sets the name for this worm.
	 * 
	 * @param name The name to set
	 * @throws IllegalArgumentException 
	 * 			When the given name is not a valid name.
	 * 			| !isValidName(name)
	 * @post 	The name for this worm equals the given name.
	 * 			| new.getName() == name
	 */
	@Raw
	public void setName(String name) throws IllegalArgumentException{
		if(!isValidName(name))
			throw new IllegalArgumentException("Illegal Name");
		this.name = name;
	}
	private String name;
}
