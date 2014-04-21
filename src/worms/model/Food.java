package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class representing WormFood.
 *
 */
public class Food extends Entity {
	/**
	 * Creates a new food ration in the given world.
	 * 
	 * @param world
	 * 			The world with which the projectile will be associated.
	 * @effect	Adds the worm to the given world.
	 * 			| world.addFood(this)
	 * @throws	NullPointerException
	 * 			The given world is not effective.
	 * 			| world == null
	 */
	public Food(World world) throws IllegalArgumentException,IllegalStateException,NullPointerException{
		world.addFood(this);
	}
	
	/**
	 * @return	The radius of a food entity equals 0.20 metres
	 * 			| result == 0.20
	 */
	@Override @Basic @Raw @Immutable
	public double getRadius() {
		return 0.20;
	}

}
