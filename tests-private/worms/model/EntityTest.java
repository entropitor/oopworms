package worms.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EntityTest {
	
	Worm willy;

	@Before
	public void setUp() throws Exception {
		willy  = new Worm(112, 358, 1.321, 34.55, "Willy Wonka");
	}

	@Test
	public void testTerminate_SingleCase(){
		assertFalse(willy.isTerminated());

		willy.terminate();
		assertTrue(willy.isTerminated());
	}

}
