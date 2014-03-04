package worms.model;

import static org.junit.Assert.*;
import static worms.util.AssertUtil.*;

import org.junit.Before;
import org.junit.Test;

public class WormTest {
	
	private Worm willy;
	private final double precision = 1e-6;
	
	@Before
	public void setup(){
		//				  x    y    dir.     r       name
		willy = new Worm(112, 358, 1.321, 34.55, "Willy Wonka");
	}
	
	@Test
	public void testConstructor_LegalCase(){
		Worm worm = new Worm(-8.45, 9.16, Math.PI/2, 2.14, "Bar");
		//Constructor should have enough precision to set the variables => 1e-10 instead of 1e-4
		assertFuzzyEquals(worm.getXCoordinate(), -8.45, precision);
		assertFuzzyEquals(worm.getYCoordinate(), 9.16, precision);
		assertFuzzyEquals(worm.getDirection(), Math.PI/2, precision);
		assertFuzzyEquals(worm.getRadius(), 2.14, precision);
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
	public void testGetRadiusLowerBound(){
		assertTrue(willy.getRadiusLowerBound() > 0);
	}
	
	@Test
	public void testIsValidRadius_TrueCase(){
		// We assume that willy.getRadiusLowerBound() < 500 here.
		assertTrue(willy.isValidRadius(500));
		assertTrue(willy.isValidRadius(willy.getRadiusLowerBound()));
	}
	
	@Test
	public void testIsValidRadius_NaN(){
		assertFalse(willy.isValidRadius(Double.NaN));
	}
	
	@Test
	public void testIsValidRadius_TooSmall(){
		assertFalse(willy.isValidRadius(0));
		assertFalse(willy.isValidRadius(willy.getRadiusLowerBound()-1e-4));
	}
	
	@Test
	public void testIsValidRadius_TooBig(){
		assertFalse(willy.isValidRadius(Double.POSITIVE_INFINITY));
	}
	
	@Test
	public void testSetRadius_TrueCase(){
		willy.setRadius(97);
		assertFuzzyEquals(97, willy.getRadius(), precision);
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
		System.out.println(willy.getMass());
		assertFuzzyEquals(183466713.419263797, willy.getMass(), precision);
	}
}
