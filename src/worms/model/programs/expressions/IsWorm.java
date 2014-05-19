package worms.model.programs.expressions;

import worms.model.Entity;
import worms.model.Program;
import worms.model.Worm;
import worms.model.programs.OneArgumentExecutable;
import worms.model.programs.WormsRuntimeException;
import worms.model.programs.types.BooleanType;
import worms.model.programs.types.EntityType;

public class IsWorm 
	extends OneArgumentExecutable<Expression<EntityType>> 
	implements Expression<BooleanType> {

	public IsWorm(Expression<EntityType> argument)
			throws IllegalArgumentException {
		super(argument);
	}

	@Override
	public BooleanType calculate(Program program) throws WormsRuntimeException{
		Entity e = this.getFirstArgument().calculate(program).getValue();
		return new BooleanType(e instanceof Worm);
	}

}
