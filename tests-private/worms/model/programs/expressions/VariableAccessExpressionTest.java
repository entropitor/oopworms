package worms.model.programs.expressions;

import static worms.util.AssertUtil.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import worms.model.Program;
import worms.model.World;
import worms.model.Worm;
import worms.model.programs.types.DoubleType;
import worms.model.programs.types.Type;

public class VariableAccessExpressionTest {
	VariableAccessExpression expressionGetA,expressionGetB;
	Program program;
	Worm willy;
	
	@Before
	public void setUp() throws Exception {
		expressionGetA = new VariableAccessExpression("a");
		expressionGetB = new VariableAccessExpression("b");
		
		Map<String, Type<?>>globals = new HashMap<String, Type<?>>();
		globals.put("a", new DoubleType(3.14));
		program = new Program(null, globals, null);
		

		World world = new World(20,30,new boolean[][]{{true,true},{false,true},{true,true}},new Random());
		willy  = new Worm(world, 112, 358, 1.321, 34.55, "Willy Wonka", null, program);
		//program is cloned so get correct program
		program = willy.getProgram();
	}

	@Test
	public void testCalculate_NormalCase() {
		assertFuzzyEquals(3.14, (Double) expressionGetA.calculate(program).getValue());
	}
	
	@Test
	public void testCalculate_DoesntHaveVariableCase() {
		assertFuzzyEquals(0, (Double) expressionGetB.calculate(program).getValue());
	}
	
	@Test
	public void testCalculate_NullProgramCase() {
		assertFuzzyEquals(0, (Double) expressionGetA.calculate(null).getValue());
	}

}
