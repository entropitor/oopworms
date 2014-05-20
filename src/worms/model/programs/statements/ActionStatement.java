package worms.model.programs.statements;

import worms.model.Program;

public abstract class ActionStatement implements Statement {
	public abstract int getCost();
	
	public boolean isPossible(Program program) {
		return (getCost() <= program.getWorm().getActionPoints());
	}
}
