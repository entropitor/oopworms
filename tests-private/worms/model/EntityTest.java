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
		willy  = new Worm(112, 358, 1.321, 34.55, "Willy Wonka");
		antares = new World(20,30,new boolean[][]{{}},new Random());
		braveNew = new World(5,6,new boolean[][]{{false}},new Random(789));
	}

	@Test
	public void testTerminate_SingleCase(){
		antares.addWorm(willy);
		assertFalse(willy.isTerminated());

		antares.removeWorm(willy);
		assertTrue(willy.isTerminated());
		assertEquals(willy.getWorld(), null);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testTermiante_WorldStillReferencesCase() {
		antares.addWorm(willy);
		willy.terminate();
	}

	@Test
	public void testCanHaveAsWorld_TerminatedTrueCase(){
		willy.terminate();
		assertTrue(willy.canHaveAsWorld(null));
	}

	@Test
	public void testCanHaveAsWorld_TerminatedFalseCase(){
		willy.terminate();
		assertFalse(willy.canHaveAsWorld(antares));
	}

	@Test
	public void testCanHaveAsWorld_NullCase(){
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
	public void testSetWorld_NormalCase(){
		assertEquals(willy.getWorld(), null);
		willy.setWorld(antares);
		assertEquals(willy.getWorld(), antares);
	}

	@Test
	public void testHasWorld_SingleCase(){
		assertFalse(willy.hasWorld());
		willy.setWorld(antares);
		assertTrue(willy.hasWorld());
	}
}
