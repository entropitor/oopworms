package worms.model.programs.expressions;

import worms.model.Program;
import worms.model.programs.types.*;

public class VariableAccessExpression implements Expression<Type<?>> {
	
	public VariableAccessExpression(String variableName){
		this.variableName = variableName;
	}
	
	private final String variableName;

	@Override
	public Type<?> calculate(Program program) {
		//Returns DoubleType.ZERO in case no value can be found.
		if(program == null)
			return DoubleType.ZERO;
		
		Type<?> value = program.getVariableValue(variableName);
		if(value == null)
			return DoubleType.ZERO;
		else
			return value;
	}

}
