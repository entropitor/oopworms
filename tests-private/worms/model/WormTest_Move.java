package worms.model;

import static java.lang.Math.PI;
import static org.junit.Assert.*;
import static worms.util.AssertUtil.assertFuzzyEquals;
import static worms.util.ModuloUtil.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class WormTest_Move {

	private World world;
	private Worm willy;
	//private Worm left,diagonal;

	private static boolean[][] strPassableMapToBool(String[] strMap){
		boolean[][] result = new boolean[strMap.length][strMap[0].length()/2];
		for (int r=0; r<strMap.length; r++){
			for (int c=0; c<strMap[r].length()/2; c++){
				result[r][c] = (!(strMap[r].charAt(2*c) == '#'));
			}
		}
		return result;
	}

	/*
	private static void checkAboveFunction(String[] strMap){
		for (boolean[] row : strPassableMapToBool(strMap))
		    System.out.println(Arrays.toString(row));
	}*/

	@Before
	public void setup(){
		String[] stairs =
		{". . . . . . . . . . . ",
		 ". . . . . . . . . . . ",
		 ". . . . . . . # . . . ",
		 ". . . . . . # # . . . ",
		 ". . . # # # # # . . . ",
		 "# # # # # # # # # # # "};
		/*String[] pdf =
		{". . . . . . ",
		 ". # . # # . ",
		 ". . . . # . ",
		 ". . . . . . ",
		 "# # # # . . "};*/
		world = new World(110,60,strPassableMapToBool(stairs),new Random());
	}
	
	@Test
	public void testGetPositionAfterMove_SingleCase() {
		willy = new Worm(world, 18, 20, PI/4, 10, "Willy Wonka");
		Position result = willy.getPositionAfterMove();
		double distance = Math.sqrt(result.squaredDistance(willy.getPosition()));
		double s = posMod(Math.atan2(result.getY()-willy.getYCoordinate(), result.getX()-willy.getXCoordinate()),2*Math.PI);
		double theta = willy.getDirection();
		assertTrue(0.1*willy.getRadius() <= distance);
		assertTrue(distance <= willy.getRadius());
		assertTrue(Math.abs(theta-s) <= 0.7875 || Math.abs(2*PI-theta) + Math.abs(s) <= 0.7875 || Math.abs(2*PI-s) + Math.abs(theta) <= 0.7875);
	}
	
	@Test
	public void testMove_Normal(){
		willy = new Worm(world, 18, 20, PI/4, 10, "Willy Wonka");
		assertEquals(4448495, willy.getActionPoints());
		assertTrue(willy.canMove());
		willy.move();
		assertFuzzyEquals(23.44, willy.getXCoordinate(), 1E-1);
		assertFuzzyEquals(28.36, willy.getYCoordinate(), 1E-1);
		assertEquals(4448495-4, willy.getActionPoints());
	}
	
	@Test(expected=IllegalStateException.class)
	public void testMove_Cannot() throws Exception{
		willy = new Worm(world, 18, 20, 3*PI/2, 10, "Willy Wonka");
		willy.move();
	}
	
	@Test
	public void testMove_FallAfterMove(){
		String[] floatingIsland =
		{". . . . . . . . . . . ",
		 ". . . . . . # . . . . ",
		 ". . . . . . . . . . . ",
		 ". . . . . . . . . . . ",
		 ". . . . . . . . . . . ",
		 "# # # # # # # # # # # "};
		world = new World(110,60,strPassableMapToBool(floatingIsland),new Random());
		willy = new Worm(world, 81, 40, 0, 10, "Just hangin'");
		assertEquals(4448495, willy.getActionPoints());
		assertEquals(4448495, willy.getHitPoints());
		assertTrue(willy.canMove());
		Position afterMove = willy.getPositionAfterMove();
		assertFuzzyEquals(91, afterMove.getX());
		assertFuzzyEquals(40, afterMove.getY());
		assertEquals(LocationType.PASSABLE, world.getLocationType(afterMove, willy.getRadius()));
		willy.move();
		assertFuzzyEquals(91, willy.getXCoordinate());
		assertFuzzyEquals(21, willy.getYCoordinate());
		assertEquals(4448495-1, willy.getActionPoints());
		assertEquals(4448495-3*(40-21), willy.getHitPoints());
	}

	@Test
	public void testMove_OutOfWorld(){
		String[] floatingIsland =
		{". . . . . . . . . . . ",
		 ". . . . . . . # . . . ",
		 ". . . . . . . . . . . ",
		 ". . . . . . . . . . . ",
		 ". . . . . . . . . . . ",
		 "# # # # # # # # # # # "};
		world = new World(110,60,strPassableMapToBool(floatingIsland),new Random());
		willy = new Worm(world, 91, 40, 0, 10, "I'll be back");
		assertTrue(willy.canMove());
		Position afterMove = willy.getPositionAfterMove();
		assertFuzzyEquals(101, afterMove.getX());
		assertFuzzyEquals(40, afterMove.getY());
		assertFalse(world.isInsideWorldBoundaries(afterMove, willy.getRadius()));
		willy.move();
		assertTrue(willy.isTerminated());
		assertFalse(world.hasAsWorm(willy));
	}
	
	@Test
	public void testGetUnitStepCost_RightDirection(){
		assertFuzzyEquals(Worm.getUnitStepCost(0),1);
	}
	
	@Test
	public void testGetUnitStepCost_LeftDirection(){
		assertFuzzyEquals(Worm.getUnitStepCost(PI),1);
	}
	
	@Test
	public void testGetUnitStepCost_UpDirection(){
		assertFuzzyEquals(Worm.getUnitStepCost(PI/2),4);
	}
	
	@Test
	public void testGetUnitStepCost_DownDirection(){
		assertFuzzyEquals(Worm.getUnitStepCost(3*PI/2),4);
	}
	
	@Test
	public void testGetUnitStepCost_RandomDirection(){
		assertFuzzyEquals(Worm.getUnitStepCost(2),4.05333654);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetUnitStepCost_IllegalDirection() throws Exception{
		Worm.getUnitStepCost(7);
	}
}
