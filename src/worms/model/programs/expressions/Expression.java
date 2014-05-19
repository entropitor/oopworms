package worms.model.programs.expressions;

import worms.model.Program;
import worms.model.programs.Executable;
import worms.model.programs.types.Type;

public interface Expression<T extends Type<?>> extends Executable{
	
	public T calculate(Program program);
}
