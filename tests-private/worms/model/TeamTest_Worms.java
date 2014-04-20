package worms.model;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class TeamTest_Worms {
	World world, otherWorld;
	Worm chilly, willy;
	Team cool;
	
	@Before
	public void setup(){
		world = new World(20,30,new boolean[][]{{true,true},{false,true},{true,true}},new Random());
		otherWorld = new World(20,30,new boolean[][]{{}},new Random());
		chilly  = new Worm(world, 5, 5, 0.6, 35, "Henk Rijckaert");
		willy  = new Worm(world, 112, 358, 1.321, 34.55, "Willy Wonka");
		cool = new Team(world, "LeWyllieWonka");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddWorm_WormInOtherWorldCase() throws Exception {
		chilly  = new Worm(otherWorld, 5, 5, 0.6, 35, "Henk Rijckaert");
		cool.addWorm(chilly);
	}

	@Test(expected=NullPointerException.class)
	public void testAddWorm_NullCase(){
		cool.addWorm(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAddWorm_TerminatedWormCase(){
		world.removeWorm(willy);
		cool.addWorm(willy);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAddWorm_TerminatedWorldCase(){
		world.removeTeam(cool);
		cool.addWorm(willy);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddWorm_DoubleCase(){
		cool.addWorm(willy);
		cool.addWorm(chilly);
		cool.addWorm(chilly);
	}

	@Test
	public void testAddWorm_NormalCase(){
		assertEquals(cool.getWorms().size(), 0);

		cool.addWorm(chilly);
		assertEquals(cool.getWorms().size(), 1);
		assertTrue(cool.getWorms().contains(chilly));

		cool.addWorm(willy);
		assertEquals(cool.getWorms().size(), 2);
		assertTrue(cool.getWorms().contains(chilly));
		assertTrue(cool.getWorms().contains(willy));
	}

	@Test
	public void testGetWorm_SingleCase(){
		cool.addWorm(chilly);
		cool.addWorm(willy);

		assertEquals(cool.getWorms().size(), 2);
		assertTrue(cool.getWorms().contains(chilly));
		assertTrue(cool.getWorms().contains(willy));
		
		world.removeWorm(chilly);

		// The team should only contain live worm.
		assertEquals(cool.getWorms().size(), 1);
		assertTrue(cool.getWorms().contains(willy));
	}
}
