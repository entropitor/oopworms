package worms.model.programs;

import worms.model.programs.statements.ActionStatement;
import worms.model.programs.statements.Foreach;
import be.kuleuven.cs.som.annotate.Raw;

public abstract class ArgumentExecutable implements Executable{
	
	@Raw
	public boolean hasAsSubExecutable(Executable executable){
		if(executable == this)
			return true;
		for(Executable argument : getSubExecutables()){
			if(argument instanceof ArgumentExecutable){
				if(((ArgumentExecutable) argument).hasAsSubExecutable(executable))
					return true;
			} else {
				if(executable == argument)
					return true;
			}
				
		}
		return false;
	}
	
	public boolean canHaveAsSubExecutable(Executable executable){
		//Null reference not allowed
		if(executable == null)
			return false;

		//No action-statments in for-each statement (seems like it shouldn't be checked)
		//if(this instanceof ForEachStatement && executable instanceof ActionStatement)
		//	return false;
		
		//The next part is technically not even necessary as it's impossible 
		//to construct loops at construction time:
		//Loop = A -> B -> ... -> C -> A (where -> means "hasAsSubExpression")
		//But to construct C, A needs to exist and be constructed 
		//since it needs to be given as parameter to C
		//And to construct A, B must be constructed and thus ... and thus C 
		//=> A needs to be constructed before it can be constructed 
		//=> No loops can occur.
		
		//No loops in executables
		if(executable == this)
			return false;
		//If executable doesn't have subexecutables => OK!
		if(!(executable instanceof ArgumentExecutable))
			return true;
		return !((ArgumentExecutable)executable).hasAsSubExecutable(this);
	}
	
	public abstract Executable[] getSubExecutables();
	
	public boolean hasActionStatementAsSubExecutable(){
		for(Executable argument : getSubExecutables()){
			if(argument instanceof ActionStatement)
				return true;
			
			if(argument instanceof ArgumentExecutable){
				if(((ArgumentExecutable) argument).hasActionStatementAsSubExecutable())
					return true;
			}				
		}
		return false;
	}
	
	public boolean hasActionStatementInsideForEach(){
		if(this instanceof Foreach)
			return hasActionStatementAsSubExecutable();
		for(Executable argument : getSubExecutables()){
			if(argument instanceof ArgumentExecutable 
					&& ((ArgumentExecutable)argument).hasActionStatementInsideForEach())
				return true;
		}
		return false;
	}

}
