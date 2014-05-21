package worms.model;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import worms.model.programs.WormsRuntimeException;
import worms.model.programs.expressions.VariableAccess;
import worms.model.programs.types.BooleanType;
import worms.model.programs.types.DoubleType;
import worms.model.programs.types.EntityType;
import worms.model.programs.types.Type;

public class ProgramTest_Globals {
	

	VariableAccess<DoubleType> expressionDoubleA,expressionDoubleB;
	VariableAccess<EntityType> expressionEntity;
	VariableAccess<BooleanType> expressionBoolean;
	Program program;
	Worm willy;
	Food pizza;

	@Before
	public void setUp() throws Exception {
		World world = new World(20,30,new boolean[][]{{true,true},{false,true},{true,true}},new Random());
		pizza = new Food(world);
		
		expressionDoubleA = new VariableAccess<>("a");
		expressionDoubleB = new VariableAccess<>("b");
		expressionEntity = new VariableAccess<>("entity");
		expressionBoolean = new VariableAccess<>("bool");
		
		Map<String, Type<?>>globals = new HashMap<String, Type<?>>();
		globals.put("a", new DoubleType(3.14));
		globals.put("entity", new EntityType(pizza));
		globals.put("bool", new BooleanType(true));
		program = new Program(null, globals, null);
		

		willy  = new Worm(world, 112, 358, 1.321, 34.55, "Willy Wonka", null, program);
		//program is cloned so get correct program
		program = willy.getProgram();
	}

	@Test
	public void testProgram() {
		new Program(null, null, null);
		//should terminate with no errors.
		
		Map<String, Type<?>>globals = new HashMap<String, Type<?>>();
		globals.put("a", new DoubleType(3.14));
		Program prog = new Program(null, globals, null);
		assertEquals(3.14, prog.getVariableValue("a").getValue());
	}

	@Test
	public void testClone() {
		Program prog = program.clone();
		assertEquals(3.14, prog.getVariableValue("a").getValue());
		assertEquals(pizza, prog.getVariableValue("entity").getValue());
		assertEquals(true, prog.getVariableValue("bool").getValue());		
	}

	@Test
	public void testGetVariableValue_NormalCase() {
		assertEquals(3.14, program.getVariableValue("a").getValue());
		assertEquals(pizza, program.getVariableValue("entity").getValue());
		assertEquals(true, program.getVariableValue("bool").getValue());
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testGetVariableValue_IllegalCase() {
		program.getVariableValue("b");
	}

	@Test
	public void testSetVariableValue_NormalCase() {
		assertEquals(3.14, program.getVariableValue("a").getValue());
		program.setVariableValue("a", new DoubleType(5));
		assertEquals(5.0, program.getVariableValue("a").getValue());
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testSetVariableValue_IllegalValueCase() throws Exception {
		program.setVariableValue("a", new BooleanType(true));
	}
	
	@Test(expected = WormsRuntimeException.class)
	public void testSetVariableValue_IllegalNameCase() throws Exception {
		program.setVariableValue("b", new BooleanType(true));
	}

	@Test
	public void testInitGlobals() {
		program.initGlobals();
		assertEquals(new Double(0.0), program.getVariableValue("a").getValue());
		assertNull(program.getVariableValue("entity").getValue());
		assertEquals(false, program.getVariableValue("bool").getValue());
	}

}
