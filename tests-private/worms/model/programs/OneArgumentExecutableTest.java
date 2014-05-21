package worms.model.programs;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import worms.model.programs.expressions.DoubleLiteral;
import worms.model.programs.expressions.Sqrt;

public class OneArgumentExecutableTest {
	
	OneArgumentExecutable<?> sqrt;
	DoubleLiteral literal9;

	@Before
	public void setUp() throws Exception {
		literal9 = new DoubleLiteral(9);
		sqrt = new Sqrt(literal9);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testOneArgumentExecutable_IllegalCase() throws Exception {
		new Sqrt(null);
	}

	@Test
	public void testGetFirstArgument() {
		assertEquals(literal9, sqrt.getFirstArgument());
	}

	@Test
	public void testGetSubExecutables() {
		assertArrayEquals(new Executable[]{literal9}, sqrt.getSubExecutables());
	}

}
