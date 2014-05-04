package worms.model;

import static java.lang.Math.PI;
import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class WormTest_Program {
	
	Worm wormWithoutProgram, wormWithProgram;

	@Before
	public void setUp() throws Exception {
		World world = new World(20,30,new boolean[][]{{true,true},{false,true},{true,true}},new Random());
		wormWithoutProgram = new Worm(world, -8.45, 9.16, PI/2, 2.14, "Bar");
		
		Program program = null;
		wormWithProgram = new Worm(world, -8.45, 9.16, PI/2, 2.14, "Bar",null,program);
		
	}
	
	@Test
	public void testGetProgram_SingleCase() {
		fail("Check if main statement is correct");
		fail("Check if action handler is correct");
		fail("Check if globals are there");
	}

	@Test
	public void testHasProgram_TrueCase() {
		assertTrue(wormWithProgram.hasProgram());
	}
	
	@Test
	public void testHasProgram_FalseCase() {
		assertFalse(wormWithoutProgram.hasProgram());
	}

	@Test
	public void testHasProperProgram_WithProgramCase() {
		assertTrue(wormWithProgram.hasProperProgram());
	}
	
	@Test
	public void testHasProperProgram_WithoutProgramCase() {
		assertTrue(wormWithoutProgram.hasProperProgram());
	}

}
