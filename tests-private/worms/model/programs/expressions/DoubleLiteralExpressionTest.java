package worms.model.programs.expressions;

import static worms.util.AssertUtil.*;

import org.junit.Test;

public class DoubleLiteralExpressionTest {

	@Test
	public void testCalculate() {
		DoubleLiteralExpression e1 = new DoubleLiteralExpression(3);
		assertFuzzyEquals(3.0, e1.calculate(null).getValue());
		

		DoubleLiteralExpression e2 = new DoubleLiteralExpression(-5);
		assertFuzzyEquals(-5, e2.calculate(null).getValue());
	}

}
