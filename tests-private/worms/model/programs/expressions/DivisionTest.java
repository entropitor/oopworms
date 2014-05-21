package worms.model.programs.expressions;

import static worms.util.AssertUtil.*;

import org.junit.Before;
import org.junit.Test;

import worms.model.programs.WormsRuntimeException;
import worms.model.programs.types.DoubleType;

public class DivisionTest {
	
	Expression<DoubleType> literal0, literal3, literal5, literal17, literalNegative20;
	
	@Before
	public void setup() {
		literal0 = new DoubleLiteral(0);
		literal3 = new DoubleLiteral(3);
		literal5 = new DoubleLiteral(5);
		literal17 = new DoubleLiteral(17);
		literalNegative20 = new DoubleLiteral(-20);
	}

	@Test
	public void testCalculate() {
		Division division = new Division(literal3, literal5);
		assertFuzzyEquals(0.6, division.calculate(null).getValue());
		
		division = new Division(literal17, literalNegative20);
		assertFuzzyEquals(-0.85, division.calculate(null).getValue());
		
		division = new Division(literalNegative20, literal5);
		assertFuzzyEquals(-4, division.calculate(null).getValue());
		
		division = new Division(literalNegative20, literalNegative20);
		assertFuzzyEquals(1, division.calculate(null).getValue());
		
		division = new Division(literal0, literal3);
		assertFuzzyEquals(0, division.calculate(null).getValue());
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testCalculate_DivisionByZeroCase() throws Exception {
		Division division = new Division(literal3, literal0);
		division.calculate(null);
	}

}
