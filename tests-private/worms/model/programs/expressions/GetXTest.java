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
import worms.model.programs.WormsRuntimeException;
import worms.model.programs.types.EntityType;
import worms.model.programs.types.Type;

public class GetXTest {
	Program program;
	Worm willy;
	World world;
	
	Expression<EntityType> literalNull, literalSelf;
	
	GetX expr;
	
	@Before
	public void setup(){
		Map<String, Type<?>>globals = new HashMap<String, Type<?>>();
		program = new Program(null, globals, null);
		World world = new World(20,30,new boolean[][]{{true,true},{false,true},{true,true}},new Random());
		willy  = new Worm(world, 112, 358, 1.321, 34.55, "Willy Wonka", null, program);
		//program is cloned so get correct program
		program = willy.getProgram();
		
		literalNull = new EntityNullLiteral();
		literalSelf = new EntitySelfLiteral();
	}

	@Test
	public void testCalculate_NormalCase() {
		expr = new GetX(literalSelf);
		assertFuzzyEquals(112, expr.calculate(program).getValue());
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testCalculate_IllegalCase() {
		expr = new GetX(literalNull);
		expr.calculate(program).getValue();
	}

}
