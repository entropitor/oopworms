package worms.model.programs;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import worms.model.programs.expressions.Addition;
import worms.model.programs.expressions.DoubleLiteral;

public class ArgumentExecutableTest {
	
	DoubleLiteral literal3, literal5;
	Addition addExpression3and3, addExpression3and5, addExpressionAddand3;

	@Before
	public void setUp() throws Exception {
		literal3 = new DoubleLiteral(3);
		literal5 = new DoubleLiteral(5);
		addExpression3and3 = new Addition(literal3, literal3);
		addExpression3and5 = new Addition(literal3, literal5);
		addExpressionAddand3 = new Addition(addExpression3and3, literal3);
	}

	@Test
	public void testHasAsSubExecutable_ThisCase() {
		assertTrue(addExpression3and3.hasAsSubExecutable(addExpression3and3));
	}
	
	@Test
	public void testHasAsSubExecutable_TrueSubExecutableCase() {
		assertTrue(addExpression3and3.hasAsSubExecutable(literal3));
		assertTrue(addExpression3and5.hasAsSubExecutable(literal5));
	}
	
	@Test
	public void testHasAsSubExecutable_FalseCase() {
		assertFalse(addExpression3and3.hasAsSubExecutable(literal5));
	}
	
	@Test
	public void testCanHaveAsSubExecutable_NullCase() {
		assertFalse(addExpression3and3.canHaveAsSubExecutable(null));
	}
	
	@Test
	public void testCanHaveAsSubExecutable_ThisCase() {
		assertFalse(addExpression3and3.canHaveAsSubExecutable(addExpression3and3));
	}
	
	@Test
	public void testCanHaveAsSubExecutable_LoopCase() {
		assertTrue(addExpressionAddand3.hasAsSubExecutable(addExpression3and3));
		assertFalse(addExpression3and3.canHaveAsSubExecutable(addExpressionAddand3));
	}
	
	@Test
	public void testCanHaveAsSubExecutable_NormalCase() {
		assertTrue(addExpression3and5.canHaveAsSubExecutable(addExpressionAddand3));
		assertTrue(addExpression3and3.canHaveAsSubExecutable(addExpression3and5));
	}

}
