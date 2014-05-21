package worms.model.programs.statements;

import worms.model.Worm;

public interface ActionStatement {
	public abstract int getCost(Worm worm);
}
