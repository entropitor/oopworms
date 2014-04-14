package worms.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ProjectileTest {
	
	Projectile projectile;

	@Before
	public void setUp() throws Exception {
		projectile = new RifleProjectile(39);
	}

	@Test
	public void testCanJump() {
		assertTrue(projectile.canJump());
	}

	@Test
	public void testConstructor_NormalCase() {
		projectile = new RifleProjectile(39);
		assertEquals(projectile.getPropulsionYield(),39);
	}
	
	@Test
	public void testConstructor_TooHighPropulsionCase(){
		projectile = new RifleProjectile(101);
		assertEquals(projectile.getPropulsionYield(),100);
	}
	
	@Test
	public void testConstructor_TooLowPropulsionCase(){
		projectile = new RifleProjectile(-3);
		assertEquals(projectile.getPropulsionYield(),0);
	}
	
	@Test
	public void testIsValidPropulsionYield_TrueCase(){
		assertTrue(projectile.isValidPropulsionYield(49));
	}
	
	@Test
	public void testIsValidPropulsionYield_FalseCase(){
		assertFalse(projectile.isValidPropulsionYield(-3));
		assertFalse(projectile.isValidPropulsionYield(101));
	}

}
