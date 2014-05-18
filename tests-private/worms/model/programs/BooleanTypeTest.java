package worms.model.programs;

import static org.junit.Assert.*;

import org.junit.Test;

public class BooleanTypeTest {

	@Test
	public void testBooleanType() {
		BooleanType bool = new BooleanType(BooleanType.Value.TRUE);
		assertEquals(BooleanType.Value.TRUE, bool.getValue());
	}

	@Test
	public void testGetNewTypeOfSameClass() {
		BooleanType bool = new BooleanType(BooleanType.Value.TRUE);
		BooleanType newBool = bool.getNewTypeOfSameClass(BooleanType.Value.FALSE);
		assertFalse(newBool.equals(bool));
		assertEquals(BooleanType.Value.FALSE, newBool.getValue());
	}
	
	@Test
	public void testEquals() {
		BooleanType bool1 = new BooleanType(BooleanType.Value.TRUE);
		BooleanType bool2 = new BooleanType(BooleanType.Value.TRUE);
		BooleanType bool3 = new BooleanType(BooleanType.Value.FALSE);
		
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
		BooleanType bool1 = new BooleanType(BooleanType.Value.TRUE);
		BooleanType bool2 = new BooleanType(BooleanType.Value.TRUE);
		
		assertTrue(bool1.equals(bool2));
		assertEquals(bool1.hashCode(), bool2.hashCode());
		
		// Inequality of hashcodes of inequal objects is not required. 
	}
}
