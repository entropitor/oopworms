package worms.model;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class WorldTest_Worms {
	
	World world, otherWorld,worldWithWormsForRemoval;
	boolean[][] passableMap;
	Worm chilly, willy;
	Worm worm0,worm1,worm2,worm3;

	@Before
	public void setUp() throws Exception {
		passableMap = new boolean[][]{{true,true},{false,true},{true,true}};
		world = new World(20,30,passableMap,new Random());
		otherWorld = new World(20,30,passableMap,new Random());
		chilly  = new Worm(world, 5, 5, 0.6, 35, "Henk Rijckaert");
		willy  = new Worm(world, 112, 358, 1.321, 34.55, "Willy Wonka");

		worldWithWormsForRemoval = new World(20,30,passableMap,new Random());
		worm0 = new Worm(worldWithWormsForRemoval, 5, 5, 0, 1, "Worm 0");
		worm1 = new Worm(worldWithWormsForRemoval, 5, 5, 0, 1, "Worm 1");
		worm2 = new Worm(worldWithWormsForRemoval, 5, 5, 0, 1, "Worm 2");
		worm3 = new Worm(worldWithWormsForRemoval, 5, 5, 0, 1, "Worm 3");

		worldWithWormsForRemoval.start();
		worm1.decreaseHitPoints(400);
		worm1.turn(2*Math.PI);
		worm2.decreaseHitPoints(400);
		worm2.turn(2*Math.PI);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testAddWorm_WorldHasStartedCase() throws Exception {
		world.start();
		new Worm(world, 5, 5, 0.6, 35, "Henk Rijckaert");
	}
		
	@Test(expected = IllegalStateException.class)
	public void testAddWorm_WormInOtherWorldCase() throws Exception {
		otherWorld.addWorm(willy);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAddWorm_NullCase(){
		world.addWorm(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAddWorm_TerminatedCase(){
		world.removeWorm(willy);
		willy.terminate();
		world.addWorm(willy);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddWorm_DoubleCase(){
		assertTrue(world.hasAsEntity(willy));
		world.addWorm(willy);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testRemoveWorm_NullCase(){
		world.removeWorm(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRemoveWorm_NotHavingCase(){
		assertFalse(otherWorld.hasAsEntity(willy));
		otherWorld.removeWorm(willy);
	}
	
	@Test
	public void testRemoveWorm_NormalCase() {
		assertTrue(world.hasAsEntity(willy));
		assertEquals(willy.getWorld(), world);

		world.removeWorm(willy);
		assertFalse(world.hasAsEntity(willy));
		assertTrue(willy.isTerminated());
		assertEquals(willy.getWorld(), null);
	}
	
	@Test
	public void testRemoveWorm_BeforeCurrentWormCase() {
		worldWithWormsForRemoval.startNextTurn();
		worldWithWormsForRemoval.startNextTurn();
		assertEquals(worm2, worldWithWormsForRemoval.getCurrentWorm());
		worldWithWormsForRemoval.removeWorm(worm0);
		assertEquals(worm2, worldWithWormsForRemoval.getCurrentWorm());
	}
	
	@Test
	public void testRemoveWorm_CurrentWormAtIndex0Case() {
		assertEquals(worm0, worldWithWormsForRemoval.getCurrentWorm());
		assertEquals(4448-400, worm1.getHitPoints());
		assertEquals(4448-60, worm1.getActionPoints());
		worldWithWormsForRemoval.removeWorm(worm0);
		assertEquals(worm1, worldWithWormsForRemoval.getCurrentWorm());
		assertEquals(4448-400+10, worm1.getHitPoints());
		assertEquals(4448, worm1.getActionPoints());
	}
	
	@Test
	public void testRemoveWorm_CurrentWormAtIndex1Case() {
		worldWithWormsForRemoval.startNextTurn();
		assertEquals(worm1, worldWithWormsForRemoval.getCurrentWorm());
		assertEquals(4448-400, worm2.getHitPoints());
		assertEquals(4448-60, worm2.getActionPoints());
		worldWithWormsForRemoval.removeWorm(worm1);
		assertEquals(worm2, worldWithWormsForRemoval.getCurrentWorm());
		assertEquals(4448-400+10, worm2.getHitPoints());
		assertEquals(4448, worm2.getActionPoints());
	}
	
	@Test
	public void testRemoveWorm_AfterCurrentWormCase() {
		worldWithWormsForRemoval.startNextTurn();
		assertEquals(worm1, worldWithWormsForRemoval.getCurrentWorm());
		worldWithWormsForRemoval.removeWorm(worm2);
		assertEquals(worm1, worldWithWormsForRemoval.getCurrentWorm());
	}

	@Test
	public void testCanHaveAsWorm_NullWormCase() {
		assertFalse(world.canHaveAsWorm(null));
	}

	@Test
	public void testCanHaveAsWorm_TerminatedWormCase() {
		world.removeWorm(willy);
		assertFalse(world.canHaveAsWorm(willy));
	}

	@Test
	public void testCanHaveAsWorm_TerminatedWorldCase() {
		world.terminate();
		assertFalse(world.canHaveAsWorm(willy));
	}

	@Test
	public void testCanHaveAsWorm_TrueCase() {
		assertTrue(world.canHaveAsWorm(willy));
	}

	@Test
	public void testCanHaveAsWormAt_NegativeIndexCase() {
		assertFalse(world.canHaveAsWormAt(willy, -1));
	}

	@Test
	public void testCanHaveAsWormAt_IndexTooBigCase() {
		assertEquals(world.getNbWorms(), 2);
		assertFalse(world.canHaveAsWormAt(willy, 3));
	}

	@Test
	public void testHasProperWorms_SingleCase() {
		assertTrue(world.hasProperWorms());
		world.removeWorm(chilly);
		assertTrue(world.hasProperWorms());
		world.removeWorm(willy);
		assertTrue(world.hasProperWorms());
	}
}
