package worms.model.programs.expressions;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import worms.model.Program;
import worms.model.World;
import worms.model.Worm;
import worms.model.programs.WormsRuntimeException;
import worms.model.programs.types.Type;

public class EntityLiteralTest {

	EntityLiteral e;
	Program program;
	Worm willy;
	
	@Before
	public void setUp() throws Exception {
		World world = new World(20,30,new boolean[][]{{true,true},{false,true},{true,true}},new Random());
		willy  = new Worm(world, 112, 358, 1.321, 34.55, "Willy Wonka", null, program);
		program = willy.getProgram(); // Program is cloned so get correct program
		
		e = new EntityLiteral(willy);
	}

	@Test
	public void testCalculate() {
		assertEquals(willy, e.calculate(program).getValue());
	}
}
