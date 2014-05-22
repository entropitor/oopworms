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

public class ProgramTest_Worm {
	
	Program programWithoutWorm, programWithWorm;
	Statement mainSt;
	HashMap<String, Type<?>> globals;
	IActionHandler handler;
	World world;
	Worm willy;

	@Before
	public void setUp() throws Exception {
		mainSt = new Skip();
		globals = new HashMap<>();
		globals.put("a", new DoubleType(3.14));
		handler = new SimpleActionHandler(new Facade());
		
		programWithoutWorm = new Program(mainSt, globals, handler);
		world = new World(20,30,new boolean[][]{{true,true},{false,true},{true,true}},new Random());
		willy = new Worm(world, -8.45, 9.16, PI/2, 2.14, "Bar",null,programWithoutWorm);
		programWithWorm = willy.getProgram();
	}

	@Test
	public void testProgram() {
		//Doesn't throw error
		new Program(mainSt, null, handler);
	}

	@Test
	public void testClone() {
		Program clone = programWithoutWorm.clone();
		assertNotSame(clone, programWithoutWorm);
		assertEquals(3.14, programWithoutWorm.getVariableValue("a").getValue());
		assertEquals(clone.getMainStatement(), programWithoutWorm.getMainStatement());
		assertEquals(clone.getActionHandler(), programWithoutWorm.getActionHandler());
	}

	@Test
	public void testHasWorm_TrueCase() {
		assertTrue(programWithWorm.hasWorm());
	}
	
	@Test
	public void testHasWorm_FalseCase() {
		assertFalse(programWithoutWorm.hasWorm());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testSetWorm_WormHasOtherProgramCase() throws Exception {
		Worm newWorm = new Worm(world, -8.45, 9.16, PI/2, 2.14, "Bar");
		assertEquals(null, programWithoutWorm.getWorm());
		programWithoutWorm.setWorm(newWorm);
	}

}
