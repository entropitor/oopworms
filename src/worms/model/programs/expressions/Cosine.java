package worms.model.programs.expressions;

import worms.model.Program;
import worms.model.programs.OneArgumentExecutable;
import worms.model.programs.types.DoubleType;

public class Cosine 
	extends OneArgumentExecutable<Expression<DoubleType>> 
	implements Expression<DoubleType> {

	public Cosine(Expression<DoubleType> argument)
			throws IllegalArgumentException {
		super(argument);
	}

	@Override
	public DoubleType calculate(Program program) {
		double left = this.getFirstArgument().calculate(program).getValue();
		
		return new DoubleType(Math.cos(left));
	}

}
