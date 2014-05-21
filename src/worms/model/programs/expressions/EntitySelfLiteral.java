package worms.model.programs.expressions;

import worms.model.Program;
import worms.model.programs.WormsRuntimeException;
import worms.model.programs.types.EntityType;

public class EntitySelfLiteral 
	implements Expression<EntityType>{

	@Override
	public EntityType calculate(Program program) throws WormsRuntimeException{
		if(program == null || program.getWorm() == null)
			throw new WormsRuntimeException();
		return new EntityType(program.getWorm());
	}

}
