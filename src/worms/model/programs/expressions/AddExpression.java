package worms.model.programs.expressions;

import worms.model.Program;
import worms.model.programs.TwoDoubleArgumentExecutable;
import worms.model.programs.types.DoubleType;

public class AddExpression extends TwoDoubleArgumentExecutable implements DoubleExpression {

	public AddExpression(DoubleExpression left, DoubleExpression right)
			throws IllegalArgumentException {
		super(left, right);
	}

	@Override
	public DoubleType calculate(Program program) {
		//FIXME tests!
		double left = this.getFirstArgument().calculate(program).getValue().doubleValue();
		double right = this.getSecondArgument().calculate(program).getValue().doubleValue();
		
		return new DoubleType(left + right);
	}

}
