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

public class SearchEntityTest {
	Program program, programWithoutWorm;
	Worm willy;
	Worm otherWorm1, otherWorm2, otherWorm3, otherWorm4;
	DoubleLiteral literalPoint3;
	World world;
	
	SearchEntity expr;
	
	@Before
	public void setup(){
		Map<String, Type<?>>globals = new HashMap<String, Type<?>>();
		programWithoutWorm = new Program(null, globals, null);
		
		world = new World(20,30,new boolean[][]{{true,true},{false,true},{true,true}},new Random());
		
		willy  = new Worm(world, 0, 0, Math.PI/4-0.3, 1, "Willy Wonka", null, programWithoutWorm);
		//program is cloned so get correct program
		program = willy.getProgram();
		
		otherWorm1 = new Worm(world, 4.12, 4.12, 1.321, 1, "Other");
		otherWorm2 = new Worm(world, 2.08, 1.24, 1.321, 1, "Other");
		otherWorm3 = new Worm(world, 0, 1.58, 1.321, 1, "Other");
		otherWorm4 = new Worm(world, 6.12, 7.54, 1.321, 1, "Other");
		
		literalPoint3 = new DoubleLiteral(0.3);
	}

	@Test
	public void testCalculate_NormalCase() {
		expr = new SearchEntity(literalPoint3);
		assertEquals(otherWorm2, expr.calculate(program).getValue());
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testCalculate_NullProgramCase() throws Exception {
		expr = new SearchEntity(literalPoint3);
		expr.calculate(null).getValue();
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testCalculate_ProgramWithoutWormCase() throws Exception {
		expr = new SearchEntity(literalPoint3);
		expr.calculate(programWithoutWorm).getValue();
	}
	
	@Test
	public void testCalculate_CenterOtherDirectionCircleRightDirectionCase() {
		world.removeAsEntity(otherWorm2);
		world.removeAsEntity(otherWorm3);
		world.removeAsEntity(otherWorm4);

		otherWorm2 = new Worm(world, -0.54, -0.26, 1.321, 1, "Other");
		
		expr = new SearchEntity(literalPoint3);
		assertEquals(otherWorm2, expr.calculate(program).getValue());
	}
	
	@Test
	public void testCalculate_CenterOtherDirectionCircleRightDirectionCase_ReversedLookupDirection() {
		world.removeAsEntity(otherWorm2);
		world.removeAsEntity(otherWorm3);
		world.removeAsEntity(otherWorm4);

		otherWorm2 = new Worm(world, -0.54, -0.26, 1.321, 1, "Other");
		
		expr = new SearchEntity(new DoubleLiteral(0.3+Math.PI));
		assertEquals(otherWorm2, expr.calculate(program).getValue());
	}
	
	@Test
	public void testCalculate_CenterOtherDirectionCircleOtherDirectionCase() {
		world.removeAsEntity(otherWorm2);
		world.removeAsEntity(otherWorm3);
		world.removeAsEntity(otherWorm4);

		otherWorm2 = new Worm(world, -1.1, -0.84, 1.321, 1, "Other");
		
		expr = new SearchEntity(literalPoint3);
		assertEquals(otherWorm1, expr.calculate(program).getValue());
	}
	
	@Test
	public void testCalculate_CenterOtherDirectionCircleOtherDirectionCase_ReversedLookupDirection() {
		world.removeAsEntity(otherWorm2);
		world.removeAsEntity(otherWorm3);
		world.removeAsEntity(otherWorm4);

		otherWorm2 = new Worm(world, -1.1, -0.84, 1.321, 1, "Other");
		
		expr = new SearchEntity(new DoubleLiteral(0.3+Math.PI));
		assertEquals(otherWorm2, expr.calculate(program).getValue());
	}

}
