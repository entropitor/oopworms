package worms.model;

/**
 * A class representing a bazooka
 */
public class Bazooka extends Weapon {

	/**
	 * @return		| result == null || result instanceof BazookaProjectile
	 */
	@Override
	protected Projectile getNewProjectile(World world, int yield) throws NullPointerException{
		if(hasMoreProjectiles())
			return new BazookaProjectile(world, yield);
		return null;
	}

	/** 
	 * @return		| result == 50
	 */
	@Override
	public int getCost() {
		return 50;
	}

	@Override
	public String getName() {
		return "Bazooka";
	}

}
