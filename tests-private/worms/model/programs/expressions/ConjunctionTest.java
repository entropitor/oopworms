package worms.model.programs.expressions;

import static org.junit.Assert.*;

import org.junit.Test;

import worms.model.Program;
import worms.model.programs.types.BooleanType;
import static worms.model.programs.expressions.BooleanLiteral.*;

public class ConjunctionTest {

	@Test
	public void testCalculate() {
		Conjunction conj = new Conjunction(FALSE_LITERAL, FALSE_LITERAL);
		assertFalse(conj.calculate(null).getValue());

		conj = new Conjunction(FALSE_LITERAL, TRUE_LITERAL);
		assertFalse(conj.calculate(null).getValue());
		
		conj = new Conjunction(TRUE_LITERAL, FALSE_LITERAL);
		assertFalse(conj.calculate(null).getValue());
		
		conj = new Conjunction(TRUE_LITERAL, TRUE_LITERAL);
		assertTrue(conj.calculate(null).getValue());
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
		Conjunction conj = new Conjunction(FALSE_LITERAL, nullExpression);
		assertFalse(conj.calculate(null).getValue());
	}

}
