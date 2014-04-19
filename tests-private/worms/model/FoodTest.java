package worms.model;

//import static org.junit.Assert.*;
import static worms.util.AssertUtil.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class FoodTest {

	World world;
	boolean[][] passableMap;
	
	@Before
	public void setUp() throws Exception {
		passableMap = new boolean[][]{{true,true},{false,true},{true,true}};
		world = new World(20,30,passableMap,new Random());
	}
	
	@Test
	public void testGetRadius() {
		assertFuzzyEquals(new Food(world).getRadius(), 0.20);
	}

}
