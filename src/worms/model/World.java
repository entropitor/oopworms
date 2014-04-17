package worms.model;

import java.util.Random;

import worms.util.ArrayUtil;
import static java.lang.Math.floor;
import static java.lang.Math.ceil;
import static java.lang.Math.min;
import static java.lang.Math.max;
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
	 * @return		| if(pos == null) result == false
	 * @return		| result == (0 <= pos.getX() && pos.getX() <= getWidth()
	 * 				|			&& 0 <= pos.getY() && pos.getY() <= getHeight())
	 */
	@Raw
	public boolean isInsideWorldBoundaries(Position pos){
		if(pos == null)
			return false;
		return 0 <= pos.getX() && pos.getX() <= getWidth()
				&& 0 <= pos.getY() && pos.getY() <= getHeight();
	}
	
	/**
	 * Checks if a circlular position (with radius) is inside the world boundaries.
	 * 
	 * @param pos		The position to check
	 * @param radius	The radius of the circle.
	 * @return			| if(pos == null) then result == false
	 * @return			| if(Double.isNaN(radius)) then result == false
	 * @return			| result == (isInsideWorldBoundaries(pos.offset(-radius, -radius)) && isInsideWorldBoundaries(pos.offset(radius,radius))
	 */
	@Raw
	public boolean isInsideWorldBoundaries(Position pos, double radius){
		if(pos == null)
			return false;
		if(Double.isNaN(radius))
			return false;
		return isInsideWorldBoundaries(pos.offset(-radius, -radius))
				&& isInsideWorldBoundaries(pos.offset(radius,radius));
	}

	/**
	 * Checks whether the circle with center pos and radius radius is only covering passable terrain.
	 * 
	 * @param pos		The center of the circle.
	 * @param radius	The radius of the circle.
	 * @return			| if(pos == null || Double.isNaN(radius)) result == false
	 * @return			| if(!isInsideWorldBoundaries(pos,radius)) result == false
	 * @return			| if(nbCellRows() == 0 || nbCellColumns() == 0) result == true
	 * @return			If there's an impassable position in the world that lies within the circle, than the result is false.
	 * 					| if( 
	 * 					|		for some Position other in Position:
	 * 					|			other.squaredDistance(pos) < Math.pow(radius,2)
	 * 					|			&& world.isInsideWorldBoundaries(pos)
	 * 					|			&& !getPassableMap()[(int)floor(getCellRowCoordinate(other.getY()))][(int)floor(getCellColumnCoordinate(other.getX()))]
	 * 					|  )
	 * 					|	then result == false
	 * 					| 	else result == true
	 */
	public boolean isPassablePosition(Position pos, double radius){
		/*
		 * Strategy for this method:
		 * 1) Find the horizontal grid lines (of the passableMap) that intersect with the circle
		 * 2) For each line:
		 * 3)		Find the 2 intersections between the horizontal line and the circle
		 * 4)		Find the grid cells that lie within these boundaries between this line and the next
		 * 5)		Check if any of them are impassable.
		 */
		if(pos == null)
			return false;
		if(Double.isNaN(radius))
			return false;
		
		if(!isInsideWorldBoundaries(pos, radius))
			return false;
		
		if(nbCellRows() == 0 || nbCellColumns() == 0)
			 return true;
		
		boolean[][] map = getPassableMap();
		
		//Get the rows in which the circle lies
		int minRow = (int)floor(getCellRowCoordinate(pos.getY()+radius));
		int maxRow = (int)ceil(getCellRowCoordinate(pos.getY()-radius));
		//Get the row in which the center of the circle lies.
		int horizontalRow = (int)floor(getCellRowCoordinate(pos.getY()));
		
		double x0 = pos.getX();
		double y0 = pos.getY();
		
		double nextLocalRadius = 0, localRadius = 0;
		for (int i = minRow; i < maxRow; i++) {
			//Get the 'radius' (half of the chord) at the level of the next row(line). 
			//('radius' = farthest offset from x0 at the level of that row(line).
			if(i != maxRow -1)
				nextLocalRadius = Math.sqrt(Math.pow(radius,2)-Math.pow(getYCoordinate(i+1)-y0,2));
			
			//Get the first and last column that overlaps with the circle in this row.
			int minColumn = (int)floor(min(getCellColumnCoordinate(x0 - nextLocalRadius), getCellColumnCoordinate(x0 - localRadius)));
			int maxColumn = (int)ceil(max(getCellColumnCoordinate(x0 + nextLocalRadius), getCellColumnCoordinate(x0 + localRadius)));
			
			
			//Circle crosses a Vertical Line twice within one grid row.
			//Only possible at the row of the center of the circle.
			if(i == horizontalRow){
				while(x0-getXCoordinate(minColumn) < radius)
					minColumn--;
				while(getXCoordinate(maxColumn)-x0 < radius)
					maxColumn++;
			}				
			
			for(int j = minColumn; j < maxColumn; j++){
				if(map[i][j] == false)
					return false;
			}
			localRadius = nextLocalRadius;
		}
		return true;
	}
	
	/**
	 * Returns the number of cells in the width of the passableMap of this world.
	 * 
	 * @return	| if(nbCellRows() == 0) 
	 * 			| 		then result == 0
	 * 			| else 
	 * 			|		result == getPassableMap()[0].length
	 */
	public int nbCellColumns(){
		boolean[][] map = getPassableMap();
		if(map.length == 0)
			return 0;
		return map[0].length;
	}
	
	/**
	 * Returns the number of cells in the height of the passableMap of this world.
	 * 
	 * @return	| result == getPassableMap().length
	 */
	public int nbCellRows(){
		return getPassableMap().length;
	}
	
	/**
	 * Returns the width of a cell of the passableMap of this world (in metres).
	 * 
	 * @return	| result == getWidth()/nbCellColumns()
	 */
	public double cellWidth(){
		return getWidth()/nbCellColumns();
	}
	
	/**
	 * Returns the height of a cell of the passableMap of this world (in metres).
	 * 
	 * @return	| result == getHeight()/nbCellRows()
	 */
	public double cellHeight(){
		return getHeight()/nbCellRows();
	}
	
	/**
	 * Transforms x-coordinates (metres) to the scale of cellcoordinates for the passableMap of this world.
	 * 
	 * @param x		The x-coordinate to transform (in metres).
	 * @return		| result == (x / cellWidth())
	 * @note		The result of this method is a double and can be a non-integer number.
	 */
	public double getCellColumnCoordinate(double x){
		return (x / cellWidth());
	}
	
	/**
	 * Transforms y-coordinates (metres) to the scale of cellcoordinates for the passableMap of this world
	 * 
	 * @param y		The y-coordinate to transform (in metres).
	 * @return		| result == (nbCellRows() - (y / cellHeight()))
	 * @note		The result of this method is a double and can be a non-integer number.
	 */
	public double getCellRowCoordinate(double y){
		return nbCellRows() - (y / cellHeight());
	}
	
	/**
	 * Transforms cell(column)coordinates (for the passableMap of this world) to the scale of x-coordinates (in metres).
	 * 
	 * @param columnCoordinate		The cell(column)coordinate
	 * @return						| result == cellWidth()*columnCoordinate
	 */
	public double getXCoordinate(int columnCoordinate){
		return cellWidth()*columnCoordinate;
	}
	
	/**
	 * Transforms cell(row)coordinates (for the passableMap of this world) to the scale of y-coordinates (in metres).
	 * 
	 * @param rowCoordinate		The cell(row)coordinate
	 * @return						| result == getHeight()-cellHeight()*rowCoordinate
	 */
	public double getYCoordinate(int rowCoordinate){
		return getHeight()-cellHeight()*rowCoordinate;
	}
	
	/**
	 * Gets the locationtype of a given position and for an entity with a given radius.
	 * 
	 * @param pos		The position to check.
	 * @param radius	The radius of the entity.
	 * @return			| if(!isPassablePosition(pos,radius)) then result == IMPASSABLE
	 * 					| else if(!isPassablePosition(pos,radius*1.1)) then result == CONTACT
	 * 					| else result == PASSABLE
	 */
	public LocationType getLocationType(Position pos, double radius){
		if(!isPassablePosition(pos,radius))
			return LocationType.IMPASSABLE;
		if(!isPassablePosition(pos,radius*1.1))
			return LocationType.CONTACT;
		return LocationType.PASSABLE;
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
	 * @return		| result == (for each boolean[] row in passableMap: (row.length == passableMap[0].length))
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
	 * @post	| if(!isValidPassableMap(passableMap))
	 * 			|		then ArrayUtil.deepEquals(new.getPassableMap(),new boolean[][]{})
	 * 			| 		else ArrayUtil.deepEquals(new.getPassableMap(),passableMap)
	 */
	@Raw @Model
	private void setPassableMap(boolean[][] passableMap){
		if(!isValidPassableMap(passableMap))
			this.passableMap = new boolean[][]{};
		else
			this.passableMap = ArrayUtil.deepClone(passableMap);
	}
	
	private boolean[][] passableMap;
	
	
}
