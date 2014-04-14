package worms.model;

//import static org.junit.Assert.*;
import static worms.util.AssertUtil.*;

import org.junit.Test;

public class FoodTest {

	@Test
	public void testGetRadius() {
		assertFuzzyEquals(new Food().getRadius(), 0.20);
	}

}
