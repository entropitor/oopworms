package worms.model;

import static java.lang.Math.PI;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of projectiles fired from Weapons, with a propulsionyield
 * 
 * @invar	| isValidPropulsionYield(getPropulsionYield())
 */
public abstract class Projectile extends MassiveEntity {
	
	/**
	 * Construct a new Projectile with a given propulsion yield.
	 *
	 * @param world
	 * 			The world with which the projectile will be associated.
	 * @param propulsionYield
	 * 			The propulsionyield for the new projectile.
	 * @effect	| setPropulsionYield(propulsionYield)
	 * @effect	| world.setProjectile(this)
	 * @throws	NullPointerException
	 * 			The given world is not effective.
	 * 			| world == null
	 */
	@Raw
	public Projectile(World world, int propulsionYield) throws NullPointerException{
		setPropulsionYield(propulsionYield);
		world.setProjectile(this);
	}
	
	/**
	 * Gets the propulsion yield for this projectile.
	 */
	@Basic @Raw
	public int getPropulsionYield(){
		return propulsionYield;
	}
	
	/**
	 * Checks whether the given propulsionYield is a valid propulsionYield
	 * 
	 * @return | result == (0 <= propulsionYield && propulsionYield <= 100)
	 */
	public boolean isValidPropulsionYield(int propulsionYield){
		return 0 <= propulsionYield && propulsionYield <= 100;
	}
	
	/**
	 * Legalizes a propulsion yield so it's a valid propulsion yield
	 * 
	 * @param propulsionYield	The propulsionyield to legalize.
	 * @return					| if(propulsionYield > 100) then result = 100
	 * 							| else if (propulsionYield < 0) then result = 0
	 * 							| else then result = propulsionYield 
	 */
	public static int legalizePropulsionYield(int propulsionYield){
		if(propulsionYield > 100)
			return 100;
		else if(propulsionYield < 0)
			return 0;
		else 
			return propulsionYield;
	}
	
	/**
	 * Sets the propulsion yield for this projectile.
	 * 
	 * @param propulsionYield
	 * 			The projectile to set
	 * @post	| new.getPropulsionYield() == legalizePropulsionYield(propulsionYield)
	 */
	@Raw @Model
	private void setPropulsionYield(int propulsionYield){
		this.propulsionYield = legalizePropulsionYield(propulsionYield);
	}
	private int propulsionYield;
	
	
	/**
	 * @return	The projectile has a density of 7800 kg/m³
	 * 			| result == 7800
	 */
	@Override @Immutable @Raw @Basic
	protected int getDensity() {
		return 7800;
	}
	
	@Override @Raw
	public abstract double getMass();
	
	@Override @Raw
	public double getRadius(){
		return Math.cbrt(getMass()/this.getDensity()/4.0*3/PI);
	}
	
	/**
	 * @post		The hitpoints of the worms (in this world) this entity overlaps with at the end of the jump are decreased with the damage this projectile does.
	 * 				The hitpoints of all other worms in this world are left untouched.
	 * 				| for each worm in getWorld().getWorms(): 
	 * 				| 	if(worm.collidesWith(getJumpStep(getJumpTime()),getRadius()))
	 * 				| 		then (new worm).gitHitPoints() == worm.getHitPoints()-getDamage()
	 * 				|	else 
	 * 				|		then (new worm).gitHitPoints() == worm.getHitPoints()
	 */
	@Override
	public void handleAfterJump(){
		for(Worm worm : getWorld().getWorms()){
			if(collidesWith(worm)){
				worm.decreaseHitPoints(getDamage());
			}
		}
		if(afterJumpRemove())
			getWorld().removeProjectile();
	}
	
	/**
	 * @return		| result == true
	 */
	@Override
	public boolean afterJumpRemove(){
		return true;
	}
	
	/**
	 * @return	True if the position overlaps with a worm
	 * 			| if(for some worm in getWorld().getWorms(): worm.collidesWith(position,getRadius())) then result == true
	 */
	@Override
	public boolean blocksJump(Position position){
		for(Worm worm : getWorld().getWorms())
			if(worm.collidesWith(position,getRadius()))
				return true;
		return super.blocksJump(position);
	}
	
	/**
	 * @return		True in all other cases.
	 * 				| result == true
	 * @note		This return-clause should only be used if all other return-clauses (of super-method) can't determine the result of this method!
	 */
	@Override
	public boolean canJump(){
		return super.canJump();
	}
	
	/**
	 * Returns the damage this projectile does.
	 * 
	 * @return	| result >= 0
	 */
	@Raw @Basic
	public abstract int getDamage();
}
