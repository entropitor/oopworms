package worms.model;

import static org.junit.Assert.*;
import static worms.util.AssertUtil.*;
import static worms.util.ArrayUtil.*;
import static worms.model.LocationType.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class WorldTest_Constructor {
	
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
	public void testConstructor_LegalCase(){
		World world = new World(20,30,passableMap,new Random());
		
		assertFuzzyEquals(20,world.getWidth());
		assertFuzzyEquals(30,world.getHeight());
		
		assertArrayEquals(passableMap, world.getPassableMap());
		assertTrue(deepEquals(passableMap, world.getPassableMap()));
		
		assertTrue(world.isPassablePosition(new Position(15,3),0));
		passableMap[2] = new boolean[]{true,false};
		assertTrue("Check shallow clone",world.isPassablePosition(new Position(15,3),0));
		
		assertTrue(world.isPassablePosition(new Position(15,3),0));
		passableMap[2][1] = false;
		assertTrue("Check deep clone",world.isPassablePosition(new Position(15,3),0));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructor_IllegalWidthCase() throws Exception{
		new World(-300,500,passableMap,new Random());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructor_IllegalHeightCase() throws Exception{
		new World(300,-500,passableMap,new Random());
	}
	
	@Test
	public void testConstructor_ExceptionalPassableMapCase(){
		passableMap = new boolean[][]{{true,true},{true}};
		world = new World(300,500,passableMap,new Random());
		assertTrue(deepEquals(new boolean[][]{}, world.getPassableMap()));
	}
}