package worms.model;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.ceil;
import static java.lang.Math.cos;
import static java.lang.Math.round;
import static java.lang.Math.sin;
import static worms.util.ModuloUtil.posMod;
import static worms.util.Util.fuzzyGreaterThanOrEqualTo;
import static worms.util.Util.fuzzyLessThanOrEqualTo;
import worms.util.MathUtil;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of massive entities with a name and action points,
 * that can move, eat and fire weapons, amongst others.
 * 
 * @invar	The name of the worm is a valid name.
 * 			| isValidName(getName())
 * @invar	The amount of action points is a valid amount of action points for this worm.
 * 			| canHaveAsActionPoints(getActionPoints())
 */
public class Worm extends MassiveEntity {

	/**
	 * Creates a new worm that is positioned at the given location, faces the given direction, 
	 * has the given radius and the given name.
	 * 
	 * @param x
	 * 			The x-coordinate of the position of the new worm (in metres)
	 * @param y
	 * 			The y-coordinate of the position of the new worm (in metres)
	 * @param direction
	 * 			The direction of the new worm (in radians)
	 * @param radius
	 * 			The radius of the new worm (in metres)
	 * @param name
	 * 			The name of the new worm
	 * @effect	Set the x- and y-coordinate
	 * 			| setPosition(new Position(x,y))
	 * @effect	Set the direction to the given direction
	 * 			| setDirection(direction)
	 * @effect	Set the name to the given name
	 * 			| setName(name);
	 * @effect	Set the radius to the given radius
	 * 			| setRadius(radius)
	 * @effect	Replenish the action points to their maximum.
	 * 			| replenishActionPoints()
	 */
	@Raw
	public Worm(double x, double y, double direction, double radius, String name) throws IllegalArgumentException{
		setPosition(new Position(x,y));
		setDirection(direction);
		setName(name);
		setRadius(radius);
		replenishActionPoints();
	}
	
	/**
	 * Checks whether the given angle is a valid angle to turn a Worm over.
	 * 
	 * @param angle
	 * 			The turning angle (in radians) to check.
	 * @return	Whether or not the given angle is in the range [-2*pi, 2*pi]
	 * 			| result == fuzzyGreaterThanOrEqualTo(angle, -2*PI)
	 *			|			&& fuzzyLessThanOrEqualTo(angle, 2*PI);
	 * @note	(NaN check is implicit in "worms.util.Util.fuzzy[..]") 
	 */
	public static boolean isValidTurningAngle(double angle){
		return fuzzyGreaterThanOrEqualTo(angle, -2*PI)
				&& fuzzyLessThanOrEqualTo(angle, 2*PI);
	}
	
	/**
	 * Returns the number of action points (APs) that a turn 
	 * over the given angle would cost.
	 * 
	 * @param angle
	 * 			The angle (in radians) of the turn.
	 * @pre		The given angle must be a valid angle to turn over.
	 * 			| isValidTurningAngle(angle)
	 * @return	The cost of a turn over the given angle, directly
	 * 			proportional with the given angle - where a 180° turn
	 *			costs 30 APs - rounded up to the nearest integer.
	 * 			| result == (int) ceil(abs(30*angle/(PI)))
	 */
	public static int getTurningCost(double angle){
		assert isValidTurningAngle(angle);
		return (int) ceil(abs(60*angle/(2*PI)));
	}
	
	/**
	 * Checks whether this worm can make a turn over
	 * the given angle.
	 * 
	 * @param angle
	 * 			The turning angle (in radians) to check.
	 * @pre		The given angle must be a valid angle to turn over.
	 * 			| isValidTurningAngle(angle)
	 * @return	Whether or not this worm has enough action points (APs) left
	 * 			to perform a turn over the given angle.
	 * 			| result == (getActionPoints() >= getTurningCost(angle))
	 */
	public boolean canTurn(double angle){
		// Implicit in getTurningCost().
		// assert isValidTurningAngle(angle);
		return getTurningCost(angle) <= this.getActionPoints();
	}
	
	/**
	 * Turns this worm over the given angle and inflicts a
	 * proportional amount of action points (APs).
	 * 
	 * @param angle
	 * 			The angle, in radians, to make this worm turn over.
	 * @pre 	The given angle is a valid angle to turn over.
 	 * 			| isValidTurningAngle(angle)
	 * @pre		The worm is able to turn over the given angle.
	 * 			| canTurn(angle)
	 * @post	This worm has turned over the given angle.
	 * 			| fuzzyEquals(new.getDirection(), posMod((getDirection() + angle), (2*PI)))
	 * @post	The action points of this worm decreased appropriately.
	 * 			| new.getActionPoints() == getActionPoints() - getTurningCost(angle)
	 */
	public void turn(double angle){
		// (We do not assert the first precondition, as this is done in canTurn())
		assert canTurn(angle);
		this.setDirection(posMod((this.getDirection() + angle), (2*PI)));
		this.decreaseActionPoints(getTurningCost(angle));
	}
	
	/**
	 * Checks whether this worm has enough action points to move it the given number of steps.
	 * 
	 * @param nbSteps 	The number of steps this worm wants to move.
	 * @return 			Return whether nbSteps is non-negative and the cost to move the given number of steps 
	 * 					(in the direction of this worm) is smaller or equal to the current action points of this worm.
	 * 					| result == (nbSteps >= 0 && getCostForMove(nbSteps,getDirection()) <= getActionPoints())
	 */
	public boolean canMove(int nbSteps){
		if(nbSteps < 0)
			return false;
		return getCostForMove(nbSteps,getDirection()) <= getActionPoints();
	}
	
	/**
	 * Calculates the cost for a worm to move nbSteps in a given direction.
	 * 
	 * @param nbSteps 		The number of steps in the movement.
	 * @param direction		The direction in which the worm will move.
	 * @return				The result equals nbSteps times the cost of a unit step in the current direction, rounded up to the next integer.
	 * 						| result == (int)(ceil(nbSteps*getUnitStepCost(direction)))
	 * @throws IllegalArgumentException 
	 * 						Thrown when nbSteps is less than zero.
	 * 						| nbSteps < 0
	 * @throws IllegalArgumentException 
	 * 						Thrown when the given direction is an invalid direction.
	 * 						| !isValidDirection(direction)
	 */
	public static int getCostForMove(int nbSteps, double direction) throws IllegalArgumentException{
		if(nbSteps < 0)
			throw new IllegalArgumentException("Illegal number of steps");
		if(!isValidDirection(direction))
			throw new IllegalArgumentException("Illegal direction");
		
		return (int)(ceil(nbSteps*getUnitStepCost(direction)));
	}
	
	/**
	 * Calculates the cost for one step in a given direction.
	 * 
	 * <p>A horizontal step costs 1 action point.<br>
	 * A vertical step costs 4 action points.<br>
	 * Every other step is the sum of the costs of the horizontal and vertical components 
	 * (in which the cost of a component is proportional to the fraction of the step and to the cost of a step along that component).<br>
	 * Because of floating point precision problems, the components are rounded to a precision of 1e-12.<br></p>
	 * 
	 * @param direction		The direction for which the cost of the unit step should be calculated.
	 * @return				Returns the cost of a unit step in the given direction. 
	 * 						| fuzzyEquals(result,abs(MathUtil.round(cos(direction),12))+4*abs(MathUtil.round(sin(direction),12)))
	 * @note				This method does not round the cost to the next integer, it just calculates the fraction 
	 * 						of action points required for a unit step in the given direction.
	 * @throws IllegalArgumentException
	 * 						Thrown when the direction is not a valid direction
	 * 						| !isValidDirection(direction)
	 */
	public static double getUnitStepCost(double direction) throws IllegalArgumentException{
		if(!isValidDirection(direction))
			throw new IllegalArgumentException("Illegal Direction");
		
		int precision = 12;
		
		double xCost = cos(direction);
		xCost = MathUtil.round(xCost, precision);
		xCost = abs(xCost);
		
		double yCost = 4*sin(direction);
		yCost = MathUtil.round(yCost, precision);
		yCost = abs(yCost);
		
		return xCost+yCost;
	}
	
	/**
	 * Moves this worm a given number of steps in the current direction.
	 * 
	 * @param nbSteps		The number of steps to move this worm.
	 * @post				This worm has moved nbSteps in the current direction
	 * 						| fuzzyEquals(new.getXCoordinate(), getXCoordinate()+nbSteps*cos(getDirection)) &&
	 * 						| fuzzyEquals(new.getYCoordinate(), getYCoordinate()+nbSteps*sin(getDirection))
	 * @post				The action points are decreased with the cost of the movement.
	 * 						| new.getActionPoints() == getActionPoints()-getCostForMovement(nbSteps,getDirection())
	 * @throws IllegalArgumentException
	 * 						Thrown when nbSteps is less than zero
	 * 						| nbSteps < 0
	 * @throws IllegalStateException	
	 * 						Thrown when this worm has not enough action points to move the given number of steps in the current direction.
	 * 						| !canMove(nbSteps)
	 */
	public void move(int nbSteps) throws IllegalStateException,IllegalArgumentException{
		if(nbSteps < 0)
			throw new IllegalArgumentException("Illegal number of steps");
		if(!canMove(nbSteps))
			throw new IllegalStateException("Has not enough action points to move.");
		
		moveWith(nbSteps*cos(getDirection()),nbSteps*sin(getDirection()));
		decreaseActionPoints(getCostForMove(nbSteps,getDirection()));
	}
	
	/**
	 * Moves this worm with the given number of metres along the x-axis and the given number of metres along the y-axis.
	 * 
	 * @param x		The number of metres to move along the x-axis
	 * @param y		The number of metres to move along the y-axis
	 * @effect		Move to the position with an offset of (x,y) metres
	 * 				setPosition(getPosition().offset(x,y))
	 * @throws IllegalArgumentException
	 * 				Thrown when x or y is not a valid number
	 * 				| Double.isNaN(x) || Double.isNaN(y)
	 */
	public void moveWith(double x, double y) throws IllegalArgumentException{
		setPosition(getPosition().offset(x,y));
	}
	
	/**
	 * Returns the name of this worm.
	 */
	@Basic @Raw
	public String getName(){
		return this.name;
	}
	
	/**
	 * Checks whether or not the given name is a valid name for a worm.
	 * 
	 * @param name The name to check.
	 * @return	Whether or not the name is at least 2 characters long, starts with an uppercase letter and only contains valid characters.
	 * 			|	result == (name.length() >= 2 && Character.isUpperCase(name.charAt(0)) 
	 * 			|				&& for all Character c in name:
	 * 			|						isValidCharacterForName(c))
	 */
	public static boolean isValidName(String name){
		if(name.length() < 2)
			return false;
		if(!Character.isUpperCase(name.charAt(0)))
			return false;
		for(Character c : name.toCharArray()){
			if(!isValidCharacterForName(c))
				return false;
		}
		return true;
	}
	
	/**
	 * Checks whether or not the given character is a valid character to be used in a name for a worm.
	 * 
	 * <p>The valid characters consist of (latin) letters (both uppercase and lowercase), quotes (both single and double), spaces and numbers.
	 * In short the list looks like this: [A-Za-z'" 0-9].
	 * 
	 * @param c The character to check.
	 * @return	Whether or not the character is a (latin) letter, quote, space or number.
	 * 			| result == (c == 32 || c == 34 || c == 39 || (65 <= c && c <= 90) || (97 <= c && c <= 122) || 48 <= c && c <= 57)
	 */
	public static boolean isValidCharacterForName(char c){
		//Space is allowed.
		if(c == 32)
			return true;
		//Single and double quotes are allowed.
		if(c == 34 || c == 39)
			return true;
		
		//Uppercase characters are allowed.
		if(65 <= c && c <= 90)
			return true;
		//Lowercase characters are allowed.
		if(97 <= c && c <= 122)
			return true;
		
		//Numbers are allowed.
		if(48 <= c && c <= 57)
			return true;
		
		//All the rest isn't allowed (at the moment).
		return false;
	}
	
	/**
	 * Sets the name for this worm.
	 * 
	 * @param name The name to set
	 * @throws IllegalArgumentException 
	 * 			When the given name is not a valid name.
	 * 			| !isValidName(name)
	 * @post 	The name for this worm equals the given name.
	 * 			| new.getName() == name
	 */
	@Raw
	public void setName(String name) throws IllegalArgumentException{
		if(!isValidName(name))
			throw new IllegalArgumentException("Illegal Name");
		this.name = name;
	}
	private String name;
	
	/**
	 * @return	The worm has a density of 1062 kg/m³
	 * 			| result == 1062
	 */
	@Override @Immutable @Raw @Basic
	protected int getDensity() {
		return 1062;
	}

	@Override @Basic @Raw
	public double getRadius() {
		return this.radius;
	}
	
	@Override @Raw
	public double getRadiusLowerBound(){
		return 0.25;
	}
	
	/**
	 * Sets the radius of this worm.
	 * 
	 * @param radius
	 * 			The new radius of this worm (in metres).
	 * @post	The radius of this worm is the given radius.
	 * 			| new.getRadius() == radius
	 * @post	If the given radius is smaller than the current radius so that the current action points 
	 * 			are no longer smaller than the (new) maximum of action points, the new current action points will equal the new maximum of action points.
	 * 			| if(new.getMaxActionPoints() <= this.getActionPoints())
	 * 			|		then new.getActionPoints() == new.getMaxActionPoints()
	 * @throws	IllegalArgumentException
	 * 			The given radius is not a valid radius for this worm.
	 * 			| !this.canHaveAsRadius(radius)
	 */
	@Raw
	public void setRadius(double radius) throws IllegalArgumentException{
		if (!this.canHaveAsRadius(radius))
			throw new IllegalArgumentException("The given radius is not a valid radius for this worm");
		this.radius = radius;
		// Check and possibly correct if this worm's action points (APs)
		// are still smaller than the maximum amount of APs for this worm
		// (which depends on this worm's radius via its mass).
		this.setActionPoints(this.getActionPoints());
	}
	private double radius;
		
	/**
	 * Returns the current number of action points of this worm.
	 */
	@Basic @Raw
	public int getActionPoints(){
		return this.actionPoints;
	}
	
	/**
	 * Returns the maximum number of action points this worm can have.
	 * 
	 * @return	The maximum number of this worm, equal to its mass
	 * 			rounded to the nearest integer. 
	 * 			When the rounded mass is bigger than Integer.MAX_VALUE, 
	 * 			this method returns Integer.MAX_VALUE.
	 * 			| if( round(getMass()) > Integer.MAX_VALUE) then result == Integer.MAX_VALUE
	 * 			| else result == (int) round(getMass());
	 */
	public int getMaxActionPoints(){
		long maxAP = round(this.getMass());
		if(maxAP > Integer.MAX_VALUE)
			return Integer.MAX_VALUE;
		return (int) maxAP;
	}
	
	 /**
	  * Checks whether or not the given amount of action points (APs) 
	  * is a valid amount of action points for this worm.
	  * 
	  * @param amount
	  * 		The amount of action points to check.
	  * @return	Whether or not the given number of APs is nonnegative
	  * 		and not bigger than the maximum allowed number of action
	  * 		points for this worm.
	  *			| result == (0 <= amount 
	  *			| 			&& amount <= getMaxActionPoints())
	  */
	@Raw
	public boolean canHaveAsActionPoints(int amount){
		return (0 <= amount 
				&& amount <= this.getMaxActionPoints());
	}
	
	/**
	 * Subtract a number of action points (APs) from this worm's APs. 
	 *
	 * @param amount
	 * 			The number of APs to be subtracted from the current amount of APs.
	 * @effect	If the specified amount is positive, set this worms APs to be 
	 *			the current amount of APs minus the specified amount.
	 *			| if (amount > 0)
	 *			| 	then setActionPoints(getActionPoints() - amount)
	 * @note	Note that provoking an overflow (underflow) is not possible here,
	 * 			given that this.getActionPoints() >= 0.
	 */
	@Model
	private void decreaseActionPoints(int amount){
		if (amount > 0)
			this.setActionPoints(this.getActionPoints() - amount);
	}

	/**
	 * Add a number of action points (APs) to this worm's APs. 
	 *
	 * @param amount
	 * 			The number of APs to be added to the current amount of APs.
	 * @effect	If the specified amount is positive, set this worms APs to be 
	 *			the current amount of APs plus the specified amount.
	 *			If an overflow would occur, the APs of this worm are set to their maximum value.
	 *			| if (amount > 0)
	 *			| 	if ((Integer.MAX_VALUE - this.getActionPoints()) <= amount)
	 *			| 		then setActionPoints(getActionPoints() + amount);
	 *			| 	else
	 *			| 		// Overflow would occur.
	 *			| 		replenishActionPoints();
	 * @note	Note that, while convenient maybe, a call like for example increaseActionPoints(-5) 
	 * 			will <i>not</i> decrease the amount of APs by 5.
	 * 			This is the reasoning behind this decision:
	 * 			When calling <i>increase</i>ActionPoints(), one should not expect
	 * 			a decrease in APs.
	 */
	@Model
	private void increaseActionPoints(int amount){
		if (amount > 0)
			if ((Integer.MAX_VALUE - this.getActionPoints()) >= amount)
				this.setActionPoints(this.getActionPoints() + amount);
			else
				// Overflow would occur.
				this.replenishActionPoints();
	}

	/**
	 * Gives this worm all his action points (APs).
	 *
	 * @effect	Sets this worm's APs to the maximum allowed number.
	 *			| setActionPoints(getMaxActionPoints())		
	 */
	@Raw @Model
	private void replenishActionPoints(){
		this.setActionPoints(this.getMaxActionPoints());
	}
	
	/**
	 * Sets this worm's action points (APs) to the given amount.
	 * 
	 * @param amount
	 * 			The number of APs 
	 * @post	If the given amount of APs is a valid amount of APs for this worm,
	 * 			set this worm's APs to the given amount.
	 * 			| if (canHaveAsActionPoints(amount))
	 * 			| 	new.getActionPoints() == amount
	 * @post	A negative amount zeroes this worm's APs.
	 * 			| if (amount < 0)
	 *			| 	new.getActionPoints() == 0
	 * @post	An amount larger than the maximum allowed number of APs
	 * 			sets this worm's APs to this maximum allowed number.
	 * 			| if (amount > this.getMaxActionPoints())
	 *			| 	new.getActionPoints() == getMaxActionPoints()
	 */
	@Raw @Model
	private void setActionPoints(int amount){
		if (this.canHaveAsActionPoints(amount))
			this.actionPoints = amount;
		if (amount < 0)
			this.actionPoints = 0;
		if (amount > this.getMaxActionPoints())
			this.actionPoints = this.getMaxActionPoints();
	}
	private int actionPoints;
	
	/**
	 * @post		The action points are set to zero.
	 * 				| new.getActionPoints() == 0
	 */
	@Override
	public void jump() throws IllegalStateException{
		super.jump();
		decreaseActionPoints(getActionPoints());
	}
	
	/**
	 * @return	The (virtual) force exerted by this worm (in Newton) equals the sum of 5 times its action points and 
	 * 			its weight (its mass times the gravitational acceleration), in case he can jump.
	 * 			Otherwise the result equals 0 Newton.
	 * 			| if(canJump())
	 * 			| 		then fuzzyEquals(result, ((5*getActionPoints()) + (getMass()*GRAVITATIONAL_ACCELERATION)))
	 * 			| else
	 * 			|		then result == 0
	 */
	@Override
	public double getJumpForce(){
		if(!canJump())
			return 0;
		return ((5*getActionPoints()) + (getMass()*GRAVITATIONAL_ACCELERATION));
	}
	
	/**
	 * @return		Whether or not this worm is facing upwards and has any action points left.
	 * 				| result == (getDirection() <= PI) && (getActionPoints() > 0)
	 */
	@Override @Raw
	public boolean canJump(){
		return (getDirection() <= PI) && (getActionPoints() > 0);
	}
}
