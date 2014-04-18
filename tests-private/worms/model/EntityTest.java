package worms.model;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class EntityTest {
	
	Worm willy;
	World antares, braveNew;

	@Before
	public void setUp() throws Exception {
		antares = new World(20,30,new boolean[][]{{}},new Random());
		braveNew = new World(5,6,new boolean[][]{{false}},new Random(789));
		willy  = new Worm(antares, 112, 358, 1.321, 34.55, "Willy Wonka");
	}

	@Test
	public void testTerminate_SingleCase(){
		assertFalse(willy.isTerminated());

		antares.removeWorm(willy);
		assertTrue(willy.isTerminated());
		assertEquals(willy.getWorld(), null);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testTermiante_WorldStillReferencesCase() {
		willy.terminate();
	}

	@Test
	public void testCanHaveAsWorld_TerminatedTrueCase(){
		antares.removeWorm(willy);
		assertTrue(willy.canHaveAsWorld(null));
	}

	@Test
	public void testCanHaveAsWorld_TerminatedFalseCase(){
		antares.removeWorm(willy);
		assertFalse(willy.canHaveAsWorld(antares));
	}

	@Test
	public void testCanHaveAsWorld_NullCase(){
		antares.removeWorm(willy);
		assertTrue(willy.canHaveAsWorld(null));
	}

	@Test
	public void testCanHaveAsWorld_NormalCase(){
		assertTrue(willy.canHaveAsWorld(antares));
	}

	@Test
	public void testCanHaveAsWorld_TerminatedWorldCase(){
		antares.terminate();
		assertFalse(willy.canHaveAsWorld(antares));
	}


	@Test(expected=IllegalArgumentException.class)
	public void testSetWorld_NullArgument(){
		willy.setWorld(null);
	}


	@Test(expected=IllegalStateException.class)
	public void testSetWorld_SecondWorld(){
		willy.setWorld(antares);
		willy.setWorld(braveNew);
	}

	@Test
	public void testHasWorld_SingleCase(){
		assertTrue(willy.hasWorld());
		antares.removeWorm(willy);
		assertFalse(willy.hasWorld());
	}
}
