package worms.model.programs.expressions;

import worms.model.Entity;
import worms.model.MassiveEntity;
import worms.model.Program;
import worms.model.programs.OneArgumentExecutable;
import worms.model.programs.WormsRuntimeException;
import worms.model.programs.types.DoubleType;
import worms.model.programs.types.EntityType;

public class GetDirection 
	extends OneArgumentExecutable<Expression<EntityType>> 
	implements Expression<DoubleType> {

	public GetDirection(Expression<EntityType> argument)
			throws IllegalArgumentException {
		super(argument);
	}

	@Override
	public DoubleType calculate(Program program) throws WormsRuntimeException{
		Entity e = this.getFirstArgument().calculate(program).getValue();
		if(e == null)
			throw new WormsRuntimeException();
		if(!(e instanceof MassiveEntity))
			throw new WormsRuntimeException();
		
		MassiveEntity me = (MassiveEntity) e;
		return new DoubleType(me.getDirection());
	}

}
