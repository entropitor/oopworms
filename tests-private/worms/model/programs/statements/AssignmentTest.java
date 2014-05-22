package worms.model.programs.statements;

import static worms.util.AssertUtil.assertFuzzyEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import worms.model.ProgramMock;
import worms.model.programs.WormsRuntimeException;
import worms.model.programs.expressions.DoubleLiteral;
import worms.model.programs.types.DoubleType;
import worms.model.programs.types.Type;

public class AssignmentTest {
	
	Statement assignment;
	ProgramMock programMock;

	@Before
	public void setUp() throws Exception {
		assignment = new Assignment("a", new DoubleLiteral(500));
		Map<String, Type<?>>globals = new HashMap<String, Type<?>>();
		globals.put("a", new DoubleType(666));
		programMock = new ProgramMock(null, globals, null);
	}
	
	@Test
	public void testExecute_NormalCase() throws Exception {
		assignment.execute(programMock);
		assertFuzzyEquals(500, (Double) programMock.getVariableValue("a").getValue());
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testExecute_NullProgramCase() throws Exception {
		assignment.execute(null);
	}
}
