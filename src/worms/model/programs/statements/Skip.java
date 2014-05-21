package worms.model.programs.statements;

import worms.model.Program;
import worms.model.Worm;
import worms.model.programs.TwoArgumentExecutable;
import worms.model.programs.WormsRuntimeException;
import worms.model.programs.expressions.Expression;
import worms.model.programs.types.BooleanType;

//FIXME test
public class Skip
		extends TwoArgumentExecutable<Expression<BooleanType>, Statement>
		implements ActionStatement {

	public Skip(Expression<BooleanType> condition, Statement body) throws IllegalArgumentException {
		super(condition, body);
	}

	@Override
	public void execute(Program program) { }

	@Override
	public int getCost(Worm worm) {
		return 0;
	}
}
