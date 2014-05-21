package worms.model.programs.statements;

import worms.model.Program;
import worms.model.programs.OneArgumentExecutable;
import worms.model.programs.WormsRuntimeException;
import worms.model.programs.expressions.Expression;
import worms.model.programs.types.Type;

//FIXME test
public class Assignment
		extends OneArgumentExecutable<Expression<Type<?>>>
		implements Statement {

	public Assignment(String variableName, Expression<Type<?>> rhs) throws IllegalArgumentException {
		super(rhs);
		this.variableName = variableName;
	}

	@Override
	public void execute(Program program) throws WormsRuntimeException {
		if (program == null)
			throw new WormsRuntimeException();
		
		program.setVariableValue(variableName, getFirstArgument().calculate(program));
	}
	
	private String variableName;
}
