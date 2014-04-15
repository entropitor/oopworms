package worms.model;

import static org.junit.Assert.*;
import static worms.util.AssertUtil.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class WorldTest {
	
	World world;

	@Before
	public void setUp() throws Exception {
		boolean[][] passableMap = {{true,true},{true,true}};
		world = new World(300,500,passableMap,new Random());
	}
	
	@Test
	public void testConstructor_LegalCase(){
		boolean[][] passableMap = {{true,true},{true,true}};
		World world = new World(300,500,passableMap,new Random());
		
		assertFuzzyEquals(300,world.getWidth());
		assertFuzzyEquals(500,world.getHeight());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructor_IllegalWidthCase() throws Exception{
		boolean[][] passableMap = {{true,true},{true,true}};
		new World(-300,500,passableMap,new Random());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructor_IllegalHeightCase() throws Exception{
		boolean[][] passableMap = {{true,true},{true,true}};
		new World(300,-500,passableMap,new Random());
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
