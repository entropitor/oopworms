package worms.model.programs.expressions;

import worms.model.Program;
import worms.model.programs.TwoArgumentExecutable;
import worms.model.programs.types.BooleanType;
import worms.model.programs.types.DoubleType;

public class LessThan 
	extends TwoArgumentExecutable<Expression<DoubleType>, Expression<DoubleType>> 
	implements Expression<BooleanType> {

	public LessThan(Expression<DoubleType> left, Expression<DoubleType> right)
			throws IllegalArgumentException {
		super(left, right);
	}

	@Override
	public BooleanType calculate(Program program) {
		double left = this.getFirstArgument().calculate(program).getValue();
		double right = this.getSecondArgument().calculate(program).getValue();
		
		return new BooleanType(left < right);
	}

}
