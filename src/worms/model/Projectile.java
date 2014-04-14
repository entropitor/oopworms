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
	 * Construct a new Projectile with a given propulsionyield.
	 * 
	 * @param propulsionYield
	 * 			The propulsionyield for the new projectile.
	 * @effect	| setPropulsionYield(propulsionYield)
	 */
	@Raw
	public Projectile(int propulsionYield){
		setPropulsionYield(propulsionYield);
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
	 * Sets the propulsion yield for this projectile.
	 * 
	 * @param propulsionYield
	 * 			The projectile to set
	 * @post	| if(propulsionYield > 100) then new.getPropulsionYield() = 100
	 * 			| else if (propulsionYield < 0) then new.getPropulsionYield() = 0
	 * 			| else then new.getPropulsionYield() = propulsionYield
	 */
	@Raw @Model
	private void setPropulsionYield(int propulsionYield){
		if(propulsionYield > 100)
			this.propulsionYield = 100;
		else if(propulsionYield < 0)
			this.propulsionYield = 0;
		else 
			this.propulsionYield = propulsionYield;
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
	 * @return	Returns true.
	 * 			| result == true
	 */
	@Override @Raw
	public boolean canJump() {
		return true;
	}
}
