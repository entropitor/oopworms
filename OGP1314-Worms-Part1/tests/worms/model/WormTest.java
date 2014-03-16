package worms.model;

import static org.junit.Assert.*;
import static worms.util.AssertUtil.*;
import static worms.util.Util.*;

import org.junit.Before;
import org.junit.Test;

public class WormTest {
	private Worm willy;
	private static Worm donald;
	private static final double PRECISION = 1e-6;

	@Before
	public void setup(){
		//				   x    y    dir.     r       name
		willy  = new Worm(112, 358, 1.321, 34.55, "Willy Wonka");
		donald = new Worm(0,     0,     3,     5, "D'Onald Duck");
	}
	
	@Test
	public void testConstructor_LegalCase(){
		//			     	   x       y    dir.       r   name
		Worm worm = new Worm(-8.45, 9.16, Math.PI/2, 2.14, "Bar");

		assertFuzzyEquals(worm.getXCoordinate(), -8.45, PRECISION);
		assertFuzzyEquals(worm.getYCoordinate(), 9.16, PRECISION);
		assertFuzzyEquals(worm.getDirection(), Math.PI/2, PRECISION);
		assertEquals(worm.getName(), "Bar");
		assertFuzzyEquals(worm.getRadius(), 2.14, PRECISION);
		assertFuzzyEquals(worm.getMass(), 43596.78321768277701414403, PRECISION);
		assertEquals(worm.getMaxActionPoints(), 43597);
		assertEquals(worm.getActionPoints(), 43597);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_IllegalXPosition() throws Exception{
		new Worm(Double.NaN, 9.16, Math.PI/2, 2.14, "Bar");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_IllegalYPosition() throws Exception{
		new Worm(-8.45, Double.NaN, Math.PI/2, 2.14, "Bar");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_IllegalName() throws Exception{
		new Worm(-8.45, Double.NaN, Math.PI/2, 2.14, "Foo	Bar");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_IllegalRadius() throws Exception{
		new Worm(-8.45, 9.16, Math.PI/2, 0, "Bar");
	}
	
	@Test
	public void testIsValidTurningAngle_TrueCases(){
		assertTrue(Worm.isValidTurningAngle(1.321));
		assertTrue(Worm.isValidTurningAngle(0));
		assertTrue(Worm.isValidTurningAngle(Math.PI-1E-3));
		assertTrue(Worm.isValidTurningAngle(-Math.PI));
	}
	
	@Test
	public void testIsValidTurningAngle_NonInclusiveUpperBound(){
		assertFalse(Worm.isValidTurningAngle(Math.PI));
	}
	
	@Test
	public void testIsValidTurningAngle_NaN(){
		assertFalse(Worm.isValidTurningAngle(Double.NaN));
	}
	
	@Test
	public void testIsValidTurningAngle_TooPositive(){
		assertFalse(Worm.isValidTurningAngle(1964));
		assertFalse(Worm.isValidTurningAngle(Math.PI+1e-2));
	}
	
	@Test
	public void testIsValidTurningAngle_TooNegative(){
		assertFalse(Worm.isValidTurningAngle(-Math.PI-1e-2));
	}
	
	@Test
	public void testGetTurningCost(){
		assertEquals(Worm.getTurningCost(0), 0);
		assertEquals(Worm.getTurningCost(1), 10);
		assertEquals(Worm.getTurningCost(-1.321), 13);
		assertEquals(Worm.getTurningCost(Math.PI-1E-3), 30);
		assertEquals(Worm.getTurningCost(-Math.PI), 30);
	}
	
	@Test
	public void testCanTurn(){
		Worm fatboy = new Worm(0, 0, 0, 20, "Big radius so lots 'o APs");
		assertTrue(fatboy.canTurn(-Math.PI));
		
//		Minimum radius is 0.25 atm., so no testing in this way.
//		Worm slim = new Worm(0, 0, 0, 0.1889, "Radius so APs is thirty");
//		assertTrue(slim.canTurn(Math.PI));
//		
//		Worm nope = new Worm(0, 0, 0, 0.1000, "Radius too small so not enough APs");
//		assertFalse(nope.canTurn(Math.PI));
		
		Worm slim = new Worm(0, 0, 0, 0.25, "Slim shady");
		assertEquals(slim.getActionPoints(), 70);
		assertTrue(slim.canTurn(-Math.PI));
		
		slim.turn(-Math.PI);
		assertEquals(slim.getActionPoints(), 40);
		assertTrue(slim.canTurn(-Math.PI));
		
		slim.turn(-Math.PI/3);
		assertEquals(slim.getActionPoints(), 30);
		assertTrue(slim.canTurn(-Math.PI));
		
		slim.turn(-Math.PI/30);
		assertEquals(slim.getActionPoints(), 29);
		assertFalse(slim.canTurn(-Math.PI));
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
		
		willy.turn(-Math.PI);
		assertFuzzyEquals(willy.getDirection(), Math.PI);
		assertEquals(willy.getActionPoints(), 183466713-10-10-13-30);
	}
	
	@Test
	public void testIsValidPosition_TrueCase(){
		assertTrue(Worm.isValidPosition(3.546,-8.5649));
	}
	
	@Test
	public void testIsValidPosition_IllegalX(){
		assertFalse(Worm.isValidPosition(Double.NaN, -4.168));
	}
	
	@Test
	public void testIsValidPosition_IllegalY(){
		assertFalse(Worm.isValidPosition(Double.POSITIVE_INFINITY, Double.NaN));
	}
	
	@Test
	public void testIsValidPosition_BothIllegal(){
		assertFalse(Worm.isValidPosition(Double.NaN, Double.NaN));
	}
	
	@Test
	public void testIsValidDirection_TrueCases(){
		assertTrue(Worm.isValidDirection(0));
		assertTrue(Worm.isValidDirection(Math.PI*2-0.001));
		assertTrue(Worm.isValidDirection(Math.PI));
		assertTrue(Worm.isValidDirection(2.14));
	}
	
	@Test
	public void testIsValidDirection_NaN(){
		assertFalse(Worm.isValidDirection(Double.NaN));
	}
	
	@Test
	public void testIsValidDirection_Negative(){
		assertFalse(Worm.isValidDirection(-2));
	}
	
	@Test
	public void testIsValidDirection_TooBig(){
		assertFalse(Worm.isValidDirection(5*Math.PI/2));
	}
	
	@Test
	public void testIsValidCharacterForName_TrueCases(){
		assertTrue(Worm.isValidCharacterForName(' '));
		assertTrue(Worm.isValidCharacterForName('\''));
		assertTrue(Worm.isValidCharacterForName('\"'));
		assertTrue(Worm.isValidCharacterForName('X'));
		assertTrue(Worm.isValidCharacterForName('d'));
	}
	
	@Test
	public void testIsValidCharacterForName_Symbols(){
		assertFalse(Worm.isValidCharacterForName('$'));
		assertFalse(Worm.isValidCharacterForName('~'));
	}
	
	@Test
	public void testIsValidCharacterForName_Numbers(){
		assertFalse(Worm.isValidCharacterForName('3'));
	}
	
	@Test
	public void testIsValidCharacterForName_Tab(){
		assertFalse(Worm.isValidCharacterForName('\t'));
	}
	
	@Test
	public void testIsValidCharacterForName_WeirdLetters(){
		assertFalse(Worm.isValidCharacterForName('Ö'));
		assertFalse(Worm.isValidCharacterForName('å'));
	}
	
	@Test
	public void testIsValidName_TrueCase(){
		assertTrue(Worm.isValidName("James o'Har\"a"));
	}
	
	@Test
	public void testIsValidName_TooShort(){
		assertFalse(Worm.isValidName("Q"));
	}
	
	@Test
	public void testIsValidName_NonUpperCaseStart(){
		assertFalse(Worm.isValidName("james o'Hara"));
		assertFalse(Worm.isValidName("'O Donald"));
	}
	
	@Test
	public void testIsValidName_WeirdSymbols(){
		assertFalse(Worm.isValidName("James *ö'Hara*"));
	}
	
	@Test
	public void testSetName_LegalCase(){
		donald.setName("Donald 'Fauntleroy' Duck");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetName_TooShort(){
		donald.setName("D");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetName_IllegalSymbols(){
		donald.setName("D*n*ld D*ck");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetName_LowerCaseStart(){
		donald.setName("donald duck");
	}	
	
	@Test
	public void testGetRadiusLowerBound(){
		assertTrue(willy.getRadiusLowerBound() > 0);
	}
	
	@Test
	public void testCanHaveAsRadius_TrueCase(){
		// We assume that willy.getRadiusLowerBound() < 500 here.
		assertTrue(willy.canHaveAsRadius(500));
		assertTrue(willy.canHaveAsRadius(willy.getRadiusLowerBound()));
	}
	
	@Test
	public void testCanHaveAsRadius_NaN(){
		assertFalse(willy.canHaveAsRadius(Double.NaN));
	}
	
	@Test
	public void testCanHaveAsRadius_TooSmall(){
		assertFalse(willy.canHaveAsRadius(0));
		assertFalse(willy.canHaveAsRadius(willy.getRadiusLowerBound()-1e-4));
	}
	
	@Test
	public void testCanHaveAsRadius_TooBig(){
		assertFalse(willy.canHaveAsRadius(Double.POSITIVE_INFINITY));
	}
	
	@Test
	public void testSetRadius_TrueCase(){
		willy.setRadius(97);
		assertFuzzyEquals(97, willy.getRadius(), PRECISION);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetRadius_NaN(){
		willy.setRadius(Double.NaN);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetRadius_Zero(){
		willy.setRadius(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetRadius_TooSmall(){
		willy.setRadius(willy.getRadiusLowerBound()-1e-4);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetRadius_TooBig(){
		willy.setRadius(Double.POSITIVE_INFINITY);
	}
	
	@Test
	public void testGetMass(){
		assertFuzzyEquals(183466713.419263797, willy.getMass(), PRECISION);
	}
	
	@Test
	public void testGetMaxActionPoints(){
		assertEquals(183466713, willy.getMaxActionPoints());
	}
	
	@Test
	public void testGetActionPoints(){
		assertEquals(183466713, willy.getActionPoints());
	}
}
