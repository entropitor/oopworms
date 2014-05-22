package worms.model.programs.statements;

import static java.lang.Math.PI;
import static org.junit.Assert.assertEquals;

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

public class SkipTest {
	
	ActionStatement skip;
	Program program;
	Worm willy;

	@Before
	public void setUp() throws Exception {
		IActionHandler actionHandler = new SimpleActionHandler(new Facade());
		program = new ProgramMock(null, null, actionHandler);
		World world = new World(20,30,new boolean[][]{{true,true},{true,true},{false,false}},new Random());
		willy  = new Worm(world, 5, 15, PI/2, 1, "Willy Wonka", null, program);
		program = willy.getProgram(); // Worm clones the program passed to it at construction.
		skip = new Skip();
	}
	
	//--------------------------------------------------------------------------
	
	@Test
	public void testGetCost_NormalCase(){
		assertEquals(0, skip.getCost(program));
	}
}
