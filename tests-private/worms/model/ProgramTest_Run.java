package worms.model;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import worms.model.programs.types.BooleanType;
import worms.model.programs.types.DoubleType;
import worms.model.programs.types.EntityType;
import worms.model.programs.types.Type;

public class ProgramTest_Run {
	
	Program program;
	Worm willy;
	Food pizza;

	@Before
	public void setUp() throws Exception {
		World world = new World(20,30,new boolean[][]{{true,true},{false,true},{true,true}},new Random());
		pizza = new Food(world);
		
		Map<String, Type<?>>globals = new HashMap<String, Type<?>>();
		globals.put("a", new DoubleType(3.14));
		globals.put("entity", new EntityType(pizza));
		globals.put("bool", new BooleanType(true));
		program = new Program(null, globals, null);
		

		willy  = new Worm(world, 112, 358, 1.321, 34.55, "Willy Wonka", null, program);
		//program is cloned so get correct program
		program = willy.getProgram();
	}

	@Test @Ignore
	public void testRun() {
		fail("Needs some statements to be able to test.");
	}

	@Test @Ignore
	public void testScheduleStatement() {
		fail("Needs some statements to be able to test.");
	}

	@Test
	public void testHasRuntimeErrorOccurred_testEncounteredRuntimeError() {
		assertFalse(program.hasRuntimeErrorOccurred());
		program.encounteredRuntimeError();
		assertTrue(program.hasRuntimeErrorOccurred());
		program.encounteredRuntimeError();
		assertTrue(program.hasRuntimeErrorOccurred());
	}

}
