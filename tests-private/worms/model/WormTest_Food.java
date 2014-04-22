package worms.model;

import static org.junit.Assert.*;
import static java.lang.Math.PI;
import static worms.util.AssertUtil.assertFuzzyEquals;


import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class WormTest_Food {
	
	World world;
	Worm willy;
	Food pizzaCalzone, zacherTorte, bicky;

	@Before
	public void setUp() throws Exception {
		world = new World(200,300,new boolean[][]{{}},new Random());
		willy = new Worm(world, 18, 20, PI, 10, "Willy Wonka");
		pizzaCalzone = new Food(world);
		zacherTorte = new Food(world);
		bicky = new Food(world);
	}

	@Test
	public void testEat_SingleCase() {
		willy.eat(bicky);
		assertFuzzyEquals(willy.getRadius(), 11);
		assertTrue(bicky.isTerminated());
		assertFalse(world.hasAsFood(bicky));
	}

	@Test
	public void testCheckForFood_NoOverlappingFoodsCase() {
		bicky.setPosition(new Position(0, 0));
		pizzaCalzone.setPosition(new Position(0,30));
		zacherTorte.setPosition(new Position(20,0));

		willy.checkForFood();

		assertFuzzyEquals(10, willy.getRadius());

		assertFalse(pizzaCalzone.isTerminated());
		assertTrue(world.hasAsFood(pizzaCalzone));

		assertFalse(zacherTorte.isTerminated());
		assertTrue(world.hasAsFood(zacherTorte));

		assertFalse(bicky.isTerminated());
		assertTrue(world.hasAsFood(bicky));
	}
	
	@Test
	public void testCheckForFood_TwoOverlappingFoodsCase() {
		bicky.setPosition(new Position(0, 0));
		pizzaCalzone.setPosition(new Position(18,20));
		zacherTorte.setPosition(new Position(10,20));

		willy.checkForFood();

		assertFuzzyEquals(12.1, willy.getRadius());

		assertTrue(pizzaCalzone.isTerminated());
		assertFalse(world.hasAsFood(pizzaCalzone));

		assertTrue(zacherTorte.isTerminated());
		assertFalse(world.hasAsFood(zacherTorte));

		assertFalse(bicky.isTerminated());
		assertTrue(world.hasAsFood(bicky));
	}
	
	@Test
	public void testCheckForFood_TooFat() {
		world = new World(20,40,new boolean[][]{{}},new Random());
		willy = new Worm(world, 10, 20, PI, 9.5, "Willy Wonka");
		zacherTorte = new Food(world);
		zacherTorte.setPosition(new Position(10,25));

		willy.checkForFood();

		assertFuzzyEquals(10.45, willy.getRadius());
		assertTrue(willy.isTerminated());
		assertFalse(world.hasAsWorm(willy));
		
		assertTrue(zacherTorte.isTerminated());
		assertFalse(world.hasAsFood(zacherTorte));
	}
}