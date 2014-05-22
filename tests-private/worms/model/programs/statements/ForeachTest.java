package worms.model.programs.statements;

import static org.junit.Assert.*;

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
import worms.model.programs.types.EntityType;
import worms.model.programs.types.Type;

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
		programMock.setWorm(jef);
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
		
		Statement[] stack = programMock.getExecutionStackAsArray();
		assertTrue(stack[0] instanceof Assignment);
		assertEquals(bodySt1, stack[1]);
		assertTrue(stack[2] instanceof Assignment);
		assertEquals(bodySt1, stack[3]);
		assertEquals(4, stack.length);
		
		
		programMock.runStatement(stack[0]);
		Object assigned1 = programMock.getVariableValue("e").getValue();
		programMock.runStatement(stack[2]);
		Object assigned2 = programMock.getVariableValue("e").getValue();
		
		assertTrue(jef == assigned1 || jef == assigned2);
		assertTrue(willy == assigned1 || willy == assigned2);
	}
	
	@Test
	public void testExecute_ForeachFood() {
		programMock.runStatement(foreachFood);
		
		Statement[] stack = programMock.getExecutionStackAsArray();
		assertTrue(stack[0] instanceof Assignment);
		assertEquals(bodySt2, stack[1]);
		assertEquals(2, stack.length);
		
		programMock.runStatement(stack[0]);
		assertEquals(pizza, programMock.getVariableValue("e").getValue());
	}
	
	@Test
	public void testExecute_ForeachAny() {
		programMock.runStatement(foreachAny);

		
		Statement[] stack = programMock.getExecutionStackAsArray();
		assertTrue(stack[0] instanceof Assignment);
		assertEquals(bodySt3, stack[1]);
		assertTrue(stack[2] instanceof Assignment);
		assertEquals(bodySt3, stack[3]);
		assertTrue(stack[4] instanceof Assignment);
		assertEquals(bodySt3, stack[5]);
		assertEquals(6, stack.length);
		
		
		programMock.runStatement(stack[0]);
		Object assigned1 = programMock.getVariableValue("e").getValue();
		programMock.runStatement(stack[2]);
		Object assigned2 = programMock.getVariableValue("e").getValue();
		programMock.runStatement(stack[4]);
		Object assigned3 = programMock.getVariableValue("e").getValue();
		
		assertTrue(jef == assigned1 || jef == assigned2 || jef == assigned3);
		assertTrue(willy == assigned1 || willy == assigned2 || willy == assigned3);
		assertTrue(pizza == assigned1 || pizza == assigned2 || pizza == assigned3);
	}
}
