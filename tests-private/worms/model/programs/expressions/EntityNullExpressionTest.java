package worms.model.programs.expressions;

import static org.junit.Assert.*;

import org.junit.Test;

public class EntityNullExpressionTest {

	@Test
	public void testCalculate() {
		EntityNullExpression e = new EntityNullExpression();
		assertNull(e.calculate(null).getValue());
	}

}
