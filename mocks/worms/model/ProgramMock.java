package worms.model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import worms.gui.game.IActionHandler;
import worms.model.programs.statements.Statement;
import worms.model.programs.types.Type;

public class ProgramMock extends Program{

	public ProgramMock(Statement mainStatement, Map<String, Type<?>> globals, IActionHandler handler) {
		super(mainStatement, globals, handler);
	}
	
	public void runStatement(Statement statement){
		executionStackCopy = new ArrayDeque<Statement>();
		statement.execute(this);
	}
	
	@Override
	public void scheduleStatement(Statement statement){
		executionStackCopy.push(statement);
	}
	
	/**
	 * Returns the executionStack in order elements will be executed
	 */
	public Statement[] getExecutionStackAsArray(){
		return executionStackCopy.toArray(new Statement[]{});
	}
	private Deque<Statement> executionStackCopy;

}
