package worms.model.programs;

import be.kuleuven.cs.som.annotate.Value;
import worms.model.Entity;

@Value
public class EntityType extends Type<Entity> {
	
	public EntityType(Entity value) {
		super(value);
	}
	
	@Override
	public EntityType getNewTypeOfSameClass(Entity value) {
		return new EntityType(value);
	}

	@Override
	public int hashCode() {
		Entity e = getValue();
		return e.getClass().hashCode() * (int) (e.getXCoordinate()*e.getYCoordinate()*e.getRadius());
	}
}
