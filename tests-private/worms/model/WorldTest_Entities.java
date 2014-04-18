package worms.model;

import static org.junit.Assert.*;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class WorldTest_Entities {
	
	World world, otherWorld;
	boolean[][] passableMap;
	Worm chilly, willy;

	@Before
	public void setUp() throws Exception {
		passableMap = new boolean[][]{{true,true},{false,true},{true,true}};
		world = new World(20,30,passableMap,new Random());
		otherWorld = new World(20,30,passableMap,new Random());
		chilly  = new Worm(world, 5, 5, 0.6, 35, "Henk Rijckaert");
		willy  = new Worm(world, 112, 358, 1.321, 34.55, "Willy Wonka");
	}
	
	@Test
	public void testTerminate(){
		assertTrue(world.hasAsEntity(chilly));
		assertTrue(world.hasAsEntity(willy));
		assertEquals(chilly.getWorld(), world);
		assertEquals(willy.getWorld(), world);

		world.terminate();

		assertFalse(world.hasAsEntity(chilly));
		assertFalse(world.hasAsEntity(willy));
		assertEquals(chilly.getWorld(), null);
		assertEquals(willy.getWorld(), null);
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
		world.addWorm(willy);
		willy.terminate();
		world.addWorm(willy);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddWorm_DoubleCase(){
		assertTrue(world.hasAsEntity(willy));
		world.addWorm(willy);
	}
	
	@Test
	public void testHasAsEntity_TrueCase() {
		assertTrue(world.hasAsEntity(willy));
	}

	@Test
	public void testHasAsEntity_FalseCase() {
		assertFalse(otherWorld.hasAsEntity(chilly));
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
	public void testCanHaveAsWorm_NullCase() {
		assertFalse(world.canHaveAsWorm(null));
	}

	@Test
	public void testCanHaveAsWorm_TerminatedCase() {
		world.removeWorm(willy);
		assertFalse(world.canHaveAsWorm(willy));
	}

	@Test
	public void testCanHaveAsWorm_TrueCase() {
		assertTrue(world.canHaveAsWorm(willy));
	}

	@Test
	public void testHasProperWorms_SingleCase() {
		assertTrue(world.hasProperWorms());
		world.removeWorm(chilly);
		assertTrue(world.hasProperWorms());
		world.removeWorm(willy);
		assertTrue(world.hasProperWorms());
	}
	
	@Test
	public void testHasProjectile_TrueCase() {
		world.setProjectile(new RifleProjectile(30));
		assertTrue(world.hasProjectile());
	}
	
	@Test
	public void testHasProjectile_FalseCase() {
		assertFalse(world.hasProjectile());
	}
}
