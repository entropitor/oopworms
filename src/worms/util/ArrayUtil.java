package worms.util;

public class ArrayUtil {

	/**
	 * Returns a deep clone of a 2D boolean array.
	 * 
	 * @param src	The 2D-array to clone
	 * @return		| deepEquals(result, src)
	 * @return		| !src.equals(result)
	 * @return		| for each rowNumber in 0..src.lenght-1:
	 * 				|	!src[rowNumber].equals(result[rowNumber])
	 */
	public static boolean[][] deepClone(boolean[][] src){
		assert (src != null);
		boolean[][] dest = new boolean[src.length][];
		for (int i = 0; i < src.length; i++) {
			dest[i] = src[i].clone();
		}
		return dest;
	}
	
	/**
	 * Checks if 2 arrays deep equal each other
	 * @param src		The first array
	 * @param dest		The second array
	 * @return			| if(src == null || dest == null)
	 * 					| 		result == (src == null) && (dest == null)
	 * @return			| if(src.length != dest.length)
	 * 					|		result == false
	 * @return			| if(
	 * 					|		for each rowNumber in 0..src.length-1:
	 * 					|			src[rowNumber].length == dest[rowNumber].length
	 * 					|			&& for each columNumber in 0..src[rowNumber].length-1
	 * 					|					src[rowNumber][columNumber] == dest[rowNumber][columnNumber]
	 * 					| )
	 * 					| then result == true
	 * 					| else result == false
	 */
	public static boolean deepEquals(boolean[][] src, boolean[][] dest){
		if(src == null || dest == null)
			return (src == null) && (dest == null);
		
		if(src.length != dest.length)
			return false;
		
		for (int i = 0; i < src.length; i++) {
			if(src[i].length != dest[i].length)
				return false;
			
			for (int j = 0; j < src[i].length; j++) {
				if(src[i][j] != dest[i][j])
					return false;
			}
		}
		return true;
	}
}
