package worms.model;

import static java.lang.Math.PI;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static worms.util.AssertUtil.assertFuzzyEquals;
import static worms.util.Util.fuzzyEquals;

import org.junit.Before;
import org.junit.Test;

public class WormTest_Turn {
	private Worm willy;
	
	@Before
	public void setup(){
		//				   x    y    dir.     r       name
		willy  = new Worm(112, 358, 1.321, 34.55, "Willy Wonka");
	}

	@Test
	public void testIsValidTurningAngle_TrueCases(){
		assertTrue(Worm.isValidTurningAngle(1.321));
		assertTrue(Worm.isValidTurningAngle(0));
		assertTrue(Worm.isValidTurningAngle(PI-1E-3));
		assertTrue(Worm.isValidTurningAngle(-PI));
	}
	
	@Test
	public void testIsValidTurningAngle_NonInclusiveUpperBound(){
		assertFalse(Worm.isValidTurningAngle(PI));
	}
	
	@Test
	public void testIsValidTurningAngle_NaN(){
		assertFalse(Worm.isValidTurningAngle(Double.NaN));
	}
	
	@Test
	public void testIsValidTurningAngle_TooPositive(){
		assertFalse(Worm.isValidTurningAngle(1964));
		assertFalse(Worm.isValidTurningAngle(PI+1e-2));
	}
	
	@Test
	public void testIsValidTurningAngle_TooNegative(){
		assertFalse(Worm.isValidTurningAngle(-PI-1e-2));
	}
	
	@Test
	public void testGetTurningCost(){
		assertEquals(Worm.getTurningCost(0), 0);
		assertEquals(Worm.getTurningCost(1), 10);
		assertEquals(Worm.getTurningCost(-1.321), 13);
		assertEquals(Worm.getTurningCost(PI-1E-3), 30);
		assertEquals(Worm.getTurningCost(-PI), 30);
	}
	
	@Test
	public void testCanTurn(){
		Worm fatboy = new Worm(0, 0, 0, 20, "Big radius so lots 'o APs");
		assertTrue(fatboy.canTurn(-PI));
		
//		Minimum radius is 0.25 atm., so no testing in this way.
//		Worm slim = new Worm(0, 0, 0, 0.1889, "Radius so APs is thirty");
//		assertTrue(slim.canTurn(PI));
//		
//		Worm nope = new Worm(0, 0, 0, 0.1000, "Radius too small so not enough APs");
//		assertFalse(nope.canTurn(PI));
		
		Worm slim = new Worm(0, 0, 0, 0.25, "Slim shady");
		assertEquals(slim.getActionPoints(), 70);
		assertTrue(slim.canTurn(-PI));
		
		slim.turn(-PI);
		assertEquals(slim.getActionPoints(), 40);
		assertTrue(slim.canTurn(-PI));
		
		slim.turn(-PI/3);
		assertEquals(slim.getActionPoints(), 30);
		assertTrue(slim.canTurn(-PI));
		
		slim.turn(-PI/30);
		assertEquals(slim.getActionPoints(), 29);
		assertFalse(slim.canTurn(-PI));
	}
	
	@Test
	public void testTurn(){
		assertFuzzyEquals(willy.getDirection(), 1.321);
		assertEquals(willy.getActionPoints(), 183466713);
		
		willy.turn(1);
		assertFuzzyEquals(willy.getDirection(), 2.321);
		assertEquals(willy.getActionPoints(), 183466713-10);
		
		willy.turn(-1);
		assertFuzzyEquals(willy.getDirection(), 1.321);
		assertEquals(willy.getActionPoints(), 183466713-10-10);
		
		willy.turn(-1.321);
		assertTrue(fuzzyEquals(willy.getDirection(), 0));
		assertEquals(willy.getActionPoints(), 183466713-10-10-13);
		
		willy.turn(-PI);
		assertFuzzyEquals(willy.getDirection(), PI);
		assertEquals(willy.getActionPoints(), 183466713-10-10-13-30);
	}
}
