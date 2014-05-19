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

public class EqualityTest {
	
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
		Equality equality = new Equality(literal3, literal5);
		assertFalse(equality.calculate(null).getValue());
		
		equality = new Equality(literal17, literalNegative20);
		assertFalse(equality.calculate(null).getValue());
		
		equality = new Equality(literalNegative20, literalNegative20);
		assertTrue(equality.calculate(null).getValue());		
	}
	
	@Test
	public void testCalculate_BooleanLiterals() {
		Equality equality = new Equality(literalTrue, literalTrue);
		assertTrue(equality.calculate(null).getValue());
		
		equality = new Equality(literalFalse, literalTrue);
		assertFalse(equality.calculate(null).getValue());
	}
	
	@Test
	public void testCalculate_EntityLiterals() {
		Equality equality = new Equality(literalNull, literalNull);
		assertTrue(equality.calculate(null).getValue());
		
		equality = new Equality(literalNull, literalSelf);
		assertFalse(equality.calculate(program).getValue());
	}
	
	@Test
	public void testCalculate_MixedLiterals(){
		Equality equality = new Equality(literal3, literalTrue);
		assertFalse(equality.calculate(null).getValue());
		
		equality = new Equality(literalNull, literalTrue);
		assertFalse(equality.calculate(null).getValue());
		
		equality = new Equality(literalTrue, literalNull);
		assertFalse(equality.calculate(null).getValue());
		
		equality = new Equality(literalSelf, literalNegative20);
		assertFalse(equality.calculate(program).getValue());
	}
}
