package worms.model;

import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of BazookaProjectiles
 */
public class BazookaProjectile extends Projectile {

	@Raw
	public BazookaProjectile(int propulsionYield) {
		super(propulsionYield);
	}

	/**
	 * @return	| result == 300e-3
	 */
	@Override @Raw
	public double getMass() {
		return 300e-3;
	}

	/** 
	 * @return	| result == 2.5+7/100*getPropulsionYield()
	 */
	@Override
	protected double getJumpForce() {
		return 2.5+7.0/100*getPropulsionYield();
	}

}