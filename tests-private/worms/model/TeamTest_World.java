package worms.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class TeamTest_World {
	Team cool;
	World antares, braveNew;
	
	@Before
	public void setup(){
		antares = new World(20,30,new boolean[][]{{}},new Random());
		braveNew = new World(5,6,new boolean[][]{{false}},new Random(789));
		cool = new Team(antares, "LeWyllieWonka");
	}

	@Test(expected=IllegalStateException.class)
	public void testTerminate_AlreadyDisassociatedCase(){
		cool.terminate();
	}
	
	@Test
	public void testCanHaveAsWorld_TerminatedCase(){
		antares.removeTeam(cool);
		assertTrue(cool.canHaveAsWorld(null));
	}

	@Test
	public void testCanHaveAsWorld_NormalCase(){
		assertTrue(cool.canHaveAsWorld(antares));
	}
	
	@Test
	public void testHasProperWorld_SingleCase(){
		assertTrue(cool.hasProperWorld());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetWorld_NullArgument(){
		cool.setWorld(null);
	}

	@Test(expected=IllegalStateException.class)
	public void testSetWorld_SecondWorld(){
		cool.setWorld(antares);
		cool.setWorld(braveNew);
	}

	@Test
	public void testHasWorld_SingleCase(){
		assertTrue(cool.hasWorld());
		antares.removeTeam(cool);
		assertFalse(cool.hasWorld());
	}
}
