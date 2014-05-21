package worms.model.programs.statements;

import worms.model.Program;
import worms.model.programs.TwoArgumentExecutable;
import worms.model.programs.WormsRuntimeException;
import worms.model.programs.expressions.Expression;
import worms.model.programs.types.BooleanType;

public class While
		extends TwoArgumentExecutable<Expression<BooleanType>, Statement>
		implements Statement {

	public While(Expression<BooleanType> condition, Statement body) throws IllegalArgumentException {
		super(condition, body);
	}

	@Override
	public void execute(Program program) throws WormsRuntimeException {
		if (program == null)
			throw new WormsRuntimeException();
		
		if(getFirstArgument().calculate(program).getValue() == true) {
			program.scheduleStatement(getSecondArgument());
			program.scheduleStatement(this);
		}
	}
}
