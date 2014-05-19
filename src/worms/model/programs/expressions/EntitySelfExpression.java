package worms.model.programs.expressions;

import worms.model.Program;
import worms.model.programs.types.EntityType;

public class EntitySelfExpression implements Expression<EntityType>{

	@Override
	public EntityType calculate(Program program) {
		if(program == null)
			return EntityType.NULL_REFERENCE;
		return new EntityType(program.getWorm());
	}

}
