package worms.model.programs;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import worms.model.programs.ProgramFactory.ForeachType;
import worms.model.programs.expressions.Addition;
import worms.model.programs.expressions.BooleanLiteral;
import worms.model.programs.expressions.DoubleLiteral;
import worms.model.programs.statements.*;

public class ArgumentExecutableTest_IsWellFormed {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testHasActionStatementAsSubExecutable_TrueCase() {
		ArgumentExecutable statement = new While(BooleanLiteral.FALSE_LITERAL, new Skip());
		assertTrue(statement.hasActionStatementAsSubExecutable());
	}
	
	@Test
	public void testHasActionStatementAsSubExecutable_DeepTrueCase() {
		While inner = new While(BooleanLiteral.FALSE_LITERAL, new Skip());
		ArgumentExecutable outer = new If(BooleanLiteral.FALSE_LITERAL, inner, inner);
		assertTrue(outer.hasActionStatementAsSubExecutable());
	}
	
	@Test
	public void testHasActionStatementAsSubExecutable_FalseCase() {
		ArgumentExecutable statement = new Turn(new DoubleLiteral(0));
		assertFalse(statement.hasActionStatementAsSubExecutable());
	}
	
	@Test
	public void testHasActionStatementAsSubExecutable_FalseExpressionCase() {
		ArgumentExecutable expression = new Addition(new DoubleLiteral(5), new DoubleLiteral(7));
		assertFalse(expression.hasActionStatementAsSubExecutable());
	}

	@Test
	public void testHasActionStatementInsideForEach_TrueCase() {
		ArrayList<Statement> list = new ArrayList<Statement>();
		list.add(new Print(BooleanLiteral.TRUE_LITERAL));
		list.add(new Skip());
		Foreach badlyFormedForStatement = new Foreach(ForeachType.WORM, "a", new Sequence(list));
		While outer = new While(BooleanLiteral.TRUE_LITERAL, badlyFormedForStatement);
		
		assertTrue(outer.hasActionStatementInsideForEach());
		assertTrue(badlyFormedForStatement.hasActionStatementInsideForEach());
	}
	
	@Test
	public void testHasActionStatementInsideForEach_FalseNoForEachAndNoActionStatementCase() {
		ArrayList<Statement> list = new ArrayList<Statement>();
		list.add(new Turn(new DoubleLiteral(3)));
		While wellFormedMainStatement = new While(
				BooleanLiteral.FALSE_LITERAL, new Sequence(list));
		assertFalse(wellFormedMainStatement.hasActionStatementInsideForEach());
	}
	
	@Test
	public void testHasActionStatementInsideForEach_FalseNoActionStatementCase() {
		ArrayList<Statement> list = new ArrayList<Statement>();
		list.add(new Print(BooleanLiteral.TRUE_LITERAL));
		list.add(new Sequence(new ArrayList<Statement>()));
		Foreach wellFormedForStatement = new Foreach(ForeachType.WORM, "a", new Sequence(list));
		While outer = new While(BooleanLiteral.TRUE_LITERAL, wellFormedForStatement);
		
		assertFalse(outer.hasActionStatementInsideForEach());
		assertFalse(wellFormedForStatement.hasActionStatementInsideForEach());
	}
	
	@Test
	public void testHasActionStatementInsideForEach_FalseNoForEachCase() {
		ArrayList<Statement> list = new ArrayList<Statement>();
		list.add(new Skip());
		While wellFormedMainStatement = new While(
				BooleanLiteral.FALSE_LITERAL, new Sequence(list));
		assertFalse(wellFormedMainStatement.hasActionStatementInsideForEach());
	}

}
