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
		chilly  = new Worm(5, 5, 0.6, 35, "Henk Rijckaert");
		willy  = new Worm(112, 358, 1.321, 34.55, "Willy Wonka");
	}
	
	@Test
	public void testTerminate(){
		world.addWorm(chilly);
		world.addWorm(willy);

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
		world.addWorm(willy);
		otherWorld.addWorm(willy);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAddWorm_NullCase(){
		world.addWorm(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAddWorm_TerminatedCase(){
		willy.terminate();
		world.addWorm(willy);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddWorm_DoubleCase(){
		assertFalse(world.hasAsEntity(willy));
		world.addWorm(willy);
		assertTrue(world.hasAsEntity(willy));
		world.addWorm(willy);
	}
	
	@Test
	public void testAddWorm_NormalCase() {
		world.addWorm(willy);
		assertTrue(world.hasAsEntity(willy));
		assertEquals(willy.getWorld(), world);
		assertEquals(1, world.getNbWorms());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testHasAsEntity_NullCase(){
		world.hasAsEntity(null);
	}
	
	@Test
	public void testHasAsEntity_TrueCase() {
		world.addWorm(willy);
		assertTrue(world.hasAsEntity(willy));
	}

	@Test
	public void testHasAsEntity_FalseCase() {
		assertFalse(world.hasAsEntity(chilly));
		world.addWorm(willy);
		assertFalse(world.hasAsEntity(chilly));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testRemoveWorm_NullCase(){
		world.removeWorm(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRemoveWorm_NotHavingCase(){
		assertFalse(world.hasAsEntity(willy));
		world.removeWorm(willy);
	}
	
	@Test
	public void testRemoveWorm_NormalCase() {
		world.addWorm(willy);
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
		willy.terminate();
		assertFalse(world.canHaveAsWorm(willy));
	}

	@Test
	public void testCanHaveAsWorm_TrueCase() {
		assertTrue(world.canHaveAsWorm(willy));
	}

	@Test
	public void testHasProperWorms_SingleCase() {
		assertTrue(world.hasProperWorms());
		world.addWorm(chilly);
		assertTrue(world.hasProperWorms());
		world.addWorm(willy);
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
