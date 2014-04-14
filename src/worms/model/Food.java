package worms.model;

import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class representing WormFood.
 *
 */
public class Food extends Entity {

	@Override @Raw
	public double getRadius() {
		return 0.20;
	}

}
