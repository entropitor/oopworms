package worms.model;

import static java.lang.Math.PI;
import static org.junit.Assert.*;
import static worms.util.AssertUtil.assertFuzzyEquals;

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
		
		// left = new Worm(world, 0, 0, PI, 1, "Left");
		// diagonal = new Worm(world, 2, -3, PI/4, 1, "Diagonal");
	}

	@Test
	public void testMove_Normal(){
		willy = new Worm(world, 18, 20, PI/4, 10, "Willy Wonka");
		assertEquals(4448495, willy.getActionPoints());
		assertTrue(willy.canMove());
		willy.move();
		assertFuzzyEquals(23.53, willy.getXCoordinate(), 1E-1);
		assertFuzzyEquals(28.36, willy.getYCoordinate(), 1E-1);
	}
	
	@Test
	public void testMove_Cannot(){
		willy = new Worm(world, 18, 20, 5*PI/4, 10, "Willy Wonka");
		assertFalse(willy.canMove());
		willy.move();
		assertFuzzyEquals(18, willy.getXCoordinate(), 1E-1);
		assertFuzzyEquals(20, willy.getYCoordinate(), 1E-1);
		//assertEquals(, willy.getActionPoints());
	}

	/*@Test
	public void testCanMove_TrueCase(){
		assertTrue(left.canMove(1));
	}
	
	@Test
	public void testCanMove_NegativeNbStepsCase(){
		assertFalse(left.canMove(-1));
	}
	
	@Test
	public void testCanMove_FalseCase(){
		assertFalse(left.canMove(4500));
	}
	
	@Test
	public void testGetCostForMove_RightDirection(){
		assertEquals(Worm.getCostForMove(2, 0),2);
	}
	
	@Test
	public void testGetCostForMove_LeftDirection(){
		assertEquals(Worm.getCostForMove(2, PI),2);
	}
	
	@Test
	public void testGetCostForMove_UpDirection(){
		assertEquals(Worm.getCostForMove(2, PI/2),8);
	}
	
	@Test
	public void testGetCostForMove_DownDirection(){
		assertEquals(Worm.getCostForMove(2, 3*PI/2),8);
	}
	
	@Test
	public void testGetCostForMove_RandomDirection(){
		assertEquals(Worm.getCostForMove(4, 2),17);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetCostForMove_NegativeNbSteps() throws Exception{
		Worm.getCostForMove(-1, 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetCostForMove_IllegalDirection() throws Exception{
		Worm.getCostForMove(1, 7);
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
	
	@Test(expected=IllegalArgumentException.class)
	public void testMove_NegativeNbSteps() throws Exception{
		willy.move(-2);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testMove_TooLargeNbSteps() throws Exception{
		left.move(4500);
	}
	
	@Test
	public void testMove_NormalCase(){
		diagonal.move(3);
		assertFuzzyEquals(diagonal.getXCoordinate(),4.1213203);
		assertFuzzyEquals(diagonal.getYCoordinate(),-0.8786796);
		assertEquals(diagonal.getActionPoints(),diagonal.getMaxActionPoints()-11);
	}
	
	@Test
	public void testMoveWith_NormalCase(){
		diagonal.moveWith(-2.14, 6.14);
		assertFuzzyEquals(diagonal.getXCoordinate(),-0.14);
		assertFuzzyEquals(diagonal.getYCoordinate(),3.14);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testMoveWith_IllegalXOffsetCase() throws Exception{
		diagonal.moveWith(Double.NaN,3);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testMoveWith_IllegalYOffsetCase() throws Exception{
		diagonal.moveWith(-10, Double.NaN);
	}*/
}
