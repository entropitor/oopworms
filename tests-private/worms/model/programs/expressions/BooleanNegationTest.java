package worms.model.programs.expressions;

import static org.junit.Assert.*;

import org.junit.Test;

import static worms.model.programs.expressions.BooleanLiteral.*;

public class BooleanNegationTest {

	@Test
	public void testCalculate() {
		BooleanNegation negation = new BooleanNegation(FALSE_LITERAL);
		assertTrue(negation.calculate(null).getValue());

		negation = new BooleanNegation(TRUE_LITERAL);
		assertFalse(negation.calculate(null).getValue());
	}

}
