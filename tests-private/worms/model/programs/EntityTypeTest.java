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
import worms.model.programs.types.EntityType;

public class EntityTypeTest {
	
	Entity willy, bicky;
	
	@Before
	public void setUp() {
		World world = new World(200,300,new boolean[][]{{}},new Random());
		willy = new Worm(world, 18, 20, PI, 10, "Willy Wonka");
		bicky = new Food(world);
	}

	@Test
	public void testEntityType() {
		EntityType e = new EntityType(bicky);
		assertEquals(bicky, e.getValue());
	}

	@Test
	public void testGetNewTypeOfSameClass() {
		EntityType e = new EntityType(bicky);
		EntityType newE = e.getNewTypeOfSameClass(willy);
		assertFalse(newE.equals(e));
		assertEquals(willy, newE.getValue());
	}
	
	@Test
	public void testEquals() {
		EntityType e1 = new EntityType(bicky);
		EntityType e2 = new EntityType(bicky);
		EntityType e3 = new EntityType(willy);
		
		// Reflexive
		assertTrue(e1.equals(e1));
		
		// Symmetric
		assertTrue(e1.equals(e2));
		assertTrue(e2.equals(e1));
		assertFalse(e1.equals(e3));
		assertFalse(e3.equals(e1));
	}
	
	@Test
	public void testHashCode() {
		EntityType e1 = new EntityType(willy);
		EntityType e2 = new EntityType(willy);
		
		assertTrue(e1.equals(e2));
		assertEquals(e1.hashCode(), e2.hashCode());
		
		// Hashcode inequality of inequal objects is not required. 
	}
}
