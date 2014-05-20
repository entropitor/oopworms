package worms.model.programs.expressions;

import worms.model.Program;
import worms.model.programs.TwoArgumentExecutable;
import worms.model.programs.types.BooleanType;
import worms.model.programs.types.Type;

public class Inequality
	extends TwoArgumentExecutable<Expression<? extends Type<?>>, Expression<? extends Type<?>>> 
	implements Expression<BooleanType> {

	public Inequality(Expression<? extends Type<?>> left, Expression<? extends Type<?>> right)
			throws IllegalArgumentException {
		super(left, right);
	}

	@Override
	public BooleanType calculate(Program program) {
		Object left = this.getFirstArgument().calculate(program).getValue();
		Object right = this.getSecondArgument().calculate(program).getValue();
		
		if(left == null)
			return new BooleanType(right != null);
		
		return new BooleanType(!left.equals(right));
	}

}
