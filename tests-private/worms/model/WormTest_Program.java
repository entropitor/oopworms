package worms.model;

import static java.lang.Math.PI;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import worms.gui.game.IActionHandler;
import worms.model.programs.statements.Skip;
import worms.model.programs.statements.Statement;
import worms.model.programs.types.DoubleType;
import worms.model.programs.types.Type;

public class WormTest_Program {
	
	Worm wormWithoutProgram, wormWithProgram;
	Statement mainSt;
	HashMap<String, Type<?>> globals;
	IActionHandler handler;

	@Before
	public void setUp() throws Exception {
		World world = new World(20,30,new boolean[][]{{true,true},{false,true},{true,true}},new Random());
		wormWithoutProgram = new Worm(world, -8.45, 9.16, PI/2, 2.14, "Bar");
		
		mainSt = new Skip();
		globals = new HashMap<>();
		globals.put("a", new DoubleType(3.14));
		handler = new SimpleActionHandler(new Facade());
		Program program = new Program(mainSt, globals, handler);
		wormWithProgram = new Worm(world, -8.45, 9.16, PI/2, 2.14, "Bar",null,program);
		
	}
	
	@Test
	public void testGetProgram_SingleCase() {
		Program prog = wormWithProgram.getProgram();
		assertEquals(mainSt, prog.getMainStatement());
		assertEquals(3.14, prog.getVariableValue("a").getValue());
		assertEquals(handler, prog.getActionHandler());
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
