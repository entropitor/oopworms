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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
 * @invar	The amount of hit points is a valid amount of hit points for this worm.
 * 			| canHaveAsHitPoints(getHitPoints())
 * @invar	The worm has proper weapons.
 * 			| hasProperWeapons()
 * @invar	The worm can have as much weapons as it has.
 * 			| canHaveAsNbWeapons(getNbWeapons())
 * @invar	The worm has a proper team.
 *			| hasProperTeam()
 */
public class Worm extends MassiveEntity {

	/**
	 * Creates a new worm without a team.
	 *
	 * @see		The overloaded constructor for info on parameters and effects.
	 *
	 * @effect	Create a new worm with the given params and no team.
	 *			| this(world, x, y, direction, radius, name, null);
	 */
	@Raw
	public Worm(World world, double x, double y, double direction, double radius, String name) throws IllegalArgumentException,NullPointerException{
		this(world, x, y, direction, radius, name, null);
	}

	/**
	 * Creates a new worm that is positioned at the given location in the given world, faces the given direction, 
	 * has the given radius and the given name.
	 * 
	 * @param world
	 * 			The world where the worm lives in.
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
	 * @effect	Replenish the hit points to their maximum.
	 * 			| replenishHitPoints()
	 * @effect	Adds the worm to the given world.
	 * 			| world.addWorm(this)
	 * @effect	Add a Bazooka to the list of weapons.
	 * 			| addWeapon(new Bazooka());
	 * @effect	Add a Rifle to the list of weapons
	 * 			| addWeapon(new Rifle());
	 * @throws	NullPointerException
	 * 			The given world is not effective.
	 * 			| world == null
	 */
	@Raw
	public Worm(World world, double x, double y, double direction, double radius, String name, Team team) throws IllegalArgumentException,NullPointerException{
		setPosition(new Position(x,y));
		setDirection(direction);
		setName(name);
		setRadius(radius);
		replenishActionPoints();
		replenishHitPoints();
		world.addWorm(this);
		addWeapon(new Bazooka());
		addWeapon(new Rifle());
		setTeam(team);
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
	 * Returns the current number of hit points of this worm.
	 */
	@Basic @Raw
	public int getHitPoints(){
		return this.hitPoints;
	}
	
	/**
	 * Returns the maximum number of hit points this worm can have.
	 * 
	 * @return	The maximum number of this worm, equal to its mass
	 * 			rounded to the nearest integer. 
	 * 			When the rounded mass is bigger than Integer.MAX_VALUE, 
	 * 			this method returns Integer.MAX_VALUE.
	 * 			| if( round(getMass()) > Integer.MAX_VALUE) then result == Integer.MAX_VALUE
	 * 			| else result == (int) round(getMass());
	 */
	public int getMaxHitPoints(){
		long maxAP = round(this.getMass());
		if(maxAP > Integer.MAX_VALUE)
			return Integer.MAX_VALUE;
		return (int) maxAP;
	}
	
	 /**
	  * Checks whether or not the given amount of hit points (HP) 
	  * is a valid amount of hit points for this worm.
	  * 
	  * @param amount
	  * 		The amount of hit points to check.
	  * @return	Whether or not the given number of HP is nonnegative
	  * 		and not bigger than the maximum allowed number of hit points
	  * 		for this worm.
	  *			| result == (0 <= amount 
	  *			| 			&& amount <= getMaxHitPoints())
	  */
	@Raw
	public boolean canHaveAsHitPoints(int amount){
		return (0 <= amount 
				&& amount <= this.getMaxHitPoints());
	}
	
	/**
	 * Subtract a number of hit points (HP) from this worm's HP. 
	 *
	 * @param amount
	 * 			The number of HP to be subtracted from the current amount of HP.
	 * @effect	If the specified amount is positive, set this worms HP to be 
	 *			the current amount of HP minus the specified amount.
	 *			| if (amount > 0)
	 *			| 	then setHitPoints(getHitPoints() - amount)
	 * @note	Note that provoking an overflow (underflow) is not possible here,
	 * 			given that this.getHitPoints() >= 0.
	 */
	@Model
	private void decreaseHitPoints(int amount){
		if (amount > 0)
			this.setHitPoints(this.getHitPoints() - amount);
	}

	/**
	 * Add a number of hit points (HP) to this worm's HP. 
	 *
	 * @param amount
	 * 			The number of HP to be added to the current amount of HP.
	 * @effect	If the specified amount is positive, set this worms HP to be 
	 *			the current amount of HP plus the specified amount.
	 *			If an overflow would occur, the HP of this worm are set to their maximum value.
	 *			| if (amount > 0)
	 *			| 	if ((Integer.MAX_VALUE - this.getHitPoints()) <= amount)
	 *			| 		then setHitPoints(getHitPoints() + amount);
	 *			| 	else
	 *			| 		// Overflow would occur.
	 *			| 		replenishHitPoints();
	 * @note	Note that, while convenient maybe, a call like for example increaseHitPoints(-5) 
	 * 			will <i>not</i> decrease the amount of HP by 5.
	 * 			This is the reasoning behind this decision:
	 * 			When calling <i>increase</i>HitPoints(), one should not expect
	 * 			a decrease in HP.
	 */
	@Model
	private void increaseHitPoints(int amount){
		if (amount > 0)
			if ((Integer.MAX_VALUE - this.getHitPoints()) >= amount)
				this.setHitPoints(this.getHitPoints() + amount);
			else
				// Overflow would occur.
				this.replenishHitPoints();
	}

	/**
	 * Gives this worm all his hit points (HP).
	 *
	 * @effect	Sets this worm's HP to the maximum allowed number.
	 *			| setHitPoints(getMaxHitPoints())		
	 */
	@Raw @Model
	private void replenishHitPoints(){
		this.setHitPoints(this.getMaxHitPoints());
	}
	
	/**
	 * Sets this worm's hit points (HP) to the given amount.
	 * 
	 * @param amount
	 * 			The number of HP 
	 * @post	If the given amount of HP is a valid amount of HP for this worm,
	 * 			set this worm's HP to the given amount.
	 * 			| if (canHaveAsHitPoints(amount))
	 * 			| 	new.getHitPoints() == amount
	 * @post	A negative amount zeroes this worm's HP.
	 * 			| if (amount < 0)
	 *			| 	new.getHitPoints() == 0
	 * @post	An amount larger than the maximum allowed number of HP
	 * 			sets this worm's HP to this maximum allowed number.
	 * 			| if (amount > this.getMaxHitPoints())
	 *			| 	new.getHitPoints() == getMaxHitPoints()
	 */
	@Raw @Model
	private void setHitPoints(int amount){
		if (this.canHaveAsHitPoints(amount))
			this.hitPoints = amount;
		if (amount < 0)
			this.hitPoints = 0;
		if (amount > this.getMaxHitPoints())
			this.hitPoints = this.getMaxHitPoints();
	}
	private int hitPoints;

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
	
	/**
	 * Fires the currently selected weapon.
	 * 
	 * @param yield		The propulsion yield with which to fire.
	 * @post			The new action points are decreased with the cost of firing the selected weapon
	 * 					| new.getActionPoints() == getActionPoints()-getSelectedWeapon().getCost()
	 * @effect			A new projectile is added to the world of the worm with the given yield
	 * 					| getSelectedWeapon().getNewProjectile(getWorld(), yield)
	 * @post			The new projectile's position is set in the direction of this worm's direction, just outsides this worm's perimeter.
	 * 					| let
	 *					| 	projectile == (new getWorld()).getProjectile() &&
	 *					| 	projectilesPosition == (new projectile).getPosition())
	 *					| in
	 * 					| 	fuzzyEquals(projectilesPosition.squaredDistance(getPosition()),pow(getRadius()+(new projectile).getRadius(),2))
	 * 					| 	&& !(new projectile).collidesWith(this)
	 * 					| 	&& fuzzyEquals(projectilesPosition.getX(), getPosition().getX()+cos(getDirection())*(getRadius()+(new projectile).getRadius()))
	 * 					| 	&& fuzzyEquals(projectilesPosition.getY(), getPosition().getY()+sin(getDirection())*(getRadius()+(new projectile).getRadius()))
	 * @post			The new projectile's direction is set to the direction of this worm.
	 * 					| fuzzyEquals((new (new getWorld()).getProjectile()).getDirection(),getDirection())
	 * @throws	IllegalStateException
	 * 					Thrown when there already is a projectile in the world.
	 * 					| getWorld().hasProjectile()
	 * @throws	IllegalStateException
	 * 					Thrown when this worm has no weapons to fire.
	 * 					| getNbWeapons() == 0
	 * @throws	IllegalStateException
	 * 					Thrown when this worm's selected weapon has no more projectiles.
	 * 					| !getSelectedWeapon().hasMoreProjectiles()
	 * @throws	IllegalStateException
	 * 					Thrown when this worm has not enough action points to fire the currently selected weapon
	 * 					| getActionPoints() < getSelectedWeapon().getCost()
	 * @throws	IllegalStateException
	 * 					Thrown when the worm is on impassable terrain
	 * 					| !getWorld().isPassablePosition(getPosition(), getRadius())
	 */
	public void fire(int yield) throws IllegalStateException{
		if(getWorld().getProjectile() != null)
			throw new IllegalStateException();
		
		if(getNbWeapons() == 0)
			throw new IllegalStateException();
		
		if(!getSelectedWeapon().hasMoreProjectiles())
			throw new IllegalStateException();
		
		if(getActionPoints() < getSelectedWeapon().getCost())
			throw new IllegalStateException();
		
		if(!getWorld().isPassablePosition(getPosition(), getRadius()))
			throw new IllegalStateException();
		
		Projectile projectile = getSelectedWeapon().getNewProjectile(getWorld(), yield);
		projectile.setDirection(getDirection());
		double distance = getRadius()+projectile.getRadius();
		Position pos = getPosition().offset(distance*cos(getDirection()),distance*sin(getDirection()));
		projectile.setPosition(pos);
		
		decreaseActionPoints(getSelectedWeapon().getCost());
	}
	
	/**
	 * Checks whether or not this worm has proper weapons.
	 * 
	 * @return	The worm can have every weapon it has, or the result is false
	 * 			| for each int index in 0..getNbWeapons()-1: (canHaveAsWeapon(getWeaponAt(index)) || result == false)
	 * @return	The worm has no weapon twice, or the result is false
	 * 			| for each int index in 0..getNbWeapons()-1:
	 * 			|	for each int index2 in 0..getNbWeapons()-1:
	 * 			|		getWeaponAt(index) != getWeaponAt(index2) || index == index2 || result == false
	 * @return	In all other cases this method returns true.
	 * 			| result == true.
	 */
	@Raw
	public boolean hasProperWeapons(){
		for(Weapon weapon : weapons){
			if(!canHaveAsWeapon(weapon))
				return false;
		}
		Set<Weapon> set = new HashSet<Weapon>(weapons);
		if(set.size() < weapons.size())
			return false;
		return true;
	}
	
	
	/**
	 * Returns the selected weapon.
	 * 
	 * @return		If this worm has no weapons, this method returns the null reference.
	 * 				| if(getNbWeapons() == 0)	result == null
	 * @return		If this worm has weapons, this method returns one of them.
	 * 				| if(getNbWeapons() > 0)
	 * 				| 	then hasWeapon(result)
	 */
	@Basic
	public Weapon getSelectedWeapon(){
		if(getNbWeapons() == 0)
			return null;
		return getWeaponAt(selectedWeapon);
	}
	
	/**
	 * Returns the index of the given weapon in the list of weapons for this worm.
	 * 
	 * @param weapon	The weapon of which the index must be returned.
	 * @return			The index of the weapon in the list of weapons or -1 if the given weapon is not in the list.
	 * 					| if(!hasWeapon(weapon))
	 * 					|		then result == -1
	 * 					| else
	 * 					|		getWeaponAt(result) == weapon
	 */
	public int getIndexOfWeapon(Weapon weapon){
		if(hasWeapon(weapon))
			return weapons.indexOf(weapon);
		else
			return -1;
	}
	
	/**
	 * Checks whether or not this worm has the given weapon.
	 * 
	 * @param weapon	The weapon to check.
	 * @return			Whether or not the weapon is in the list of weapons for this worm.
	 * 					| result == (for some int index in 0..getNbWeapons()-1: getWeaponAt(index) == weapon))
	 */
	public boolean hasWeapon(Weapon weapon){
		return weapons.contains(weapon);
	}
	
	/**
	 * Selects the next weapon for this worm.
	 * 
	 * @post	If this worm has multiple weapons, this method will select the next one.
	 * 			| if(getNbWeapons() > 0)
	 * 			|		new.getSelectedWeapon() == getWeaponAt((getIndexOfWeapon(getSelectedWeapon)+1)%getNbWeapons())
	 */
	public void selectNextWeapon(){
		if(getNbWeapons() == 0)
			selectedWeapon = 0;
		else{
			++selectedWeapon;
			selectedWeapon %= getNbWeapons();
		}
	}
	
	/**
	 * Get the weapon at the given index.
	 * 
	 * @param index			The index of the weapon in the list
	 * @throws IndexOutOfBoundsException	
	 * 						If the given index is larger than or equal to getNbWeapons() or smaller than zero.
	 * 						| index < 0 || index >= getNbWeapons()
	 */
	@Basic @Raw
	public Weapon getWeaponAt(int index) throws IndexOutOfBoundsException{
		return weapons.get(index);
	}
	
	/**
	 * Returns the number of weapons for this Worm.
	 */
	@Basic @Raw
	public int getNbWeapons(){
		return weapons.size();
	}
	
	/**
	 * Checks whether or not the worm can have as many weapons as provided.
	 * 
	 * @param nbWeapons		The number (of weapons) to check.
	 * @return				Whether or not the number is bigger than or equal to zero.
	 * 						| result == (nbWeapons >= 0)
	 */
	public boolean canHaveAsNbWeapons(int nbWeapons){
		return nbWeapons >= 0;
	}
	
	/**
	 * Adds a weapon to the list of weapons for this worm.
	 * 
	 * @param weapon	The weapon to add.
	 * @post			If the weapon is valid and not yet in the list of weapons, the weapon is added at the end of the list.
	 * 					| if(canHaveAsWeapon(weapon) && canHaveAsNbWeapons(getNbWeapons()+1) && !hasWeapon(weapon))
	 * 					| then new.getWeaponAt(getNbWeapons()) == weapon && new.getNbWeapons() == getNbWeapons()+1
	 */
	public void addWeapon(Weapon weapon){
		if(!canHaveAsWeapon(weapon))
			return;
		if(!canHaveAsNbWeapons(getNbWeapons()+1))
			return;
		if(!hasWeapon(weapon)){
			weapons.add(weapon);
		}
	}
	
	/**
	 * Checks whether or not this worm can have the weapon as a Weapon.
	 * 
	 * @param weapon	The weapon to check.
	 * @return			Whether or not the weapon does NOT equal the null-reference
	 * 					|	weapon != null
	 */
	public boolean canHaveAsWeapon(Weapon weapon){
		return weapon != null;
	}
	
	/**
	 * Remove a weapon from the list of weapons for this worm.
	 * 
	 * @param weapon	The weapon to remove
	 * @post			If the weapon is in the list of weapons, and the worm can survive with a weapon less, then the weapon is removed and all weapons further in the list or shifted to the right.
	 * 					| if(canHaveAsNbWeapons(getNbWeapons()-1) && hasWeapon(weapon))
	 * 					| 		then !new.hasWeapon(weapon) && new.getNbWeapons() == getNbWeapons()-1
	 * 					|		&& for each int index in getIndexOfWeapon(weapon)+1..getNbWeapons()-1: new.getWeaponAt(index-1) == getWeaponAt(index)
	 * @post			If the weapon was the active weapon, the next weapon will be selected, if there's any.
	 * 					| if(getSelectedWeapon() == weapon)
	 * 					|	if(getNbWeapons() > 1)
	 * 					|		new.getSelectedWeapon() == getWeaponAt((getIndexOfWeapon(getSelectedWeapon)+1)%new.getNbWeapons())
	 * 					|	else new.getSelectedWeapon() == null
	 */
	public void removeWeapon(Weapon weapon){
		if(!canHaveAsNbWeapons(getNbWeapons()-1))
			return;
		if(hasWeapon(weapon)){
			int index = getIndexOfWeapon(weapon);
			weapons.remove(weapon);
			
			if(index < selectedWeapon)
				selectedWeapon--;
			if(getNbWeapons() > 0)
				selectedWeapon %= getNbWeapons();
		}
	}
	
	/**
	 * The list of weapons for this worm.
	 * 
	 * @invar		Every weapon only appears once in the list.
	 *				| for each i,j in 0..weapons.size()-1:
	 *				|    ((i == j) ||
	 *				|     (weapons.get(i) != weapons.get(j))
	 * @invar		The null reference is not in the list of weapons
	 * 				| for each weapon in weapons:
	 * 				|	(weapon != null)
	 */
	private final List<Weapon> weapons = new ArrayList<Weapon>();
	/**
	 * The index of the selected weapon in the list of weapons.
	 * 
	 * @invar		If there are weapons, than this index is bigger or equal to 0 and smaller than the number of weapons, else it's 0.
	 * 				| if(0 < getNbWeapons())
	 * 				|		then 0 <= selectedWeapon && selectedWeapon < getNbWeapons()
	 * 				| else
	 * 				|		selectedWeapon == 0
	 */
	private int selectedWeapon = 0;

	/**
	 * Terminates this worm.
	 *
	 * @post	This worm is removed from its team.
	 *			| !new.hasTeam()
	 */
	@Raw
	@Override
	public void terminate() throws IllegalStateException{
		removeTeam();
		super.terminate();
	}

	/** 
	 * Returns the team of this worm 
	 * or null if this worm is not registered in a team. 
	 */
	@Basic @Raw
	public Team getTeam() {
		return this.team;
	}

	/**
	 * Checks whether this worm can have the given team as its team.
	 *
	 * @param	team
	 *			The team to check.
	 * @return	True if the team is null or this worm and the given team are not terminated
	 *			and the team is in the same world as this worm.
	 *			| result == ((team == null) || 
	 *			|				((!team.isTerminated() && !this.isTerminated()) &&
	 *			|				(team.getWorld() == this.getWorld())))
	 */
	@Raw
	public boolean canHaveAsTeam(@Raw Team team) {
		return ((team == null) || 
					((!team.isTerminated() && !this.isTerminated()) &&
					(team.getWorld() == this.getWorld())));
	}

	/**
	 * Checks whether this worm has a proper team.
	 *
	 * @return	True iff this worm can have its current team as its team.
	 *			| result == canHaveAsTeam(getTeam())
	 */
	@Raw
	public boolean hasProperTeam() {
		return canHaveAsTeam(getTeam());
	}

	/** 
	 * Registers the given team as the team of this worm.
	 * 
	 * @param 	team
	 *			The team to be registered.
	 * @post	The worm has the given team as its team.
	 *			| new.getTeam() == team
	 * @throws	IllegalArgumentException
	 *			When this worm cannot have the given team as its team.
	 *			| !canHaveAsTeam(team)
	 * @throws	IllegalArgumentException
	 *			When this worm is already registered with the given team.
	 *			| (hasTeam() && (getTeam() == team))
	 */
	@Raw
	public void setTeam(@Raw Team team) throws IllegalArgumentException {
		if(!canHaveAsTeam(team) || (hasTeam() && (getTeam() == team)))
			throw new IllegalArgumentException();
		this.team = team;
	}
	
	/**
	 * Disassociates this worm's team.
	 *
	 * @effect	Sets this worm's team to the null reference.
	 *			| setTeam(null)
	 */
	public void removeTeam() {
		setTeam(null);
	}

	/**
	 * Checks if this worm has a team
	 * 
	 * @return	Whether this worm's team is not null.
	 *			| result == (getTeam() != null)
	 */
	public boolean hasTeam(){
		return getTeam() != null;
	}

	/**
	 * The team of this worm, or null if this worm does not have a team.
	 */
	private Team team = null;
}
