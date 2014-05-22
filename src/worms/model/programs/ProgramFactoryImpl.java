package worms.model.programs;

import java.util.List;

import worms.model.programs.expressions.*;
import worms.model.programs.statements.*;
import worms.model.programs.types.*;

@SuppressWarnings("unchecked")
public class ProgramFactoryImpl implements worms.model.programs.ProgramFactory<Expression<?>, Statement, Type<?>> {

	@Override
	public Expression<?> createDoubleLiteral(int line, int column, double d) {
		return new DoubleLiteral(d);
	}

	@Override
	public Expression<?> createBooleanLiteral(int line, int column, boolean b) {
		return new BooleanLiteral(b);
	}

	@Override
	public Expression<?> createAnd(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		return new Conjunction((Expression<BooleanType>) e1, (Expression<BooleanType>) e2);
	}

	@Override
	public Expression<?> createOr(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		return new Disjunction((Expression<BooleanType>) e1, (Expression<BooleanType>) e2);
	}

	@Override
	public Expression<?> createNot(int line, int column, Expression<?> e) {
		return new BooleanNegation((Expression<BooleanType>) e);
	}

	@Override
	public Expression<?> createNull(int line, int column) {
		return new EntityNullLiteral();
	}

	@Override
	public Expression<?> createSelf(int line, int column) {
		return new EntitySelfLiteral();
	}

	@Override
	public Expression<?> createGetX(int line, int column, Expression<?> e) {
		return new GetX((Expression<EntityType>) e);
	}

	@Override
	public Expression<?> createGetY(int line, int column, Expression<?> e) {
		return new GetY((Expression<EntityType>) e);
	}

	@Override
	public Expression<?> createGetRadius(int line, int column, Expression<?> e) {
		return new GetRadius((Expression<EntityType>) e);
	}

	@Override
	public Expression<?> createGetDir(int line, int column, Expression<?> e) {
		return new GetDirection((Expression<EntityType>) e);
	}

	@Override
	public Expression<?> createGetAP(int line, int column, Expression<?> e) {
		return new GetAP((Expression<EntityType>) e);
	}

	@Override
	public Expression<?> createGetMaxAP(int line, int column, Expression<?> e) {
		return new GetMaxAP((Expression<EntityType>) e);
	}

	@Override
	public Expression<?> createGetHP(int line, int column, Expression<?> e) {
		return new GetHP((Expression<EntityType>) e);
	}

	@Override
	public Expression<?> createGetMaxHP(int line, int column, Expression<?> e) {
		return new GetMaxHP((Expression<EntityType>) e);
	}

	@Override
	public Expression<?> createSameTeam(int line, int column, Expression<?> e) {
		return new SameTeam((Expression<EntityType>) e);
	}

	@Override
	public Expression<?> createSearchObj(int line, int column, Expression<?> e) {
		return new SearchEntity((Expression<DoubleType>) e);
	}

	@Override
	public Expression<?> createIsWorm(int line, int column, Expression<?> e) {
		return new IsWorm((Expression<EntityType>) e);
	}

	@Override
	public Expression<?> createIsFood(int line, int column, Expression<?> e) {
		return new IsFood((Expression<EntityType>) e);
	}

	@Override
	public Expression<?> createVariableAccess(int line, int column, String name) {
		return null;
	}

	@Override
	public Expression<?> createVariableAccess(int line, int column, String name,
			Type<?> type) {
		//This works because of the raw types underlying generic classes.
		return new VariableAccess<Type<?>>(name);
	}

	@Override
	public Expression<?> createLessThan(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		return new LessThan((Expression<DoubleType>) e1, (Expression<DoubleType>) e2);
	}

	@Override
	public Expression<?> createGreaterThan(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		return new GreaterThan((Expression<DoubleType>) e1, (Expression<DoubleType>) e2);
	}

	@Override
	public Expression<?> createLessThanOrEqualTo(int line, int column,
			Expression<?> e1, Expression<?> e2) {
		return new LessThanOrEqualTo((Expression<DoubleType>) e1, (Expression<DoubleType>) e2);
	}

	@Override
	public Expression<?> createGreaterThanOrEqualTo(int line, int column,
			Expression<?> e1, Expression<?> e2) {
		return new GreaterThanOrEqualTo((Expression<DoubleType>) e1, (Expression<DoubleType>) e2);
	}

	@Override
	public Expression<?> createEquality(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		return new Equality(e1, e2);
	}

	@Override
	public Expression<?> createInequality(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		return new Inequality(e1, e2);
	}

	@Override
	public Expression<?> createAdd(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		return new Addition((Expression<DoubleType>) e1, (Expression<DoubleType>) e2);
	}

	@Override
	public Expression<?> createSubtraction(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		return new Subtraction((Expression<DoubleType>) e1, (Expression<DoubleType>) e2);
	}

	@Override
	public Expression<?> createMul(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		return new Multiplication((Expression<DoubleType>) e1, (Expression<DoubleType>) e2);
	}

	@Override
	public Expression<?> createDivision(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		return new Division((Expression<DoubleType>) e1, (Expression<DoubleType>) e2);
	}

	@Override
	public Expression<?> createSqrt(int line, int column, Expression<?> e) {
		return new Sqrt((Expression<DoubleType>) e);
	}

	@Override
	public Expression<?> createSin(int line, int column, Expression<?> e) {
		return new Sine((Expression<DoubleType>) e);
	}

	@Override
	public Expression<?> createCos(int line, int column, Expression<?> e) {
		return new Cosine((Expression<DoubleType>) e);
	}

	@Override
	public Statement createTurn(int line, int column, Expression<?> angle) {
		return new Turn((Expression<DoubleType>) angle);
	}

	@Override
	public Statement createMove(int line, int column) {
		return new Move();
	}

	@Override
	public Statement createJump(int line, int column) {
		return new Jump();
	}

	@Override
	public Statement createToggleWeap(int line, int column) {
		return new ToggleWeapon();
	}

	@Override
	public Statement createFire(int line, int column, Expression<?> yield) {
		return new Fire((Expression<DoubleType>) yield);
	}

	@Override
	public Statement createSkip(int line, int column) {
		return new Skip();
	}

	@Override
	public Statement createAssignment(int line, int column,
			String variableName, Expression<?> rhs) {
		return new Assignment(variableName, rhs);
	}

	@Override
	public Statement createIf(int line, int column, Expression<?> condition,
			Statement then, Statement otherwise) {
		return new If((Expression<BooleanType>) condition, then, otherwise);
	}

	@Override
	public Statement createWhile(int line, int column, Expression<?> condition,
			Statement body) {
		return new While((Expression<BooleanType>) condition, body);
	}

	@Override
	public Statement createForeach(int line, int column,
			worms.model.programs.ProgramFactory.ForeachType type,
			String variableName, Statement body) {
		return new Foreach(type, variableName, body);
	}

	@Override
	public Statement createSequence(int line, int column,
			List<Statement> statements) {
		return new Sequence(statements);
	}

	@Override
	public Statement createPrint(int line, int column, Expression<?> e) {
		return new Print(e);
	}

	@Override
	public Type<?> createDoubleType() {
		return DoubleType.ZERO;
	}

	@Override
	public Type<?> createBooleanType() {
		return BooleanType.FALSE;
	}

	@Override
	public Type<?> createEntityType() {
		return EntityType.NULL_REFERENCE;
	}

}
