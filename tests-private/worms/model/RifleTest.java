package worms.model;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class RifleTest {
	
	Weapon rifle;
	World world;

	@Before
	public void setUp() throws Exception {
		rifle = new Rifle();
		world = new World(20,30,new boolean[][]{{true,true},{false,true},{true,true}},new Random());
	}
	
	@Test
	public void testHasMoreProjectiles_InfiniteAmmunitionCase() {
		assertTrue(rifle.hasMoreProjectiles());
	}
	
	@Test(expected = NullPointerException.class)
	public void testGetNewProjectile_NullWorldCase() throws Exception {
		rifle.getNewProjectile(null, 39);
	}

	@Test
	public void testGetNewProjectile_NormalYieldCase() {
		Projectile proj = rifle.getNewProjectile(world, 39);
		assertEquals(39, proj.getPropulsionYield());
		assertTrue(proj instanceof RifleProjectile);
		assertEquals(world, proj.getWorld());
		assertEquals(proj, world.getProjectile());
	}

	@Test
	public void testGetNewProjectile_TooHighYieldCase() {
		assertEquals(100, rifle.getNewProjectile(world, 200).getPropulsionYield());
	}
	
	@Test
	public void testGetNewProjectile_TooLowYieldCase() {
		assertEquals(0, rifle.getNewProjectile(world, -200).getPropulsionYield());
	}

	@Test
	public void testGetCost() {
		assertEquals(10, rifle.getCost());
	}
	
	@Test
	public void testGetName_SingleCase() {
		assertEquals("Rifle", rifle.getName());
	}

}
