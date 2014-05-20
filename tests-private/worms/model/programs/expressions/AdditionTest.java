package worms.model.programs.expressions;

import static worms.util.AssertUtil.*;

import org.junit.Before;
import org.junit.Test;

import worms.model.programs.types.DoubleType;

public class AdditionTest {
	
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
		Addition addExpr = new Addition(literal3, literal5);
		assertFuzzyEquals(8, addExpr.calculate(null).getValue());
		
		addExpr = new Addition(literal17, literalNegative20);
		assertFuzzyEquals(-3, addExpr.calculate(null).getValue());
		
		addExpr = new Addition(literalNegative20, literal5);
		assertFuzzyEquals(-15, addExpr.calculate(null).getValue());
		
		addExpr = new Addition(literalNegative20, literalNegative20);
		assertFuzzyEquals(-40, addExpr.calculate(null).getValue());
	}

}
