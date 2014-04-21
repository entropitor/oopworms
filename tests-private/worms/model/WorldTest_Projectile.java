package worms.model;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class WorldTest_Projectile {
	
	World world, otherWorld, notStartedWorld;
	boolean[][] passableMap;
	Projectile bullet;

	@Before
	public void setUp() throws Exception {
		passableMap = new boolean[][]{{true,true},{false,true},{true,true}};
		world = new World(20,30,passableMap,new Random());
		otherWorld = new World(20,30,passableMap,new Random());
		notStartedWorld = new World(20,30,passableMap,new Random());
		world.start();
		otherWorld.start();
		bullet = new RifleProjectile(world, 30);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testSetProjectile_WorldNotStartedCase() throws Exception {
		new RifleProjectile(notStartedWorld, 30);
	}
	
	@Test
	public void testCanHaveAsProjectile_TrueNullCase() {
		assertTrue(world.canHaveAsProjectile(null));
	}

	@Test
	public void testCanHaveAsProjectile_TrueNormalCase() {
		assertTrue(world.canHaveAsProjectile(bullet));
	}

	@Test
	public void testCanHaveAsProjectile_TerminatedWorldCase() {
		world.terminate();
		assertFalse(world.canHaveAsProjectile(bullet));
	}

	@Test
	public void testHasProperProjectile_TrueCase() {
		assertTrue(world.hasProperProjectile());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetProjectile_TerminatedWorldCase() {
		world.terminate();
		world.setProjectile(bullet);
	}

	@Test(expected=IllegalStateException.class)
	public void testSetProjectile_AlreadyHasWorldCase() {
		Projectile bullet = new RifleProjectile(otherWorld, 30);
		world.setProjectile(bullet);
	}

	@Test
	public void testSetProjectile_NormalCase() {
		assertEquals(world.getProjectile(), bullet);
		assertFalse(bullet.isTerminated());
		Projectile stigg = new RifleProjectile(world, 30);
		assertEquals(world.getProjectile(), stigg);
		assertTrue(bullet.isTerminated());
	}

	@Test
	public void testRemoveProjectile_NormalCase() {
		assertTrue(world.hasProjectile());
		world.removeProjectile();
		assertFalse(world.hasProjectile());
	}

	@Test
	public void testHasProjectile_TrueCase() {
		assertTrue(world.hasProjectile());
	}
	
	@Test
	public void testHasProjectile_FalseCase() {
		world.removeProjectile();
		assertFalse(world.hasProjectile());
	}
}
