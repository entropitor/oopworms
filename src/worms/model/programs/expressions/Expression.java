package worms.model.programs.expressions;

import worms.model.Program;
import worms.model.programs.Executable;
import worms.model.programs.types.Type;

public interface Expression extends Executable{
	
	/**
	 * @pre 	| program != null && program.hasAsSubExecutable(this)
	 */
	public Type calculate(Program program);
}
