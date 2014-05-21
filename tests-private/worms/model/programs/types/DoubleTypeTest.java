package worms.model.programs.types;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import worms.model.programs.types.DoubleType;

public class DoubleTypeTest {
	
	DoubleType double5_6;
	
	@Before
	public void setup(){
		double5_6 = new DoubleType(5.6);
	}

	@Test
	public void testDoubleType() {
		assertEquals(new Double(5.6), double5_6.getValue());
	}

	@Test
	public void testGetNewTypeOfSameClass() {
		DoubleType newD = double5_6.getNewTypeOfSameClass(987789);
		assertFalse(newD.equals(double5_6));
		assertEquals(new Double(987789), newD.getValue());
		
		newD = double5_6.getNewTypeOfSameClass(0.015);
		assertFalse(newD.equals(double5_6));
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
	
	@Test
	public void testGetDefaultTypeForThisClass() {
		assertEquals(new Double(0.0), double5_6.getDefaultTypeForThisClass().getValue());
	}
}
