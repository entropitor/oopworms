package worms.model.programs.expressions;

import static worms.util.AssertUtil.*;

import org.junit.Test;

public class DoubleLiteralTest {

	@Test
	public void testCalculate() {
		DoubleLiteral e1 = new DoubleLiteral(3);
		assertFuzzyEquals(3.0, e1.calculate(null).getValue());
		

		DoubleLiteral e2 = new DoubleLiteral(-5);
		assertFuzzyEquals(-5, e2.calculate(null).getValue());
	}

}
