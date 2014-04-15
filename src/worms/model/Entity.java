package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of objects taking up space at a certain position in a game world.
 * 
 * @invar 	The position of the worm is a valid position. 
 * 		 	| Position.isValidPosition(getPosition())
 * @invar	The radius of the worm is a valid radius for this worm.
 * 			| canHaveAsRadius(getRadius())
 */
public abstract class Entity {
	/**
	 * Returns the x-coordinate of the current location of this entity (in metres).
	 */
	@Raw
	public double getXCoordinate(){
		return getPosition().getX();
	}
	
	/**
	 * Returns the y-coordinate of the current location of this entity (in metres).
	 */
	@Raw
	public double getYCoordinate(){
		return getPosition().getY();
	}
	
	/**
	 * Sets the position for this entity.
	 * 
	 * @param position	The new position to set.
	 * @post	The new position equals the given position.
	 * 			| new.getPosition().equals(position)
	 * @throws IllegalArgumentException
	 * 			Thrown when the given position isn't a valid position.
	 * 			| !Position.isValidPosition(position)
	 */
	@Raw
	protected void setPosition(Position position) throws IllegalArgumentException{
		if(!Position.isValidPosition(position))
			throw new IllegalArgumentException();
		this.position = position;
	}
	
	/**
	 * Returns the position of this entity.
	 */
	@Raw @Basic
	protected Position getPosition(){
		return position;
	}
	private Position position;
	
	/**
	 * Returns the radius of this entity (in metres).
	 */
	@Basic @Raw
	public abstract double getRadius();
	
	/**
	 * Checks whether the given radius is a valid radius for this entity.
	 * 
	 * @param radius
	 * 			The radius to check.
	 * @return	False if the radius is not a valid, finite number.
	 * 			| if(Double.isNaN(radius) || Double.isInfinite(radius))
	 * 			|		result == false
	 * @return	Whether or not the radius is bigger than the lower bound.
	 * 			| result == (radius >= getRadiusLowerBound())
	 */
	@Raw
	public boolean canHaveAsRadius(double radius){
		if(Double.isNaN(radius) || Double.isInfinite(radius))
			return false;
		return radius >= getRadiusLowerBound();
	}
	
	/**
	 * Returns a lower bound on the radius of this entity.
	 * 
	 * @return A strictly positive lower bound on the radius of this entity.
	 * 		   | result > 0
	 */
	@Raw
	public double getRadiusLowerBound(){
		return Double.MIN_VALUE;
	}
	
	/**
	 * Determines whether this entity collides with the given entity.
	 * 
	 * @param entity	The entity to check collision against.
	 * @return			Whether this entity collides with the given entity.
	 */
	public boolean collidesWith(Entity entity){
		//TODO implement
		//TODO update @return-clause with formal statement.
		return false;
	}
}
