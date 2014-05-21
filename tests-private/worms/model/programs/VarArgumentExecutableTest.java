package worms.model.programs;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import worms.model.programs.expressions.BooleanLiteral;
import worms.model.programs.statements.Sequence;
import worms.model.programs.statements.Skip;
import worms.model.programs.statements.Statement;
import worms.model.programs.statements.While;

public class VarArgumentExecutableTest {
	
	VarArgumentExecutable<?> sequence, emptySequence;
	Statement skipSt, whileSt;

	@Before
	public void setUp() throws Exception {
		ArrayList<Statement> list = new ArrayList<>();
		skipSt = new Skip();
		whileSt = new While(BooleanLiteral.TRUE_LITERAL, skipSt);
		list.add(skipSt);
		list.add(whileSt);
		sequence = new Sequence(list);
		
		emptySequence = new Sequence(new ArrayList<Statement>());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testVarArgumentExecutable_IllegalListCase() throws Exception {
		new Sequence(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testVarArgumentExecutable_IllegalElementAtPos0Case() throws Exception {
		ArrayList<Statement> list = new ArrayList<>();
		list.add(null);
		new Sequence(list);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testVarArgumentExecutable_IllegalElementAtPos2Case() throws Exception {
		ArrayList<Statement> list = new ArrayList<>();
		list.add(new Skip());
		list.add(new Skip());
		list.add(null);
		new Sequence(list);
	}

	@Test
	public void testArgumentAt() {
		assertEquals(skipSt, sequence.getArgumentAt(0));
		assertEquals(whileSt, sequence.getArgumentAt(1));
	}
	
	@Test
	public void testGetNbArguments() {
		assertEquals(2, sequence.getNbArguments());
		assertEquals(0, emptySequence.getNbArguments());
	}

	@Test
	public void testGetSubExecutables() {
		assertArrayEquals(new Executable[]{skipSt, whileSt}, sequence.getSubExecutables());
		assertArrayEquals(new Executable[]{}, emptySequence.getSubExecutables());
	}

}
