package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class representing a weapon that can fire Projectiles.
 */
public abstract class Weapon {
	
	/**
	 * Returns a new projectile with the given yield binded to the given world.
	 * 
	 * @param world		The world in which the new projectile will live.
	 * @param yield		The propulsionYield for the projectile
	 * @return			| if(hasMoreProjectiles)
	 * 					|	then result != null && result.getPropulsionYield() == Projectile.legalizePropulsionYield(yield) 
	 * 					|			&& result.getWorld() == world && (new world).getProjectile() == result
	 * 					| else result == null
	 * @throws NullPointerException
	 * 					| world == null
	 */
	protected abstract Projectile getNewProjectile(World world, int yield) throws NullPointerException;
	
	/**
	 * Checks whether or not this weapon has more projectiles to fire.
	 */
	@Raw @Basic
	public boolean hasMoreProjectiles(){
		return true;
	}
	
	/**
	 * Gets the cost (in number of action points) for firing this weapon.
	 * 
	 * @return	| result >= 0
	 */
	@Raw @Basic
	public abstract int getCost();
	
	/**
	 * Gets the name of the weapon.
	 * 
	 * @return	| result != ""
	 */
	@Raw @Basic
	public abstract String getName();

}
