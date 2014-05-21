package worms.model.programs.statements;

import worms.model.Program;
import worms.model.Worm;

//FIXME test
public class Skip
		implements ActionStatement {

	@Override
	public void execute(Program program) { }

	@Override
	public int getCost(Worm worm) {
		return 0;
	}
}
