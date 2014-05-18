package worms.model.programs.types;

import be.kuleuven.cs.som.annotate.Value;

@Value
public class BooleanType extends Type<Boolean> {
	
	public BooleanType(Boolean value) {
		super(value);
	}
	
	@Override
	public BooleanType getNewTypeOfSameClass(Boolean value) {
		return new BooleanType(value);
	}
}
