package worms.model.programs.expressions;

import worms.model.Program;
import worms.model.programs.TwoArgumentExecutable;
import worms.model.programs.types.DoubleType;

public class Subtraction 
	extends TwoArgumentExecutable<Expression<DoubleType>,Expression<DoubleType>> 
	implements Expression<DoubleType> {

	public Subtraction(Expression<DoubleType> left, Expression<DoubleType> right)
			throws IllegalArgumentException {
		super(left, right);
	}

	@Override
	public DoubleType calculate(Program program) {
		double left = this.getFirstArgument().calculate(program).getValue();
		double right = this.getSecondArgument().calculate(program).getValue();
		
		return new DoubleType(left - right);
	}

}
