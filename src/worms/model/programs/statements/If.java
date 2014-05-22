package worms.model.programs.statements;

import worms.model.Program;
import worms.model.programs.ThreeArgumentExecutable;
import worms.model.programs.WormsRuntimeException;
import worms.model.programs.expressions.Expression;
import worms.model.programs.types.BooleanType;

public class If
		extends ThreeArgumentExecutable<Expression<BooleanType>, Statement, Statement>
		implements Statement {

	public If(Expression<BooleanType> condition, Statement then, Statement otherwise) throws IllegalArgumentException {
		super(condition, then, otherwise);
	}

	@Override
	public void execute(Program program) throws WormsRuntimeException {
		if (program == null)
			throw new WormsRuntimeException();
		
		if(getFirstArgument().calculate(program).getValue() == true)
			program.scheduleStatement(getSecondArgument());
		else
			program.scheduleStatement(getThirdArgument());
	}
}
