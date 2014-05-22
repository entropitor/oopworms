package worms.model.programs.statements;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import worms.model.Food;
import worms.model.Program;
import worms.model.ProgramMock;
import worms.model.World;
import worms.model.Worm;
import worms.model.programs.WormsRuntimeException;
import worms.model.programs.ProgramFactory.ForeachType;
import worms.model.programs.expressions.BooleanLiteral;
import worms.model.programs.expressions.EntityNullLiteral;
import worms.model.programs.expressions.Expression;
import worms.model.programs.types.BooleanType;
import worms.model.programs.types.DoubleType;
import worms.model.programs.types.EntityType;
import worms.model.programs.types.Type;

public class ForeachTest {
	
	Statement foreachWorm, foreachFood, foreachAny;
	Statement bodySt1, bodySt2, bodySt3;
	ProgramMock programMock;

	@Before
	public void setUp() throws Exception {
		bodySt1 = new Skip();
		bodySt2 = new Skip();
		bodySt3 = new Skip();
		
		foreachWorm = new Foreach(ForeachType.WORM, "e", bodySt1);
		foreachFood = new Foreach(ForeachType.FOOD, "e", bodySt2);
		foreachAny = new Foreach(ForeachType.ANY, "e", bodySt3);
		
		Map<String, Type<?>>globals = new HashMap<String, Type<?>>();
		globals.put("e", EntityType.NULL_REFERENCE);
		programMock = new ProgramMock(null, globals, null);
		
		World world = new World(20,30,new boolean[][]{{true,true},{false,true},{true,true}},new Random());
		Worm willy  = new Worm(world, 112, 358, 1.321, 34.55, "Willy Wonka", null, programMock);
		Worm jef = new Worm(world, 1, 2, 0, 5, "Jef", null, programMock); // TODO maybe copy prog
		Food pizza = new Food(world);
	}

	@Test(expected = WormsRuntimeException.class)
	public void testExecute_NullProgram(){
		foreachWorm.execute(null);
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testExecute_NullWorm(){
		programMock = new ProgramMock(null, null, null);
		foreachWorm.execute(programMock);
	}
	
	@Test
	public void testExecute_Worms() {
		//First run => condition equals true
		programMock.runStatement(whileStChanging);
		assertArrayEquals(new Statement[]{bodySt1, whileStChanging}, programMock.getExecutionStackAsArray());
		
		//Second run => condition equals false
		programMock.runStatement(whileStChanging);
		assertArrayEquals(new Statement[]{}, programMock.getExecutionStackAsArray());
	}
}
