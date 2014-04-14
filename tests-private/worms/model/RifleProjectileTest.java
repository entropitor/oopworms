package worms.model;

import static org.junit.Assert.*;
import static worms.util.AssertUtil.*;

import org.junit.Before;
import org.junit.Test;

public class RifleProjectileTest {
	
	Projectile rifle;

	@Before
	public void setUp() throws Exception {
		rifle = new RifleProjectile(39);
	}
	
	@Test
	public void testCanJump_SingleCase(){
		assertTrue(rifle.canJump());
	}

	@Test
	public void testGetRadius() {
		assertFuzzyEquals(rifle.getRadius(),0.00673915734);
	}

	@Test
	public void testGetJumpForce() {
		assertFuzzyEquals(rifle.getJumpForce(),1.5);
	}

	@Test
	public void testGetMass() {
		assertFuzzyEquals(rifle.getMass(),10e-3);
	}

}
