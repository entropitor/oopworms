package worms.model;

import static org.junit.Assert.*;
import static worms.util.AssertUtil.*;
import static worms.util.ArrayUtil.*;
import static worms.model.LocationType.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class WorldTest_Passability {
	
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
	public void testIsPassablePosition_ImpassableRadiusRightCase() {
		passableMap = new boolean[][]{{true,true,true},{true,true,false},{true,true,true}};
		world = new World(300,300,passableMap,new Random());
		assertEquals(false, world.isPassablePosition(new Position(150,150), 55));
	}
	
	@Test
	public void testIsPassablePosition_ImpassableRadiusLeftCase() {
		passableMap = new boolean[][]{{true,true,true},{false,true,true},{true,true,true}};
		world = new World(300,300,passableMap,new Random());
		assertEquals(false, world.isPassablePosition(new Position(150,150), 55));
	}
	
	@Test
	public void testIsPassablePosition_ImpassableRadiusAboveCase() {
		passableMap = new boolean[][]{{true,false,true},{true,true,true},{true,true,true}};
		world = new World(300,300,passableMap,new Random());
		assertEquals(false, world.isPassablePosition(new Position(150,150), 55));
	}
	
	@Test
	public void testIsPassablePosition_ImpassableRadiusBelowCase() {
		passableMap = new boolean[][]{{true,true,true},{true,true,true},{true,false,true}};
		world = new World(300,300,passableMap,new Random());
		assertEquals(false, world.isPassablePosition(new Position(150,150), 55));
	}
	
	@Test
	public void testIsPassablePosition_ImpassableRadiusLeftUpperDiagonalCase() {
		passableMap = new boolean[][]{{false,true,true},{true,true,true},{true,true,true}};
		world = new World(300,300,passableMap,new Random());
		assertEquals(false, world.isPassablePosition(new Position(150,150), 73));
	}
	
	@Test
	public void testIsPassablePosition_ImpassableRadiusRightUpperDiagonalCase() {
		passableMap = new boolean[][]{{true,true,false},{true,true,true},{true,true,true}};
		world = new World(300,300,passableMap,new Random());
		assertEquals(false, world.isPassablePosition(new Position(150,150), 73));
	}
	
	@Test
	public void testIsPassablePosition_ImpassableRadiusLeftLowerDiagonalCase() {
		passableMap = new boolean[][]{{true,true,true},{true,true,true},{false,true,true}};
		world = new World(300,300,passableMap,new Random());
		assertEquals(false, world.isPassablePosition(new Position(150,150), 73));
	}
	
	@Test
	public void testIsPassablePosition_ImpassableRadiusRightLowerDiagonalCase() {
		passableMap = new boolean[][]{{true,true,true},{true,true,true},{true,true,false}};
		world = new World(300,300,passableMap,new Random());
		assertEquals(false, world.isPassablePosition(new Position(150,150), 73));
	}
	
	@Test
	public void testIsPassablePosition_VericalCrossingLeftCase() {
		//Circle crosses a Vertical Line twice within one grid row.
		//Vertical line on the left of the circle
		passableMap = new boolean[][]	{{true,true,true,true,true,true},
										{true,true,true,true,true,true},
										{false,true,true,true,true,true},
										{true,true,true,true,true,true},
										{true,true,true,true,true,true}};
		world = new World(60,50,passableMap,new Random());
		assertEquals(false, world.isPassablePosition(new Position(22,25), 12.5));
		
		passableMap = new boolean[][]	{{true,true,true,true,true,true},
										{true,true,true,true,true,true},
										{true,true,true,true,false,true},
										{true,true,true,true,true,true},
										{true,true,true,true,true,true}};
		world = new World(60,50,passableMap,new Random());
		assertEquals(true, world.isPassablePosition(new Position(22,25), 12.5));
	}
	
	@Test
	public void testIsPassablePosition_VericalCrossingRightCase() {
		//Circle crosses a Vertical Line twice within one grid row.
		//Vertical line on the right of the circle
		passableMap = new boolean[][]	{{true,true,true,true,true,true},
										{true,true,true,true,true,true},
										{false,true,true,true,true,true},
										{true,true,true,true,true,true},
										{true,true,true,true,true,true}};
		world = new World(60,50,passableMap,new Random());
		assertEquals(true, world.isPassablePosition(new Position(29,25), 11.5));
		
		passableMap = new boolean[][]	{{true,true,true,true,true,true},
										{true,true,true,true,true,true},
										{true,true,true,true,false,true},
										{true,true,true,true,true,true},
										{true,true,true,true,true,true}};
		world = new World(60,50,passableMap,new Random());
		assertEquals(false, world.isPassablePosition(new Position(29,25), 11.5));
	}
	
	@Test
	public void testIsPassablePosition_ImpassableFarRadiusCase() {
		passableMap = new boolean[][]	{{false,true,true,true,true,true},
										{true,true,true,true,true,true},
										{true,true,true,true,true,true},
										{true,true,true,true,true,true},
										{true,true,true,true,true,true}};
		world = new World(60,50,passableMap,new Random());
		assertEquals(false, world.isPassablePosition(new Position(25,25), 22));
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
