package worms.model.programs.expressions;

import worms.model.Program;
import worms.model.programs.WormsRuntimeException;
import worms.model.programs.types.*;

public class VariableAccess<T extends Type<?>> 
	implements Expression<T> {
	
	public VariableAccess(String variableName){
		this.variableName = variableName;
	}
	
	private final String variableName;

	@SuppressWarnings("unchecked")
	@Override
	public T calculate(Program program) throws WormsRuntimeException {
		if(program == null)
			throw new WormsRuntimeException();
		
		//If the variable requested doesn't exist in the program, 
		//a wormsRuntimeException will also be thrown

		//The type of a variable can't change at runtime 
		//+ the correct type is given to this expression at construction.
		Type<?> value = program.getVariableValue(variableName);
		return (T) value;
	}

}
