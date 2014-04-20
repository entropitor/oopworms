package worms.model;

import static org.junit.Assert.*;
import static worms.util.AssertUtil.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class ProjectileTest_Jump {

	Projectile projectileOutOfWorld,projectileContactLocation,projectileHitWorm;
	World jumpWorld,jumpWorld2,jumpWorld3;
	Worm cannonFodder,hidingWorm;
	boolean[][] passableMap;
	static final double TIMESTEP = 1e-4;
	
	@Before
	public void setUp() throws Exception {
		passableMap = new boolean[][]{	{true,true,true,true},
										{true,true,true,true},
										{true,true,true,true},
										{true,true,false,true},
										{true,true,true,true}};
		jumpWorld = new World(40,20,passableMap,new Random());
		hidingWorm = new Worm(jumpWorld, 5, 5, Math.PI/2, 1, "I'm hiding from the flying projectiles");
		projectileContactLocation = new BazookaProjectile(jumpWorld, 39);
		projectileContactLocation.setPosition(new Position(12,10));
		projectileContactLocation.setDirection(Math.PI/4);

		jumpWorld2 = new World(40,20,passableMap,new Random());
		projectileOutOfWorld = new BazookaProjectile(jumpWorld2, 39);
		projectileOutOfWorld.setPosition(new Position(12,10));
		projectileOutOfWorld.setDirection(3*Math.PI/4);
		
		jumpWorld3 = new World(40,20,passableMap,new Random());
		cannonFodder = new Worm(jumpWorld3, 6, 9, Math.PI/2, 3, "I'm gonna be hit soon");
		projectileHitWorm = new BazookaProjectile(jumpWorld3, 39);
		projectileHitWorm.setPosition(new Position(12,10));
		projectileHitWorm.setDirection(3*Math.PI/4);
	}

	@Test
	public void testJump_HitWormCase() {
		projectileHitWorm.jump(TIMESTEP);
		assertFuzzyEquals(7.11843, projectileHitWorm.getPosition().getX());
		assertFuzzyEquals(11.8059, projectileHitWorm.getPosition().getY());
		assertTrue(projectileHitWorm.isTerminated());
		assertFalse(jumpWorld.hasAsEntity(projectileHitWorm));

		assertFuzzyEquals(120109-80, cannonFodder.getHitPoints());
	}
	
	@Test
	public void testJump_OutOfWorldCase() {
		projectileOutOfWorld.jump(TIMESTEP);
		assertFuzzyEquals(0.02041, projectileOutOfWorld.getPosition().getX());
		assertFuzzyEquals(3.45689, projectileOutOfWorld.getPosition().getY());
		assertTrue(projectileOutOfWorld.isTerminated());
		assertFalse(jumpWorld2.hasAsEntity(projectileOutOfWorld));
	}
	
	@Test
	public void testJump_ContactLocationCase() {
		projectileContactLocation.jump(TIMESTEP);
		assertFuzzyEquals(21.38101, projectileContactLocation.getPosition().getX());
		assertFuzzyEquals(8.02254, projectileContactLocation.getPosition().getY());
		assertTrue(projectileContactLocation.isTerminated());
		assertFalse(jumpWorld.hasAsEntity(projectileContactLocation));
		assertEquals(4448,hidingWorm.getHitPoints());
	}

	@Test
	public void testGetJumpTime_HitWormCase() {
		assertFuzzyEquals(0.7919999, projectileHitWorm.getJumpTime(TIMESTEP));
	}
	
	@Test
	public void testGetJumpTime_OutOfWorldCase() {
		assertFuzzyEquals(1.9435999, projectileOutOfWorld.getJumpTime(TIMESTEP));
	}
	
	@Test
	public void testGetJumpTime_ContactLocationCase() {
		assertFuzzyEquals(1.52199,projectileContactLocation.getJumpTime(TIMESTEP));
	}
	
	@Test
	public void testafterJumpRemove_OnlyCaseAndTrue() {
		assertTrue(projectileContactLocation.afterJumpRemove());
	}

}
