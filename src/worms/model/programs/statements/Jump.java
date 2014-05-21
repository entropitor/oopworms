package worms.model.programs.statements;

import worms.model.Program;
import worms.model.programs.WormsRuntimeException;

//FIXME test
public class Jump
		implements ActionStatement {

	public Jump() throws IllegalArgumentException {
		super();
	}

	@Override
	public void execute(Program program) throws WormsRuntimeException {
		if (program == null)
			throw new WormsRuntimeException();
		
		program.getActionHandler().jump(program.getWorm());
	}

	@Override
	public int getCost(Program program) throws WormsRuntimeException {
		if (program == null || program.getWorm() == null)
			throw new WormsRuntimeException();
		
		int AP = program.getWorm().getActionPoints();
		if (AP == 0)
			return 1; // A worm with no action points left should not be able to jump.
					  // (Trying to would cause an IllegalStateException.)
		else 
			return AP;
	}
}
