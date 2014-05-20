package worms.model.programs.expressions;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import worms.model.Program;
import worms.model.World;
import worms.model.Worm;
import worms.model.programs.types.BooleanType;
import worms.model.programs.types.DoubleType;
import worms.model.programs.types.EntityType;
import worms.model.programs.types.Type;

public class InequalityTest {
	
	Program program;
	Worm willy;
	World world;
	
	Expression<DoubleType> literal3, literal5, literal17, literalNegative20;
	Expression<BooleanType> literalTrue, literalFalse;
	Expression<EntityType> literalNull, literalSelf;
	
	@Before
	public void setup() {
		Map<String, Type<?>>globals = new HashMap<String, Type<?>>();
		program = new Program(null, globals, null);
		World world = new World(20,30,new boolean[][]{{true,true},{false,true},{true,true}},new Random());
		willy  = new Worm(world, 112, 358, 1.321, 34.55, "Willy Wonka", null, program);
		//program is cloned so get correct program
		program = willy.getProgram();
		
		literal3 = new DoubleLiteral(3);
		literal5 = new DoubleLiteral(5);
		literal17 = new DoubleLiteral(17);
		literalNegative20 = new DoubleLiteral(-20);
		
		literalTrue = BooleanLiteral.TRUE_LITERAL;
		literalFalse = BooleanLiteral.FALSE_LITERAL;
		
		literalNull = new EntityNullLiteral();
		literalSelf = new EntitySelfLiteral();
	}

	@Test
	public void testCalculate_DoubleLiterals() {
		Inequality inequality = new Inequality(literal3, literal5);
		assertTrue(inequality.calculate(null).getValue());
		
		inequality = new Inequality(literal17, literalNegative20);
		assertTrue(inequality.calculate(null).getValue());
		
		inequality = new Inequality(literalNegative20, literalNegative20);
		assertFalse(inequality.calculate(null).getValue());		
	}
	
	@Test
	public void testCalculate_BooleanLiterals() {
		Inequality inequality = new Inequality(literalTrue, literalTrue);
		assertFalse(inequality.calculate(null).getValue());
		
		inequality = new Inequality(literalFalse, literalTrue);
		assertTrue(inequality.calculate(null).getValue());
	}
	
	@Test
	public void testCalculate_EntityLiterals() {
		Inequality inequality = new Inequality(literalNull, literalNull);
		assertFalse(inequality.calculate(null).getValue());
		
		inequality = new Inequality(literalNull, literalSelf);
		assertTrue(inequality.calculate(program).getValue());
	}
	
	@Test
	public void testCalculate_MixedLiterals(){
		Inequality inequality = new Inequality(literal3, literalTrue);
		assertTrue(inequality.calculate(null).getValue());
		
		inequality = new Inequality(literalNull, literalTrue);
		assertTrue(inequality.calculate(null).getValue());
		
		inequality = new Inequality(literalTrue, literalNull);
		assertTrue(inequality.calculate(null).getValue());
		
		inequality = new Inequality(literalSelf, literalNegative20);
		assertTrue(inequality.calculate(program).getValue());
	}
}
