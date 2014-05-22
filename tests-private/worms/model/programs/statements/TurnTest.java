package worms.model.programs.statements;

import static org.junit.Assert.*;
import static worms.util.AssertUtil.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import worms.gui.game.IActionHandler;
import worms.model.Facade;
import worms.model.Program;
import worms.model.ProgramMock;
import worms.model.SimpleActionHandler;
import worms.model.World;
import worms.model.Worm;
import worms.model.programs.WormsRuntimeException;
import worms.model.programs.expressions.DoubleLiteral;

public class TurnTest {
	
	ActionStatement turn;
	Program program;
	Worm willy;

	@Before
	public void setUp() throws Exception {
		IActionHandler actionHandler = new SimpleActionHandler(new Facade());
		program = new ProgramMock(null, null, actionHandler);
		World world = new World(20,30,new boolean[][]{{true,true},{false,true},{true,true}},new Random());
		willy  = new Worm(world, 112, 358, 1.321, 34.55, "Willy Wonka", null, program);
		program = willy.getProgram(); // Worm clones the program passed to it at construction.
		turn = new Turn(new DoubleLiteral(1.11));
	}
	
	//--------------------------------------------------------------------------
	
	@Test(expected = WormsRuntimeException.class)
	public void testExecute_NullProgram(){
		turn.execute(null);
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testExecute_NullWorm(){
		program = new ProgramMock(null, null, null);
		turn.execute(program);
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testExecute_InvalidTurningAngle(){
		turn = new Turn(new DoubleLiteral(60));		
		turn.execute(program);
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testExecute_CannotTurn(){
		willy.jump(1);	// Deplete AP.
		turn.execute(program);
	}
	
	@Test
	public void testExecute_NormalCases(){
		turn.execute(program);
		assertFuzzyEquals(1.321+1.11, willy.getDirection());
	}
	
	//--------------------------------------------------------------------------
	
	@Test(expected = WormsRuntimeException.class)
	public void testGetCost_NullProgram(){
		turn.getCost(null);
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testGetCost_NullWorm(){
		program = new ProgramMock(null, null, null);
		turn.getCost(program);
	}
	
	@Test
	public void testGetCost_NormalCase(){
		assertEquals(Worm.getTurningCost(1.11), turn.getCost(program));
	}
}
