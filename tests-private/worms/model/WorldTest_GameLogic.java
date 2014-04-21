package worms.model;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class WorldTest_GameLogic {
	
	World world;
	Worm chilly, willy;
	
	World worldWithOrderdWorms;
	Worm worm0,worm1,worm2,worm3,worm4,worm5;
	Team team0, team2, team35;
	
	World worldWithProjectileAndDeadWorm;
	Worm alive0, alive1, dead2, alive3, alive4, dead5;
	Projectile projectile;

	@Before
	public void setUp() throws Exception {
		boolean[][] passableMap = new boolean[][]{{true,true},{false,true},{true,true}};
		world = new World(20,30,passableMap,new Random());
		chilly  = new Worm(world, 5, 5, 0.6, 35, "Henk Rijckaert");
		willy  = new Worm(world, 112, 358, 1.321, 34.55, "Willy Wonka");
		
		worldWithOrderdWorms = new World(20,30,passableMap,new Random());
		team0 = new Team(worldWithOrderdWorms,"TeamForWormZero");
		team2 = new Team(worldWithOrderdWorms,"TeamForWormTwo");
		team35 = new Team(worldWithOrderdWorms,"TeamForWormThreeAndFive");
		worm0 = new Worm(worldWithOrderdWorms, 5, 5, 0, 1, "Worm 0",team0);
		worm1 = new Worm(worldWithOrderdWorms, 5, 5, 0, 1, "Worm 1");
		worm2 = new Worm(worldWithOrderdWorms, 5, 5, 0, 1, "Worm 2",team2);
		worm3 = new Worm(worldWithOrderdWorms, 5, 5, 0, 1, "Worm 3",team35);
		worm4 = new Worm(worldWithOrderdWorms, 5, 5, 0, 1, "Worm 4");
		worm5 = new Worm(worldWithOrderdWorms, 5, 5, 0, 1, "Worm 5",team35);
		
		worldWithOrderdWorms.start();
		
		worldWithProjectileAndDeadWorm = new World(20,30,passableMap,new Random());
		alive0 = new Worm(worldWithProjectileAndDeadWorm, 5, 5, 0, 1, "Worm 0");
		alive1 = new Worm(worldWithProjectileAndDeadWorm, 5, 5, 0, 1, "Worm 1");
		dead2 = new Worm(worldWithProjectileAndDeadWorm, 5, 5, 0, 1, "Worm 2");
		alive3 = new Worm(worldWithProjectileAndDeadWorm, 5, 5, 0, 1, "Worm 3");
		alive4 = new Worm(worldWithProjectileAndDeadWorm, 5, 5, 0, 1, "Worm 4");
		dead5 = new Worm(worldWithProjectileAndDeadWorm, 5, 5, 0, 1, "Worm 5");
		
		worldWithProjectileAndDeadWorm.start();
		projectile = new RifleProjectile(worldWithProjectileAndDeadWorm, 35);
		dead2.decreaseHitPoints(dead2.getHitPoints());
		alive0.turn(2*Math.PI);
		alive0.decreaseHitPoints(400);
		alive1.turn(2*Math.PI);
		alive1.decreaseHitPoints(400);
		alive3.turn(2*Math.PI);
		alive3.decreaseHitPoints(400);
		alive4.turn(2*Math.PI);
		alive4.decreaseHitPoints(400);
		dead5.decreaseHitPoints(dead5.getHitPoints());
	}

	@Test
	public void testHasStarted_FalseCase() {
		assertFalse(world.hasStarted());
	}
	
	@Test
	public void testHasStarted_TrueCase() {
		world.start();
		assertTrue(world.hasStarted());
	}

	@Test
	public void testStart() {
		world.start();
		assertTrue(world.hasStarted());
		assertEquals(chilly, world.getCurrentWorm());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testStart_HasStartedCase() throws Exception {
		world.start();
		world.start();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testCheckForWinners_HasntStartedCase() throws Exception {
		world.checkForWinners();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testCheckForWinners_TerminatedCase() throws Exception {
		world.start();
		world.terminate();
		world.checkForWinners();
	}
	
	@Test
	public void testCheckForWinners_NoWormsCase() {
		world.removeWorm(chilly);
		world.removeWorm(willy);
		world.start();
		world.checkForWinners();
		assertTrue(world.isTerminated());
		assertEquals("", world.getWinners());
	}
	
	@Test
	public void testCheckForWinners_FirstWormNoTeamFalseCase() {
		worldWithOrderdWorms.removeWorm(worm0);
		worldWithOrderdWorms.checkForWinners();
		assertFalse(world.isTerminated());
	}
	
	@Test
	public void testCheckForWinners_FirstWormNoTeamTrueCase() {
		worldWithOrderdWorms.removeWorm(worm0);
		worldWithOrderdWorms.removeWorm(worm2);
		worldWithOrderdWorms.removeWorm(worm3);
		worldWithOrderdWorms.removeWorm(worm4);
		worldWithOrderdWorms.removeWorm(worm5);
		worldWithOrderdWorms.checkForWinners();
		assertTrue(worldWithOrderdWorms.isTerminated());
		assertEquals("Worm 1", worldWithOrderdWorms.getWinners());
	}

	@Test
	public void testCheckForWinners_FirstWormHasTeamOtherTeamsCase() {
		worldWithOrderdWorms.removeWorm(worm1);
		worldWithOrderdWorms.removeWorm(worm4);
		worldWithOrderdWorms.removeWorm(worm5);
		worldWithOrderdWorms.checkForWinners();
		assertFalse(world.isTerminated());
	}
	
	@Test
	public void testCheckForWinners_FirstWormHasTeamOtherFFAWormsCase() {
		worldWithOrderdWorms.removeWorm(worm1);
		worldWithOrderdWorms.removeWorm(worm2);
		worldWithOrderdWorms.removeWorm(worm3);
		worldWithOrderdWorms.removeWorm(worm5);
		worldWithOrderdWorms.checkForWinners();
		assertFalse(world.isTerminated());
	}
	
	@Test
	public void testCheckForWinners_FirstWormHasTeamSingleTeamSingleWormCase() {
		worldWithOrderdWorms.removeWorm(worm1);
		worldWithOrderdWorms.removeWorm(worm2);
		worldWithOrderdWorms.removeWorm(worm3);
		worldWithOrderdWorms.removeWorm(worm4);
		worldWithOrderdWorms.removeWorm(worm5);
		worldWithOrderdWorms.checkForWinners();
		assertTrue(worldWithOrderdWorms.isTerminated());
		assertEquals("TeamForWormZero", worldWithOrderdWorms.getWinners());
	}
	
	@Test
	public void testCheckForWinners_FirstWormHasTeamSingleTeamMultipleWormCase() {
		worldWithOrderdWorms.removeWorm(worm0);
		worldWithOrderdWorms.removeWorm(worm1);
		worldWithOrderdWorms.removeWorm(worm2);
		worldWithOrderdWorms.removeWorm(worm4);
		worldWithOrderdWorms.checkForWinners();
		assertTrue(worldWithOrderdWorms.isTerminated());
		assertEquals("TeamForWormThreeAndFive", worldWithOrderdWorms.getWinners());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testGetWinners_NotTerminatedCase() throws Exception {
		world.getWinners();
	}
	
	@Test
	public void testGetCurrentWorm_NormalCase() {
		assertEquals(worm0,worldWithOrderdWorms.getCurrentWorm());
		worldWithOrderdWorms.startNextTurn();
		assertEquals(worm1,worldWithOrderdWorms.getCurrentWorm());
	}
	
	@Test
	public void testGetCurrentWorm_NoWormsCase() {
		world.removeWorm(willy);
		world.removeWorm(chilly);
		assertNull(world.getCurrentWorm());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testStartNextTurn_HasntStartedCase() throws Exception {
		world.startNextTurn();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testStartNextTurn_NotStartedButTerminatedCase() throws Exception {
		world.terminate();
		world.startNextTurn();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testStartNextTurn_StartedAndTerminatedCase() throws Exception {
		world.start();
		world.terminate();
		world.startNextTurn();
	}
	
	@Test
	public void testStartNextTurn_NormalCase() {
		assertTrue(worldWithProjectileAndDeadWorm.hasProjectile());
		assertTrue(worldWithProjectileAndDeadWorm.hasAsWorm(dead2));
		assertTrue(worldWithProjectileAndDeadWorm.hasAsWorm(dead5));

		assertEquals(4448-400, alive1.getHitPoints());
		assertEquals(4448-60,alive1.getActionPoints());
		
		worldWithProjectileAndDeadWorm.startNextTurn();
		
		assertFalse(worldWithProjectileAndDeadWorm.hasProjectile());
		assertFalse(worldWithProjectileAndDeadWorm.hasAsWorm(dead2));
		assertFalse(worldWithProjectileAndDeadWorm.hasAsWorm(dead5));
		
		assertEquals(alive1,worldWithProjectileAndDeadWorm.getCurrentWorm());
		assertEquals(4448-400+10, alive1.getHitPoints());
		assertEquals(4448,alive1.getActionPoints());
	}
	
	@Test
	public void testStartNextTurn_ADeadWormIsNextCurrentWormCase() {
		worldWithProjectileAndDeadWorm.startNextTurn();
		assertEquals(alive1,worldWithProjectileAndDeadWorm.getCurrentWorm());
		assertEquals(alive1,worldWithProjectileAndDeadWorm.getWormAt(1));
		assertEquals(alive3,worldWithProjectileAndDeadWorm.getWormAt(2));
		assertEquals(alive4,worldWithProjectileAndDeadWorm.getWormAt(3));
		assertEquals(alive0,worldWithProjectileAndDeadWorm.getWormAt(0));
		assertEquals(4,worldWithProjectileAndDeadWorm.getNbWorms());
		
		//Set HP of alive3 to 0
		alive3.decreaseHitPoints(alive3.getHitPoints());

		assertEquals(alive1,worldWithProjectileAndDeadWorm.getCurrentWorm());
		assertTrue(worldWithProjectileAndDeadWorm.hasAsWorm(alive3));
		assertEquals(4448-400, alive4.getHitPoints());
		assertEquals(4448-60,alive4.getActionPoints());
		assertEquals(4448-400, alive0.getHitPoints());
		assertEquals(4448-60,alive0.getActionPoints());

		worldWithProjectileAndDeadWorm.startNextTurn();
		
		//Alive 3 is gone
		assertFalse(worldWithProjectileAndDeadWorm.hasAsWorm(alive3));
		//Alive 4 is new current worm
		assertEquals(alive4,worldWithProjectileAndDeadWorm.getCurrentWorm());
		//Alive 4 is replenished
		assertEquals(4448-400+10, alive4.getHitPoints());
		assertEquals(4448,alive4.getActionPoints());
		//Alive 0 isn't replenished
		assertEquals(4448-400, alive0.getHitPoints());
		assertEquals(4448-60,alive0.getActionPoints());
	}
	
	@Test
	public void testStartNextTurn_TwoDeadWormsAreNextCurrentWormsCase() {
		worldWithProjectileAndDeadWorm.startNextTurn();
		assertEquals(alive1,worldWithProjectileAndDeadWorm.getCurrentWorm());
		assertEquals(alive1,worldWithProjectileAndDeadWorm.getWormAt(1));
		assertEquals(alive3,worldWithProjectileAndDeadWorm.getWormAt(2));
		assertEquals(alive4,worldWithProjectileAndDeadWorm.getWormAt(3));
		assertEquals(alive0,worldWithProjectileAndDeadWorm.getWormAt(0));
		assertEquals(4,worldWithProjectileAndDeadWorm.getNbWorms());
		
		//Set HP of alive3 and alive4 to 0
		alive3.decreaseHitPoints(alive3.getHitPoints());
		alive4.decreaseHitPoints(alive4.getHitPoints());
		
		//Decrease AP of alive1
		alive1.turn(2*Math.PI);

		assertEquals(alive1,worldWithProjectileAndDeadWorm.getCurrentWorm());
		assertTrue(worldWithProjectileAndDeadWorm.hasAsWorm(alive3));
		assertTrue(worldWithProjectileAndDeadWorm.hasAsWorm(alive4));
		assertEquals(4448-400, alive0.getHitPoints());
		assertEquals(4448-60,alive0.getActionPoints());
		assertEquals(4448-400+10, alive1.getHitPoints());
		assertEquals(4448-60,alive1.getActionPoints());

		worldWithProjectileAndDeadWorm.startNextTurn();
		
		//Alive3 and Alive4 is gone
		assertFalse(worldWithProjectileAndDeadWorm.hasAsWorm(alive3));
		assertFalse(worldWithProjectileAndDeadWorm.hasAsWorm(alive4));
		//Alive 0 is new current worm
		assertEquals(alive0,worldWithProjectileAndDeadWorm.getCurrentWorm());
		//Alive 0 is replenished
		assertEquals(4448-400+10, alive0.getHitPoints());
		assertEquals(4448,alive0.getActionPoints());
		//Alive 1 isn't replenished
		assertEquals(4448-400+10, alive1.getHitPoints());
		assertEquals(4448-60,alive1.getActionPoints());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testStartNextTurn_NoWormsCase() throws Exception {
		world.removeWorm(willy);
		world.removeWorm(chilly);
		world.start();
		world.startNextTurn();
	}
	
	@Test
	public void testStartNextTurn_GameEndedCase() {
		world.start();
		world.removeWorm(willy);
		world.startNextTurn();
		assertTrue(world.isTerminated());
	}
}
