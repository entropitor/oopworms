package worms.model.programs.expressions;

import worms.model.Program;
import worms.model.programs.OneArgumentExecutable;
import worms.model.programs.WormsRuntimeException;
import worms.model.programs.types.DoubleType;

public class Sqrt 
	extends OneArgumentExecutable<Expression<DoubleType>> 
	implements Expression<DoubleType> {

	public Sqrt(Expression<DoubleType> argument)
			throws IllegalArgumentException {
		super(argument);
	}

	@Override
	public DoubleType calculate(Program program) throws WormsRuntimeException{
		double left = this.getFirstArgument().calculate(program).getValue();
		
		if(left < 0)
			throw new WormsRuntimeException();
		
		return new DoubleType(Math.sqrt(left));
	}

}
