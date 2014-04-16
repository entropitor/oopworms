package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * An enum representing the different location types: PASSABLE, IMPASSABLE and CONTACT.
 */
public enum LocationType {
	
	PASSABLE(true),IMPASSABLE(false),CONTACT(true);
	
	/**
	 * Constructs a new LocationType
	 * 
	 * @param passable	Whether or not the LocationType is passable
	 * @post	new.isPassable() == passable
	 */
	LocationType(boolean passable){
		this.passable = passable;
	}
	
	/**
	 * Returns whether or not this locationtype is passable.
	 */
	@Basic @Raw @Immutable
	public boolean isPassable(){
		return passable;
	}
	
	private boolean passable;
}
