package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of objects taking up space at a certain position in a game world.
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
	 * 			| new.getPosition() == position
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
	 * Determines whether this entity collides with the given entity.
	 * 
	 * @param entity	The entity to check collision against.
	 * @return			Whether this entity collides with the given entity.
	 */
	public boolean collidesWith(Entity entity){
		//TODO implement
		return false;
	}
}
