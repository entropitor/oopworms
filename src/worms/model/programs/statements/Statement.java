package worms.model.programs.statements;

import worms.model.Program;
import worms.model.programs.Executable;
import worms.model.programs.WormsRuntimeException;

public interface Statement extends Executable{
	public void execute(Program program) throws WormsRuntimeException;
}
