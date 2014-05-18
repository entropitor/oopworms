package worms.model.programs.expressions;

import worms.model.Program;
import worms.model.programs.TwoArgumentExecutable;
import worms.model.programs.types.DoubleType;

public class AddExpression extends TwoArgumentExecutable<DoubleExpression, DoubleExpression> implements DoubleExpression {

	public AddExpression(DoubleExpression left, DoubleExpression right)
			throws IllegalArgumentException {
		super(left, right);
	}

	@Override
	public DoubleType calculate(Program program) {
		double left = this.getFirstArgument().calculate(program).getDoubleValue();
		double right = this.getSecondArgument().calculate(program).getDoubleValue();
		
		return new DoubleType(left + right);
	}

}
