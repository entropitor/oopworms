package worms.model.programs.expressions;

import static worms.util.AssertUtil.*;

import org.junit.Before;
import org.junit.Test;

import worms.model.programs.types.DoubleType;

public class SubtractionTest {
	
	Expression<DoubleType> literal3, literal5, literal17, literalNegative20;
	
	@Before
	public void setup() {
		literal3 = new DoubleLiteral(3);
		literal5 = new DoubleLiteral(5);
		literal17 = new DoubleLiteral(17);
		literalNegative20 = new DoubleLiteral(-20);
	}

	@Test
	public void testCalculate() {
		Subtraction subtraction = new Subtraction(literal3, literal5);
		assertFuzzyEquals(-2, subtraction.calculate(null).getValue());
		
		subtraction = new Subtraction(literal17, literalNegative20);
		assertFuzzyEquals(37, subtraction.calculate(null).getValue());
		
		subtraction = new Subtraction(literalNegative20, literal5);
		assertFuzzyEquals(-25, subtraction.calculate(null).getValue());
		
		subtraction = new Subtraction(literalNegative20, literalNegative20);
		assertFuzzyEquals(0, subtraction.calculate(null).getValue());
	}

}
