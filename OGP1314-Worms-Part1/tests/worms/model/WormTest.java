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
		//Constructor should have enough precision. =>
		double precision = 1e-10;
		assertFuzzyEquals(worm.getXPosition(), -8.45, precision);
		assertFuzzyEquals(worm.getYPosition(), 9.16, precision);
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
	
}
