package worms.model.programs.expressions;

import worms.model.Entity;
import worms.model.Program;
import worms.model.Team;
import worms.model.Worm;
import worms.model.programs.OneArgumentExecutable;
import worms.model.programs.WormsRuntimeException;
import worms.model.programs.types.BooleanType;
import worms.model.programs.types.EntityType;

public class SameTeam 
	extends OneArgumentExecutable<Expression<EntityType>> 
	implements Expression<BooleanType> {

	public SameTeam(Expression<EntityType> argument)
			throws IllegalArgumentException {
		super(argument);
	}

	@Override
	public BooleanType calculate(Program program) throws WormsRuntimeException{
		if(program == null || program.getWorm() == null)
			throw new WormsRuntimeException();
		
		Entity other = this.getFirstArgument().calculate(program).getValue();
		if(!(other instanceof Worm))
			throw new WormsRuntimeException();
		
		Team ownTeam = program.getWorm().getTeam();
		Team otherTeam = ((Worm)other).getTeam();
		
		return new BooleanType(ownTeam == otherTeam);
	}

}
