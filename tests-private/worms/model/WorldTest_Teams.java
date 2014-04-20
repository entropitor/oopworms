package worms.model;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class WorldTest_Teams {
	
	World world, otherWorld;
	Team cool, Ateam;

	@Before
	public void setUp() throws Exception {
		world = new World(20,30,new boolean[][]{{}},new Random());
		otherWorld = new World(20,30,new boolean[][]{{}},new Random());
		cool = new Team(world, "TeamCool");
		Ateam = new Team(world, "ATeam");
	}

	@Test(expected = IllegalStateException.class)
	public void testAddTeam_TeamInOtherWorldCase(){
		otherWorld.addTeam(cool);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAddTeam_NullCase(){
		world.addTeam(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAddTeam_TerminatedCase(){
		world.removeTeam(cool);
		world.addTeam(cool);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddTeam_DoubleCase(){
		assertTrue(world.hasAsTeam(cool));
		world.addTeam(cool);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testRemoveTeam_NullCase(){
		world.removeTeam(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRemoveTeam_NotHavingCase(){
		assertFalse(otherWorld.hasAsTeam(cool));
		otherWorld.removeTeam(cool);
	}
	
	@Test
	public void testRemoveTeam_NormalCase() {
		assertTrue(world.hasAsTeam(cool));
		assertEquals(cool.getWorld(), world);

		world.removeTeam(cool);
		
		assertFalse(world.hasAsTeam(cool));
		assertTrue(cool.isTerminated());
		assertEquals(cool.getWorld(), null);
	}

	@Test
	public void testCanHaveAsTeam_NullTeamCase() {
		assertFalse(world.canHaveAsTeam(null));
	}

	@Test
	public void testCanHaveAsTeam_TerminatedTeamCase() {
		world.removeTeam(cool);
		assertFalse(world.canHaveAsTeam(cool));
	}

	@Test
	public void testCanHaveAsTeam_TerminatedWorldCase() {
		world.terminate();
		assertFalse(world.canHaveAsTeam(cool));
	}

	@Test
	public void testCanHaveAsTeam_TrueCase() {
		assertTrue(world.canHaveAsTeam(cool));
	}

	@Test
	public void testHasProperTeams_NormalCase() {
		assertTrue(world.hasProperTeams());
		world.removeTeam(Ateam);
		assertTrue(world.hasProperTeams());
		world.removeTeam(cool);
		assertTrue(world.hasProperTeams());
	}

	@Test
	public void testHasProperTeams_TenTeamsCase() {
		new Team(world, "Three");
		new Team(world, "Four");
		new Team(world, "Five");
		new Team(world, "Six");
		new Team(world, "Seven");
		new Team(world, "Eight");
		new Team(world, "Nine");
		new Team(world, "Ten");
	}

	@Test(expected = IllegalStateException.class)
	public void testHasProperTeams_ElevenTeamsCase() {
		new Team(world, "Three");
		new Team(world, "Four");
		new Team(world, "Five");
		new Team(world, "Six");
		new Team(world, "Seven");
		new Team(world, "Eight");
		new Team(world, "Nine");
		new Team(world, "Ten");
		new Team(world, "NopeNoPlaceForYou");
	}
}
