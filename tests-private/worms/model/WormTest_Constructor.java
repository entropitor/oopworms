package worms.model;

import static java.lang.Math.PI;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static worms.util.AssertUtil.assertFuzzyEquals;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class WormTest_Constructor {
	
	World world;
	
	@Before
	public void setup() {
		world = new World(20,30,new boolean[][]{{true,true},{false,true},{true,true}},new Random());
	}

	@Test
	public void testConstructor_LegalCase(){
		Worm worm = new Worm(world, -8.45, 9.16, PI/2, 2.14, "Bar");

		assertFuzzyEquals(worm.getXCoordinate(), -8.45);
		assertFuzzyEquals(worm.getYCoordinate(), 9.16);
		assertFuzzyEquals(worm.getDirection(), PI/2);
		assertEquals(worm.getName(), "Bar");
		assertFuzzyEquals(worm.getRadius(), 2.14);
		assertFuzzyEquals(worm.getMass(), 43596.78321768277701414403);
		assertEquals(worm.getMaxActionPoints(), 43597);
		assertEquals(worm.getActionPoints(), 43597);
		assertEquals(worm.getWorld(), world);
		assertTrue(world.hasAsEntity(worm));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_IllegalXPosition() throws Exception{
		new Worm(world, Double.NaN, 9.16, PI/2, 2.14, "Bar");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_IllegalYPosition() throws Exception{
		new Worm(world, -8.45, Double.NaN, PI/2, 2.14, "Bar");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_IllegalName() throws Exception{
		new Worm(world, -8.45, Double.NaN, PI/2, 2.14, "Foo	Bar");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_IllegalRadius() throws Exception{
		new Worm(world, -8.45, 9.16, PI/2, 0, "Bar");
	}
}
