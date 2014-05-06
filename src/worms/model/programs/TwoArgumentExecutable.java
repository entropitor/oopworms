package worms.model.programs;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

public abstract class TwoArgumentExecutable extends OneArgumentExecutable{

	@Override
	public boolean hasAsSubExecutable(Executable executable) {
		//FIXME tests
		if(super.hasAsSubExecutable(executable))
			return true;
		if(executable == getSecondArgument())
			return true;
		return false;
	}
	
	@Basic @Raw
	public Executable getSecondArgument() {
		return secondArgument;
	}
	
	@Raw
	void setSecondArgument(Executable argument) throws IllegalArgumentException{
		//FIXME tests
		if(!canHaveAsSubExecutable(argument))
			throw new IllegalArgumentException();
		this.secondArgument = argument;
	}
	private Executable secondArgument;
	
	public int getNbArguments(){
		//FIXME tests
		return 2;
	}
}
