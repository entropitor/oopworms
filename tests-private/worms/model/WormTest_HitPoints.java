package worms.model;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.Random;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WormTest_HitPoints {

	private static Method setHitPoints;
	private static Method decreaseHitPoints;
	private static Method increaseHitPoints;
	private static Method replenishHitPoints;

	private Worm willy;
	
	@Before
	public void setup(){
		World world = new World(20,30,new boolean[][]{{true,true},{false,true},{true,true}},new Random());
		willy  = new Worm(world, 112, 358, 1.321, 34.55, "Willy Wonka");
	}


	@BeforeClass
	public static void setupClass() throws NoSuchMethodException, SecurityException{		
		setHitPoints = Worm.class.getDeclaredMethod("setHitPoints", int.class);
		setHitPoints.setAccessible(true);
		
		decreaseHitPoints = Worm.class.getDeclaredMethod("decreaseHitPoints", int.class);
		decreaseHitPoints.setAccessible(true);
		
		increaseHitPoints = Worm.class.getDeclaredMethod("increaseHitPoints", int.class);
		increaseHitPoints.setAccessible(true);
		
		replenishHitPoints = Worm.class.getDeclaredMethod("replenishHitPoints");
		replenishHitPoints.setAccessible(true);
	}
	
	@Test
	public void testGetMaxHitPoints_SimpleCase(){
		assertEquals(183466713, willy.getMaxHitPoints());
	}
	
	@Test
	public void testGetMaxHitPoints_IntegerOverflow(){
		willy.setRadius(79);
		assertEquals(Integer.MAX_VALUE, willy.getMaxHitPoints());
	}
	
	@Test
	public void testGetHitPoints(){
		assertEquals(183466713, willy.getHitPoints());
	}
	
	@Test
	public void testSetHitPoints_NormalCase() throws Exception{
		setHitPoints.invoke(willy, 500);
		assertEquals(willy.getHitPoints(),500);
	}
	
	@Test
	public void testSetHitPoints_TooBigCase() throws Exception{
		setHitPoints.invoke(willy, Integer.MAX_VALUE);
		assertEquals(willy.getHitPoints(),willy.getMaxHitPoints());
	}
	
	@Test
	public void testSetHitPoints_TooSmallCase() throws Exception{
		setHitPoints.invoke(willy, -100);
		assertEquals(willy.getHitPoints(),0);
	}
	
	@Test
	public void testDecreaseHitPoints_NormalCase() throws Exception{
		decreaseHitPoints.invoke(willy,100);
		assertEquals(willy.getHitPoints(),willy.getMaxHitPoints()-100);
	}
	
	@Test
	public void testDecreaseHitPoints_LargeNumberCase() throws Exception{
		decreaseHitPoints.invoke(willy,Integer.MAX_VALUE);
		assertEquals(willy.getHitPoints(),0);
	}
	
	@Test
	public void testDecreaseHitPoints_NegativeCase() throws Exception{
		decreaseHitPoints.invoke(willy,100);
		decreaseHitPoints.invoke(willy,-100);
		assertEquals(willy.getHitPoints(),willy.getMaxHitPoints()-100);
	}
	
	@Test
	public void testIncreaseHitPoints_NormalCase() throws Exception{
		decreaseHitPoints.invoke(willy, 1000);
		increaseHitPoints.invoke(willy,100);
		assertEquals(willy.getHitPoints(),willy.getMaxHitPoints()-900);
	}
	
	@Test
	public void testIncreaseHitPoints_LargeNumberCase() throws Exception{
		increaseHitPoints.invoke(willy,Integer.MAX_VALUE);
		assertEquals(willy.getHitPoints(),willy.getMaxHitPoints());
	}
	
	@Test
	public void testIncreaseHitPoints_NegativeCase() throws Exception{
		increaseHitPoints.invoke(willy,-100);
		assertEquals(willy.getHitPoints(),willy.getMaxHitPoints());
	}
	
	@Test
	public void testReplenishHitPoints_SingleCase() throws Exception{
		decreaseHitPoints.invoke(willy,100);
		replenishHitPoints.invoke(willy);
		assertEquals(willy.getHitPoints(),willy.getMaxHitPoints());
	}
}
