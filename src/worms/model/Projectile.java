package worms.model;

public class Projectile extends MassiveEntity {
	//TODO implement these methods

	/**
	 * @see worms.model.MassiveEntity#getDensity()
	 */
	@Override
	protected int getDensity() {
		return 7800;
	}

	/**
	 * @see worms.model.MassiveEntity#getMass()
	 * 
	 * @return
	 */
	@Override
	protected double getMass() {
		// TODO Auto-generated method stub
		//Given by the Weapon.
		return 0;
	}

	/**
	 * @see worms.model.MassiveEntity#getJumpForce()
	 * 
	 * @return	
	 */
	@Override
	protected double getJumpForce() {
		// TODO Auto-generated method stub
		//Given by the Weapon.
		return 0;
	}

	/**
	 * @see worms.model.Entity#getRadius()
	 * 
	 * @return	
	 */
	@Override
	public double getRadius() {
		// TODO Auto-generated method stub
		//From mass
		return 0;
	}
}
