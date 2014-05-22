package worms.model.programs.statements;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import worms.model.Food;
import worms.model.ProgramMock;
import worms.model.World;
import worms.model.Worm;
import worms.model.programs.ProgramFactory.ForeachType;
import worms.model.programs.WormsRuntimeException;
import worms.model.programs.expressions.EntityLiteral;
import worms.model.programs.types.EntityType;
import worms.model.programs.types.Type;

//FIXME something with Program/ProgramMock
public class ForeachTest {
	
	Statement foreachWorm, foreachFood, foreachAny;
	Statement bodySt1, bodySt2, bodySt3;
	ProgramMock programMock;
	Worm willy, jef;
	Food pizza;

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
		willy  = new Worm(world, 112, 358, 1.321, 34.55, "Willy Wonka", null, programMock);
		jef = new Worm(world, 1, 2, 0, 5, "Jef", null, programMock);
		//programMock = (ProgramMock) jef.getProgram();
		pizza = new Food(world);
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
	public void testExecute_ForeachWorm() {
		programMock.runStatement(foreachWorm);
		Statement[] willyStatements = 
				new Statement[]{new Assignment("e", new EntityLiteral(willy)), bodySt1};
		Statement[] jefStatements = 
				new Statement[]{new Assignment("e", new EntityLiteral(jef)), bodySt1};
		
		assertTrue(hasAsSubArray(programMock.getExecutionStackAsArray(), willyStatements));
		assertTrue(hasAsSubArray(programMock.getExecutionStackAsArray(), jefStatements));
	}
	
	@Test
	public void testExecute_ForeachFood() {
		programMock.runStatement(foreachFood);
		Statement[] pizzaStatements = 
				new Statement[]{new Assignment("e", new EntityLiteral(pizza)), bodySt2};
		
		assertTrue(hasAsSubArray(programMock.getExecutionStackAsArray(), pizzaStatements));
	}
	
	@Test
	public void testExecute_ForeachAny() {
		programMock.runStatement(foreachAny);
		Statement[] willyStatements = 
				new Statement[]{new Assignment("e", new EntityLiteral(willy)), bodySt3};
		Statement[] jefStatements = 
				new Statement[]{new Assignment("e", new EntityLiteral(jef)), bodySt3};
		Statement[] pizzaStatements = 
				new Statement[]{new Assignment("e", new EntityLiteral(pizza)), bodySt3};
		
		assertTrue(hasAsSubArray(programMock.getExecutionStackAsArray(), willyStatements));
		assertTrue(hasAsSubArray(programMock.getExecutionStackAsArray(), jefStatements));
		assertTrue(hasAsSubArray(programMock.getExecutionStackAsArray(), pizzaStatements));
	}
	
	private boolean hasAsSubArray(Statement[] array, Statement[] subArray) {
		return (Collections.indexOfSubList(Arrays.asList(array), Arrays.asList(subArray)) != -1);
	}
}
