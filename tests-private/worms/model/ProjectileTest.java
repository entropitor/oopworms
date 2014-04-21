package worms.model;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class ProjectileTest {
	
	Projectile projectile;
	World world;
	boolean[][] passableMap;

	@Before
	public void setUp() throws Exception {
		passableMap = new boolean[][]{{true,true},{false,true},{true,true}};
		world = new World(20,30,passableMap,new Random());
		world.start();
		projectile = new RifleProjectile(world, 39);
	}

	@Test
	public void testConstructor_NormalCase() {
		projectile = new RifleProjectile(world, 39);
		assertEquals(projectile.getPropulsionYield(),39);
	}
	
	@Test
	public void testConstructor_TooHighPropulsionCase(){
		projectile = new RifleProjectile(world, 101);
		assertEquals(projectile.getPropulsionYield(),100);
	}
	
	@Test
	public void testConstructor_TooLowPropulsionCase(){
		projectile = new RifleProjectile(world, -3);
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
	
	@Test
	public void testLegalizePropulsionYield_NormalCase() {
		assertEquals(48,Projectile.legalizePropulsionYield(48));
	}
	
	@Test
	public void testLegalizePropulsionYield_NegativeCase() {
		assertEquals(0,Projectile.legalizePropulsionYield(-48));
	}
	
	@Test
	public void testLegalizePropulsionYield_TooHighCase() {
		assertEquals(100,Projectile.legalizePropulsionYield(148));
	}
}
