package worms.util;

import static worms.util.Util.*;
import static org.junit.Assert.*;

public class AssertUtil {
	/**
	 * @effect 	Wraps a JUnit assertTrue around a fuzzyEquals
	 * 			| assertTrue(fuzzyEquals(x,y));
	 */
	public static void assertFuzzyEquals(double x, double y){
		assertTrue(fuzzyEquals(x,y));
	}
	
	/**
	 * @effect 	Wraps a JUnit assertTrue around a fuzzyEquals (with given precision)
	 * 			| assertTrue(fuzzyEquals(x,y,precision));
	 */
	public static void assertFuzzyEquals(double x, double y, double precision){
		assertTrue(fuzzyEquals(x,y,precision));
	}
}
