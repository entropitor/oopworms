package worms.model.programs.expressions;

import static worms.util.AssertUtil.*;

import org.junit.Before;
import org.junit.Test;

import worms.model.programs.WormsRuntimeException;
import worms.model.programs.types.DoubleType;

public class SqrtTest {
	
	Expression<DoubleType> literal3, literal17, literalNegative20;
	
	@Before
	public void setup() {
		literal3 = new DoubleLiteral(3);
		literal17 = new DoubleLiteral(17);
		literalNegative20 = new DoubleLiteral(-20);
	}

	@Test
	public void testCalculate_NormalCase() {
		Sqrt sqrt = new Sqrt(literal3);
		assertFuzzyEquals(1.73205, sqrt.calculate(null).getValue());
		
		sqrt = new Sqrt(literal17);
		assertFuzzyEquals(4.12311, sqrt.calculate(null).getValue());
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testCalculate_NegativeArgumentCase() throws Exception {
		Sqrt sqrt = new Sqrt(literalNegative20);
		sqrt.calculate(null);
	}

}
