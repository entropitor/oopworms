package worms.model.programs.expressions;

import worms.model.Program;
import worms.model.programs.types.EntityType;

public class EntityNullLiteral 
	implements Expression<EntityType> {

	@Override
	public EntityType calculate(Program program) {
		return EntityType.NULL_REFERENCE;
	}

}
