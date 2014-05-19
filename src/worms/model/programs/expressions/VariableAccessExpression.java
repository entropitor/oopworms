package worms.model.programs.expressions;

import worms.model.Program;
import worms.model.programs.types.*;

public class VariableAccessExpression<T extends Type<?>> implements Expression<T> {
	
	public VariableAccessExpression(String variableName){
		this.variableName = variableName;
	}
	
	private final String variableName;

	@SuppressWarnings("unchecked")
	@Override
	public T calculate(Program program) {
		if(program == null)
			return null;

		//The type of a variable can't change at runtime 
		//+ the correct expression is constructed in ProgramFactory
		Type<?> value = program.getVariableValue(variableName);
		return (T) value;
	}

}
