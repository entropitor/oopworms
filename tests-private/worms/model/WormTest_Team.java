package worms.model;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class WormTest_Team {
	World world;
	Worm willy;
	Team cool;
	
	@Before
	public void setup(){
		world = new World(20,30,new boolean[][]{{true,true},{false,true},{true,true}},new Random());
		cool = new Team(world, "LeWyllieWonka");
		willy  = new Worm(world, 112, 358, 1.321, 34.55, "Willy Wonka", cool);
	}

	@Test
	public void testConstructor_TeamNullCase(){
		willy  = new Worm(world, 112, 358, 1.321, 34.55, "Willy Wonka");
		assertFalse(willy.hasTeam());
		assertEquals(willy.getTeam(), null);
	}

	@Test
	public void testConstructor_TeamSetCase(){
		willy  = new Worm(world, 112, 358, 1.321, 34.55, "Willy Wonka", cool);
		assertTrue(willy.hasTeam());
		assertEquals(willy.getTeam(), cool);
	}

	@Test
	public void testRemoveTeam_SingleCase(){
		assertTrue(willy.hasTeam());
		assertEquals(willy.getTeam(), cool);
		willy.removeTeam();
		assertFalse(willy.hasTeam());
		assertEquals(willy.getTeam(), null);
	}

	@Test
	public void testHasProperTeam_TeamSetCase(){
		assertTrue(willy.hasTeam());
		assertEquals(willy.getTeam(), cool);
		assertTrue(willy.hasProperTeam());
	}

	@Test
	public void testHasProperTeam_TeamNullCase(){
		willy  = new Worm(world, 112, 358, 1.321, 34.55, "Willy Wonka");
		assertFalse(willy.hasTeam());
		assertEquals(willy.getTeam(), null);
		assertTrue(willy.hasProperTeam());
	}
}
