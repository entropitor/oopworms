package worms.model.programs;

import static java.lang.Math.PI;
import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import worms.model.Entity;
import worms.model.Food;
import worms.model.World;
import worms.model.Worm;

public class TypeTest {
	
	EntityType e1, e2, e3;
	BooleanType bool;
	
	@Before
	public void setUp() {
		World world = new World(200,300,new boolean[][]{{}},new Random());
		Food bicky = new Food(world);
		Worm willy = new Worm(world, 18, 20, PI, 10, "Willy Wonka");
		e1 = new EntityType(bicky);
		e2 = new EntityType(bicky);
		e3 = new EntityType(willy);
		bool = new BooleanType(false);
	}
	
	@Test
	public void testEquals_Reflexive() {
		assertTrue(e1.equals(e1));
	}
	
	@Test
	public void testEquals_Symmetric() {
		assertTrue(e1.equals(e2));
		assertTrue(e2.equals(e1));
		assertFalse(e1.equals(e3));
		assertFalse(e3.equals(e1));
	}
		
	@Test
	public void testEquals_NullArgument() {
		assertFalse(e1.equals(null));
	}
		
	@Test
	public void testEquals_DifferentClass() {
		assertFalse(bool.equals(e1));
	}
	
	@Test
	public void testEquals_OneNullValue() {
		e1 = e1.getNewTypeOfSameClass(null);
		assertFalse(e1.equals(e2));
	}
	
	@Test
	public void testEquals_TwoNullValues() {
		e1 = e1.getNewTypeOfSameClass(null);
		e2 = e2.getNewTypeOfSameClass(null);
		assertTrue(e1.equals(e2));
	}
}
