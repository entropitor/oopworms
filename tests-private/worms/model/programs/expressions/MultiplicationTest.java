package worms.model.programs.expressions;

import static worms.util.AssertUtil.*;

import org.junit.Before;
import org.junit.Test;

import worms.model.programs.types.DoubleType;

public class MultiplicationTest {
	
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
		Multiplication multiplication = new Multiplication(literal3, literal5);
		assertFuzzyEquals(15, multiplication.calculate(null).getValue());
		
		multiplication = new Multiplication(literal17, literalNegative20);
		assertFuzzyEquals(-340, multiplication.calculate(null).getValue());
		
		multiplication = new Multiplication(literalNegative20, literal5);
		assertFuzzyEquals(-100, multiplication.calculate(null).getValue());
		
		multiplication = new Multiplication(literalNegative20, literalNegative20);
		assertFuzzyEquals(400, multiplication.calculate(null).getValue());
	}

}
