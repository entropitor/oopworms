package worms.model.programs.types;

import be.kuleuven.cs.som.annotate.Value;

@Value
public class DoubleType extends Type<Double> {
	
	public static final DoubleType ZERO = new DoubleType(0);

	public DoubleType(Double value) {
		super(value);
	}
	
	public DoubleType(Integer value) {
		super(new Double(value));
	}
	
	@Override
	public DoubleType getNewTypeOfSameClass(Double value) {
		return new DoubleType(value);
	}

	public DoubleType getNewTypeOfSameClass(Integer value) {
		return new DoubleType(value);
	}

	@Override
	public DoubleType getDefaultTypeForThisClass() {
		return DoubleType.ZERO;
	}
}
