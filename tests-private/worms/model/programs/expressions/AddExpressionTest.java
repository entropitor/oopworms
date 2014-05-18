package worms.model.programs.expressions;

import static worms.util.AssertUtil.*;

import org.junit.Before;
import org.junit.Test;

public class AddExpressionTest {
	
	DoubleExpression literal3, literal5, literal17, literalNegative20;
	
	@Before
	public void setup() {
		literal3 = new DoubleLiteralExpression(3);
		literal5 = new DoubleLiteralExpression(5);
		literal17 = new DoubleLiteralExpression(17);
		literalNegative20 = new DoubleLiteralExpression(-20);
	}

	@Test
	public void testCalculate() {
		AddExpression addExpr = new AddExpression(literal3, literal5);
		assertFuzzyEquals(8, addExpr.calculate(null).getDoubleValue());
		
		addExpr = new AddExpression(literal17, literalNegative20);
		assertFuzzyEquals(-3, addExpr.calculate(null).getDoubleValue());
		
		addExpr = new AddExpression(literalNegative20, literal5);
		assertFuzzyEquals(-15, addExpr.calculate(null).getDoubleValue());
		
		addExpr = new AddExpression(literalNegative20, literalNegative20);
		assertFuzzyEquals(-40, addExpr.calculate(null).getDoubleValue());
	}

}
