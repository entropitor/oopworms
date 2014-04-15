package worms.model;

import static org.junit.Assert.*;
import static worms.util.AssertUtil.*;
import static worms.util.ArrayUtil.*;
import static worms.model.LocationType.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class WorldTest {
	
	World world;
	boolean[][] passableMap;

	@Before
	public void setUp() throws Exception {
		passableMap = new boolean[][]{{true,true},{false,true},{true,true}};
		world = new World(20,30,passableMap,new Random());
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
	
	@Test
	public void testIsValidPassableMap_EmptyCase(){
		passableMap = new boolean[][]{};
		assertTrue(World.isValidPassableMap(passableMap));
	}
	
	@Test
	public void testIsInsideWorldBoundaries_TrueCase(){
		assertTrue(world.isInsideWorldBoundaries(new Position(15,15)));
	}
	
	@Test
	public void testIsInsideWorldBoundaries_FalseCase(){
		assertFalse(world.isInsideWorldBoundaries(new Position(30,15)));
		assertFalse(world.isInsideWorldBoundaries(new Position(15,35)));
		assertFalse(world.isInsideWorldBoundaries(new Position(-15,15)));
		assertFalse(world.isInsideWorldBoundaries(new Position(15,-15)));
	}
	
	@Test
	public void testIsInsideWorldBoundaries_Radius_TrueCase(){
		assertTrue(world.isInsideWorldBoundaries(new Position(15,15),5));
	}
	
	@Test
	public void testIsInsideWorldBoundaries_Radius_FalseCase(){
		assertFalse(world.isInsideWorldBoundaries(new Position(30,15),5));
		assertFalse(world.isInsideWorldBoundaries(new Position(15,35),5));
		assertFalse(world.isInsideWorldBoundaries(new Position(-15,15),5));
		assertFalse(world.isInsideWorldBoundaries(new Position(15,-15),5));
		assertFalse(world.isInsideWorldBoundaries(new Position(15,15),10));
	}
	
	@Test
	public void testIsValidPassableMap_SingleRowCase(){
		passableMap = new boolean[][]{{true,true}};
		assertTrue(World.isValidPassableMap(passableMap));
	}
	
	@Test
	public void testIsValidPassableMap_NormalTrueCase(){
		passableMap = new boolean[][]{{true,true,true},{true,false,true}};
		assertTrue(World.isValidPassableMap(passableMap));
	}
	
	@Test
	public void testIsValidPassableMap_FalseCase(){
		passableMap = new boolean[][]{{true,true},{true}};
		assertFalse(World.isValidPassableMap(passableMap));
	}
	
	@Test
	public void testIsValidPassableMap_NullCase(){
		assertFalse(World.isValidPassableMap(null));
	}
	
	@Test
	public void testIsPassablePosition_TrueCase(){
		passableMap = new boolean[][]{{false,false},{false,false},{false,true}};
		world = new World(20,300,passableMap,new Random());
		assertTrue(world.isPassablePosition(new Position(15,30),0));
	}
	
	@Test
	public void testIsPassablePosition_FalseCase(){
		assertFalse(world.isPassablePosition(new Position(3,15),0));
	}
	
	@Test
	public void testIsPassablePosition_IllegalCase(){
		assertFalse(world.isPassablePosition(new Position(100,100),0));
	}
	
	@Test
	public void testIsPassablePosition_NoLevelCase(){
		passableMap = new boolean[][]{};
		world = new World(20,30,passableMap,new Random());
		assertTrue(world.isPassablePosition(new Position(3,15),0));
		
		passableMap = new boolean[][]{{},{}};
		world = new World(20,30,passableMap,new Random());
		assertTrue(world.isPassablePosition(new Position(3,15),0));
	}
	
	@Test
	public void testGetLocationType_PassableCase(){
		passableMap = new boolean[][]{{false,false},{false,false},{false,true}};
		world = new World(20,300,passableMap,new Random());
		assertEquals(PASSABLE, world.getLocationType(new Position(15,30), 3));
	}
	
	@Test
	public void testGetLocationType_ImpassableCenterCase(){
		assertEquals(IMPASSABLE, world.getLocationType(new Position(3,15),2));
	}

	@Test
	public void testGetLocationType_ImpassableRadiusCase() {
		passableMap = new boolean[][]{{false,false,false},{false,true,false},{false,false,false}};
		world = new World(300,300,passableMap,new Random());
		assertEquals(IMPASSABLE, world.getLocationType(new Position(150,150), 55));
	}
	
	@Test
	public void testGetLocationType_ContactCase(){
		passableMap = new boolean[][]{{false,false,false},{false,true,false},{false,false,false}};
		world = new World(300,300,passableMap,new Random());
		assertEquals(CONTACT, world.getLocationType(new Position(150,150), 48));
	}
	
	@Test
	public void testGetLocationType_BorderOfWorldCase() {
		passableMap = new boolean[][]{{true,true,true},{true,true,true},{true,true,true}};
		world = new World(300,300,passableMap,new Random());
		//contact because out of world is impassable terrain...
		assertEquals(CONTACT, world.getLocationType(new Position(50,250), 50));
	}
	
	@Test
	public void testGetLocationType_ExceptionalPositionCase(){
		assertEquals(IMPASSABLE, world.getLocationType(new Position(100,100),20));
	}
	
	@Test
	public void testGetLocationType_ExceptionalRadiusCase(){
		assertEquals(IMPASSABLE, world.getLocationType(new Position(5,5), 10));
	}
	
	@Test
	public void testGetXCoordinate_SingleCase() {
		assertFuzzyEquals(world.getXCoordinate(1),10);
	}
	
	@Test
	public void testGetYCoordinate_SingleCase() {
		assertFuzzyEquals(world.getYCoordinate(2),10);
	}
}
