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

public class EntitySelfLiteralTest {

	EntitySelfLiteral e;
	Program programWithoutWorm, programWithWorm;
	Worm willy;
	
	@Before
	public void setUp() throws Exception {
		e = new EntitySelfLiteral();
		
		Map<String, Type<?>>globals = new HashMap<String, Type<?>>();
		programWithoutWorm = new Program(null, globals, null);
		

		World world = new World(20,30,new boolean[][]{{true,true},{false,true},{true,true}},new Random());
		willy  = new Worm(world, 112, 358, 1.321, 34.55, "Willy Wonka", null, programWithoutWorm);
		//program is cloned so get correct program
		programWithWorm = willy.getProgram();
	}

	@Test
	public void testCalculate_ValidProgram() {
		assertEquals(willy, programWithWorm.getWorm());
		assertEquals(willy, e.calculate(programWithWorm).getValue());
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testCalculate_NullProgram() {
		e.calculate(null);
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testCalculate_ProgramWithoutWormCase() throws Exception {
		e.calculate(programWithoutWorm);
	}

}
