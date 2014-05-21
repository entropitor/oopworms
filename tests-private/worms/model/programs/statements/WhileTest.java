package worms.model.programs.statements;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import worms.model.Program;
import worms.model.ProgramMock;
import worms.model.programs.WormsRuntimeException;
import worms.model.programs.expressions.BooleanLiteral;
import worms.model.programs.expressions.Expression;
import worms.model.programs.types.BooleanType;

public class WhileTest {
	
	Statement whileStChanging,whileStFalse,whileStTrue;
	Statement bodySt1, bodySt2, bodySt3;
	ProgramMock programMock;

	@Before
	public void setUp() throws Exception {
		bodySt1 = new Skip();
		bodySt2 = new Skip();
		bodySt3 = new Skip();
		
		Expression<BooleanType> whileChangingCondition = new Expression<BooleanType>(){

			@Override
			public BooleanType calculate(Program program)
					throws WormsRuntimeException {
				BooleanType result = new BooleanType(firstTime);
				firstTime = false;
				return result;
			}
			
			boolean firstTime = true;
			
		};
		
		whileStChanging = new While(whileChangingCondition, bodySt1);
		whileStFalse = new While(BooleanLiteral.FALSE_LITERAL, bodySt2);
		whileStTrue = new While(BooleanLiteral.TRUE_LITERAL, bodySt3);
		
		programMock = new ProgramMock(null, null, null);
	}

	@Test
	public void testExecute_ChangingCondition() {
		//First run => condition equals true
		programMock.runStatement(whileStChanging);
		assertArrayEquals(new Statement[]{bodySt1, whileStChanging}, programMock.getExecutionStackAsArray());
		
		//Second run => condition equals false
		programMock.runStatement(whileStChanging);
		assertArrayEquals(new Statement[]{}, programMock.getExecutionStackAsArray());
	}
	
	@Test
	public void testExecute_TrueCondition() {
		//First run => condition equals true
		programMock.runStatement(whileStTrue);
		assertArrayEquals(new Statement[]{bodySt3, whileStTrue}, programMock.getExecutionStackAsArray());
		
		//Second run => condition equals true
		programMock.runStatement(whileStTrue);
		assertArrayEquals(new Statement[]{bodySt3, whileStTrue}, programMock.getExecutionStackAsArray());
	}
	
	@Test
	public void testExecute_FalseCondition() {
		//First run => condition equals false
		programMock.runStatement(whileStFalse);
		assertArrayEquals(new Statement[]{}, programMock.getExecutionStackAsArray());
		
		//Second run => condition equals false
		programMock.runStatement(whileStFalse);
		assertArrayEquals(new Statement[]{}, programMock.getExecutionStackAsArray());
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testExecute_NullProgramCase() throws Exception {
		whileStFalse.execute(null);
	}

}
