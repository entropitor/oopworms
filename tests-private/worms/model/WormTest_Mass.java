package worms.model;
import static worms.util.AssertUtil.assertFuzzyEquals;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;


public class WormTest_Mass {
	private Worm willy;

	@Before
	public void setup(){
		World world = new World(20,30,new boolean[][]{{true,true},{false,true},{true,true}},new Random());
		willy  = new Worm(world, 112, 358, 1.321, 34.55, "Willy Wonka");
	}
	
	@Test
	public void testGetMass(){
		assertFuzzyEquals(183466713.419263797, willy.getMass());
	}
}
