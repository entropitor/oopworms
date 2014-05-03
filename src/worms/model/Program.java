package worms.model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import worms.gui.game.IActionHandler;
import worms.model.programs.Statement;
import worms.model.programs.Type;

/**
 * A class representing a program with global variables, a main statement and a worm.
 * 
 * @invar	This program has a proper worm
 * 			| hasProperWorm()
 */
public class Program implements Cloneable{
	
	@Raw
	public Program(Statement mainStatement, Map<String, Type> globals, IActionHandler handler){
		this.mainStatement = mainStatement;
		this.globals = new HashMap<String, Type>(globals);
		this.handler = handler;
		this.executionStack = new ArrayDeque<Statement>();
		//@Raw => worm isn't set at the end but that's ok since this program can be raw at end of a @Raw method.
	}
	
	@Override
	public @Raw Program clone(){
		return new Program(getMainStatement(), new HashMap<String, Type>(this.globals), getActionHandler());
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
	private Statement mainStatement;
	
	
	private Map<String, Type> globals;
	
	@Raw @Basic
	public IActionHandler getActionHandler(){
		return handler;
	}
	private IActionHandler handler;
	
	
	private Deque<Statement> executionStack = new ArrayDeque<Statement>();
}
