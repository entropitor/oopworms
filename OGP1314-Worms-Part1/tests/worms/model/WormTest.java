package worms.model;

import static org.junit.Assert.*;
import static worms.util.AssertUtil.*;

import org.junit.Before;
import org.junit.Test;

public class WormTest {
	
	@Before
	public void setup(){
	}
	
	@Test
	public void testConstructorLegalCase(){
		Worm worm = new Worm(-8.45, 9.16, Math.PI/2, 2.14, "Bar");
		//Constructor should have enough precision to set the variables => 1e-10 instead of 1e-4
		double precision = 1e-10;
		assertFuzzyEquals(worm.getXCoordinate(), -8.45, precision);
		assertFuzzyEquals(worm.getYCoordinate(), 9.16, precision);
		//TODO complete constructor test.
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorIllegalXPosition() throws Exception{
		new Worm(Double.NaN, 9.16, Math.PI/2, 2.14, "Bar");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorIllegalYPosition() throws Exception{
		new Worm(-8.45, Double.NaN, Math.PI/2, 2.14, "Bar");
	}
	
	@Test
	public void testIsValidPositionTrueCase(){
		assertTrue(Worm.isValidPosition(3.546,-8.5649));
	}
	
	@Test
	public void testIsValidPositionFalseXCase(){
		assertFalse(Worm.isValidPosition(Double.NaN, -4.168));
	}
	
	@Test
	public void testIsValidPositionFalseYCase(){
		assertFalse(Worm.isValidPosition(Double.POSITIVE_INFINITY, Double.NaN));
	}
	
	@Test
	public void testIsValidPositionBothFalseCase(){
		assertFalse(Worm.isValidPosition(Double.NaN, Double.NaN));
	}
	
}
