package worms.model.programs.expressions;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import worms.model.Food;
import worms.model.Program;
import worms.model.World;
import worms.model.Worm;
import worms.model.programs.WormsRuntimeException;
import worms.model.programs.types.EntityType;
import worms.model.programs.types.Type;

public class IsFoodTest {
	Program program;
	Worm willy;
	World world;
	
	Expression<EntityType> literalNull, literalSelf, literalFood;
	
	IsFood expr;
	
	@Before
	public void setup(){
		Map<String, Type<?>>globals = new HashMap<String, Type<?>>();
		program = new Program(null, globals, null);
		final World world = new World(20,30,new boolean[][]{{true,true},{false,true},{true,true}},new Random());
		willy  = new Worm(world, 112, 358, 1.321, 34.55, "Willy Wonka", null, program);
		//program is cloned so get correct program
		program = willy.getProgram();
		
		literalNull = new EntityNullLiteral();
		literalSelf = new EntitySelfLiteral();
		literalFood = new Expression<EntityType>(){

			@Override
			public EntityType calculate(Program program)
					throws WormsRuntimeException {
				return new EntityType(new Food(world));
			}
			
		};
	}

	@Test
	public void testCalculate_FalseWormCase() {
		expr = new IsFood(literalSelf);
		assertFalse(expr.calculate(program).getValue());
	}
	
	@Test
	public void testCalculate_FalseNullCase() {
		expr = new IsFood(literalNull);
		assertFalse(expr.calculate(program).getValue());
	}
	
	@Test
	public void testCalculate_TrueCase() {
		expr = new IsFood(literalFood);
		assertTrue(expr.calculate(program).getValue());
	}

}
