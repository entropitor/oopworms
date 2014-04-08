package worms.model;
import static worms.util.AssertUtil.assertFuzzyEquals;

import org.junit.Before;
import org.junit.Test;


public class WormTest_Mass {
	private Worm willy;

	@Before
	public void setup(){
		//				   x    y    dir.     r       name
		willy  = new Worm(112, 358, 1.321, 34.55, "Willy Wonka");
	}
	
	@Test
	public void testGetMass(){
		assertFuzzyEquals(183466713.419263797, willy.getMass());
	}
}
