package worms.model;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WormTest_Position {
	private static Method setPosition;	
	
	private Worm willy;

	@Before
	public void setup(){
		//				   x    y    dir.     r       name
		willy  = new Worm(112, 358, 1.321, 34.55, "Willy Wonka");
	}

	@BeforeClass
	public static void setupClass() throws NoSuchMethodException, SecurityException{		
		setPosition = Entity.class.getDeclaredMethod("setPosition", Position.class);
		setPosition.setAccessible(true);
	}
	
	@Test
	public void testSetXCoordinate_LegalCase() throws Exception{
		setPosition.invoke(willy, new Position(10,Double.POSITIVE_INFINITY));
		assertEquals(willy.getPosition(),new Position(10,Double.POSITIVE_INFINITY));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetXCoordinate_IllegalCase() throws Throwable{
		try {
			setPosition.invoke(willy, new Position(Double.NaN,-3));
		} catch (InvocationTargetException e) {
			throw e.getCause();
		}
	}
}
