package worms.model;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.atan2;
import static java.lang.Math.ceil;
import static java.lang.Math.cos;
import static java.lang.Math.round;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.floor;

import static worms.util.ModuloUtil.posMod;
import static worms.util.Util.fuzzyGreaterThanOrEqualTo;
import static worms.util.Util.fuzzyLessThanOrEqualTo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import worms.util.MathUtil;
import worms.util.Util;
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
	 * @see 	#Worm(World, double, double, double, double, String, Team) 
	 * 			The overloaded constructor for info on parameters and effects.
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
	 * 			| addWeapon(new Bazooka())
	 * @effect	Add a Rifle to the list of weapons
	 * 			| addWeapon(new Rifle())
	 * @effect	Add the worm to the given team
	 * 			| setTeam(team)
	 * @throws	NullPointerException
	 * 			The given world is not effective.
	 * 			| world == null
	 */
	@Raw
	public Worm(World world, double x, double y, double direction, double radius, String name, Team team)
			throws IllegalArgumentException,IllegalStateException,NullPointerException{
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
	 * Calculates the cost for a worm to move from its current position
	 * to the given position.
	 * 
	 * @param positionAfterMove
	 * 						The position of this worm after the move.
	 * @return				The result equals a fraction (distance/getRadius()) of the cost of 
	 * 						a unit step in the current direction, rounded up to the next integer.
	 * 						| let
	 * 						|		distance = sqrt(getPosition().squaredDistance(positionAfterMove))
	 * 								direction = posMod(atan2(positionAfterMove.getY()-getYCoordinate(),positionAfterMove.getX()-getXCoordinate()),2*PI)
	 * 						| in:
	 * 						| 		result == (int)(ceil((distance/getRadius())*getUnitStepCost(direction)))
	 */
	public int getCostForMove(Position positionAfterMove){
		double distance = sqrt(getPosition().squaredDistance(positionAfterMove));
		double direction = posMod(
								atan2(
									positionAfterMove.getY()-getYCoordinate(), 
								 	positionAfterMove.getX()-getXCoordinate()),
								 2*PI);
		return (int)(ceil((distance/getRadius())*getUnitStepCost(direction)));
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
	 * Changes this worm's position based on its current position, 
	 * orientation and the terrain.
	 * 
	 * @post	The worm is in a contact location (a location that is passable 
	 * 			for this worm and adjacent to impassable terrain for this worm) ...
	 * 			| if(getWorld().isInsideWorldBoundaries(new.getPosition(), getRadius()))
	 * 			| 	getWorld().getLocationType(new.getPosition(), getRadius()) == LocationType.CONTACT
	 * @effect	... or has left the world.
	 * 			| if(!getWorld().isInsideWorldBoundaries(new.getPosition(), getRadius()))
	 * 			| 	getWorld().removeWorm(this)
	 * @post	The worm's action points will be decreased.
	 * 			| new.getActionPoints() < getActionPoints()
	 * @post	If this worm's new position after the move is a contact location, the worm moves there.
	 *			| if(canMove() && getWorld().getLocationType(getPositionAfterMove(), getRadius()) == LocationType.CONTACT)
	 *			|	new.getPosition() == getPositionAfterMove()
	 * @effect	If this worm'ss new position after the move is a passable location (but not a contact location), 
	 *			the worm first moves there and then falls.
	 *			| if(canMove() && getWorld().getLocationType(getPositionAfterMove(), getRadius()) == LocationType.PASSABLE)
	 *			|	setPosition(getPositionAfterMove())
	 *			|	fall()
	 * @effect	The worm eats all food rations it touches after the move.
	 * 			| checkForFood()
	 * @effect	Incur an action point cost.
	 * 			| decreaseActionPoints(getCostForMove(distance, direction));
	 * 
	 * (TODO: what if the worm is currently in an IMPASSABLE location.)
	 */
	public void move() throws IllegalStateException{
		if(!canMove())
			throw new IllegalStateException();
		
		Position positionAfterMove = getPositionAfterMove();
		if(!getWorld().isInsideWorldBoundaries(positionAfterMove, getRadius())){
			getWorld().removeWorm(this);
			return;
		}
		decreaseActionPoints(getCostForMove(positionAfterMove));
		setPosition(positionAfterMove);
		if(getWorld().getLocationType(positionAfterMove, getRadius()) == LocationType.PASSABLE)
			fall();
		checkForFood();
	}
	
	/**
	 * Tests whether this worm can move in its current state.
	 * 
	 * @return	Whether the worm can move in its current direction, from its current position
	 * 			and on the current terrain, and whether he has enough action points left to
	 * 			perform the move.
	 * 			| result == ((getPositionAfterMove() != getPosition()) && 
	 *			|			 (getCostForMove(getPositionAfterMove()) <= getActionPoints()))
	 */
	public boolean canMove(){
		Position positionAfterMove = getPositionAfterMove();
		return ((!positionAfterMove.equals(getPosition())) && 
				(getCostForMove(positionAfterMove) <= getActionPoints()));
	}
	
	/**
	 * Object storing a direction in which this worm could move,
	 * the farthest legal distance this worm could move in that direction
	 * and whether the position after the move would be a contact location.
	 */
	private class DirectionInfo{
		public double direction;
		public double distance;
		public boolean contactLocation;
	}
	
	/**
	 * 
	 * 
	 * @return	The new location is a CONTACT or a PASSABLE location.
	 * 			| getWorld().getLocationType(result, getRadius()).isPassable()
	 * @return	The difference between this worms direction
	 * 			and the direction of the move is not greater than 45° (0.7875 rad).
	 * 			| let
	 * 			| 	θ = getDirection()
	 * 			|	s = posMod(atan2(result.getY()-getYCoordinate(), result.getX()-getXCoordinate()),2*PI)
	 * 			| in
	 * 			|	abs(θ-s) <= 0.7875 || abs(2*PI-θ) + abs(s) <= 0.7875 || abs(2*PI-s) + abs(θ) <= 0.7875
	 * @return	The new position is the optimal position to move to for this worm in its current state.
	 * 			(The divergence abs(θ-s) as defined above is minimised while
	 * 			the travelled distance d is maximised, only considering contact locations when
	 * 			at least one possible contact location is found.)
	 * 			| TODO
	 */
	public Position getPositionAfterMove(){
		List<DirectionInfo> directions = new ArrayList<DirectionInfo>();
		double theta = getDirection();
		double dStep = getRadius()/100; // probing distance step.
		double r = getRadius();
		boolean positionFound = false;
		boolean contactPositionFound = false;
		for(double s = theta-0.7875; s <= theta+0.7875; s += 0.0175){
			DirectionInfo directionInfo = new DirectionInfo();
			directionInfo.direction = s;
			
			double distance;
			boolean wasPreviousPositionContactLocation = false;
			boolean imPassablePotitionFound = false;
			
			for(distance = 0; !imPassablePotitionFound && distance < r; distance += dStep){
				Position probePosition = getPosition().offset((distance+dStep)*cos(s),(distance+dStep)*sin(s));
				LocationType locType = getWorld().getLocationType(probePosition, getRadius());
				if(locType.isPassable()) {
					wasPreviousPositionContactLocation = (locType == LocationType.CONTACT);
				} else {
					imPassablePotitionFound = true;
				}
			}
			//FIX distance because 
			distance -= dStep;
			if(distance > 0){
				positionFound = true;
				directionInfo.distance = distance;
				directionInfo.contactLocation = wasPreviousPositionContactLocation;
				directions.add(directionInfo);
				if (wasPreviousPositionContactLocation)
					contactPositionFound = true;
			}
		}
		if(positionFound)
			return getOptimalPosition(directions, contactPositionFound);
		else
			return getPosition();
	}
	
	/**
	 * Given a set of (direction, distance) values, returns a position
	 * for which the distance travelled is maximal while the divergence 
	 * from the worm's current direction is minimal.
	 * 
	 * @param	distances
	 * 			List of DirectionInfo objects.
	 * @param	contactLocationFound
	 * 			Whether a contact location was found among the entries in 'distances'.
	 * @return	The position calculated from the (direction, distance) pair for which the
	 * 			'weigh()' function is maximal. If it is specified that a contact location 
	 * 			was found, only considers (direction, distance) pairs which are contact locations. 
	 * 			| let
	 *  		|	filteredDirections = filter(direction in directions: !contactLocationFound || direction.contactLocation)
	 * 			|	maxDirectionInfo = max(directionInfo in filteredDirections: weigh(directionInfo.distance, directionInfo.direction-getDirection())
	 * 			|	maxDirection = maxDirectionInfo.direction
	 * 			|	maxDistance = maxDirectionInfo.distance
	 * 			| in
	 * 			|	result == (getPosition().offset(maxDistance*cos(maxDirection),
	 * 			|									maxDistance*sin(maxDirection)))
	 */
	@Model
	private Position getOptimalPosition(List<DirectionInfo> directions, boolean contactLocationFound){
		double maxDirection = 0;
		double maxDistance = 0;
		double maxWeightedDistance = -Double.MAX_VALUE;
		for(DirectionInfo directionInfo : directions) {
			if(!contactLocationFound || directionInfo.contactLocation) {
				double direction = directionInfo.direction;
				double distance = directionInfo.distance;
				double divergence = direction-getDirection();
				double weightedDistance = weigh(distance, divergence);
				if(weightedDistance > maxWeightedDistance) {
			    	 maxDistance = distance;
			    	 maxDirection = direction;
			    	 maxWeightedDistance = weightedDistance;
			    }
			}
		}
		return getPosition().offset(maxDistance*cos(maxDirection),maxDistance*sin(maxDirection));
	}
	
	/**
	 * Transforms the given distance under some function
	 * so that distances with a bigger divergence weigh less.
	 * 
	 * @param	distance
	 * @param	divergence
	 * @return	The distance weighted so that distances with a bigger divergence weigh less.
	 * 			| if((dist1 == dist2 && abs(div1) > abs(div2))
	 * 			|	result1 < result2
	 */
	@Model
	private double weigh(double distance, double divergence){
		return distance-abs(divergence);
	}
	
	public void fall(){
		
	}
	
	// /**
	//  * Moves this worm a given number of steps in the current direction.
	//  * 
	//  * @param nbSteps		The number of steps to move this worm.
	//  * @post				This worm has moved nbSteps in the current direction
	//  * 						| fuzzyEquals(new.getXCoordinate(), getXCoordinate()+nbSteps*cos(getDirection)) &&
	//  * 						| fuzzyEquals(new.getYCoordinate(), getYCoordinate()+nbSteps*sin(getDirection))
	//  * @post				The action points are decreased with the cost of the movement.
	//  * 						| new.getActionPoints() == getActionPoints()-getCostForMovement(nbSteps,getDirection())
	//  * @throws IllegalArgumentException
	//  * 						Thrown when nbSteps is less than zero
	//  * 						| nbSteps < 0
	//  * @throws IllegalStateException	
	//  * 						Thrown when this worm has not enough action points to move the given number of steps in the current direction.
	//  * 						| !canMove(nbSteps)
	//  */
	// public void move(int nbSteps) throws IllegalStateException,IllegalArgumentException{
	// 	if(nbSteps < 0)
	// 		throw new IllegalArgumentException("Illegal number of steps");
	// 	if(!canMove(nbSteps))
	// 		throw new IllegalStateException("Has not enough action points to move.");
		
	// 	moveWith(nbSteps*cos(getDirection()),nbSteps*sin(getDirection()));
	// 	decreaseActionPoints(getCostForMove(nbSteps,getDirection()));
	// }
	
	// /**
	//  * Moves this worm with the given number of metres along the x-axis and the given number of metres along the y-axis.
	//  * 
	//  * @param x		The number of metres to move along the x-axis
	//  * @param y		The number of metres to move along the y-axis
	//  * @effect		Move to the position with an offset of (x,y) metres
	//  * 				setPosition(getPosition().offset(x,y))
	//  * @throws IllegalArgumentException
	//  * 				Thrown when x or y is not a valid number
	//  * 				| Double.isNaN(x) || Double.isNaN(y)
	//  */
	// public void moveWith(double x, double y) throws IllegalArgumentException{
	// 	setPosition(getPosition().offset(x,y));
	// }
	
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
	protected void replenishActionPoints(){
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
	protected void decreaseHitPoints(int amount){
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
	protected void increaseHitPoints(int amount){
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
	 * @return	False if the entity lies in the world.
	 * 			| if(getWorld().isInsideWorldBoundaries(getPosition(), getRadius())) then result == false
	 * @note	This return-clause should only be used if all other return-clauses (of super-method) can't determine the result of this method!
	 */
	@Override
	public boolean afterJumpRemove(){
		//Implied in super
		/*if(getWorld().isInsideWorldBoundaries(getPosition(), getRadius()))
			return false;*/
		return super.afterJumpRemove();
	}
	
	/**
	 * @post	The hit points of other worms are left untouched
	 * 			| for each worm in getWorld().getWorms(): (new worm).gitHitPoints() == worm.getHitPoints()
	 * @post	The action points are set to zero.
	 * 			| new.getActionPoints() == 0
	 * @effect	Eat food rations the worm now touches if the worm doesn't leave the world.
	 * 			| if(!afterJumpRemove()) then checkForFood()
	 */
	@Override
	public void handleAfterJump(){
		decreaseActionPoints(getActionPoints());
		super.handleAfterJump();
		if(!isTerminated())
			checkForFood();
	}
	
	/**
	 * @return	The (virtual) force exerted by this worm (in Newton) equals the sum of 5 times its action points and 
	 * 			its weight (its mass times the gravitational acceleration).
	 * 			| fuzzyEquals(result, ((5*getActionPoints()) + (getMass()*GRAVITATIONAL_ACCELERATION)))
	 */
	@Override
	public double getJumpForce(){
		return ((5*getActionPoints()) + (getMass()*GRAVITATIONAL_ACCELERATION));
	}
	
	/**
	 * @return		False when this worm has no action points left.
	 * 				| if(getActionPoints() == 0) then result == false
	 * @note		This return-clause should only be used if all other return-clauses (of super-method) can't determine the result of this method!
	 */
	@Override
	public boolean canJump(){
		if(getActionPoints() == 0)
			return false;
		return super.canJump() ;
	}
	
	/**
	 * @return	False if the position overlaps with any entity.
	 * 			| if(for some entity in getWorld().getEntities(): entity.collidesWith(position,getRadius())) then result == false
	 * @note	This return-clause should only be used if all other return-clauses (of super-method) can't determine the result of this method!
	 */
	@Override
	public boolean blocksJump(Position position){
		//Implied in super
		/*for(Entity entity : getWorld().getEntities())
			if(entity.collidesWith(position,getRadius()))
				return false;*/
		return super.blocksJump(position);
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
	 * @post			If the weapon is in the list of weapons, and the worm can survive with a weapon less, then the weapon is removed and all weapons further in the list or shifted to the left.
	 * 					| if(canHaveAsNbWeapons(getNbWeapons()-1) && hasWeapon(weapon))
	 * 					| 		then !new.hasWeapon(weapon) && new.getNbWeapons() == getNbWeapons()-1
	 * 					|		&& for each int index in getIndexOfWeapon(weapon)+1..getNbWeapons()-1: new.getWeaponAt(index-1) == getWeaponAt(index)
	 * @post			If the weapon was the active weapon (and will be removed), the next weapon will be selected, if there's any.
	 * 					| if(canHaveAsNbWeapons(getNbWeapons()-1) && getSelectedWeapon() == weapon)
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
	
	/**
	 * Checks whether or not this worm can fall
	 * 
	 * @return	False if the worm is terminated.
	 * 			| if(isTerminated()) then result == false
	 * @return	False if the given position is a contact location or impassable terrain. True if it's passable terrain
	 * 			| result == getWorld().isPassablePosition(getPosition(), 1.1*getRadius())
	 */
	public boolean canFall(){
		if(isTerminated())
			return false;
		return getWorld().isPassablePosition(getPosition(), 1.1*getRadius());
	}
	
	/**
	 * Lets the worm fall
	 * 
	 * @effect	Set the new position to the position after the fall.
	 * 			| setPosition(findFallPosition())
	 * @effect	Remove the worm from the world if it's no longer within the boundaries.
	 * 			| if(!getWorld().isInsideWorldBoundaries(findFallPosition(), getRadius())) then getWorld().removeWorm(this);
	 * @effect	Subtract 3 HP per metre fallen
	 * 			| let
	 * 			|		long damage = round(floor(3*sqrt(oldPos.squaredDistance(findFallPosition()))))
	 * 			| in:
	 * 			|		if(damage > Integer.MAX_VALUE) then decreaseHitPoints(Integer.MAX_VALUE)
	 * 			|		else decreaseHitPoints((int) damage);
	 * @throws IllegalStateException 
	 * 			When this worm can't fall.
	 * 			| !canFall()
	 */
	public void fall()throws IllegalStateException{
		if(!canFall())
			throw new IllegalStateException();

		Position oldPos = getPosition();
		Position newPos = findFallPosition();
		setPosition(newPos);
		
		if(!getWorld().isInsideWorldBoundaries(newPos, getRadius()))
			getWorld().removeWorm(this);
		
		double distance = sqrt(oldPos.squaredDistance(newPos));
		long damage = round(floor(3*distance));
		if(damage > Integer.MAX_VALUE)
			decreaseHitPoints(Integer.MAX_VALUE);
		else
			decreaseHitPoints((int) damage);
	}
	
	/**
	 * Finds the first location that would block the falling of the worm.
	 * 
	 * @return	There's no position beneath this position that doesn't block the fall and has a larger y-coordinate than the result.
	 * 			| for each pos in Position:
	 * 			|		pos.getX() != getPosition().getX()
	 * 			|		|| fuzzyGreatherThanOrEqualTo(pos.getY(),result.getY())
	 * 			|		|| !blocksFall(pos)
	 */
	public Position findFallPosition(){
		Position pos = getPosition();
		if(blocksFall(pos))
			return pos;
		
		double offset = getWorld().cellHeight();
		pos.offset(0, -offset);
		while(true){
			if(blocksFall(pos))
				break;
			pos = pos.offset(0, -offset);
		}
		pos = pos.offset(0, offset);
		
		offset = Util.DEFAULT_EPSILON;
		while(true){
			if(blocksFall(pos))
				break;
			pos = pos.offset(0, -offset);
		}
		return pos;
	}
	
	/**
	 * Checks whether or not the given position would stop the fall of this worm.
	 * 
	 * @param position	The position to check.
	 * @return	True if the position is not within the world boundaries.
	 * 			| if(!getWorld().isInsideWorldBoundaries(position, getRadius())) then result == true
	 * @return	True if the position is a contact location or impassable terrain for the given worm.
	 * 			| if(!getWorld().isPassablePosition(position, getRadius()*1.1)) then result == true
	 * @return	False in all other cases
	 * 			| result == false
	 */
	public boolean blocksFall(Position position){
		if(!getWorld().isInsideWorldBoundaries(position, getRadius()))
			return true;
		if(!getWorld().isPassablePosition(position, getRadius()*1.1))
			return true;
		return false;
	}
	
	/**
	 * Checks for food rations overlapping with this worm and eats them if found.
	 * 
	 * @effect	If this worm overlaps with one or more food rations, he eats them.
	 * 			| for food in getWorld.getFoods():
	 * 			| 	if(food.collidesWith(this)):
	 *			|		eat(food);
	 */
	public void checkForFood(){
		for (Food food : getWorld().getFoods())
			if(food.collidesWith(this))
				eat(food);
	}
	
	/**
	 * Makes this worm eat the given food ration, and by doing so, 
	 * increases his radius by 10%. The eaten food ration is removed 
	 * from the world.
	 * 
	 * @param	food
	 * 			The food ration to be eaten.
	 * @post	The worm's radius has increased by 10%.
	 * 			| new.getRadius() == 1.1*getRadius()
	 * @effect	The given food ration is removed from the world.
	 * 			| getWorld().removeFood(food)
	 * @effect	The worm will be removed from its world if it has grown to be
	 * 			outside the world's boundaries.
	 * 			| if(!getWorld().isInsideWorldBoundaries(getPosition(), new.getRadius()))
	 * 			|	getWorld().removeWorm(this)
	 * @note	The worm may, as a result of eating food, be placed at impassable terrain for this worm.
	 * 			(Fatties may get stuck).
	 */
	public void eat(Food food){
		setRadius(getRadius()*1.1);
		getWorld().removeFood(food);
		if(!getWorld().isInsideWorldBoundaries(getPosition(), getRadius()))
			getWorld().removeWorm(this);
	}
}
