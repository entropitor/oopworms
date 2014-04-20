package worms.model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class WorldTest_Entities {
	
	World world, otherWorld;
	boolean[][] passableMap;
	Food pizzaCalzone, zacherTorte;
	Worm chilly, willy;
	Projectile bullet;

	@Before
	public void setUp() throws Exception {
		passableMap = new boolean[][]{{true,true},{false,true},{true,true}};
		world = new World(20,30,passableMap,new Random());
		otherWorld = new World(20,30,passableMap,new Random());
		pizzaCalzone = new Food(world);
		zacherTorte = new Food(world);
		chilly  = new Worm(world, 5, 5, 0.6, 35, "Henk Rijckaert");
		willy  = new Worm(world, 112, 358, 1.321, 34.55, "Willy Wonka");
		bullet = new RifleProjectile(world, 30);
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

	@Test
	public void testGetEntities() {
		Set<Entity> expected = new HashSet<Entity>(Arrays.asList(
			chilly, willy, pizzaCalzone, zacherTorte, bullet));
		for (Entity entity : expected)
			assertTrue(world.getEntities().contains(entity));
		assertEquals(world.getEntities().size(), expected.size());
	}

	@Test
	public void testHasAsEntity_TrueWormCase() {
		assertTrue(world.hasAsEntity(willy));
	}

	@Test
	public void testHasAsEntity_TrueFoodCase() {
		assertTrue(world.hasAsEntity(pizzaCalzone));
	}

	@Test
	public void testHasAsEntity_TrueProjectileCase() {
		assertTrue(world.hasAsEntity(bullet));
	}

	@Test
	public void testHasAsEntity_FalseWormCase() {
		assertFalse(otherWorld.hasAsEntity(willy));
	}

	@Test
	public void testHasAsEntity_FalseFoodCase() {
		assertFalse(otherWorld.hasAsEntity(pizzaCalzone));
	}

	@Test
	public void testHasAsEntity_FalseProjectileCase() {
		assertFalse(otherWorld.hasAsEntity(bullet));
	}
}
