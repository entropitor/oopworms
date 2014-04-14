package worms.model;

import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of BazookaProjectiles
 */
public class RifleProjectile extends Projectile {

	@Raw
	public RifleProjectile(int propulsionYield) {
		super(propulsionYield);
	}

	/**
	 * return	| result == 1.5
	 */
	@Override
	protected double getJumpForce() {
		return 1.5;
	}

	/**
	 * return	| result == 10e-3
	 */
	@Override @Raw
	public double getMass() {
		return 10e-3;
	}

}
