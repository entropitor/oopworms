package worms.model.programs.statements;

import worms.model.Program;
import worms.model.programs.WormsRuntimeException;

public interface ActionStatement extends Statement {
	public abstract int getCost(Program program) throws WormsRuntimeException;
}
