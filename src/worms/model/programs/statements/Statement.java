package worms.model.programs.statements;

import worms.model.Program;
import worms.model.programs.Executable;

public interface Statement extends Executable{
	public void execute(Program program);
}
