package worms.model.programs.expressions;

import static worms.util.AssertUtil.*;

import org.junit.Before;
import org.junit.Test;

import worms.model.programs.types.DoubleType;

public class CosinusTest {
	
	Expression<DoubleType> literal3, literalNegative20;
	
	@Before
	public void setup() {
		literal3 = new DoubleLiteral(3);
		literalNegative20 = new DoubleLiteral(-20);
	}

	@Test
	public void testCalculate_NormalCase() {
		Cosinus cos = new Cosinus(literal3);
		assertFuzzyEquals(-0.989992, cos.calculate(null).getValue());
		
		cos = new Cosinus(literalNegative20);
		assertFuzzyEquals(0.4080820, cos.calculate(null).getValue());
	}

}
