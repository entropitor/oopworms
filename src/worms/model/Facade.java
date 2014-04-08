package worms.model;

import java.util.Collection;
import java.util.Random;

public class Facade implements IFacade {

	/*@Override
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
	}*/

	@Override
	public boolean canTurn(Worm worm, double angle) throws ModelException {
// 		Check on precondition is commented out: see Worm.getTurningCost() for the reason why.
//		if (worm.isValidTurningAngle(angle))
			return worm.canTurn(angle);
//		else
//			throw new ModelException("The given angle is not a valid angle to turn a Worm over.");
	}

	@Override
	public void turn(Worm worm, double angle) throws ModelException {
		if (worm.canTurn(angle))
			worm.turn(angle);
		else
			throw new ModelException("The worm can not turn over the given angle.");
	}

	/*@Override
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
	}*/

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
	public void setRadius(Worm worm, double newRadius) throws ModelException {
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

	@Override
	public void addEmptyTeam(World world, String newName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addNewFood(World world) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addNewWorm(World world) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canFall(Worm worm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canMove(Worm worm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Food createFood(World world, double x, double y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public World createWorld(double width, double height,
			boolean[][] passableMap, Random random) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Worm createWorm(World world, double x, double y, double direction,
			double radius, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fall(Worm worm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Projectile getActiveProjectile(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Worm getCurrentWorm(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Food> getFood(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHitPoints(Worm worm) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double[] getJumpStep(Projectile projectile, double t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getJumpTime(Projectile projectile, double timeStep) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getJumpTime(Worm worm, double timeStep) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxHitPoints(Worm worm) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getRadius(Food food) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getRadius(Projectile projectile) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getSelectedWeapon(Worm worm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTeamName(Worm worm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWinner(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Worm> getWorms(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getX(Food food) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getX(Projectile projectile) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getY(Food food) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getY(Projectile projectile) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isActive(Food food) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isActive(Projectile projectile) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAdjacent(World world, double x, double y, double radius) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAlive(Worm worm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGameFinished(World world) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isImpassable(World world, double x, double y, double radius) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void jump(Projectile projectile, double timeStep) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void jump(Worm worm, double timeStep) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(Worm worm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectNextWeapon(Worm worm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shoot(Worm worm, int yield) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startGame(World world) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startNextTurn(World world) {
		// TODO Auto-generated method stub
		
	}

}
