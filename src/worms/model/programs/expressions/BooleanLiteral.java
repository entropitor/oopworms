package worms.model.programs.expressions;

import worms.model.Program;
import worms.model.programs.types.BooleanType;

public class BooleanLiteral 
	implements Expression<BooleanType> {
	
	public static final BooleanLiteral TRUE_EXPRESSION = new BooleanLiteral(true);
	public static final BooleanLiteral FALSE_EXPRESSION = new BooleanLiteral(false);
	
	public BooleanLiteral(boolean value){
		this.value = new BooleanType(value);
	}
	
	private final BooleanType value;

	@Override
	public BooleanType calculate(Program program) {
		return value;
	}

}
