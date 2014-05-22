package worms.model.programs.statements;

import worms.model.Entity;
import worms.model.Food;
import worms.model.Program;
import worms.model.World;
import worms.model.Worm;
import worms.model.programs.OneArgumentExecutable;
import worms.model.programs.ProgramFactory.ForeachType;
import worms.model.programs.WormsRuntimeException;
import worms.model.programs.expressions.EntityLiteral;

public class Foreach
		extends OneArgumentExecutable<Statement>
		implements Statement {

	public Foreach(ForeachType type, String variableName, Statement body) throws IllegalArgumentException {
		super(body);
		this.type = type;
		this.variableName = variableName;
	}

	@Override
	public void execute(Program program) throws WormsRuntimeException {
		if (program == null || program.getWorm() == null)
			throw new WormsRuntimeException();
		
		World world = program.getWorm().getWorld();
		for (Entity e : world.getEntities()) {
			if (((type == ForeachType.WORM || type == ForeachType.ANY) && e instanceof Worm)
				|| ((type == ForeachType.FOOD || type == ForeachType.ANY) && e instanceof Food)) {
				program.scheduleStatement(getFirstArgument());
				program.scheduleStatement(new Assignment(variableName, new EntityLiteral(e)));
			}
		}
	}
		
	private ForeachType type;
	private String variableName;
}