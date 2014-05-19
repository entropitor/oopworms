package worms.model.programs.expressions;

import static org.junit.Assert.*;

import org.junit.Test;

public class BooleanLiteralExpressionTest {

	@Test
	public void testCalculate() {
		BooleanLiteralExpression e1 = new BooleanLiteralExpression(false);
		assertFalse(e1.calculate(null).getValue());
		

		BooleanLiteralExpression e2 = new BooleanLiteralExpression(true);
		assertTrue(e2.calculate(null).getValue());
		
		assertTrue(BooleanLiteralExpression.TRUE_EXPRESSION.calculate(null).getValue());
		assertFalse(BooleanLiteralExpression.FALSE_EXPRESSION.calculate(null).getValue());
	}

}
