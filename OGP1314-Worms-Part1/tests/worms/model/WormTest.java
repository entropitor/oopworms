package worms.model;

import static org.junit.Assert.*;
import static worms.util.AssertUtil.*;

import org.junit.Before;
import org.junit.Test;

public class WormTest {
	private Worm willy;
	private static Worm donald;
	private Worm skippy,eiffelTower;
	private static final double PRECISION = 1e-6;

	@Before
	public void setup(){
		//				   x    y    dir.     r       name
		willy  = new Worm(112, 358, 1.321, 34.55, "Willy Wonka");
		donald = new Worm(0,     0,     3,     5, "D'Onald Duck");
		skippy = new Worm(2.72, -3.14, 2, 1.5, "Skippy The Bush Kangaroo");
		eiffelTower = new Worm(48.51, 2.21, 3.4, 21851, "The Eiffel Tower");
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
	
	@Test
	public void testJump_CanJumpCase(){
		skippy.jump();
		assertFuzzyEquals(skippy.getXCoordinate(), -1.5098204, PRECISION);
		assertFuzzyEquals(skippy.getYCoordinate(), -3.14, PRECISION);
		assertEquals(skippy.getActionPoints(), 0);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testJump_CanNotJumpCase() throws Exception{
		eiffelTower.jump();
	}
	
	@Test
	public void testGetJumpForce_CanJumpCase(){
		assertFuzzyEquals(skippy.getJumpForce(), 222303.8195708, PRECISION);
	}
	
	@Test
	public void testGetJumpForce_CanNotJumpCase(){
		assertFuzzyEquals(eiffelTower.getJumpForce(), 0, PRECISION);
	}
	
	@Test
	public void testGetJumpVelocity_CanJumpCase(){
		assertFuzzyEquals(skippy.getJumpVelocity(), 7.4033797, PRECISION);
	}
	
	@Test
	public void testGetJumpVelocity_CanNotJumpCase(){
		assertFuzzyEquals(eiffelTower.getJumpVelocity(), 0, PRECISION);
	}
	
	@Test
	public void testCanJump_TrueCase(){
		assertTrue(skippy.canJump());
	}
	
	@Test
	public void testCanJump_FalseCase(){
		assertFalse(eiffelTower.canJump());
	}
	
	@Test
	public void testGetJumpTime_CanJumpCase(){
		assertFuzzyEquals(skippy.getJumpTime(), 1.3729202, PRECISION);
	}
	
	@Test
	public void testGetJumpTime_CanNotJumpCase(){
		assertFuzzyEquals(eiffelTower.getJumpTime(), 0, PRECISION);
	}
	
	@Test
	public void testGetJumpStep_MidAirCase(){
		double[] jumpstep = skippy.getJumpStep(1);
		assertEquals(jumpstep.length, 2);
		assertFuzzyEquals(jumpstep[0], -0.36089306, PRECISION);
		assertFuzzyEquals(jumpstep[1], -1.3114509, PRECISION);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetJumpStep_NegativeTimeCase() throws Exception{
		skippy.getJumpStep(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetJumpStep_NaNCase() throws Exception{
		skippy.getJumpStep(Double.NaN);
	}
	
	@Test
	public void testGetJumpStep_AfterJumpCase(){
		double[] jumpstep = skippy.getJumpStep(5);
		assertEquals(jumpstep.length, 2);
		assertFuzzyEquals(jumpstep[0], -1.5098204, PRECISION);
		assertFuzzyEquals(jumpstep[1], -3.14, PRECISION);
	}
	
	@Test
	public void testGetJumpStep_BeforeJumpCase(){
		double[] jumpstep = skippy.getJumpStep(0);
		assertEquals(jumpstep.length, 2);
		assertFuzzyEquals(jumpstep[0], 2.72, PRECISION);
		assertFuzzyEquals(jumpstep[1], -3.14, PRECISION);
	}
	
	@Test
	public void testGetJumpStep_CanNotJumpCase(){
		double[] jumpstep = eiffelTower.getJumpStep(1);
		assertEquals(jumpstep.length, 2);
		assertFuzzyEquals(jumpstep[0], 48.51, PRECISION);
		assertFuzzyEquals(jumpstep[1], 2.21, PRECISION);
	}
}
