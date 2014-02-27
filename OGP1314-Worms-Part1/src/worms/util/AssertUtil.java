package worms.util;

import static worms.util.Util.*;
import static org.junit.Assert.*;

public class AssertUtil {
	public static void assertFuzzyEquals(double x, double y){
		assertTrue(fuzzyEquals(x,y));
	}
	
	public static void assertFuzzyEquals(double x, double y, double precision){
		assertTrue(fuzzyEquals(x,y,precision));
	}
}
