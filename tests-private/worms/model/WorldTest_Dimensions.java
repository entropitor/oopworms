package worms.model;

import static org.junit.Assert.*;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class WorldTest_Dimensions {
	
	World world;
	boolean[][] passableMap;
	Worm chilly, willy;

	@Before
	public void setUp() throws Exception {
		passableMap = new boolean[][]{{true,true},{false,true},{true,true}};
		world = new World(20,30,passableMap,new Random());
		chilly  = new Worm(5, 5, 0.6, 35, "Henk Rijckaert");
		willy  = new Worm(112, 358, 1.321, 34.55, "Willy Wonka");
	}

	@Test
	public void testGetWidthUpperBound() {
		assertTrue(World.getWidthUpperBound() >= 0);
	}

	@Test
	public void testGetHeightUpperBound() {
		assertTrue(World.getHeightUpperBound() >= 0);
	}

	@Test
	public void testIsValidWidth_TrueCase() {
		assertTrue(World.isValidWidth(300));
	}
	
	@Test
	public void testIsValidWidht_FalseSmallCase(){
		assertFalse(World.isValidWidth(-300));
	}
	
	@Test
	public void testIsValidWidht_FalseLargeCase(){
		assertFalse(World.isValidWidth(Double.POSITIVE_INFINITY));
	}
	
	@Test
	public void testIsValidWidht_FalseNaNCase(){
		assertFalse(World.isValidWidth(Double.NaN));
	}
	
	@Test
	public void testIsValidHeight_TrueCase(){
		assertTrue(World.isValidHeight(500));
	}
	
	@Test
	public void testIsValidHeight_FalseSmallCase(){
		assertFalse(World.isValidHeight(-500));
	}
	
	@Test
	public void testIsValidHeight_FalseLargeCase(){
		assertFalse(World.isValidHeight(Double.POSITIVE_INFINITY));
	}
	
	@Test
	public void testIsValidHeight_NaNCase(){
		assertFalse(World.isValidHeight(Double.NaN));
	}
}