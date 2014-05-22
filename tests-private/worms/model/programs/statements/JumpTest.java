package worms.model.programs.statements;

import static java.lang.Math.PI;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

public class JumpTest {
	
	ActionStatement jump;
	Program program;
	Worm willy;

	@Before
	public void setUp() throws Exception {
		IActionHandler actionHandler = new SimpleActionHandler(new Facade());
		program = new ProgramMock(null, null, actionHandler);
		World world = new World(20,30,new boolean[][]{{true,true},{true,true},{false,false}},new Random());
		willy  = new Worm(world, 5, 15, PI/2, 1, "Willy Wonka", null, program);
		program = willy.getProgram(); // Worm clones the program passed to it at construction.
		jump = new Jump();
	}
	
	//--------------------------------------------------------------------------
	
	@Test(expected = WormsRuntimeException.class)
	public void testExecute_NullProgram(){
		jump.execute(null);
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testExecute_NullWorm(){
		program = new ProgramMock(null, null, null);
		jump.execute(program);
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testExecute_CannotJump(){
		willy.jump(0.01);	// Deplete AP.
		jump.execute(program);
	}
	
	@Test
	public void testExecute_NormalCases(){
		assertTrue(willy.canJump());
		jump.execute(program);
		assertEquals(0, willy.getActionPoints());
	}
	
	//--------------------------------------------------------------------------
	
	@Test(expected = WormsRuntimeException.class)
	public void testGetCost_NullProgram(){
		jump.getCost(null);
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testGetCost_NullWorm(){
		program = new ProgramMock(null, null, null);
		jump.getCost(program);
	}
	
	@Test
	public void testGetCost_NormalCase(){
		assertEquals(willy.getActionPoints(), jump.getCost(program));
	}
	
	@Test
	public void testGetCost_NoAPCase(){
		willy.jump(0.01);	// Deplete AP.
		assertEquals(0, willy.getActionPoints());
		assertEquals(1, jump.getCost(program));
	}
}
