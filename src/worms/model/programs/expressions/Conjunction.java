package worms.model.programs.expressions;

import worms.model.Program;
import worms.model.programs.TwoArgumentExecutable;
import worms.model.programs.types.BooleanType;

public class Conjunction 
	extends TwoArgumentExecutable<Expression<BooleanType>, Expression<BooleanType>> 
	implements Expression<BooleanType> {

	public Conjunction(Expression<BooleanType> left, Expression<BooleanType> right)
			throws IllegalArgumentException {
		super(left, right);
	}

	@Override
	public BooleanType calculate(Program program) {
		//result == left && right
		boolean left = this.getFirstArgument().calculate(program).getValue();
		if(!left)
			return BooleanType.FALSE;
		
		boolean right = this.getSecondArgument().calculate(program).getValue();
		
		return new BooleanType(right);
	}

}
