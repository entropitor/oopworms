package worms.model.programs.expressions;

import worms.model.Program;
import worms.model.programs.types.BooleanType;

public class BooleanLiteralExpression implements Expression<BooleanType> {
	
	public static final BooleanLiteralExpression TRUE_EXPRESSION = new BooleanLiteralExpression(true);
	public static final BooleanLiteralExpression FALSE_EXPRESSION = new BooleanLiteralExpression(false);
	
	public BooleanLiteralExpression(boolean value){
		this.value = new BooleanType(value);
	}
	
	private final BooleanType value;

	@Override
	public BooleanType calculate(Program program) {
		return value;
	}

}
