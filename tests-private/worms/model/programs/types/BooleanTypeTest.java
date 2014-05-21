package worms.model.programs.types;

import static org.junit.Assert.*;

import org.junit.Test;

import worms.model.programs.types.BooleanType;

public class BooleanTypeTest {

	@Test
	public void testBooleanType() {
		BooleanType bool = new BooleanType(true);
		assertEquals(true, bool.getValue());
	}

	@Test
	public void testGetNewTypeOfSameClass() {
		BooleanType bool = new BooleanType(true);
		BooleanType newBool = bool.getNewTypeOfSameClass(false);
		assertFalse(newBool.equals(bool));
		assertEquals(false, newBool.getValue());
	}
	
	@Test
	public void testEquals() {
		BooleanType bool1 = new BooleanType(true);
		BooleanType bool2 = new BooleanType(true);
		BooleanType bool3 = new BooleanType(false);
		
		// Reflexive
		assertTrue(bool1.equals(bool1));
		
		// Symmetric
		assertTrue(bool1.equals(bool2));
		assertTrue(bool2.equals(bool1));
		assertFalse(bool1.equals(bool3));
		assertFalse(bool3.equals(bool1));
	}
	
	@Test
	public void testHashCode() {
		BooleanType bool1 = new BooleanType(true);
		BooleanType bool2 = new BooleanType(true);
		
		assertTrue(bool1.equals(bool2));
		assertEquals(bool1.hashCode(), bool2.hashCode());
		
		// Inequality of hashcodes of inequal objects is not required. 
	}
	
	@Test
	public void testGetDefaultTypeForThisClass() {
		assertEquals(false, new BooleanType(true).getDefaultTypeForThisClass().getValue());
	}
}
