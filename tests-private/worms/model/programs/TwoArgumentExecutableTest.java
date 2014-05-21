package worms.model.programs;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import worms.model.programs.expressions.Addition;
import worms.model.programs.expressions.DoubleLiteral;

public class TwoArgumentExecutableTest {
	
	TwoArgumentExecutable<?,?> add;
	DoubleLiteral literal9,literal7;

	@Before
	public void setUp() throws Exception {
		literal9 = new DoubleLiteral(9);
		literal7 = new DoubleLiteral(7);
		add = new Addition(literal9,literal7);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testTwoArgumentExecutable_IllegalFirstArgumentCase() throws Exception {
		new Addition(null,literal7);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testTwoArgumentExecutable_IllegalSecondArgumentCase() throws Exception {
		new Addition(literal9,null);
	}

	@Test
	public void testGetFirstArgument() {
		assertEquals(literal9, add.getFirstArgument());
	}
	
	@Test
	public void testGetSecondArgument() {
		assertEquals(literal7, add.getSecondArgument());
	}

	@Test
	public void testGetSubExecutables() {
		assertArrayEquals(new Executable[]{literal9,literal7}, add.getSubExecutables());
	}

}
