package worms.model.programs.expressions;

import worms.model.Program;
import worms.model.programs.OneArgumentExecutable;
import worms.model.programs.types.BooleanType;

public class BooleanNegation 
	extends OneArgumentExecutable<Expression<BooleanType>> 
	implements Expression<BooleanType> {

	public BooleanNegation(Expression<BooleanType> argument)
			throws IllegalArgumentException {
		super(argument);
	}

	@Override
	public BooleanType calculate(Program program) {
		boolean value = this.getFirstArgument().calculate(program).getValue();
		return new BooleanType(!value);
	}

}
