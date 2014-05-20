package worms.model.programs.expressions;

import worms.model.Entity;
import worms.model.Position;
import worms.model.Program;
import worms.model.World;
import worms.model.Worm;
import worms.model.programs.OneArgumentExecutable;
import worms.model.programs.WormsRuntimeException;
import worms.model.programs.types.DoubleType;
import worms.model.programs.types.EntityType;

public class SearchEntity 
	extends OneArgumentExecutable<Expression<DoubleType>> 
	implements Expression<EntityType> {

	public SearchEntity(Expression<DoubleType> argument)
			throws IllegalArgumentException {
		super(argument);
	}

	@Override
	public EntityType calculate(Program program) throws WormsRuntimeException{
		double directionOffset = this.getFirstArgument().calculate(program).getValue();
		if(program == null || program.getWorm() == null || program.getWorm().getWorld() == null)
			throw new WormsRuntimeException();
		
		Worm worm = program.getWorm();
		World world = worm.getWorld();
		Position wormPosition = worm.getPosition();
		double direction = directionOffset + worm.getDirection();
		
		double x0 = wormPosition.getX();
		double y0 = wormPosition.getY();
		double tan = Math.tan(direction);
		double gamma = y0 - tan*x0;
		
		Entity closestEntity = null;
		double closestEntitySquaredDistance = 0;
		for(Entity e : world.getEntities()){
			if(e == worm)
				continue;
			
			double x1 = e.getXCoordinate();
			double y1 = e.getYCoordinate();
			double R = e.getRadius();

			double a = (1+tan*tan);
			double b = (-2*x1 + 2*gamma*tan - 2*y1*tan);
			double c = (x1*x1 + y1*y1 - R*R - 2*y1*gamma + gamma*gamma);
			double discriminant = b*b - 4* a * c;
					
			if(discriminant >= 0){
				double squaredDistance = wormPosition.squaredDistance(e.getPosition());
				if(closestEntity == null || squaredDistance < closestEntitySquaredDistance){
					closestEntity = e;
					closestEntitySquaredDistance = squaredDistance;
				}
			}
		}
		
		return new EntityType(closestEntity);
	}

}
