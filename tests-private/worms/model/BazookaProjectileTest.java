package worms.model;

import static worms.util.AssertUtil.*;

import org.junit.Before;
import org.junit.Test;

public class BazookaProjectileTest {
	
	Projectile bazookaProjectile;

	@Before
	public void setUp() throws Exception {
		bazookaProjectile = new BazookaProjectile(39);
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
