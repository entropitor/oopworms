package worms.model.programs.statements;

import worms.model.Program;
import worms.model.Worm;
import worms.model.programs.WormsRuntimeException;

//FIXME test
public class Move
		implements ActionStatement {

	public Move() throws IllegalArgumentException {
		super();
	}

	@Override
	public void execute(Program program) throws WormsRuntimeException {
		if (program == null || program.getWorm() == null)
			throw new WormsRuntimeException();
		Worm w = program.getWorm();
		if (!w.canMove())
			throw new WormsRuntimeException();
		program.getActionHandler().move(w);
	}

	@Override
	public int getCost(Program program) throws WormsRuntimeException {
		if (program == null || program.getWorm() == null)
			throw new WormsRuntimeException();
		
		Worm w = program.getWorm();
		return w.getCostForMove(w.getPositionAfterMove());
	}
}
