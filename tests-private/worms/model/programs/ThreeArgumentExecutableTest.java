package worms.model.programs;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import worms.model.programs.expressions.BooleanLiteral;
import worms.model.programs.expressions.DoubleLiteral;
import worms.model.programs.statements.Fire;
import worms.model.programs.statements.If;
import worms.model.programs.statements.Skip;
import worms.model.programs.statements.Statement;

public class ThreeArgumentExecutableTest {
	
	ThreeArgumentExecutable<?,?,?> ifelse;
	BooleanLiteral literalTrue;
	Statement statementIf,statementElse;

	@Before
	public void setUp() throws Exception {
		literalTrue = BooleanLiteral.TRUE_LITERAL;
		statementIf = new Skip();
		statementElse = new Fire(new DoubleLiteral(45));
		ifelse = new If(literalTrue, statementIf, statementElse);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testThreeArgumentExecutable_IllegalFirstArgumentCase() throws Exception {
		new If(null, statementIf, statementElse);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testThreeArgumentExecutable_IllegalSecondArgumentCase() throws Exception {
		new If(literalTrue, null, statementElse);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testThreeArgumentExecutable_IllegalThirdArgumentCase() throws Exception {
		new If(literalTrue, statementIf, null);
	}

	@Test
	public void testGetFirstArgument() {
		assertEquals(literalTrue, ifelse.getFirstArgument());
	}
	
	@Test
	public void testGetSecondArgument() {
		assertEquals(statementIf, ifelse.getSecondArgument());
	}
	
	@Test
	public void testGetThirdArgument() {
		assertEquals(statementElse, ifelse.getThirdArgument());
	}

	@Test
	public void testGetSubExecutables() {
		assertArrayEquals(new Executable[]{literalTrue, statementIf, statementElse}, ifelse.getSubExecutables());
	}

}
