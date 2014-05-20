package worms.model.programs.expressions;

import static org.junit.Assert.*;

import org.junit.Test;

public class EntityNullLiteralTest {

	@Test
	public void testCalculate() {
		EntityNullLiteral e = new EntityNullLiteral();
		assertNull(e.calculate(null).getValue());
	}

}
