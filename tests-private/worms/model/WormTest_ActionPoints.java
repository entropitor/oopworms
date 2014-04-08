package worms.model;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WormTest_ActionPoints {

	private static Method setActionPoints;
	private static Method decreaseActionPoints;
	private static Method increaseActionPoints;
	private static Method replenishActionPoints;

	private Worm willy;
	
	@Before
	public void setup(){
		//				   x    y    dir.     r       name
		willy  = new Worm(112, 358, 1.321, 34.55, "Willy Wonka");
	}


	@BeforeClass
	public static void setupClass() throws NoSuchMethodException, SecurityException{		
		setActionPoints = Worm.class.getDeclaredMethod("setActionPoints", int.class);
		setActionPoints.setAccessible(true);
		
		decreaseActionPoints = Worm.class.getDeclaredMethod("decreaseActionPoints", int.class);
		decreaseActionPoints.setAccessible(true);
		
		increaseActionPoints = Worm.class.getDeclaredMethod("increaseActionPoints", int.class);
		increaseActionPoints.setAccessible(true);
		
		replenishActionPoints = Worm.class.getDeclaredMethod("replenishActionPoints");
		replenishActionPoints.setAccessible(true);
	}
	
	@Test
	public void testGetMaxActionPoints_SimpleCase(){
		assertEquals(183466713, willy.getMaxActionPoints());
	}
	
	@Test
	public void testGetMaxActionPoints_IntegerOverflow(){
		willy.setRadius(79);
		assertEquals(Integer.MAX_VALUE, willy.getMaxActionPoints());
	}
	
	@Test
	public void testGetActionPoints(){
		assertEquals(183466713, willy.getActionPoints());
	}
	
	@Test
	public void testSetActionPoints_NormalCase() throws Exception{
		setActionPoints.invoke(willy, 500);
		assertEquals(willy.getActionPoints(),500);
	}
	
	@Test
	public void testSetActionPoints_TooBigCase() throws Exception{
		setActionPoints.invoke(willy, Integer.MAX_VALUE);
		assertEquals(willy.getActionPoints(),willy.getMaxActionPoints());
	}
	
	@Test
	public void testSetActionPoints_TooSmallCase() throws Exception{
		setActionPoints.invoke(willy, -100);
		assertEquals(willy.getActionPoints(),0);
	}
	
	@Test
	public void testDecreaseActionPoints_NormalCase() throws Exception{
		decreaseActionPoints.invoke(willy,100);
		assertEquals(willy.getActionPoints(),willy.getMaxActionPoints()-100);
	}
	
	@Test
	public void testDecreaseActionPoints_LargeNumberCase() throws Exception{
		decreaseActionPoints.invoke(willy,Integer.MAX_VALUE);
		assertEquals(willy.getActionPoints(),0);
	}
	
	@Test
	public void testDecreaseActionPoints_NegativeCase() throws Exception{
		decreaseActionPoints.invoke(willy,100);
		decreaseActionPoints.invoke(willy,-100);
		assertEquals(willy.getActionPoints(),willy.getMaxActionPoints()-100);
	}
	
	@Test
	public void testIncreaseActionPoints_NormalCase() throws Exception{
		decreaseActionPoints.invoke(willy, 1000);
		increaseActionPoints.invoke(willy,100);
		assertEquals(willy.getActionPoints(),willy.getMaxActionPoints()-900);
	}
	
	@Test
	public void testIncreaseActionPoints_LargeNumberCase() throws Exception{
		increaseActionPoints.invoke(willy,Integer.MAX_VALUE);
		assertEquals(willy.getActionPoints(),willy.getMaxActionPoints());
	}
	
	@Test
	public void testIncreaseActionPoints_NegativeCase() throws Exception{
		increaseActionPoints.invoke(willy,-100);
		assertEquals(willy.getActionPoints(),willy.getMaxActionPoints());
	}
	
	@Test
	public void testReplenishActionPoints_SingleCase() throws Exception{
		decreaseActionPoints.invoke(willy,100);
		replenishActionPoints.invoke(willy);
		assertEquals(willy.getActionPoints(),willy.getMaxActionPoints());
	}
}
