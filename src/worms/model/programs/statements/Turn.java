package worms.model.programs.statements;

import worms.model.Program;
import worms.model.Worm;
import worms.model.programs.OneArgumentExecutable;
import worms.model.programs.WormsRuntimeException;
import worms.model.programs.expressions.Expression;
import worms.model.programs.types.DoubleType;

// FIXME tests
public class Turn
		extends OneArgumentExecutable<Expression<DoubleType>>
		implements ActionStatement {

	public Turn(Expression<DoubleType> angle) throws IllegalArgumentException {
		super(angle);
	}

	@Override
	public void execute(Program program) throws WormsRuntimeException {
		if (program == null || program.getWorm() == null)
			throw new WormsRuntimeException();
		Worm w = program.getWorm();
		Double angle = getFirstArgument().calculate(program).getValue();
		if (!Worm.isValidTurningAngle(angle) || !w.canTurn(angle))
			throw new WormsRuntimeException();
		program.getActionHandler().turn(w, angle);
	}

	@Override
	public int getCost(Program program) throws WormsRuntimeException {
		if (program == null || program.getWorm() == null)
			throw new WormsRuntimeException();
		
		Double angle = getFirstArgument().calculate(program).getValue();
		return Worm.getTurningCost(angle);
	}
}
