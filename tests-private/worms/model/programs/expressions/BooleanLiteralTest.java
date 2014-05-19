package worms.model.programs.expressions;

import static org.junit.Assert.*;

import org.junit.Test;

public class BooleanLiteralTest {

	@Test
	public void testCalculate() {
		BooleanLiteral e1 = new BooleanLiteral(false);
		assertFalse(e1.calculate(null).getValue());
		

		BooleanLiteral e2 = new BooleanLiteral(true);
		assertTrue(e2.calculate(null).getValue());
		
		assertTrue(BooleanLiteral.TRUE_EXPRESSION.calculate(null).getValue());
		assertFalse(BooleanLiteral.FALSE_EXPRESSION.calculate(null).getValue());
	}

}
