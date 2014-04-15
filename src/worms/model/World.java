package worms.model;

import java.util.Random;

import worms.util.ArrayUtil;
import static java.lang.Math.floor;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class representing a game world with a width, height.
 * 
 * @invar	| isValidWidth(getWidth()) && isValidHeight(getHeight())
 * @invar	| isValidPassableMap(getPassableMap())
 */
public class World {

	/**
	 * Creates a new world with a given width, height, passableMap and random number generator
	 * 
	 * @param width
	 * 				The width of the world (in meter)
	 * @param height
	 * 				The height of the world (in meter)
	 * @param passableMap
	 * 				A rectangular matrix indicating which parts of the terrain are passable and impassable. 
	 * 				This matrix is derived from the transparency of the pixels in the image file of the terrain.
	 * 				passableMap[r][c] is true if the location at row r and column c is passable, 
	 * 				and false if that location is impassable. 
	 * 				The elements in the first row (row 0) represent the pixels at the top of the terrain (i.e., largest y-coordinates). 
	 * 				The elements in the last row (row passableMap.length-1) represent pixels at the bottom of the terrain (smallest y-coordinates). 
	 * 				The elements in the first column (column 0) represent the pixels at the left of the terrain (i.e., smallest x-coordinates). 
	 * 				The elements in the last column (column passableMap[0].length-1) represent the pixels at the right of the terrain (i.e., largest x-coordinates).
	 * @param random
	 * 				A random number generator, seeded with the value obtained from the command line or from GUIOptions, 
	 * 				that can be used to randomize aspects of the world in a repeatable way.
	 * @post	| new.getWidth() == width
	 * @post	| new.getHeight() == height
	 * @effect	| setPassableMap(passableMap)
	 * @throws IllegalArgumentException
	 * 			| !isValidWidth(width) || !isValidHeight(height)
	 */
	@Raw
	public World(double width, double height, boolean[][] passableMap, Random random) throws IllegalArgumentException{
		// TODO Auto-generated constructor stub
		
		if(!isValidWidth(width) || !isValidHeight(height))
			throw new IllegalArgumentException();
		
		this.width = width;
		this.height = height;
		setPassableMap(passableMap);
	}
	
	/**
	 * Gets the width of this world (in metres).
	 */
	@Raw @Basic @Immutable
	public double getWidth(){
		return width;
	}
	
	/**
	 * Gets the height of this world (in metres).
	 */
	@Raw @Basic @Immutable
	public double getHeight(){
		return height;
	}
	
	/**
	 * Gets the upperbound to the width of worlds.
	 * 
	 * @return	| result >= 0
	 */
	public static double getWidthUpperBound(){
		return Double.MAX_VALUE;
	}
	
	/**
	 * Gets the upperbound to the height of worlds.
	 * 
	 * @return	| result >= 0
	 */
	public static double getHeightUpperBound(){
		return Double.MAX_VALUE;
	}
	
	/**
	 * Checks if the given width is a valid width.
	 * 
	 * @param width		The width to check
	 * @return			| result == (0 <= width && width <= getWidthUpperBound())
	 */
	public static boolean isValidWidth(double width){
		return 0 <= width && width <= getWidthUpperBound();
	}
	
	/**
	 * Checks if the given height is a valid height
	 * 
	 * @param height	The height to check
	 * @return			| result == (0 <= height && height <= getHeightUpperBound())
	 */
	public static boolean isValidHeight(double height){
		return 0 <= height && height <= getHeightUpperBound();
	}
	private double width,height;
	
	/**
	 * Checks if a position is inside the world boundaries.
	 * 
	 * @param pos	The position to check
	 * @return		| result == (0 <= pos.getX() && pos.getX() <= getWidth()
	 * 				|			&& 0 <= pos.getY() && pos.getY() <= getHeight())
	 */
	@Raw
	public boolean isInsideWorldBoundaries(Position pos){
		return 0 <= pos.getX() && pos.getX() <= getWidth()
				&& 0 <= pos.getY() && pos.getY() <= getHeight();
	}
	
	/**
	 * Checks if the given position is a position that is passable for entities.
	 * 
	 * @param pos	The position to check
	 * @return	| if(getPassableMap().length == 0 || getPassableMap()[0].length == 0)
	 * 			|	result == true
	 * @return	| let nbCellsWidth = getPassableMap()[0],
	 * 			|     nbCellsHeight = getPassableMap().length,
	 * 			|     row = (int) (nbCellsHeight - 1 - floor(pos.getY() / (getHeight()/nbCellsHeight))),
	 * 			|     column = (int)floor(pos.getX() / (getWidth()/nbCellsWidth))
	 * 			| in (result == getPassableMap()[row][column])
	 * @throws IllegalArgumentException
	 * 			|	!isInsideWorldBoundaries(pos)
	 */
	public boolean isPassablePosition(Position pos) throws IllegalArgumentException{		
		if(!isInsideWorldBoundaries(pos))
			throw new IllegalArgumentException();

		boolean[][] map = getPassableMap();
		
		if(map.length == 0 || map[0].length == 0)
			 return true;

		int nbCellsWidth = map[0].length;
		int nbCellsHeight = map.length;

		double cellWidth = getWidth()/nbCellsWidth;
		double cellHeight = getHeight()/nbCellsHeight;
		
		//double (-> long) -> int conversion shouldn't be a problem because we're not expecting
		//levels with passableMaps that has 2 billion rows/columns.
		int column = (int)floor(pos.getX() / cellWidth);
		int row = (int) (nbCellsHeight - 1 - floor(pos.getY() / cellHeight));
		return map[row][column];
	}
	
	/**
	 * Returns (a deep clone of) the passable map for this world 
	 * or null if the passableMap isn't set yet (raw state).
	 */
	@Raw @Basic
	protected boolean[][] getPassableMap(){
		if(passableMap == null)
			return null;
		return ArrayUtil.deepClone(passableMap);
	}
	
	/**
	 * Checks if the given passable map is a valid passable map.
	 * 
	 * @param passableMap	The map to check
	 * @return		| if(passableMap == null)
	 * 				|	then result == false
	 * @return		| result == (for each row in passableMap: (row.length == passableMap[0].length))
	 */
	public static boolean isValidPassableMap(boolean[][] passableMap){
		if(passableMap == null)
			return false;
		for (int i = 1; i < passableMap.length; i++) {
			if(passableMap[i].length != passableMap[0].length)
				return false;
		}
		return true;
	}
	
	/**
	 * Sets the passable map for this world.
	 * 
	 * @param passableMap The map to set
	 * @post	| ArrayUtil.deepEquals(new.getPassableMap(),passableMap)
	 * @throws IllegalArgumentException
	 * 			| !isValidPassableMap(passableMap)
	 */
	@Raw @Model
	private void setPassableMap(boolean[][] passableMap) throws IllegalArgumentException{
		if(!isValidPassableMap(passableMap))
			throw new IllegalArgumentException();
		
		this.passableMap = ArrayUtil.deepClone(passableMap);
	}
	
	private boolean[][] passableMap;
}
