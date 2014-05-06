package worms.model.programs;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

public abstract class OneArgumentExecutable extends ArgumentExecutable {
	
	@Override
	public boolean hasAsSubExecutable(Executable executable) {
		//FIXME tests
		if(super.hasAsSubExecutable(executable))
			return true;
		return executable == getFirstArgument();
	}

	@Basic @Raw
	public Executable getFirstArgument() {
		return argument;
	}
	
	@Raw
	void setFirstArgument(Executable argument) throws IllegalArgumentException{
		//FIXME tests		
		if(!canHaveAsSubExecutable(argument))
			throw new IllegalArgumentException();
		this.argument = argument;
	}
	private Executable argument;
	
	public int getNbArguments(){
		//FIXME tests
		return 1;
	}
}
