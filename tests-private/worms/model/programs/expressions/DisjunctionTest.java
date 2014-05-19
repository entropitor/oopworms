package worms.model.programs.expressions;

import static org.junit.Assert.*;

import org.junit.Test;

import worms.model.Program;
import worms.model.programs.types.BooleanType;
import static worms.model.programs.expressions.BooleanLiteral.*;

public class DisjunctionTest {

	@Test
	public void testCalculate() {
		Disjunction disj = new Disjunction(FALSE_LITERAL, FALSE_LITERAL);
		assertFalse(disj.calculate(null).getValue());

		disj = new Disjunction(FALSE_LITERAL, TRUE_LITERAL);
		assertTrue(disj.calculate(null).getValue());
		
		disj = new Disjunction(TRUE_LITERAL, FALSE_LITERAL);
		assertTrue(disj.calculate(null).getValue());
		
		disj = new Disjunction(TRUE_LITERAL, TRUE_LITERAL);
		assertTrue(disj.calculate(null).getValue());
	}
	
	@Test
	public void testCalculate_FastEvaluation() {
		Expression<BooleanType> nullExpression = 
				new Expression<BooleanType>(){
					@Override
					public BooleanType calculate(Program program) {
						return null;
					}
				};
		Disjunction disj = new Disjunction(TRUE_LITERAL, nullExpression);
		assertTrue(disj.calculate(null).getValue());
	}

}
