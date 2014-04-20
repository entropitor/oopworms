package worms.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static worms.util.AssertUtil.assertFuzzyEquals;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class WormTest_Jump {
	Worm skippy,eiffelTower,jumperContact,jumperOutOfWorld;
	World jumpWorld;
	static final double TIMESTEP = 1e-4;
	
	@Before
	public void setup(){
		World world = new World(300,500,new boolean[][]{{true,true},{false,true},{true,true}},new Random());
		skippy = new Worm(world, 2.72, -3.14, 2, 1.5, "Skippy The Bush Kangaroo");
		eiffelTower = new Worm(world, 48.51, 2.21, 3.4, 41, "The Eiffel Tower");
		eiffelTower.jump(TIMESTEP);
		
		boolean[][] passableMap = new boolean[][]{	{true,true,true,true},
													{true,true,true,true},
													{true,true,true,true},
													{true,true,false,true},
													{true,true,true,true}};
		jumpWorld = new World(40,20,passableMap,new Random());
		jumperContact = new Worm(jumpWorld, 12, 10, Math.PI/4, 3, "Jumper");
		jumperOutOfWorld = new Worm(jumpWorld, 7, 11, 3*Math.PI/4, 3, "Jumps Out Of The World");
	}

	@Test
	public void testJump_ContactLocationCase() {
		jumperContact.jump(TIMESTEP);
		assertEquals(0, jumperContact.getActionPoints());
		assertFuzzyEquals(17.46888,jumperContact.getPosition().getX());
		assertFuzzyEquals(10.1175,jumperContact.getPosition().getY());
	}

	@Test
	public void testJump_OutOfWorldCase() {
		jumperOutOfWorld.jump(TIMESTEP);
		assertFalse(jumpWorld.hasAsWorm(jumperOutOfWorld));
		assertTrue(jumperOutOfWorld.isTerminated());
	}
	
	@Test
	public void testGetJumpTime_ContactLocationCase(){
		assertFuzzyEquals(1.04469, jumperContact.getJumpTime(TIMESTEP));
	}
	
	@Test
	public void testGetJumpTime_OutOfWorldCase() {
		assertFuzzyEquals(0.764098, jumperOutOfWorld.getJumpTime(TIMESTEP));
	}
	
	@Test(expected=IllegalStateException.class)
	public void testJump_CanNotJumpZeroAPsCase() throws Exception{
		eiffelTower.jump(TIMESTEP);
	}
	
	@Test
	public void testGetJumpForce_CanJumpCase(){
		assertFuzzyEquals(skippy.getJumpForce(), 222303.8195708);
	}
	
	@Test
	public void testGetJumpForce_CanNotJumpZeroAPCase(){
		assertFuzzyEquals(eiffelTower.getJumpForce(), 0);
	}
	
	@Test
	public void testGetJumpVelocity_CanJumpCase(){
		assertFuzzyEquals(skippy.getJumpVelocity(), 7.4033797);
	}
	
	@Test
	public void testGetJumpVelocity_CanNotJumpZeroAPCase(){
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
	public void testGetJumpTime_CanNotJumpZeroAPCase(){
		assertFuzzyEquals(eiffelTower.getJumpTime(TIMESTEP), 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetJumpTime_NegativeTimeStepCase() throws Exception{
		skippy.getJumpTime(-TIMESTEP);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetJumpTime_NaNTimestepCase() throws Exception{
		skippy.getJumpTime(Double.NaN);
	}
	
	@Test
	public void testGetJumpStep_MidAirCase(){
		Position jumpstep = skippy.getJumpStep(1);
		assertFuzzyEquals(jumpstep.getX(), -0.36089306);
		assertFuzzyEquals(jumpstep.getY(), -1.3114509);
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
	public void testGetJumpStep_BeforeJumpCase(){
		Position jumpstep = skippy.getJumpStep(0);
		assertFuzzyEquals(jumpstep.getX(), 2.72);
		assertFuzzyEquals(jumpstep.getY(), -3.14);
	}
}
