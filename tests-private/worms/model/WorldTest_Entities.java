package worms.model;

import static org.junit.Assert.*;
import static worms.util.AssertUtil.*;
import static worms.util.ArrayUtil.*;
import static worms.model.LocationType.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class WorldTest_Entities {
	
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
	public void testTerminate(){
		world.addAsEntity(chilly);
		world.addAsEntity(willy);

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

	@Test(expected=IllegalArgumentException.class)
	public void testAddAsEntity_NullCase(){
		world.addAsEntity(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAddAsEntity_TerminatedCase(){
		willy.terminate();
		world.addAsEntity(willy);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddAsEntity_DoubleCase(){
		assertFalse(world.hasAsEntity(willy));
		world.addAsEntity(willy);
		assertTrue(world.hasAsEntity(willy));
		world.addAsEntity(willy);
	}
	
	@Test
	public void testAddAsEntity_NormalCase() {
		world.addAsEntity(willy);
		assertTrue(world.hasAsEntity(willy));
		assertEquals(willy.getWorld(), world);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testHasAsEntity_NullCase(){
		world.hasAsEntity(null);
	}
	
	@Test
	public void testHasAsEntity_TrueCase() {
		world.addAsEntity(willy);
		assertTrue(world.hasAsEntity(willy));
	}

	@Test
	public void testHasAsEntity_FalseCase() {
		assertFalse(world.hasAsEntity(chilly));
		world.addAsEntity(willy);
		assertFalse(world.hasAsEntity(chilly));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testRemoveAsEntity_NullCase(){
		world.removeAsEntity(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRemoveAsEntity_NotHavingCase(){
		assertFalse(world.hasAsEntity(willy));
		world.removeAsEntity(willy);
	}
	
	@Test
	public void testRemoveAsEntity_NormalCase() {
		world.addAsEntity(willy);
		assertTrue(world.hasAsEntity(willy));
		assertEquals(willy.getWorld(), world);

		world.removeAsEntity(willy);
		assertFalse(world.hasAsEntity(willy));
		assertTrue(willy.isTerminated());
		assertEquals(willy.getWorld(), null);
	}

	@Test
	public void testCanHaveAsEntity_NullCase() {
		assertFalse(world.canHaveAsEntity(null));
	}

	@Test
	public void testCanHaveAsEntity_TerminatedCase() {
		willy.terminate();
		assertFalse(world.canHaveAsEntity(willy));
	}

	@Test
	public void testCanHaveAsEntity_TrueCase() {
		assertTrue(world.canHaveAsEntity(willy));
	}

	@Test
	public void testHasProperEntities_SingleCase() {
		assertTrue(world.hasProperEntities());
		world.addAsEntity(chilly);
		assertTrue(world.hasProperEntities());
		world.addAsEntity(willy);
		assertTrue(world.hasProperEntities());
		world.removeAsEntity(chilly);
		assertTrue(world.hasProperEntities());
		world.removeAsEntity(willy);
		assertTrue(world.hasProperEntities());
	}
}
