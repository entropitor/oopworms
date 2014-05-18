package worms.model.programs;

import be.kuleuven.cs.som.annotate.Value;

@Value
public class BooleanType extends Type<BooleanType.Value> {
	
	public enum Value {TRUE, FALSE};
	
	public BooleanType(Value value) {
		super(value);
	}
	
	@Override
	public BooleanType getNewTypeOfSameClass(Value value) {
		return new BooleanType(value);
	}
	
	@Override
	public int hashCode() {
		if (getValue() == Value.FALSE)
			return 0;
		else
			return 1;
	}
}
