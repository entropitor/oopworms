package worms.model;

import static java.lang.Math.PI;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static worms.util.AssertUtil.assertFuzzyEquals;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WormTest_Direction {

	private static Method setDirection;
	
	private Worm willy;

	@Before
	public void setup(){
		//				   x    y    dir.     r       name
		willy  = new Worm(112, 358, 1.321, 34.55, "Willy Wonka");
	}

	@BeforeClass
	public static void setupClass() throws NoSuchMethodException, SecurityException{		
		setDirection = Worm.class.getDeclaredMethod("setDirection", double.class);
		setDirection.setAccessible(true);
	}

	@Test
	public void testIsValidDirection_TrueCases(){
		assertTrue(Worm.isValidDirection(0));
		assertTrue(Worm.isValidDirection(PI*2-0.001));
		assertTrue(Worm.isValidDirection(PI));
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
		assertFalse(Worm.isValidDirection(5*PI/2));
	}
	
	@Test
	public void testSetDirection_LegalCase() throws Exception{
		setDirection.invoke(willy,PI);
		assertFuzzyEquals(willy.getDirection(),PI);
	}
}
