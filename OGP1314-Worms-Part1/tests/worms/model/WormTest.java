package worms.model;

import static org.junit.Assert.*;
import static worms.util.AssertUtil.*;

import org.junit.Before;
import org.junit.Test;

public class WormTest {
	private Worm willy;
	private static Worm donald;
	private Worm left,diagonal;
	private static final double PRECISION = 1e-6;

	@Before
	public void setup(){
		//				   x    y    dir.     r       name
		willy  = new Worm(112, 358, 1.321, 34.55, "Willy Wonka");
		donald = new Worm(0,     0,     3,     5, "D'Onald Duck");
		left = new Worm(0, 0, Math.PI, 1, "Left");
		diagonal = new Worm(2, -3, Math.PI/4, 1, "Diagonal");
	}
	
	@Test
	public void testConstructor_LegalCase(){
		//			     	   x       y    dir.       r   name
		Worm worm = new Worm(-8.45, 9.16, Math.PI/2, 2.14, "Bar");

		assertFuzzyEquals(worm.getXCoordinate(), -8.45, PRECISION);
		assertFuzzyEquals(worm.getYCoordinate(), 9.16, PRECISION);
		assertFuzzyEquals(worm.getDirection(), Math.PI/2, PRECISION);
		assertEquals(worm.getName(), "Bar");
		assertFuzzyEquals(worm.getRadius(), 2.14, PRECISION);
		assertFuzzyEquals(worm.getMass(), 43596.78321768277701414403, PRECISION);
		assertEquals(worm.getMaxActionPoints(), 43597);
		assertEquals(worm.getActionPoints(), 43597);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_IllegalXPosition() throws Exception{
		new Worm(Double.NaN, 9.16, Math.PI/2, 2.14, "Bar");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_IllegalYPosition() throws Exception{
		new Worm(-8.45, Double.NaN, Math.PI/2, 2.14, "Bar");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_IllegalName() throws Exception{
		new Worm(-8.45, Double.NaN, Math.PI/2, 2.14, "Foo	Bar");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_IllegalRadius() throws Exception{
		new Worm(-8.45, 9.16, Math.PI/2, 0, "Bar");
	}
	
	@Test
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
		assertEquals(Worm.getCostForMove(2, Math.PI),2);
	}
	
	@Test
	public void testGetCostForMove_UpDirection(){
		assertEquals(Worm.getCostForMove(2, Math.PI/2),8);
	}
	
	@Test
	public void testGetCostForMove_DownDirection(){
		assertEquals(Worm.getCostForMove(2, 3*Math.PI/2),8);
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
		assertFuzzyEquals(Worm.getUnitStepCost(Math.PI),1);
	}
	
	@Test
	public void testGetUnitStepCost_UpDirection(){
		assertFuzzyEquals(Worm.getUnitStepCost(Math.PI/2),4);
	}
	
	@Test
	public void testGetUnitStepCost_DownDirection(){
		assertFuzzyEquals(Worm.getUnitStepCost(3*Math.PI/2),4);
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
		assertFuzzyEquals(diagonal.getXCoordinate(),4.1213203,PRECISION);
		assertFuzzyEquals(diagonal.getYCoordinate(),-0.8786796,PRECISION);
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
	public void testIsValidDirection_TrueCases(){
		assertTrue(Worm.isValidDirection(0));
		assertTrue(Worm.isValidDirection(Math.PI*2-0.001));
		assertTrue(Worm.isValidDirection(Math.PI));
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
		assertFalse(Worm.isValidDirection(5*Math.PI/2));
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
		donald.setName("Donald 'Fauntleroy' Duck");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetName_TooShort(){
		donald.setName("D");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetName_IllegalSymbols(){
		donald.setName("D*n*ld D*ck");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetName_LowerCaseStart(){
		donald.setName("donald duck");
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
		assertFuzzyEquals(97, willy.getRadius(), PRECISION);
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
		assertFuzzyEquals(183466713.419263797, willy.getMass(), PRECISION);
	}
	
	@Test
	public void testGetMaxActionPoints(){
		assertEquals(183466713, willy.getMaxActionPoints());
	}
	
	@Test
	public void testGetActionPoints(){
		assertEquals(183466713, willy.getActionPoints());
	}
}
