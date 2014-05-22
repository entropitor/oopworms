package worms.model.programs.statements;

import java.util.List;

import worms.model.Program;
import worms.model.programs.VarArgumentExecutable;
import worms.model.programs.WormsRuntimeException;

public class Sequence
		extends VarArgumentExecutable<Statement>
		implements Statement {

	public Sequence(List<Statement> statements) throws IllegalArgumentException {
		super(statements);
	}

	@Override
	public void execute(Program program) throws WormsRuntimeException {
		if (program == null)
			throw new WormsRuntimeException();
		
		for (int i = getNbArguments()-1; i >= 0; i--) {
			program.scheduleStatement(getArgumentAt(i));
		}
	}
}
