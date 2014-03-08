package worms.model;

import static org.junit.Assert.*;
import static worms.util.AssertUtil.*;

import org.junit.Before;
import org.junit.Test;

public class WormTest {
	private Worm willy;
	private static Worm donald;
	private static final double PRECISION = 1e-6;

	@Before
	public void setup(){
		//				  x    y    dir.     r       name
		willy = new Worm(112, 358, 1.321, 34.55, "Willy Wonka");
		donald = new Worm(0,0,3,5,"D'Onald Duck");
	}
	
	@Test
	public void testConstructor_LegalCase(){
		Worm worm = new Worm(-8.45, 9.16, Math.PI/2, 2.14, "Bar");

		assertFuzzyEquals(worm.getXCoordinate(), -8.45, PRECISION);
		assertFuzzyEquals(worm.getYCoordinate(), 9.16, PRECISION);
		assertFuzzyEquals(worm.getDirection(), Math.PI/2, PRECISION);
		assertEquals(worm.getName(), "Bar");
		assertFuzzyEquals(worm.getRadius(), 2.14, PRECISION);

		//TODO complete constructor test.
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
}
