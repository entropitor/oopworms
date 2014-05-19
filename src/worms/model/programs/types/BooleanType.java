package worms.model.programs.types;

import be.kuleuven.cs.som.annotate.Value;

@Value
public class BooleanType extends Type<Boolean> {
	
	public static final BooleanType FALSE = new BooleanType(false);
	public static final BooleanType TRUE = new BooleanType(true);
	
	public BooleanType(Boolean value) {
		super(value);
	}
	
	@Override
	public BooleanType getNewTypeOfSameClass(Boolean value) {
		return new BooleanType(value);
	}
}
