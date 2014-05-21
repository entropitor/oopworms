package worms.model.programs.statements;

import worms.model.Worm;
import worms.model.programs.WormsRuntimeException;

public interface ActionStatement extends Statement {
	public abstract int getCost(Worm worm) throws WormsRuntimeException;
}
