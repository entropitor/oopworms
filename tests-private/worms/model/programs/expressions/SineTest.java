package worms.model.programs.expressions;

import static worms.util.AssertUtil.*;

import org.junit.Before;
import org.junit.Test;

import worms.model.programs.types.DoubleType;

public class SineTest {
	
	Expression<DoubleType> literal3, literalNegative20;
	
	@Before
	public void setup() {
		literal3 = new DoubleLiteral(3);
		literalNegative20 = new DoubleLiteral(-20);
	}

	@Test
	public void testCalculate_NormalCase() {
		Sine cos = new Sine(literal3);
		assertFuzzyEquals(0.141120, cos.calculate(null).getValue());
		
		cos = new Sine(literalNegative20);
		assertFuzzyEquals(-0.912945, cos.calculate(null).getValue());
	}

}
