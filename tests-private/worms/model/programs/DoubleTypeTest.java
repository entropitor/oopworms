package worms.model.programs;

import static org.junit.Assert.*;

import org.junit.Test;

public class DoubleTypeTest {

	@Test
	public void testDoubleType() {
		DoubleType d = new DoubleType(5.6);
		assertEquals(new Double(5.6), d.getValue());
	}

	@Test
	public void testGetNewTypeOfSameClass() {
		DoubleType d = new DoubleType(5.6);
		DoubleType newD = d.getNewTypeOfSameClass(987789);
		assertFalse(newD.equals(d));
		assertEquals(new Double(987789), newD.getValue());
		
		newD = d.getNewTypeOfSameClass(0.015);
		assertFalse(newD.equals(d));
		assertEquals(new Double(0.015), newD.getValue());
	}
	
	@Test
	public void testEquals() {
		DoubleType d1 = new DoubleType(5.6);
		DoubleType d2 = new DoubleType(5.6);
		DoubleType d3 = new DoubleType(987789);
		
		// Reflexive
		assertTrue(d1.equals(d1));
		
		// Symmetric
		assertTrue(d1.equals(d2));
		assertTrue(d2.equals(d1));
		assertFalse(d1.equals(d3));
		assertFalse(d3.equals(d1));
	}
	
	@Test
	public void testHashCode() {
		DoubleType d1 = new DoubleType(5.6);
		DoubleType d2 = new DoubleType(5.6);
		
		assertTrue(d1.equals(d2));
		assertEquals(d1.hashCode(), d2.hashCode());
		
		// Hashcode inequality of inequal objects is not required. 
	}
}
