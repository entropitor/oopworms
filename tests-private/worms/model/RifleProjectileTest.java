package worms.model;

import static org.junit.Assert.*;
import static worms.util.AssertUtil.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class RifleProjectileTest {
	
	Projectile rifleProjectile;
	World world;
	boolean[][] passableMap;

	@Before
	public void setUp() throws Exception {
		passableMap = new boolean[][]{{true,true},{false,true},{true,true}};
		world = new World(20,30,passableMap,new Random());
		rifleProjectile = new RifleProjectile(world, 39);
	}
	
	@Test
	public void testCanJump_SingleCase(){
		assertTrue(rifleProjectile.canJump());
	}

	@Test
	public void testGetRadius() {
		assertFuzzyEquals(rifleProjectile.getRadius(),0.00673915734);
	}

	@Test
	public void testGetJumpForce() {
		assertFuzzyEquals(rifleProjectile.getJumpForce(),1.5);
	}

	@Test
	public void testGetMass() {
		assertFuzzyEquals(rifleProjectile.getMass(),10e-3);
	}

}
