package worms.model.programs.expressions;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import worms.model.Food;
import worms.model.Program;
import worms.model.Team;
import worms.model.World;
import worms.model.Worm;
import worms.model.programs.WormsRuntimeException;
import worms.model.programs.types.EntityType;
import worms.model.programs.types.Type;

public class SameTeamTest {
	Program programWithTeam,programWithoutTeam;
	Worm willyWithTeam,willyWithoutTeam;
	Expression<EntityType> otherWormFFA, otherWormTeamA, otherWormTeamB;
	World world;
	
	Expression<EntityType> literalNull, literalSelf, literalFood;
	
	SameTeam expr;
	
	@Before
	public void setup(){
		Map<String, Type<?>>globals = new HashMap<String, Type<?>>();
		programWithTeam = new Program(null, globals, null);
		
		final World world = new World(20,30,new boolean[][]{{true,true},{false,true},{true,true}},new Random());
		final Team teamA = new Team(world, "TeamA");
		final Team teamB = new Team(world, "TeamB");
		
		willyWithTeam  = new Worm(world, 112, 358, 1.321, 34.55, "Willy Wonka", teamA, programWithTeam);
		//program is cloned so get correct program
		programWithTeam = willyWithTeam.getProgram();
		
		willyWithoutTeam  = new Worm(world, 112, 358, 1.321, 34.55, "Willy Wonka", null, programWithTeam);
		//program is cloned so get correct program
		programWithoutTeam = willyWithoutTeam.getProgram();
		
		otherWormFFA = new Expression<EntityType>() {
			@Override
			public EntityType calculate(Program program) throws WormsRuntimeException {
				return new EntityType(new Worm(world, 112, 358, 1.321, 34.55, "Other", null));
			}
		};
		otherWormTeamA = new Expression<EntityType>() {
			@Override
			public EntityType calculate(Program program) throws WormsRuntimeException {
				return new EntityType(new Worm(world, 112, 358, 1.321, 34.55, "Other", teamA));
			}
		};
		otherWormTeamB = new Expression<EntityType>() {
			@Override
			public EntityType calculate(Program program) throws WormsRuntimeException {
				return new EntityType(new Worm(world, 112, 358, 1.321, 34.55, "Other", teamB));
			}
		};
		
		literalNull = new EntityNullLiteral();
		literalSelf = new EntitySelfLiteral();
		literalFood = new Expression<EntityType>(){

			@Override
			public EntityType calculate(Program program)
					throws WormsRuntimeException {
				return new EntityType(new Food(world));
			}
			
		};
	}

	@Test
	public void testCalculate_SelfWithTeamCase() {
		expr = new SameTeam(otherWormFFA);
		assertFalse(expr.calculate(programWithTeam).getValue());
		
		expr = new SameTeam(otherWormTeamA);
		assertTrue(expr.calculate(programWithTeam).getValue());
		
		expr = new SameTeam(otherWormTeamB);
		assertFalse(expr.calculate(programWithTeam).getValue());
		
		expr = new SameTeam(literalSelf);
		assertTrue(expr.calculate(programWithTeam).getValue());
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testCalculate_SelfWithTeamAndFoodCase() throws Exception {
		expr = new SameTeam(literalFood);
		expr.calculate(programWithTeam).getValue();
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testCalculate_SelfWithTeamAndNullCase() throws Exception {
		expr = new SameTeam(literalNull);
		expr.calculate(programWithTeam).getValue();
	}
	
	@Test
	public void testCalculate_SelfwithoutTeamCase() {
		expr = new SameTeam(otherWormFFA);
		assertTrue(expr.calculate(programWithoutTeam).getValue());
		
		expr = new SameTeam(otherWormTeamA);
		assertFalse(expr.calculate(programWithoutTeam).getValue());
		
		expr = new SameTeam(otherWormTeamB);
		assertFalse(expr.calculate(programWithoutTeam).getValue());
		
		expr = new SameTeam(literalSelf);
		assertTrue(expr.calculate(programWithoutTeam).getValue());
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testCalculate_SelfwithoutTeamAndFoodCase() throws Exception {
		expr = new SameTeam(literalFood);
		expr.calculate(programWithoutTeam).getValue();
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testCalculate_SelfwithoutTeamAndNullCase() throws Exception {
		expr = new SameTeam(literalNull);
		expr.calculate(programWithoutTeam).getValue();
	}

}
