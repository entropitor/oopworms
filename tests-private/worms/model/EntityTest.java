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
	public void testCollidesWith_TrueCase() {
		willy = new Worm(antares, 30,30, 1, 1, "Willy Wonka");
		assertTrue(willy.collidesWith(new Worm(antares, 32, 30, 1, 1.01, "Charlie")));
		assertTrue(willy.collidesWith(new Worm(antares, 30, 32, 1, 1.01, "Charlie")));
		assertTrue(willy.collidesWith(new Worm(antares, 32, 32, 1, 2.01, "Charlie")));
	}
	
	@Test
	public void testCollidesWith_FalseCase() {
		willy = new Worm(antares, 30,30, 1, 1, "Willy Wonka");
		assertFalse(willy.collidesWith(new Worm(antares, 32, 30, 1, 0.5, "Charlie")));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCollidesWith_NullReferenceCase() throws Exception {
		willy.collidesWith(null);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testCollidesWith_TerminatedCase() throws Exception {
		antares.removeWorm(willy);
		willy.collidesWith(new Worm(antares, 30, 30, 1, 1, "Charlie"));
	}
	
	@Test(expected = IllegalStateException.class)
	public void testCollidesWith_TerminatedComparedWithCase() throws Exception{
		Worm worm = new Worm(antares, 30, 30, 1, 1, "Charlie");
		antares.removeWorm(worm);
		willy.collidesWith(worm);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testCollidesWith_DifferentWorldsCase() throws Exception {
		willy.collidesWith(new Worm(braveNew, 30, 30, 1, 1, "Charlie"));
	}
	
	@Test
	public void testCollidesWithCircle_TrueCase() {
		willy = new Worm(antares, 30,30, 1, 1, "Willy Wonka");
		assertTrue(willy.collidesWith(new Position(32, 30), 1.01));
		assertTrue(willy.collidesWith(new Position(30, 32), 1.01));
		assertTrue(willy.collidesWith(new Position(32, 32), 2.01));
	}
	
	@Test
	public void testCollidesWithCircle_FalseCase() {
		willy = new Worm(antares, 30,30, 1, 1, "Willy Wonka");
		assertFalse(willy.collidesWith(new Position(32,30), 0.5));		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCollidesWithCircle_NaNRadiusCase() throws Exception {
		willy.collidesWith(new Position(30,30),Double.NaN);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCollidesWithCircle_NullPositionCase() throws Exception {
		willy.collidesWith(null, 30);
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

	@Test
	public void testHasProperWorld_SingleCase(){
		assertTrue(willy.hasProperWorld());
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
