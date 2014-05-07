package worms.model.programs;

import be.kuleuven.cs.som.annotate.Raw;

public abstract class ArgumentExecutable implements Executable{
	
	@Raw
	public boolean hasAsSubExecutable(Executable executable){
		//FIXME test
		if(executable == this)
			return true;
		for(Executable argument : getSubExecutables())
			if(argument instanceof ArgumentExecutable && ((ArgumentExecutable) argument).hasAsSubExecutable(executable))
				return true;
		return false;
	}
	
	public boolean canHaveAsSubExecutable(Executable executable){
		//FIXME test
		return executable != null && !hasAsSubExecutable(executable);
	}
	
	//FIXME test
	public abstract Executable[] getSubExecutables();

}
