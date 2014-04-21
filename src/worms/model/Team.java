package worms.model;

import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A team of worms in a world.
 *
 * @invar	This team is properly associated with a World.
 * 			| hasProperWorld()
 * @invar	This team has a valid name.
 * 			| isValidName(getName())
 */
public class Team {

	/**
	 * Creates a new team in the given world, with the given name.
	 * 
	 * @param world
	 * 			The world with which the team will be associated.
	 * @param name
	 * 			The name of the new team.
	 * @effect	Set the name to the given name
	 * 			| setName(name);
	 * @effect	Adds the team to the given world.
	 * 			| world.addTeam(this)
	 * @throws	NullPointerException
	 * 			The given world is not effective.
	 * 			| world == null
	 */
	@Raw
	public Team(World world, String name) throws IllegalArgumentException,IllegalStateException,NullPointerException{
		setName(name);
		world.addTeam(this);
	}

	/**
	 * Checks whether this team is terminated.
	 */
	@Basic @Raw
	public boolean isTerminated(){
		return isTerminated;
	}
	
	/**
	 * Terminates this team
	 *
	 * @post	This team is terminated.
	 * 			| new.isTerminated()
	 * @post	The team has broken its side of the association
	 *			with its world.
	 *			| !new.hasWorld()
	 * @throws	IllegalStateException
	 * 			When the world still references this Team.
	 * 			| getWorld().hasAsTeam(this)
	 */
	@Raw
	public void terminate() throws IllegalStateException{
		if(!isTerminated()){
			if(world.hasAsTeam(this))
				throw new IllegalStateException();
			world = null;
			isTerminated = true;
		}
	}
	
	private boolean isTerminated = false;
	
	/**
	 * Checks whether this team can be in the given world.
	 * 
	 * @param world	The world to be checked against.
	 * @return	If this team is terminated, true if the given world is 
	 *			not effective.
	 *			If this entry is not terminated, true if
	 *			the given world is effective and not terminated.
	 *			| if(isTerminated)
	 * 			|	result == (world == null)
	 * 			| else
	 * 			|	result == (world != null && !world.isTerminated())
	 */
	public boolean canHaveAsWorld(World world){
		if(isTerminated())
			return (world == null);
		else
			return (world != null && !world.isTerminated());
	}
	
	/**
	 * Checks whether this team is properly associated with its world.
	 * 
	 * @return	Whether this team can have its current world as its world
	 * 			and whether its world has registered this team (if the world doesn't equal the null references).
	 * 			| result == (canHaveAsWorld(world) && (world == null || world.hasAsTeam(this)))
	 */
	public boolean hasProperWorld(){
		return (canHaveAsWorld(world) && (world == null || world.hasAsTeam(this)));
	}

	/**
	 * Checks whether this team is in a world.
	 * 
	 * @return	True iff this team's world is effective.
	 *			| result == (getWorld() != null)
	 */
	public boolean hasWorld(){
		return (getWorld() != null);
	}

	/**
	 * Sets this team's world to the given world.
	 * 
	 * @param	world
	 *			The new world this team will belong to.
	 * @post	This team has a world.
	 *			| hasWorld()
	 * @post	This team references the given world.
	 *			| new.getWorld() == world
	 * @throws	IllegalArgumentException
	 * 			This team cannot have the given world as its world
	 * 			or the given world has not (yet) registered this team. 
	 * 			| (!canHaveAsWorld(world) || !world.hasAsTeam(this))
	 * @throws	IllegalStateException
	 * 			This team already has a world.
	 * 			| hasWorld()
	 */
	@Raw
	public void setWorld(@Raw World world) throws IllegalArgumentException,IllegalStateException{
		if(!canHaveAsWorld(world) || !world.hasAsTeam(this))
			throw new IllegalArgumentException();
		if(hasWorld())
			throw new IllegalStateException("This team already has a world.");
		this.world = world;
	}
	
	/**
	 * Returns the world this team belongs to.
	 */
	@Raw @Basic
	public World getWorld(){
		return world;
	}
	
	private World world = null;

	/**
	 * Returns the name of this team.
	 */
	@Basic @Raw
	public String getName(){
		return this.name;
	}
	
	/**
	 * Checks whether or not the given name is a valid name for a team.
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
	 * Checks whether or not the given character is a valid character to be used in a name for a team.
	 * 
	 * <p>The valid characters consist of only(latin) letters (both uppercase and lowercase)
	 * The list of valid characters thus looks like this: [A-Za-z].</p>
	 * 
	 * @param c The character to check.
	 * @return	Whether or not the character is a (latin) letter.
	 * 			| result == ((65 <= c && c <= 90) || (97 <= c && c <= 122))
	 */
	public static boolean isValidCharacterForName(char c){
		//Uppercase characters are allowed.
		if(65 <= c && c <= 90)
			return true;
		//Lowercase characters are allowed.
		if(97 <= c && c <= 122)
			return true;
		//All the rest isn't allowed.
		return false;
	}
	
	/**
	 * Sets the name for this team.
	 * 
	 * @param name The name to set
	 * @throws IllegalArgumentException 
	 * 			When the given name is not a valid name.
	 * 			| !isValidName(name)
	 * @post 	The name for this team equals the given name.
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
	 * Registers the given worm in this team.
	 * 
	 * @param 	worm
	 *			The worm to be registered.
	 * @post	| (new worm).getTeam() == this
	 * @post	| new.getWorms().contains(worm)
	 * @throws	NullPointerException
	 *			When the given worm is not effective.
	 *			| (worm == null)
	 * @throws	IllegalArgumentException
	 *			When the given worm cannot have this team as its team.
	 *			| !worm.canHaveAsTeam(this)
	 */
	public void addWorm(Worm worm) throws NullPointerException,IllegalArgumentException{
		worm.setTeam(this);
	}

	/**
	 * Returns all worms that are registered with this team (and only those).
	 *
	 * @return	| for each worm in this.getWorld().getWorms():
	 *			|	if(worm.getTeam() == this)
	 *			|		result.contains(worm)
	 * @return  | for each worm in result:
	 *			|	worm.getTeam() == this && worm.getWorld() == getWorld()
	 */
	public Set<Worm> getWorms(){
		Set<Worm> result = new HashSet<Worm>();
		for (Worm worm : getWorld().getWorms())
			if(worm.getTeam() == this)
				result.add(worm);
		return result;
	}
}
