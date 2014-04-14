package worms.model;

import static worms.util.AssertUtil.*;

import org.junit.Before;
import org.junit.Test;

public class BazookaProjectileTest {
	
	Projectile bazooka;

	@Before
	public void setUp() throws Exception {
		bazooka = new BazookaProjectile(39);
	}

	@Test
	public void testGetRadius() {
		assertFuzzyEquals(bazooka.getRadius(),0.0209401);
	}

	@Test
	public void testGetJumpForce() {
		assertFuzzyEquals(bazooka.getJumpForce(),5.230000);
	}

	@Test
	public void testGetMass() {
		assertFuzzyEquals(bazooka.getMass(),0.3);
	}

}
