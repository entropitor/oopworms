package worms.model.programs.expressions;

import worms.model.Program;
import worms.model.programs.WormsRuntimeException;
import worms.model.programs.types.DoubleType;

public class DoubleLiteral 
	implements Expression<DoubleType> {
	
	public DoubleLiteral(double value){
		this.value = new DoubleType(value);
	}

	@Override
	public DoubleType calculate(Program program) throws WormsRuntimeException{
		if(Double.isNaN(value.getValue()))
			throw new WormsRuntimeException();
		return value;
	}
	
	private final DoubleType value;

}
