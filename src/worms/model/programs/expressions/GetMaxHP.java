package worms.model.programs.expressions;

import worms.model.Entity;
import worms.model.Program;
import worms.model.Worm;
import worms.model.programs.OneArgumentExecutable;
import worms.model.programs.WormsRuntimeException;
import worms.model.programs.types.DoubleType;
import worms.model.programs.types.EntityType;

public class GetMaxHP 
	extends OneArgumentExecutable<Expression<EntityType>> 
	implements Expression<DoubleType> {

	public GetMaxHP(Expression<EntityType> argument)
			throws IllegalArgumentException {
		super(argument);
	}

	@Override
	public DoubleType calculate(Program program) throws WormsRuntimeException{
		Entity e = this.getFirstArgument().calculate(program).getValue();
		if(e == null)
			throw new WormsRuntimeException();
		if(!(e instanceof Worm))
			throw new WormsRuntimeException();
		
		Worm w = (Worm) e;
		return new DoubleType(w.getMaxHitPoints());
	}

}
