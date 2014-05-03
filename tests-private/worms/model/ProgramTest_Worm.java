package worms.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class ProgramTest_Worm {
	
	Program program;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testProgram() {
		fail("Not yet implemented");
	}

	@Test
	public void testClone() {
		Program clone = program.clone();
		fail("Test execution stack is empty");
		fail("Test global variables with same value");
		fail("Test global variables aren't changed when they are changed in original program");
		assertEquals(clone.getMainStatement(), program.getMainStatement());
		assertEquals(clone.getActionHandler(), program.getActionHandler());
	}

	@Test
	public void testHasWorm_TrueCase() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testHasWorm_FalseCase() {
		fail("Not yet implemented");
	}

	@Test(expected = IllegalStateException.class)
	public void testSetWorm_HasWormCase() throws Exception {
		
	}
	
	@Test(expected = IllegalStateException.class)
	public void testSetWorm_WormHasOtherProgramCase() throws Exception {
		
	}
	
	@Test
	public void testSetWorm_NormalCase() {
		fail("Not yet implemented");
	}

}
