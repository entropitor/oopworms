package worms.model;

import static org.junit.Assert.*;
import static worms.util.AssertUtil.*;

import org.junit.Before;
import org.junit.Test;

public class PositionTest {
	
	private Position position;
	
	@Before
	public void setup(){
		position = new Position(4,5);
	}
	
	@Test
	public void testConstructor_LegalCase(){
		new Position(3,4);
		new Position(-20, Integer.MAX_VALUE+1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructor_IllegalCase() throws Exception{
		new Position(Double.NaN,-500);
	}
	
	@Test
	public void testIsValidPosition_TrueCase(){
		assertTrue(Position.isValidPosition(-23,54.204840));
		assertTrue(Position.isValidPosition(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY));
	}
	
	@Test
	public void testIsValidPosition_FalseCase(){
		assertFalse(Position.isValidPosition(85859.29340308589404, Double.NaN));
	}
	
	@Test
	public void testIsValidCoordinate_TrueCase(){
		assertTrue(Position.isValidCoordinate(5454994));
	}
	
	@Test
	public void testIsValidCoordinate_FalseCase(){
		assertFalse(Position.isValidCoordinate(Double.NaN));
	}
	
	@Test
	public void testOffset_LegalCase(){
		Position offset = position.offset(-1, -2);
		assertFuzzyEquals(offset.getX(), 3);
		assertFuzzyEquals(offset.getY(), 3);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testOffset_IllegalCase() throws Exception{
		position.offset(Double.NaN, 10000);
	}
	
	@Test
	public void testEquals_TrueCase(){
		assertTrue(position.equals(new Position(4,5)));
		assertTrue(position.equals(position));
	}
	
	@Test
	public void testEquals_FalseCase(){
		assertFalse(position.equals(new Position(4,6)));
		assertFalse(position.equals(new Position(3,5)));
		assertFalse(position.equals(null));
	}
	
	@Test
	public void testSquaredDistance_LegalCase() {
		assertFuzzyEquals(position.squaredDistance(new Position(2,9)),20);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSquaredDistance_IllegalCase() throws Exception {
		position.squaredDistance(null);
	}
}
