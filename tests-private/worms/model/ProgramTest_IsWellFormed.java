package worms.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import worms.model.programs.ProgramFactory.ForeachType;
import worms.model.programs.expressions.BooleanLiteral;
import worms.model.programs.statements.*;

public class ProgramTest_IsWellFormed {

	@Test
	public void testIsWellFormed_TrueCase() {
		ArrayList<Statement> list = new ArrayList<Statement>();
		list.add(new Skip());
		Statement wellFormedMainStatement = new While(
				BooleanLiteral.FALSE_LITERAL, new Sequence(list));
		Program wellFormedProgram = new Program(wellFormedMainStatement, null, null);
		assertTrue(wellFormedProgram.isWellFormed());
		
		assertTrue(new Program(new Skip(), null,null).isWellFormed());
	}
	
	@Test
	public void testIsWellFormed_FalseCase() {
		Statement badlyFormedMainStatement = new Foreach(ForeachType.WORM, "a", new Skip());
		Program badlyFormedProgram = new Program(badlyFormedMainStatement, null, null);
		assertFalse(badlyFormedProgram.isWellFormed());
		assertTrue(badlyFormedProgram.hasRuntimeErrorOccurred());
		
		ArrayList<Statement> list = new ArrayList<Statement>();
		list.add(new Print(BooleanLiteral.TRUE_LITERAL));
		list.add(new Skip());
		badlyFormedMainStatement = new Foreach(ForeachType.WORM, "a", new Sequence(list));
		badlyFormedProgram = new Program(badlyFormedMainStatement, null, null);
		assertFalse(badlyFormedProgram.isWellFormed());
		assertTrue(badlyFormedProgram.hasRuntimeErrorOccurred());
	}

}
