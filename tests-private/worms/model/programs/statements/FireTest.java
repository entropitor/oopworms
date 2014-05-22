package worms.model.programs.statements;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import worms.model.ActionHandlerMock;
import worms.model.Program;
import worms.model.ProgramMock;
import worms.model.World;
import worms.model.Worm;
import worms.model.programs.WormsRuntimeException;
import worms.model.programs.expressions.DoubleLiteral;

public class FireTest {
	
	ActionStatement fire;
	Program program;
	Worm willy;
	ActionHandlerMock actionHandler;

	@Before
	public void setUp() throws Exception {
		actionHandler = new ActionHandlerMock();
		program = new ProgramMock(null, null, actionHandler);
		World world = new World(20,30,new boolean[][]{{true,true},{false,true},{true,true}},new Random());
		willy  = new Worm(world, 112, 358, 1.321, 34.55, "Willy Wonka", null, program);
		program = willy.getProgram(); // Worm clones the program passed to it at construction.
		fire = new Fire(new DoubleLiteral(80));
	}
	
	//--------------------------------------------------------------------------
	
	@Test(expected = WormsRuntimeException.class)
	public void testExecute_NullProgram(){
		fire.execute(null);
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testExecute_NullWorm(){
		program = new ProgramMock(null, null, null);
		fire.execute(program);
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testExecute_YieldTooBig(){
		fire = new Fire(new DoubleLiteral(101.1));		
		fire.execute(program);
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testExecute_NegativeYield(){
		fire = new Fire(new DoubleLiteral(-0.5));		
		fire.execute(program);
	}
	
	@Test
	public void testExecute_NormalCases(){
		int AP = willy.getActionPoints();
		System.out.println(AP);
		System.out.println(willy.getSelectedWeapon().getCost());
		assertTrue(willy.canFire(80));
		fire.execute(program);
		assertTrue(actionHandler.hasCalledFire);
	}
	
	//--------------------------------------------------------------------------
	
	@Test(expected = WormsRuntimeException.class)
	public void testGetCost_NullProgram(){
		fire.getCost(null);
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testGetCost_NullWorm(){
		program = new ProgramMock(null, null, null);
		fire.getCost(program);
	}
	
	@Test
	public void testGetCost_NormalCase(){
		assertEquals(willy.getSelectedWeapon().getCost(), fire.getCost(program));
	}
}
