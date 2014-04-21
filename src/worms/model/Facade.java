package worms.model;

import java.util.Collection;
import java.util.Random;

public class Facade implements IFacade {

	/*@Override
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
		if (Worm.isValidTurningAngle(angle))
			return worm.canTurn(angle);
		else
			throw new ModelException("The given angle is not a valid angle to turn a Worm over.");
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
	}*/

	@Override
	public double[] getJumpStep(Worm worm, double t) throws ModelException{
		try{
			return worm.getJumpStep(t).getAsArray();
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
	public void addEmptyTeam(World world, String newName) throws ModelException {
		try {
			new Team(world, newName);
		} catch (IllegalArgumentException|NullPointerException e) {
			throw new ModelException(e);
		}
	}

	@Override
	public void addNewFood(World world) {
		try{
			world.addNewFood();
		}catch(IllegalStateException e){
			throw new ModelException(e);
		}
		
	}

	@Override
	public void addNewWorm(World world) {
		try{
			world.addNewWorm();
		}catch(IllegalStateException e){
			throw new ModelException(e);
		}
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
		try {
			Food f = new Food(world);
			f.setPosition(new Position(x, y));
			return f;
		} catch (IllegalArgumentException|IllegalStateException|NullPointerException e) {
			throw new ModelException(e);
		}
	}

	@Override
	public World createWorld(double width, double height,
			boolean[][] passableMap, Random random) {
		try {
			return new World(width, height, passableMap, random);
		} catch (IllegalArgumentException e) {
			throw new ModelException(e);
		}
	}

	@Override
	public Worm createWorm(World world, double x, double y, double direction,
			double radius, String name) {
		if(!Worm.isValidDirection(direction))
			throw new ModelException("Not a valid direction.");
		try {
			return new Worm(world, x, y, direction, radius, name);
		} catch (IllegalArgumentException|NullPointerException|IllegalStateException e) {
			throw new ModelException(e);
		}
	}

	@Override
	public void fall(Worm worm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Projectile getActiveProjectile(World world) {
		return world.getProjectile();
	}

	@Override
	public Worm getCurrentWorm(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Food> getFood(World world) {
		return world.getFoods();
	}

	@Override
	public int getHitPoints(Worm worm) {
		return worm.getHitPoints();
	}

	@Override
	public double[] getJumpStep(Projectile projectile, double t) {
		return projectile.getJumpStep(t).getAsArray();
	}

	@Override
	public double getJumpTime(Projectile projectile, double timeStep) {
		return projectile.getJumpTime(timeStep);
	}

	@Override
	public double getJumpTime(Worm worm, double timeStep) {
		return worm.getJumpTime(timeStep);
	}

	@Override
	public int getMaxHitPoints(Worm worm) {
		return worm.getMaxHitPoints();
	}

	@Override
	public double getRadius(Food food) {
		return food.getRadius();
	}

	@Override
	public double getRadius(Projectile projectile) {
		return projectile.getRadius();
	}

	@Override
	public String getSelectedWeapon(Worm worm) {
		return worm.getSelectedWeapon().getName();
	}

	@Override
	public String getTeamName(Worm worm) {
		if(worm.hasTeam())
			return worm.getTeam().getName();
		else
			return null;
	}

	@Override
	public String getWinner(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Worm> getWorms(World world) {
		return world.getWorms();
	}

	@Override
	public double getX(Food food) {
		return food.getPosition().getX();
	}

	@Override
	public double getX(Projectile projectile) {
		return projectile.getPosition().getX();
	}

	@Override
	public double getY(Food food) {
		return food.getPosition().getY();
	}

	@Override
	public double getY(Projectile projectile) {
		return projectile.getPosition().getY();
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
		return (world.getLocationType(new Position(x, y), radius) == LocationType.CONTACT);
	}

	@Override
	public boolean isAlive(Worm worm) {
		return worm.isTerminated();
	}

	@Override
	public boolean isGameFinished(World world) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isImpassable(World world, double x, double y, double radius) {
		return (world.getLocationType(new Position(x, y), radius) == LocationType.IMPASSABLE);
	}

	@Override
	public void jump(Projectile projectile, double timeStep) {
		try {
			projectile.jump(timeStep);
		} catch (IllegalStateException e) {
			throw new ModelException(e);
		}
	}

	@Override
	public void jump(Worm worm, double timeStep) {
		try {
			worm.jump(timeStep);
		} catch (IllegalStateException e) {
			throw new ModelException(e);
		}
	}

	@Override
	public void move(Worm worm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectNextWeapon(Worm worm) {
		worm.selectNextWeapon();
	}

	@Override
	public void shoot(Worm worm, int yield) {
		worm.fire(yield);
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
