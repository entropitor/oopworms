package worms.model;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.min;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of entities being able to move along a ballistic trajectory
 * and having a mass.
 */
public abstract class MassiveEntity extends Entity {
	/**
	 * The gravitational acceleration on earth, in m/s².
	 */
	protected static final double GRAVITATIONAL_ACCELERATION = 9.80665;
	
	/**
	 * Returns the constant density of this entity, in kg/m³.
	 * @note	This method should be declared 'static'
	 *			(Density is a property of each Subclass itself, not of instances
	 *			of a Subclass.) Unfortunately, Java does not support this.
	 */
	protected abstract int getDensity();
	
	/**
	 * Returns the mass of this entity, in kg.
	 */
	protected abstract double getMass();
	
	
	/**
	 * Returns the direction of this entity (in radians).
	 */
	@Basic @Raw
	public double getDirection(){
		return direction;
	}
	
	/**
	 * Checks whether the given direction is a valid direction.
	 * 
	 * @param direction The direction to check
	 * @return 	Whether or not direction is a valid number between 0 and 2*PI
	 * 			| result == (!double.isNaN(direction) && 0 <= direction && direction < 2*PI)
	 */
	public static boolean isValidDirection(double direction){
		if(Double.isNaN(direction))
			return false;
		return 0 <= direction && direction < 2*PI;
	}
	
	/**
	 * Sets the direction of this entity to the given direction.
	 * 
	 * @param direction
	 * 			The new direction of the entity (in radians).
	 * @pre 	The given direction is a valid direction.
	 * 			| isValidDirection(direction)
	 * @post 	The new direction of this entity equals the given direction.
	 * 			| new.getDirection() == direction
	 */
	@Raw @Model
	protected void setDirection(double direction){
		assert isValidDirection(direction);
		this.direction = direction;
	}
	private double direction;
	
	/**
	 * Makes this entity move along a ballistic trajectory.
	 * 
	 * @post		The new x- and y-coordinate of this entity will equal the x- and y-coordinates as 
	 * 				calculated by getJumpStemp() after the jump is completed
	 * 				| new.getXCoordinate() == getJumpStep(getJumpTime())[0]
	 * 				| new.getYCoordinate() == getJumpStep(getJumpTime())[1]
	 * @note		With the current physics, the new y-coordinate will (almost) equal the old y-coordinate
	 * 				(taking floating point precision in calculation).
	 */
	public void jump(){
		double[] newCoordinates = getJumpStep(getJumpTime());
		setPosition(new Position(newCoordinates[0],newCoordinates[1]));
	}
	
	/**
	 * Calculates the force exerted by this entity (in Newton) in a potential jump from his current position.
	 */
	protected abstract double getJumpForce();
	
	/**
	 * Calculates the initial velocity of this entity (in m/s) in a potential jump from his current position.
	 * 
	 * @return	The (virtual) velocity of this entity (in m/s) equals the (virtual) force exerted by this entity divided by its mass and multiplied with 0.5 seconds.
	 * 			| fuzzyEquals(result, (getJumpForce()/getMass())*0.5);
	 */
	protected double getJumpVelocity(){
		return (getJumpForce()/getMass())*0.5;
	}
	
	/**
	 * Calculates the time this entity will take to complete a potential jump from his current position.
	 * 
	 * @return 	Returns the amount of time needed to complete his (virtual) jump from his current position when he can jump.
	 * 			This time equals 2 times the y-component of the initial velocity divided by the gravitational acceleration.
	 * 			| fuzzyEquals(result, (2*getJumpVelocity()*sin(getDirection())/GRAVITATIONAL_ACCELERATION))
	 */
	public double getJumpTime(){
		return (2*getJumpVelocity()*sin(getDirection())/GRAVITATIONAL_ACCELERATION);
	}
	
	/**
	 * Calculates the x- and y-coordinate of this entity in a potential jump from his current position after
	 * the given period of time t.
	 * 
	 * @param t		The time after the jump (in seconds).
	 * @return		An array of two doubles, the first one equals the x-coordinate t seconds after the jump, 
	 * 				the second one equals the y-coordinate t seconds after the jump.
	 * 				|	result.length == 2 &&
	 * 				|	fuzzyEqulas(result[0], (getXCoordinate()+(getJumpVelocity()*cos(getDirection())*min(t,getJumpTime()))) &&
	 * 				|	fuzzyEquals(result[1], (getYCoordinate()+(getJumpVelocity()*sin(getDirection())*min(t,getJumpTime()))-(GRAVITATIONAL_ACCELERATION/2*pow(time,2)))
	 * @throws IllegalArgumentException
	 * 				Thrown when t is not a valid number or is less than zero
	 * 				| Double.isNaN(t) || t < 0
	 * @note		When this entity is unable to jump, this method will just return an array containing
	 * 				the current x and y position, invariant of the given t.
	 */
	public double[] getJumpStep(double t) throws IllegalArgumentException{
		if(Double.isNaN(t) || t < 0)
			throw new IllegalArgumentException("Illegal timestep");
		
		//If t > getJumpTime() => jump is over, use getJumpTime() as time instead of t.
		double time = min(t,getJumpTime());
		double velocity = getJumpVelocity();
		double direction = getDirection();
		
		//TODO maybe use Position here, that's cleaner.
		//(And convert to a double[] in Facade - that's what facades are for.)
		double newX = getXCoordinate()+(velocity*cos(direction)*time);
		double newY = getYCoordinate()+(velocity*sin(direction)*time)-(GRAVITATIONAL_ACCELERATION/2*pow(time,2));
		return new double[]{newX,newY};
	}
}
