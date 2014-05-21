package worms.model.programs.statements;

import static java.lang.Math.floor;
import worms.model.Program;
import worms.model.Worm;
import worms.model.programs.OneArgumentExecutable;
import worms.model.programs.WormsRuntimeException;
import worms.model.programs.expressions.Expression;
import worms.model.programs.types.DoubleType;

//FIXME tests
public class Fire
		extends OneArgumentExecutable<Expression<DoubleType>>
		implements ActionStatement {

	public Fire(Expression<DoubleType> yield) throws IllegalArgumentException {
		super(yield);
	}

	@Override
	public void execute(Program program) throws WormsRuntimeException {
		if (program == null || program.getWorm() == null)
			throw new WormsRuntimeException();
		
		Worm w = program.getWorm();
		int yield = (int) floor(getFirstArgument().calculate(program).getValue());
		
		if (yield < 0 || yield > 100)
			throw new WormsRuntimeException();
		
		program.getActionHandler().fire(w, yield);
	}

	@Override
	public int getCost(Program program) throws WormsRuntimeException {
		if (program == null || program.getWorm() == null)
			throw new WormsRuntimeException();
		
		return program.getWorm().getSelectedWeapon().getCost();
	}
}
