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
import worms.model.programs.types.Type;

public class EntitySelfExpressionTest {

	EntitySelfExpression e;
	Program program;
	Worm willy;
	
	@Before
	public void setUp() throws Exception {
		e = new EntitySelfExpression();
		
		Map<String, Type<?>>globals = new HashMap<String, Type<?>>();
		program = new Program(null, globals, null);
		

		World world = new World(20,30,new boolean[][]{{true,true},{false,true},{true,true}},new Random());
		willy  = new Worm(world, 112, 358, 1.321, 34.55, "Willy Wonka", null, program);
		//program is cloned so get correct program
		program = willy.getProgram();
	}

	@Test
	public void testCalculate_ValidProgram() {
		assertEquals(willy, program.getWorm());
		assertEquals(willy, e.calculate(program).getValue());
	}
	
	@Test
	public void testCalculate_NullProgram() {
		assertNull(e.calculate(null).getValue());
	}

}
