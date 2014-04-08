package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Value;

/**
 * A class of Positions with x- and y-coordinate.
 * @author Jens Claes
 * @author Tomas Fiers
 * @version 1.0
 * 
 * @invar	The position is a valid position
 * 			| isValidPosition(getX(),getY())
 */
@Value
public class Position {
	private final double x,y;
	
	/**
	 * Constructs a new position with given x- and y-coordinates. (in meters).
	 * @param x
	 * 			The x-coordinate of the position of the new worm (in metres)
	 * @param y
	 * 			The y-coordinate of the position of the new worm (in metres)
	 * @post	The x-coordinate of this position equals the given x-coordinate
	 * 			| new.getX() == x
	 * @post	The y-coordinate of this position equals the given y-coordinate
	 * 			| new.getY() == y
	 * @throws IllegalArgumentException
	 * 			Thrown when the coordinates are not a valid position.
	 * 			| !isValidPosition(x,y)
	 */
	public Position(double x, double y) throws IllegalArgumentException{
		if(!isValidPosition(x,y))
			throw new IllegalArgumentException();
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Checks whether the 2 given coordinates are valid coordinates for a position.
	 * 
	 * @param x		The x-coordinate to check
	 * @param y		The y-coordinate to check.
	 * @return	Whether both coordinates are valid coordinates
	 * 			| result == isValidCoordinate(x) && isValidCoordinate(y)
	 */
	public static boolean isValidPosition(double x, double y){
		return isValidCoordinate(x) && isValidCoordinate(y);
	}
	
	/**
	 * Checks whether a position (with x- and y-coordinates) is a valid position.
	 * 
	 * @param pos	The position to check
	 * @return 	Whether the position isn't null and the coordinates of the position are valid.
	 * 			| result == (pos != null) && Position.isValidPosition(pos.getX(), pos.getY())
	 */
	public static boolean isValidPosition(Position pos){
		return (pos != null) && Position.isValidPosition(pos.getX(), pos.getY());
	}
	
	/**
	 * Checks whether the given coordinate is a valid coordinate for a position.
	 * 
	 * @param coordinate	The coordinate to check
	 * @return				Whether the coordinate is a valid number
	 * 						| result == !Double.isNaN(coordinate)
	 */
	public static boolean isValidCoordinate(double coordinate){
		return !Double.isNaN(coordinate);
	}
	
	/**
	 * Returns the x-coordinate of this Position
	 */
	@Basic @Immutable
	public double getX(){
		return x;
	}
	
	/**
	 * Returns the y-coordinate of this Position
	 */
	@Basic @Immutable
	public double getY(){
		return y;
	}
	
	/**
	 * Returns the position (x,y) metres from this position.
	 * 
	 * @param x		The given offset along the x-axis (in metres).
	 * @param y		The given offset along the y-axis (in metres).
	 * @return		The position offset by (x,y) metres.
	 * 				| result == new Position(this.getX()+x,this.getY()+y)
	 * @throws IllegalArgumentException
	 * 				When the x- or y-offset is not a valid number.
	 * 				| Double.isNaN(x) || Double.isNaN(y)
	 */
	public Position offset(double x, double y) throws IllegalArgumentException{
		return new Position(this.getX()+x,this.getY()+y);
	}
	
	/**
	 * Checks whether 2 positions are equal to one-another.
	 * 
	 * @return 	False if the object isn't a position.
	 * 			| if(!(object instanceof Position)) then result == false
	 * @return 	True when both positions have the same coordinates.
	 * 			| result == ((Position)object.getX() == this.getX() && (Position)object.getY() == this.getY())
	 */
	@Override
	public boolean equals(Object object){
		if(!(object instanceof Position))
			return false;
		Position other = (Position)object;
		return other.getX() == this.getX() && other.getY() == this.getY();
	}
	
	@Override
	public int hashCode(){
		return (int) (getX()*getY());
	}
}
