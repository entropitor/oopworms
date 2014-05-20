package worms.model.programs.types;

import be.kuleuven.cs.som.annotate.Value;
import worms.model.Entity;

@Value
public class EntityType extends Type<Entity> {
	
	public static final EntityType NULL_REFERENCE = new EntityType(null);
	
	public EntityType(Entity value) {
		super(value);
	}
	
	@Override
	public EntityType getNewTypeOfSameClass(Entity value) {
		return new EntityType(value);
	}
}
