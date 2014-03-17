package worms.model;

import static org.junit.Assert.*;
import static worms.util.AssertUtil.*;
import static worms.util.Util.*;

import static java.lang.Math.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WormTest {
	private Worm willy;
	private Worm skippy,eiffelTower;
	private Worm left,diagonal;
	
	private static Method setXCoordinate;
	private static Method setYCoordinate;
	private static Method setDirection;
	private static Method setActionPoints;
	private static Method decreaseActionPoints;
	private static Method increaseActionPoints;
	private static Method replenishActionPoints;

	@Before
	public void setup(){
		//				   x    y    dir.     r       name
		willy  = new Worm(112, 358, 1.321, 34.55, "Willy Wonka");
		skippy = new Worm(2.72, -3.14, 2, 1.5, "Skippy The Bush Kangaroo");
		eiffelTower = new Worm(48.51, 2.21, 3.4, 21851, "The Eiffel Tower");
		left = new Worm(0, 0, PI, 1, "Left");
		diagonal = new Worm(2, -3, PI/4, 1, "Diagonal");
	}
	
	@BeforeClass
	public static void setupClass() throws NoSuchMethodException, SecurityException{
		Worm worm = new Worm(0,0,1,1,"Ba");
		
		setXCoordinate = worm.getClass().getDeclaredMethod("setXCoordinate", double.class);
		setXCoordinate.setAccessible(true);
		
		setYCoordinate = worm.getClass().getDeclaredMethod("setYCoordinate", double.class);
		setYCoordinate.setAccessible(true);
		
		setDirection = worm.getClass().getDeclaredMethod("setDirection", double.class);
		setDirection.setAccessible(true);
		
		setActionPoints = worm.getClass().getDeclaredMethod("setActionPoints", int.class);
		setActionPoints.setAccessible(true);
		
		decreaseActionPoints = worm.getClass().getDeclaredMethod("decreaseActionPoints", int.class);
		decreaseActionPoints.setAccessible(true);
		
		increaseActionPoints = worm.getClass().getDeclaredMethod("increaseActionPoints", int.class);
		increaseActionPoints.setAccessible(true);
		
		replenishActionPoints = worm.getClass().getDeclaredMethod("replenishActionPoints");
		replenishActionPoints.setAccessible(true);
	}
	
	@Test
	public void testConstructor_LegalCase(){
		//			     	   x       y    dir.       r   name
		Worm worm = new Worm(-8.45, 9.16, PI/2, 2.14, "Bar");

		assertFuzzyEquals(worm.getXCoordinate(), -8.45);
		assertFuzzyEquals(worm.getYCoordinate(), 9.16);
		assertFuzzyEquals(worm.getDirection(), PI/2);
		assertEquals(worm.getName(), "Bar");
		assertFuzzyEquals(worm.getRadius(), 2.14);
		assertFuzzyEquals(worm.getMass(), 43596.78321768277701414403);
		assertEquals(worm.getMaxActionPoints(), 43597);
		assertEquals(worm.getActionPoints(), 43597);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_IllegalXPosition() throws Exception{
		new Worm(Double.NaN, 9.16, PI/2, 2.14, "Bar");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_IllegalYPosition() throws Exception{
		new Worm(-8.45, Double.NaN, PI/2, 2.14, "Bar");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_IllegalName() throws Exception{
		new Worm(-8.45, Double.NaN, PI/2, 2.14, "Foo	Bar");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_IllegalRadius() throws Exception{
		new Worm(-8.45, 9.16, PI/2, 0, "Bar");
	}
	
	@Test
	public void testIsValidTurningAngle_TrueCases(){
		assertTrue(Worm.isValidTurningAngle(1.321));
		assertTrue(Worm.isValidTurningAngle(0));
		assertTrue(Worm.isValidTurningAngle(PI-1E-3));
		assertTrue(Worm.isValidTurningAngle(-PI));
	}
	
	@Test
	public void testIsValidTurningAngle_NonInclusiveUpperBound(){
		assertFalse(Worm.isValidTurningAngle(PI));
	}
	
	@Test
	public void testIsValidTurningAngle_NaN(){
		assertFalse(Worm.isValidTurningAngle(Double.NaN));
	}
	
	@Test
	public void testIsValidTurningAngle_TooPositive(){
		assertFalse(Worm.isValidTurningAngle(1964));
		assertFalse(Worm.isValidTurningAngle(PI+1e-2));
	}
	
	@Test
	public void testIsValidTurningAngle_TooNegative(){
		assertFalse(Worm.isValidTurningAngle(-PI-1e-2));
	}
	
	@Test
	public void testGetTurningCost(){
		assertEquals(Worm.getTurningCost(0), 0);
		assertEquals(Worm.getTurningCost(1), 10);
		assertEquals(Worm.getTurningCost(-1.321), 13);
		assertEquals(Worm.getTurningCost(PI-1E-3), 30);
		assertEquals(Worm.getTurningCost(-PI), 30);
	}
	
	@Test
	public void testCanTurn(){
		Worm fatboy = new Worm(0, 0, 0, 20, "Big radius so lots 'o APs");
		assertTrue(fatboy.canTurn(-PI));
		
//		Minimum radius is 0.25 atm., so no testing in this way.
//		Worm slim = new Worm(0, 0, 0, 0.1889, "Radius so APs is thirty");
//		assertTrue(slim.canTurn(PI));
//		
//		Worm nope = new Worm(0, 0, 0, 0.1000, "Radius too small so not enough APs");
//		assertFalse(nope.canTurn(PI));
		
		Worm slim = new Worm(0, 0, 0, 0.25, "Slim shady");
		assertEquals(slim.getActionPoints(), 70);
		assertTrue(slim.canTurn(-PI));
		
		slim.turn(-PI);
		assertEquals(slim.getActionPoints(), 40);
		assertTrue(slim.canTurn(-PI));
		
		slim.turn(-PI/3);
		assertEquals(slim.getActionPoints(), 30);
		assertTrue(slim.canTurn(-PI));
		
		slim.turn(-PI/30);
		assertEquals(slim.getActionPoints(), 29);
		assertFalse(slim.canTurn(-PI));
	}
	
	@Test
	public void testTurn(){
		assertFuzzyEquals(willy.getDirection(), 1.321);
		assertEquals(willy.getActionPoints(), 183466713);
		
		willy.turn(1);
		assertFuzzyEquals(willy.getDirection(), 2.321);
		assertEquals(willy.getActionPoints(), 183466713-10);
		
		willy.turn(-1);
		assertFuzzyEquals(willy.getDirection(), 1.321);
		assertEquals(willy.getActionPoints(), 183466713-10-10);
		
		willy.turn(-1.321);
		assertTrue(fuzzyEquals(willy.getDirection(), 0));
		assertEquals(willy.getActionPoints(), 183466713-10-10-13);
		
		willy.turn(-PI);
		assertFuzzyEquals(willy.getDirection(), PI);
		assertEquals(willy.getActionPoints(), 183466713-10-10-13-30);
	}
	
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
	}
	
	@Test
	public void testIsValidPosition_TrueCase(){
		assertTrue(Worm.isValidPosition(3.546,-8.5649));
	}
	
	@Test
	public void testIsValidPosition_IllegalX(){
		assertFalse(Worm.isValidPosition(Double.NaN, -4.168));
	}
	
	@Test
	public void testIsValidPosition_IllegalY(){
		assertFalse(Worm.isValidPosition(Double.POSITIVE_INFINITY, Double.NaN));
	}
	
	@Test
	public void testIsValidPosition_BothIllegal(){
		assertFalse(Worm.isValidPosition(Double.NaN, Double.NaN));
	}
	
	@Test
	public void testSetXCoordinate_LegalCase() throws Exception{
		setXCoordinate.invoke(willy, 10);
		assertFuzzyEquals(willy.getXCoordinate(),10);
	}
	
	@Test
	public void testSetXCoordinate_InfinityCase() throws Exception{
		setXCoordinate.invoke(willy, Double.POSITIVE_INFINITY);
		assertFuzzyEquals(willy.getXCoordinate(),Double.POSITIVE_INFINITY);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetXCoordinate_IllegalCase() throws Throwable{
		try {
			setXCoordinate.invoke(willy, Double.NaN);
		} catch (InvocationTargetException e) {
			throw e.getCause();
		}
	}
	
	@Test
	public void testSetYCoordinate_LegalCase() throws Exception{
		setYCoordinate.invoke(willy, 10);
		assertFuzzyEquals(willy.getYCoordinate(),10);
	}
	
	@Test
	public void testSetYCoordinate_InfinityCase() throws Exception{
		setYCoordinate.invoke(willy, Double.NEGATIVE_INFINITY);
		assertFuzzyEquals(willy.getYCoordinate(),Double.NEGATIVE_INFINITY);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetYCoordinate_IllegalCase() throws Throwable{
		try {
			setYCoordinate.invoke(willy, Double.NaN);
		} catch (InvocationTargetException e) {
			throw e.getCause();
		}
	}
	
	@Test
	public void testIsValidDirection_TrueCases(){
		assertTrue(Worm.isValidDirection(0));
		assertTrue(Worm.isValidDirection(PI*2-0.001));
		assertTrue(Worm.isValidDirection(PI));
		assertTrue(Worm.isValidDirection(2.14));
	}
	
	@Test
	public void testIsValidDirection_NaN(){
		assertFalse(Worm.isValidDirection(Double.NaN));
	}
	
	@Test
	public void testIsValidDirection_Negative(){
		assertFalse(Worm.isValidDirection(-2));
	}
	
	@Test
	public void testIsValidDirection_TooBig(){
		assertFalse(Worm.isValidDirection(5*PI/2));
	}
	
	@Test
	public void testSetDirection_LegalCase() throws Exception{
		setDirection.invoke(willy,PI);
		assertFuzzyEquals(willy.getDirection(),PI);
	}
	
	@Test
	public void testIsValidCharacterForName_TrueCases(){
		assertTrue(Worm.isValidCharacterForName(' '));
		assertTrue(Worm.isValidCharacterForName('\''));
		assertTrue(Worm.isValidCharacterForName('\"'));
		assertTrue(Worm.isValidCharacterForName('X'));
		assertTrue(Worm.isValidCharacterForName('d'));
	}
	
	@Test
	public void testIsValidCharacterForName_Symbols(){
		assertFalse(Worm.isValidCharacterForName('$'));
		assertFalse(Worm.isValidCharacterForName('~'));
	}
	
	@Test
	public void testIsValidCharacterForName_Numbers(){
		assertFalse(Worm.isValidCharacterForName('3'));
	}
	
	@Test
	public void testIsValidCharacterForName_Tab(){
		assertFalse(Worm.isValidCharacterForName('\t'));
	}
	
	@Test
	public void testIsValidCharacterForName_WeirdLetters(){
		assertFalse(Worm.isValidCharacterForName('Ö'));
		assertFalse(Worm.isValidCharacterForName('å'));
	}
	
	@Test
	public void testIsValidName_TrueCase(){
		assertTrue(Worm.isValidName("James o'Har\"a"));
	}
	
	@Test
	public void testIsValidName_TooShort(){
		assertFalse(Worm.isValidName("Q"));
	}
	
	@Test
	public void testIsValidName_NonUpperCaseStart(){
		assertFalse(Worm.isValidName("james o'Hara"));
		assertFalse(Worm.isValidName("'O Donald"));
	}
	
	@Test
	public void testIsValidName_WeirdSymbols(){
		assertFalse(Worm.isValidName("James *ö'Hara*"));
	}
	
	@Test
	public void testSetName_LegalCase(){
		willy.setName("Donald 'Fauntleroy' Duck");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetName_TooShort(){
		willy.setName("D");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetName_IllegalSymbols(){
		willy.setName("D*n*ld D*ck");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetName_LowerCaseStart(){
		willy.setName("donald duck");
	}	
	
	@Test
	public void testGetRadiusLowerBound(){
		assertTrue(willy.getRadiusLowerBound() > 0);
	}
	
	@Test
	public void testCanHaveAsRadius_TrueCase(){
		// We assume that willy.getRadiusLowerBound() < 500 here.
		assertTrue(willy.canHaveAsRadius(500));
		assertTrue(willy.canHaveAsRadius(willy.getRadiusLowerBound()));
	}
	
	@Test
	public void testCanHaveAsRadius_NaN(){
		assertFalse(willy.canHaveAsRadius(Double.NaN));
	}
	
	@Test
	public void testCanHaveAsRadius_TooSmall(){
		assertFalse(willy.canHaveAsRadius(0));
		assertFalse(willy.canHaveAsRadius(willy.getRadiusLowerBound()-1e-4));
	}
	
	@Test
	public void testCanHaveAsRadius_TooBig(){
		assertFalse(willy.canHaveAsRadius(Double.POSITIVE_INFINITY));
	}
	
	@Test
	public void testSetRadius_TrueCase(){
		willy.setRadius(97);
		assertFuzzyEquals(97, willy.getRadius());
	}
	
	@Test
	public void testSetRadius_ConflictForActionPointsCase(){
		willy.setRadius(1);
		assertEquals(willy.getActionPoints(),4448);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetRadius_NaN(){
		willy.setRadius(Double.NaN);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetRadius_Zero(){
		willy.setRadius(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetRadius_TooSmall(){
		willy.setRadius(willy.getRadiusLowerBound()-1e-4);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetRadius_TooBig(){
		willy.setRadius(Double.POSITIVE_INFINITY);
	}
	
	@Test
	public void testGetMass(){
		assertFuzzyEquals(183466713.419263797, willy.getMass());
	}
	
	@Test
	public void testGetMaxActionPoints(){
		assertEquals(183466713, willy.getMaxActionPoints());
	}
	
	@Test
	public void testGetActionPoints(){
		assertEquals(183466713, willy.getActionPoints());
	}
	
	@Test
	public void testSetActionPoints_NormalCase() throws Exception{
		setActionPoints.invoke(willy, 500);
		assertEquals(willy.getActionPoints(),500);
	}
	
	@Test
	public void testSetActionPoints_TooBigCase() throws Exception{
		setActionPoints.invoke(willy, Integer.MAX_VALUE);
		assertEquals(willy.getActionPoints(),willy.getMaxActionPoints());
	}
	
	@Test
	public void testSetActionPoints_TooSmallCase() throws Exception{
		setActionPoints.invoke(willy, -100);
		assertEquals(willy.getActionPoints(),0);
	}
	
	@Test
	public void testDecreaseActionPoints_NormalCase() throws Exception{
		decreaseActionPoints.invoke(willy,100);
		assertEquals(willy.getActionPoints(),willy.getMaxActionPoints()-100);
	}
	
	@Test
	public void testDecreaseActionPoints_LargeNumberCase() throws Exception{
		decreaseActionPoints.invoke(willy,Integer.MAX_VALUE);
		assertEquals(willy.getActionPoints(),0);
	}
	
	@Test
	public void testDecreaseActionPoints_NegativeCase() throws Exception{
		decreaseActionPoints.invoke(willy,100);
		decreaseActionPoints.invoke(willy,-100);
		assertEquals(willy.getActionPoints(),willy.getMaxActionPoints()-100);
	}
	
	@Test
	public void testIncreaseActionPoints_NormalCase() throws Exception{
		decreaseActionPoints.invoke(willy, 1000);
		increaseActionPoints.invoke(willy,100);
		assertEquals(willy.getActionPoints(),willy.getMaxActionPoints()-900);
	}
	
	@Test
	public void testIncreaseActionPoints_LargeNumberCase() throws Exception{
		increaseActionPoints.invoke(willy,Integer.MAX_VALUE);
		assertEquals(willy.getActionPoints(),willy.getMaxActionPoints());
	}
	
	@Test
	public void testIncreaseActionPoints_NegativeCase() throws Exception{
		increaseActionPoints.invoke(willy,-100);
		assertEquals(willy.getActionPoints(),willy.getMaxActionPoints());
	}
	
	@Test
	public void testReplenishActionPoints_SingleCase() throws Exception{
		decreaseActionPoints.invoke(willy,100);
		replenishActionPoints.invoke(willy);
		assertEquals(willy.getActionPoints(),willy.getMaxActionPoints());
	}

	public void testJump_CanJumpCase(){
		skippy.jump();
		assertFuzzyEquals(skippy.getXCoordinate(), -1.5098204);
		assertFuzzyEquals(skippy.getYCoordinate(), -3.14);
		assertEquals(skippy.getActionPoints(), 0);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testJump_CanNotJumpCase() throws Exception{
		eiffelTower.jump();
	}
	
	@Test
	public void testGetJumpForce_CanJumpCase(){
		assertFuzzyEquals(skippy.getJumpForce(), 222303.8195708);
	}
	
	@Test
	public void testGetJumpForce_CanNotJumpCase(){
		assertFuzzyEquals(eiffelTower.getJumpForce(), 0);
	}
	
	@Test
	public void testGetJumpVelocity_CanJumpCase(){
		assertFuzzyEquals(skippy.getJumpVelocity(), 7.4033797);
	}
	
	@Test
	public void testGetJumpVelocity_CanNotJumpCase(){
		assertFuzzyEquals(eiffelTower.getJumpVelocity(), 0);
	}
	
	@Test
	public void testCanJump_TrueCase(){
		assertTrue(skippy.canJump());
	}
	
	@Test
	public void testCanJump_FalseCase(){
		assertFalse(eiffelTower.canJump());
	}
	
	@Test
	public void testGetJumpTime_CanJumpCase(){
		assertFuzzyEquals(skippy.getJumpTime(), 1.3729202);
	}
	
	@Test
	public void testGetJumpTime_CanNotJumpCase(){
		assertFuzzyEquals(eiffelTower.getJumpTime(), 0);
	}
	
	@Test
	public void testGetJumpStep_MidAirCase(){
		double[] jumpstep = skippy.getJumpStep(1);
		assertEquals(jumpstep.length, 2);
		assertFuzzyEquals(jumpstep[0], -0.36089306);
		assertFuzzyEquals(jumpstep[1], -1.3114509);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetJumpStep_NegativeTimeCase() throws Exception{
		skippy.getJumpStep(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetJumpStep_NaNCase() throws Exception{
		skippy.getJumpStep(Double.NaN);
	}
	
	@Test
	public void testGetJumpStep_AfterJumpCase(){
		double[] jumpstep = skippy.getJumpStep(5);
		assertEquals(jumpstep.length, 2);
		assertFuzzyEquals(jumpstep[0], -1.5098204);
		assertFuzzyEquals(jumpstep[1], -3.14);
	}
	
	@Test
	public void testGetJumpStep_BeforeJumpCase(){
		double[] jumpstep = skippy.getJumpStep(0);
		assertEquals(jumpstep.length, 2);
		assertFuzzyEquals(jumpstep[0], 2.72);
		assertFuzzyEquals(jumpstep[1], -3.14);
	}
	
	@Test
	public void testGetJumpStep_CanNotJumpCase(){
		double[] jumpstep = eiffelTower.getJumpStep(1);
		assertEquals(jumpstep.length, 2);
		assertFuzzyEquals(jumpstep[0], 48.51);
		assertFuzzyEquals(jumpstep[1], 2.21);
	}
}
