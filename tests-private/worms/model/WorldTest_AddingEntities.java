package worms.model;

import static org.junit.Assert.*;
import static worms.util.AssertUtil.*;

import java.lang.reflect.Method;
import java.util.Random;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WorldTest_AddingEntities {
	
	World world,worldFood;
	Random worldRandom;
	Team team;
	
	static Method getRandom;
	
	@BeforeClass
	public static void setUp_Class() throws Exception{
		getRandom = World.class.getDeclaredMethod("getRandom");
		getRandom.setAccessible(true);
	}

	@Before
	public void setUp() throws Exception {
		boolean[][] passableMap = new boolean[][]{{true,true},{false,true},{true,true}};
		world = new World(20,30,passableMap,new Random(123456789));
		passableMap = new boolean[][]{{true,true},{true,false},{true,true}};
		worldFood = new World(20,30,passableMap,new Random(123456789));
		team = new Team(world, "AwesomeTeam");
		worldRandom = (Random) getRandom.invoke(world);
	}

	@Test
	public void testGetRandom_SingleCase() {
		assertFuzzyEquals(2.011512,worldRandom.nextGaussian());
		assertEquals(1677212580,worldRandom.nextInt());
	}
	
	/*@Test
	public void testGetRandom_IsProtectedAgainstSeedSetting() {
		worldRandom.setSeed(1);
		assertNotEquals(-1155869325, worldRandom.nextInt());
		assertEquals(1677212580, worldRandom.nextInt());
	}*/
	
	@Test
	public void testGetRandomPerimeterLocation_RandomCases() {
		for(int i=0;i<10;++i){
			Position result = world.getRandomPerimeterLocation();
			assertTrue(world.isInsideWorldBoundaries(result));
			assertTrue(result.getX() == 0 || result.getX() == world.getWidth() || result.getY() == 0 || result.getY() == world.getHeight());
		}
	}
	
	@Test
	public void testAddNewWorm() {
		world.addNewWorm(null);
		Worm worm = world.getWormAt(0);
		assertTrue(Worm.isValidName(worm.getName()));
		assertTrue(Worm.isValidDirection(worm.getDirection()));
		assertTrue(worm.canHaveAsRadius(worm.getRadius()));
		assertTrue(worm.getTeam() == null || worm.getTeam() == team);
		assertTrue(worm.canHaveAsTeam(worm.getTeam()));
		assertTrue(Position.isValidPosition(worm.getPosition()));
		assertTrue(world.isInsideWorldBoundaries(worm.getPosition(), worm.getRadius()));
		assertEquals(LocationType.CONTACT, world.getLocationType(worm.getPosition(), worm.getRadius()));
		assertEquals(world, worm.getWorld());
	}
	
	@Test(expected=IllegalStateException.class)
	public void testAddNewFood_IllegalCase() throws Exception{
		//ATTENTION: if this test fails it might be because the order of 'random' numbers (asked for in addNewFood) are changed.
		world.addNewFood();
	}
	@Test
	public void testAddNewFood_LegalCase() {
		worldFood.addNewFood();
		for(Food food : worldFood.getFoods()){
			assertTrue(worldFood.isInsideWorldBoundaries(food.getPosition(), food.getRadius()));
			assertEquals(LocationType.CONTACT, worldFood.getLocationType(food.getPosition(), food.getRadius()));
			assertEquals(worldFood, food.getWorld());
		}
	}

}
