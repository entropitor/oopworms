package worms.model.programs.expressions;

import static worms.util.AssertUtil.*;

import org.junit.Test;

import worms.model.programs.WormsRuntimeException;

public class DoubleLiteralTest {

	@Test
	public void testCalculate() {
		DoubleLiteral e1 = new DoubleLiteral(3);
		assertFuzzyEquals(3.0, e1.calculate(null).getValue());
		

		DoubleLiteral e2 = new DoubleLiteral(-5);
		assertFuzzyEquals(-5, e2.calculate(null).getValue());
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testCalculate_NaNCase() throws Exception {
		DoubleLiteral e1 = new DoubleLiteral(Double.NaN);
		e1.calculate(null);
	}

}
