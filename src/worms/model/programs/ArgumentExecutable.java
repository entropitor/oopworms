package worms.model.programs;

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
		
		//No loops in executables
		if(executable == this)
			return false;
		//If executable doesn't have subexecutables => OK!
		if(!(executable instanceof ArgumentExecutable))
			return true;
		return !((ArgumentExecutable)executable).hasAsSubExecutable(this);
	}
	
	//FIXME test
	public abstract Executable[] getSubExecutables();

}
