package worms.util;

import static worms.util.Util.*;
import static org.junit.Assert.*;

public class AssertUtil {
	/**
	 * @effect 	Calls assertFuzzyEquals with precision = DEFAULT_EPSILON
	 * 			| assertFuzzyEquals(x, y, DEFAULT_EPSILON);
	 */
	public static void assertFuzzyEquals(double x, double y){
		assertFuzzyEquals(x, y, DEFAULT_EPSILON);
	}
	
	/**
	 * @effect 	Calls the assertEquals with the same arguments 
	 * 			(this method just renames assertEquals to a more appropiate name).
	 * 			| assertEquals(x, y, precision);
	 */
	public static void assertFuzzyEquals(double x, double y, double precision){
		assertEquals(x, y, precision);
	}
}
