package worms.model.programs.expressions;

import worms.model.Program;
import worms.model.programs.TwoArgumentExecutable;
import worms.model.programs.WormsRuntimeException;
import worms.model.programs.types.DoubleType;

public class Division 
	extends TwoArgumentExecutable<Expression<DoubleType>, Expression<DoubleType>> 
	implements Expression<DoubleType> {

	public Division(Expression<DoubleType> left, Expression<DoubleType> right)
			throws IllegalArgumentException {
		super(left, right);
	}

	@Override
	public DoubleType calculate(Program program) throws WormsRuntimeException{
		double numerator = this.getFirstArgument().calculate(program).getValue();
		double denominator = this.getSecondArgument().calculate(program).getValue();
		
		if(denominator == 0)
			throw new WormsRuntimeException();
		
		return new DoubleType(numerator / denominator);
	}

}
