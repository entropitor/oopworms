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
import worms.util.ModuloUtil;
import worms.util.Util;

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
		//Convert direction to range [-Pi, Pi]
		direction = ModuloUtil.posMod(direction, Math.PI*2);
		if(direction > Math.PI)
			direction -= 2*Math.PI;
		
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
			if(discriminant < 0)
				continue;
			
			//First check if center is on the right side => sure circle is on the right side
			//Else check if circle itself is on the right side
			if(!Util.fuzzyBetween(direction-Math.PI/2, direction+Math.PI/2, Math.atan2(y1-y0,x1-x0))){
				double xIntersection1 = (-b + Math.sqrt(discriminant))/(2*a);
				double xIntersection2 = (-b - Math.sqrt(discriminant))/(2*a);
				
				double yIntersection1 = gamma + tan * xIntersection1;
				double yIntersection2 = gamma + tan * xIntersection2;
				
				if(!Util.fuzzyBetween(direction-Math.PI/2, direction+Math.PI/2,Math.atan2(yIntersection1-y0,xIntersection1-x0))
						&& !Util.fuzzyBetween(direction-Math.PI/2, direction+Math.PI/2, Math.atan2(yIntersection2-y0,xIntersection2-x0)))
					continue;
			}
			
			double squaredDistance = wormPosition.squaredDistance(e.getPosition());
			if(closestEntity == null || squaredDistance < closestEntitySquaredDistance){
				closestEntity = e;
				closestEntitySquaredDistance = squaredDistance;
			}
		}
		
		return new EntityType(closestEntity);
	}

}
