package worms.model.programs;

import worms.model.programs.expressions.DoubleExpression;

public abstract class TwoDoubleArgumentExecutable extends TwoArgumentExecutable{

	public TwoDoubleArgumentExecutable(DoubleExpression left, DoubleExpression right) throws IllegalArgumentException{
		//FIXME tests
		setFirstArgument(left);
		setSecondArgument(right);
	}
	
	@Override
	public DoubleExpression getFirstArgument(){
		return (DoubleExpression)super.getFirstArgument();
	}
	@Override
	public DoubleExpression getSecondArgument(){
		return (DoubleExpression)super.getSecondArgument();
	}
	
	@Override
	protected void setFirstArgument(Executable argument){
		//FIXME tests
		if(!(argument instanceof DoubleExpression))
			throw new IllegalArgumentException();
		super.setFirstArgument(argument);
	}
	
	@Override
	protected void setSecondArgument(Executable argument){
		//FIXME tests
		if(!(argument instanceof DoubleExpression))
			throw new IllegalArgumentException();
		super.setSecondArgument(argument);
	}
}
