package worms.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static worms.util.AssertUtil.assertFuzzyEquals;

import org.junit.Before;
import org.junit.Test;

public class WormTest_Jump {
	private Worm skippy,eiffelTower;
	
	@Before
	public void setup(){
		//				   x    y    dir.     r       name
		skippy = new Worm(2.72, -3.14, 2, 1.5, "Skippy The Bush Kangaroo");
		eiffelTower = new Worm(48.51, 2.21, 3.4, 21851, "The Eiffel Tower");
	}

	@Test
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
