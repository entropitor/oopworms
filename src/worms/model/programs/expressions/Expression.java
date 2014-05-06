package worms.model.programs.expressions;

import worms.model.Program;
import worms.model.programs.Executable;
import worms.model.programs.types.Type;

public interface Expression extends Executable{
	
	public Type calculate(Program program);
}
