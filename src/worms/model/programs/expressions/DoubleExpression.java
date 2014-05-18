package worms.model.programs.expressions;

import worms.model.Program;
import worms.model.programs.types.DoubleType;

public interface DoubleExpression extends Expression {

	public DoubleType calculate(Program program);
}
