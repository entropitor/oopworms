package worms.model.programs.statements;

import worms.model.Program;

//FIXME test
public class Skip
		implements ActionStatement {

	@Override
	public void execute(Program program) { }

	@Override
	public int getCost(Program program) {
		return 0;
	}
}
