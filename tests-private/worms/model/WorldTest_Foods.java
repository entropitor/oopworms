package worms.model;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class WorldTest_Foods {
	
	World world, otherWorld;
	boolean[][] passableMap;
	Food pizzaCalzone, zacherTorte;

	@Before
	public void setUp() throws Exception {
		passableMap = new boolean[][]{{true,true},{false,true},{true,true}};
		world = new World(20,30,passableMap,new Random());
		otherWorld = new World(20,30,passableMap,new Random());
		pizzaCalzone = new Food(world);
		zacherTorte = new Food(world);
	}


	@Test
	public void testGetNbFoods_SingleCase() {
		assertEquals(world.getNbFoods(), 2);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testAddFood_FoodInOtherWorldCase() throws Exception {
		otherWorld.addFood(pizzaCalzone);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAddFood_NullCase(){
		world.addFood(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAddFood_TerminatedCase(){
		world.removeFood(pizzaCalzone);
		pizzaCalzone.terminate();
		world.addFood(pizzaCalzone);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddFood_DoubleCase(){
		assertTrue(world.hasAsEntity(pizzaCalzone));
		world.addFood(pizzaCalzone);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testRemoveFood_NullCase(){
		world.removeFood(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRemoveFood_NotHavingCase(){
		assertFalse(otherWorld.hasAsEntity(pizzaCalzone));
		otherWorld.removeFood(pizzaCalzone);
	}
	
	@Test
	public void testRemoveFood_NormalCase() {
		assertTrue(world.hasAsEntity(pizzaCalzone));
		assertEquals(pizzaCalzone.getWorld(), world);

		world.removeFood(pizzaCalzone);
		assertFalse(world.hasAsEntity(pizzaCalzone));
		assertTrue(pizzaCalzone.isTerminated());
		assertEquals(pizzaCalzone.getWorld(), null);
	}

	@Test
	public void testCanHaveAsFood_NullFoodCase() {
		assertFalse(world.canHaveAsFood(null));
	}

	@Test
	public void testCanHaveAsFood_TerminatedFoodCase() {
		world.removeFood(pizzaCalzone);
		assertFalse(world.canHaveAsFood(pizzaCalzone));
	}

	@Test
	public void testCanHaveAsFood_TerminatedWorldCase() {
		world.terminate();
		assertFalse(world.canHaveAsFood(pizzaCalzone));
	}

	@Test
	public void testCanHaveAsFood_TrueCase() {
		assertTrue(world.canHaveAsFood(pizzaCalzone));
	}

	@Test
	public void testHasProperFoods_SingleCase() {
		assertTrue(world.hasProperFoods());
		world.removeFood(zacherTorte);
		assertTrue(world.hasProperFoods());
		world.removeFood(pizzaCalzone);
		assertTrue(world.hasProperFoods());
	}
}
