package worms.model.programs.statements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static worms.util.AssertUtil.assertFuzzyEquals;

import static java.lang.Math.PI;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import worms.gui.game.IActionHandler;
import worms.model.Facade;
import worms.model.Position;
import worms.model.Program;
import worms.model.ProgramMock;
import worms.model.SimpleActionHandler;
import worms.model.World;
import worms.model.Worm;
import worms.model.programs.WormsRuntimeException;

public class MoveTest {
	
	ActionStatement move;
	Program program;
	Worm willy;

	@Before
	public void setUp() throws Exception {
		IActionHandler actionHandler = new SimpleActionHandler(new Facade());
		program = new ProgramMock(null, null, actionHandler);
		World world = new World(20,30,new boolean[][]{{true,true},{true,true},{false,false}},new Random());
		willy  = new Worm(world, 5, 15, PI/2, 1, "Willy Wonka", null, program);
		program = willy.getProgram(); // Worm clones the program passed to it at construction.
		move = new Move();
	}
	
	//--------------------------------------------------------------------------
	
	@Test(expected = WormsRuntimeException.class)
	public void testExecute_NullProgram(){
		move.execute(null);
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testExecute_NullWorm(){
		program = new ProgramMock(null, null, null);
		move.execute(program);
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testExecute_CannotMove(){
		willy.jump(0.01);	// Deplete AP.
		move.execute(program);
	}
	
	@Test
	public void testExecute_NormalCases(){
		willy.turn(-PI/2); // Face right
		assertTrue(willy.canMove());
		Position after = willy.getPositionAfterMove();
		move.execute(program);
		assertFuzzyEquals(after.getX(), willy.getPosition().getX());
		// (The y-coordinate will be different from 'after.getY()' because the worm falls.)
	}
	
	//--------------------------------------------------------------------------
	
	@Test(expected = WormsRuntimeException.class)
	public void testGetCost_NullProgram(){
		move.getCost(null);
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testGetCost_NullWorm(){
		program = new ProgramMock(null, null, null);
		move.getCost(program);
	}
	
	@Test
	public void testGetCost_NormalCase(){
		assertEquals(willy.getCostForMove(willy.getPositionAfterMove()), move.getCost(program));
	}
}
