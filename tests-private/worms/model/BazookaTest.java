package worms.model;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class BazookaTest {
	
	Weapon bazooka;
	World world;

	@Before
	public void setUp() throws Exception {
		world = new World(20,30,new boolean[][]{{true,true},{false,true},{true,true}},new Random());
		bazooka = new Bazooka();
	}
	
	@Test
	public void testHasMoreProjectiles_InfiniteAmmunitionCase() {
		assertTrue(bazooka.hasMoreProjectiles());
	}
	
	@Test(expected = NullPointerException.class)
	public void testGetNewProjectile_NullWorldCase() throws Exception {
		bazooka.getNewProjectile(null, 39);
	}

	@Test
	public void testGetNewProjectile_NormalYieldCase() {
		Projectile proj = bazooka.getNewProjectile(world, 39);
		assertEquals(39, proj.getPropulsionYield());
		assertTrue(proj instanceof BazookaProjectile);
		assertEquals(world, proj.getWorld());
		assertEquals(proj, world.getProjectile());
	}
	
	@Test
	public void testGetNewProjectile_TooHighYieldCase() {
		assertEquals(100, bazooka.getNewProjectile(world, 200).getPropulsionYield());
	}
	
	@Test
	public void testGetNewProjectile_TooLowYieldCase() {
		assertEquals(0, bazooka.getNewProjectile(world, -200).getPropulsionYield());
	}

	@Test
	public void testGetCost() {
		assertEquals(50, bazooka.getCost());
	}
	
	@Test
	public void testGetName_SingleCase() {
		assertEquals("Bazooka", bazooka.getName());
	}

}
