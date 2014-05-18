package worms.model;

import static org.junit.Assert.*;
import static worms.util.AssertUtil.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class WormTest_Fall {
	
	World world;
	Worm wormOutOfWorld,wormImpassableStart,wormContactStart,wormContactEnd,terminatedWorm;

	@Before
	public void setUp() throws Exception {
		world = new World(20,30,new boolean[][]{{true,true},{true,true},{false,true}},new Random());
		wormOutOfWorld = new Worm(world, 15.36, 24.54, 0, 1, "Falls out of the world");
		wormImpassableStart = new Worm(world, 4.74, 5.22, 0, 1, "Sits on impassable terrain");
		wormContactStart = new Worm(world, 4.12, 11.04, 0, 1, "Sits on a contact location");
		wormContactEnd = new Worm(world, 3.56, 15.89, 0, 1, "Will fall onto contact location");
		terminatedWorm = new Worm(world, 3, 3, 0, 1, "Is terminated");
		world.removeAsEntity(terminatedWorm);
	}
	
	@Test
	public void testCanFall_OutOfWorldCase() {
		assertTrue(wormOutOfWorld.canFall());
	}
	
	@Test
	public void testCanFall_ImpassableStartCase() {
		assertFalse(wormImpassableStart.canFall());
	}
	
	@Test
	public void testCanFall_ContactStartCase() {
		assertFalse(wormContactStart.canFall());
	}
	
	@Test
	public void testCanFall_ContactEndCase() {
		assertTrue(wormContactEnd.canFall());
	}
	
	@Test
	public void testCanFall_TerminatedCase() {
		assertFalse(terminatedWorm.canFall());
	}
	
	@Test
	public void testFall_OutsideOfWorldCase() {
		wormOutOfWorld.fall();
		assertTrue(wormOutOfWorld.isTerminated());
		assertFalse(world.hasAsEntity(wormOutOfWorld));
		assertEquals(4448-69,wormOutOfWorld.getHitPoints());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testFall_ImpassableStartCase() throws Exception{
		wormImpassableStart.fall();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testFall_ContactStartCase() throws Exception{
		wormContactStart.fall();
	}
	
	@Test
	public void testFall_ContactEndCase() {
		wormContactEnd.fall();
		assertEquals(4448-12,wormContactEnd.getHitPoints());
		assertFuzzyEquals(3.56, wormContactEnd.getPosition().getX());
		assertFuzzyEquals(11.1, wormContactEnd.getPosition().getY());
	}
	
	@Test
	public void testFindFallPosition_OutsideOfWorldCase() {
		Position fallPosition = wormOutOfWorld.findFallPosition();
		assertFuzzyEquals(15.36, fallPosition.getX());
		assertFuzzyEquals(1, fallPosition.getY());
	}
	
	@Test
	public void testFindFallPosition_ImpassableStartCase() {
		Position fallPosition = wormImpassableStart.findFallPosition();
		assertFuzzyEquals(4.74, fallPosition.getX());
		assertFuzzyEquals(5.22, fallPosition.getY());
	}
	
	@Test
	public void testFindFallPosition_ContactStartCase() {
		Position fallPosition = wormContactStart.findFallPosition();
		assertFuzzyEquals(4.12, fallPosition.getX());
		assertFuzzyEquals(11.04, fallPosition.getY());
	}
	
	@Test
	public void testFindFallPosition_ContactEndCase() {
		Position fallPosition = wormContactEnd.findFallPosition();
		assertFuzzyEquals(3.56, fallPosition.getX());
		assertFuzzyEquals(10+1.1, fallPosition.getY());
	}
	
	@Test
	public void testBlocksFall_OutsideWorldCase() {
		assertTrue(wormContactEnd.blocksFall(new Position(-3, 3)));
	}
	
	@Test
	public void testBlocksFall_ImpassableCase() {
		assertTrue(wormContactEnd.blocksFall(new Position(4.74, 5.22)));
	}
	
	@Test
	public void testBlocksFall_ContactCase() {
		assertTrue(wormContactEnd.blocksFall(new Position(4.74, 11.05)));
	}
	
	@Test
	public void testBlocksFall_PassableCase() {
		assertFalse(wormContactEnd.blocksFall(new Position(15.35, 24.39)));
	}

}
