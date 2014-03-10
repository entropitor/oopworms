package worms.model;

import static org.junit.Assert.*;
import static worms.util.AssertUtil.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WormTest {
	private Worm willy;
	private static final double PRECISION = 1e-6;
	
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
		assertFuzzyEquals(willy.getXCoordinate(),10,PRECISION);
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
		assertFuzzyEquals(willy.getYCoordinate(),10,PRECISION);
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
	public void testSetDirection_LegalCase() throws Exception{
		setDirection.invoke(willy,Math.PI);
		assertFuzzyEquals(willy.getDirection(),Math.PI,PRECISION);
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
		assertFuzzyEquals(97, willy.getRadius(), PRECISION);
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
}
