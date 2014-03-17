package worms.model;

public class Facade implements IFacade {

	@Override
	public Worm createWorm(double x, double y, double direction, double radius,
			String name) throws ModelException{
		if(!Worm.isValidDirection(direction))
			throw new ModelException("Not a valid direction");
		try{
			return new Worm(x,y,direction,radius,name);
		}catch(IllegalArgumentException e){
			throw new ModelException(e);
		}
	}

	@Override
	public boolean canMove(Worm worm, int nbSteps) {
		return worm.canMove(nbSteps);
	}

	@Override
	public void move(Worm worm, int nbSteps) throws ModelException{
		try{
			worm.move(nbSteps);
		}catch(IllegalStateException | IllegalArgumentException e){
			throw new ModelException(e);
		}
	}

	@Override
	public boolean canTurn(Worm worm, double angle) {
		return worm.canTurn(angle);
	}

	@Override
	public void turn(Worm worm, double angle) {
		worm.turn(angle);
	}

	@Override
	public void jump(Worm worm) throws ModelException{
		try{
			worm.jump();
		}catch(IllegalStateException e){
			throw new ModelException(e);
		}
	}

	@Override
	public double getJumpTime(Worm worm) {
		return worm.getJumpTime();
	}

	@Override
	public double[] getJumpStep(Worm worm, double t) throws ModelException{
		try{
			return worm.getJumpStep(t);
		}catch(IllegalArgumentException e){
			throw new ModelException(e);
		}
	}

	@Override
	public double getX(Worm worm) {
		return worm.getXCoordinate();
	}

	@Override
	public double getY(Worm worm) {
		return worm.getYCoordinate();
	}

	@Override
	public double getOrientation(Worm worm) {
		return worm.getDirection();
	}

	@Override
	public double getRadius(Worm worm) {
		return worm.getRadius();
	}

	@Override
	public void setRadius(Worm worm, double newRadius) {
		worm.setRadius(newRadius);
	}

	@Override
	public double getMinimalRadius(Worm worm) {
		return worm.getRadiusLowerBound();
	}

	@Override
	public int getActionPoints(Worm worm) {
		return worm.getActionPoints();
	}

	@Override
	public int getMaxActionPoints(Worm worm) {
		return worm.getMaxActionPoints();
	}

	@Override
	public String getName(Worm worm) {
		return worm.getName();
	}

	@Override
	public void rename(Worm worm, String newName) throws ModelException{
		try{
			worm.setName(newName);
		}catch(IllegalArgumentException e){
			throw new ModelException(e);
		}
	}

	@Override
	public double getMass(Worm worm) {
		return worm.getMass();
	}

}
