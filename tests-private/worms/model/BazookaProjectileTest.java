package worms.model;

import static worms.util.AssertUtil.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class BazookaProjectileTest {
	
	Projectile bazookaProjectile;
	World world;
	boolean[][] passableMap;

	@Before
	public void setUp() throws Exception {
		passableMap = new boolean[][]{{true,true},{false,true},{true,true}};
		world = new World(20,30,passableMap,new Random());
		bazookaProjectile = new BazookaProjectile(world, 39);
	}

	@Test
	public void testGetRadius() {
		assertFuzzyEquals(bazookaProjectile.getRadius(),0.0209401);
	}

	@Test
	public void testGetJumpForce() {
		assertFuzzyEquals(bazookaProjectile.getJumpForce(),5.230000);
	}

	@Test
	public void testGetMass() {
		assertFuzzyEquals(bazookaProjectile.getMass(),0.3);
	}

}
