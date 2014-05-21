package worms.model.programs.expressions;

import worms.model.Entity;
import worms.model.Program;
import worms.model.programs.WormsRuntimeException;
import worms.model.programs.types.EntityType;

public class EntityLiteral 
	implements Expression<EntityType> {
	
	public EntityLiteral(Entity value){
		this.value = new EntityType(value);
	}

	@Override
	public EntityType calculate(Program program) throws WormsRuntimeException{
		return value;
	}
	
	private final EntityType value;

}
