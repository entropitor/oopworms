package worms.model.programs;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import worms.model.programs.expressions.AddExpression;
import worms.model.programs.expressions.DoubleLiteralExpression;

public class ArgumentExecutableTest {
	
	DoubleLiteralExpression literal3, literal5;
	AddExpression addExpression3and3, addExpression3and5, addExpressionAddand3;

	@Before
	public void setUp() throws Exception {
		literal3 = new DoubleLiteralExpression(3);
		literal5 = new DoubleLiteralExpression(5);
		addExpression3and3 = new AddExpression(literal3, literal3);
		addExpression3and5 = new AddExpression(literal3, literal5);
		addExpressionAddand3 = new AddExpression(addExpression3and3, literal3);
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
