package worms.model;

public class Facade implements IFacade {

	@Override
	public Worm createWorm(double x, double y, double direction, double radius,
			String name) {
		//TODO catch IllegalArgumentExceptions and throw ModelExceptions.
		//TODO check assertions or throw ModelExceptions.
		return new Worm(x,y,direction,radius,name);
	}

	@Override
	public boolean canMove(Worm worm, int nbSteps) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void move(Worm worm, int nbSteps) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canTurn(Worm worm, double angle) throws ModelException {
//		if (worm.isValidTurningAngle(angle))
			return worm.canTurn(angle);
//		else
//			throw new ModelException("The given angle is not a valid angle to turn a Worm over.");
			// Check on precondition commented out: see canTurn() for the reason why.
	}

	@Override
	public void turn(Worm worm, double angle) {
		if (worm.canTurn(angle))
			worm.turn(angle);
		else
			throw new ModelException("The worm can not turn over the given angle.");
	}

	@Override
	public void jump(Worm worm) {
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
	public double[] getJumpStep(Worm worm, double t) {
		return worm.getJumpStep(t);
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
		// TODO Auto-generated method stub
		return worm.getDirection();
	}

	@Override
	public double getRadius(Worm worm) {
		return worm.getRadius();
	}

	@Override
	public void setRadius(Worm worm, double newRadius) {
		try{
			worm.setRadius(newRadius);
		}catch(IllegalArgumentException e){
			throw new ModelException(e);
		}
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void rename(Worm worm, String newName) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getMass(Worm worm) {
		return worm.getMass();
	}

}
