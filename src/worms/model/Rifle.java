package worms.model;

/**
 * A class representing a rifle
 */
public class Rifle extends Weapon {

	/**
	 * @return		| result == null || result instanceof RifleProjectile
	 */
	@Override
	protected Projectile getNewProjectile(World world, int yield) throws NullPointerException{
		if(hasMoreProjectiles())
			return new RifleProjectile(world, yield);
		return null;
	}

	/** 
	 * @return		| result == 10
	 */
	@Override
	public int getCost() {
		return 10;
	}

	@Override
	public String getName() {
		return "Rifle";
	}

}
