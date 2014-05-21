package worms.model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import worms.gui.game.IActionHandler;
import worms.model.programs.ArgumentExecutable;
import worms.model.programs.Executable;
import worms.model.programs.WormsRuntimeException;
import worms.model.programs.statements.ActionStatement;
import worms.model.programs.statements.Statement;
import worms.model.programs.types.Type;

/**
 * A class representing a program with global variables, a main statement and a worm.
 * 
 * @invar	This program has a proper worm
 * 			| hasProperWorm()
 */
public class Program implements Cloneable{
	
	@Raw
	public Program(Statement mainStatement, Map<String, Type<?>> globals, IActionHandler handler){
		this.mainStatement = mainStatement;
		if(globals == null)
			this.globals = new HashMap<String, Type<?>>();
		else
			this.globals = new HashMap<String, Type<?>>(globals);
		this.handler = handler;
		this.executionStack = new ArrayDeque<Statement>();
		//@Raw => worm isn't set at the end but that's ok since this program can be raw at end of a @Raw method.
	}
	
	@Override
	public @Raw Program clone(){
		return new Program(getMainStatement(), this.globals, getActionHandler());
	}
	
	@Basic @Raw
	public Worm getWorm(){
		return worm;
	}
	
	@Raw
	public boolean hasWorm(){
		return getWorm() != null;
	}
	
	/**
	 * @return	| hasWorm() && getWorm().getProgram() == this
	 */
	@Raw
	public boolean hasProperWorm(){
		return hasWorm() && getWorm().getProgram() == this;
	}
	
	@Raw
	public void setWorm(Worm worm) throws IllegalStateException{
		if(worm.getProgram() != this)
			throw new IllegalStateException();
		if(hasWorm())
			throw new IllegalStateException();
		this.worm = worm;
	}
	
	private Worm worm = null;
	
	@Raw @Basic
	public Statement getMainStatement(){
		return mainStatement;
	}
	public boolean hasAsSubExecutable(Executable executable){
		//FIXME test
		if(getMainStatement() instanceof ArgumentExecutable){
			return ((ArgumentExecutable)getMainStatement()).hasAsSubExecutable(executable);
		}else{
			return getMainStatement() == executable;
		}
	}
	private Statement mainStatement;
	
	public Type<?> getVariableValue(String name) throws WormsRuntimeException{
		Type<?> current = globals.get(name);
		if(current == null)
			throw new WormsRuntimeException();
		
		return current;
	}
	public void setVariableValue(String name, Type<?> value) throws WormsRuntimeException{
		Type<?> current = globals.get(name);
		if(current == null || current.getClass() != value.getClass())
			throw new WormsRuntimeException();
		
		globals.put(name, value);
	}
	protected void initGlobals(){
		for(Entry<String, Type<?>> entry : globals.entrySet()){
			setVariableValue(entry.getKey(), entry.getValue().getDefaultTypeForThisClass());
		}
	}
	private Map<String, Type<?>> globals;
	
	@Raw @Basic
	public IActionHandler getActionHandler(){
		return handler;
	}
	private IActionHandler handler;
	
	public void run(){
		if(getWorm() == null)
			return;
		if(runtimeErrorOccurred())
			return;
		
		int nbStatementsThisTurn = 0;
		
		if(executionStack.size() == 0){
			initGlobals();
			scheduleStatement(getMainStatement());
		}
		
		while(executionStack.size() > 0 && nbStatementsThisTurn < 1000){
			Statement nextStatement = executionStack.pop();
			
			if(nextStatement instanceof ActionStatement 
					&& ((ActionStatement)nextStatement).getCost(getWorm()) > getWorm().getActionPoints())
				return;
			
			try{
				nextStatement.execute(this);
			}catch(WormsRuntimeException e){
				encounteredRuntimeError();
				return;
			}
		}
	}
	@Raw
	public void scheduleStatement(Statement statement){
		if(statement != null)
			executionStack.push(statement);
	}
	private Deque<Statement> executionStack;
	
	@Raw @Basic
	public boolean runtimeErrorOccurred(){
		return runtimeErrorOccurred;
	}
	public void encounteredRuntimeError(){
		runtimeErrorOccurred = true;
	}
	private boolean runtimeErrorOccurred = false;
}
