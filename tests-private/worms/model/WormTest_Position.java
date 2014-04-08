package worms.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static worms.util.AssertUtil.assertFuzzyEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WormTest_Position {
	private static Method setXCoordinate;
	private static Method setYCoordinate;	
	
	private Worm willy;

	@Before
	public void setup(){
		//				   x    y    dir.     r       name
		willy  = new Worm(112, 358, 1.321, 34.55, "Willy Wonka");
	}

	@BeforeClass
	public static void setupClass() throws NoSuchMethodException, SecurityException{		
		setXCoordinate = Worm.class.getDeclaredMethod("setXCoordinate", double.class);
		setXCoordinate.setAccessible(true);
		
		setYCoordinate = Worm.class.getDeclaredMethod("setYCoordinate", double.class);
		setYCoordinate.setAccessible(true);
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
	public void testSetXCoordinate_LegalCase() throws Exception{
		setXCoordinate.invoke(willy, 10);
		assertFuzzyEquals(willy.getXCoordinate(),10);
	}
	
	@Test
	public void testSetXCoordinate_InfinityCase() throws Exception{
		setXCoordinate.invoke(willy, Double.POSITIVE_INFINITY);
		assertFuzzyEquals(willy.getXCoordinate(),Double.POSITIVE_INFINITY);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetXCoordinate_IllegalCase() throws Throwable{
		try {
			setXCoordinate.invoke(willy, Double.NaN);
		} catch (InvocationTargetException e) {
			throw e.getCause();
		}
	}
	
	@Test
	public void testSetYCoordinate_LegalCase() throws Exception{
		setYCoordinate.invoke(willy, 10);
		assertFuzzyEquals(willy.getYCoordinate(),10);
	}
	
	@Test
	public void testSetYCoordinate_InfinityCase() throws Exception{
		setYCoordinate.invoke(willy, Double.NEGATIVE_INFINITY);
		assertFuzzyEquals(willy.getYCoordinate(),Double.NEGATIVE_INFINITY);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetYCoordinate_IllegalCase() throws Throwable{
		try {
			setYCoordinate.invoke(willy, Double.NaN);
		} catch (InvocationTargetException e) {
			throw e.getCause();
		}
	}
}
