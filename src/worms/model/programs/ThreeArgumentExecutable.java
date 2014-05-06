package worms.model.programs;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

public abstract class ThreeArgumentExecutable extends TwoArgumentExecutable {

	@Override
	public boolean hasAsSubExecutable(Executable executable) {
		//FIXME tests
		if(super.hasAsSubExecutable(executable))
			return true;
		if(executable == getThirdArgument())
			return true;
		return false;
	}
	
	@Basic @Raw
	public Executable getThirdArgument() {
		return thirdArgument;
	}
	
	@Raw
	void setThirdArgument(Executable argument) throws IllegalArgumentException{
		//FIXME tests
		if(!canHaveAsSubExecutable(argument))
			throw new IllegalArgumentException();
		this.thirdArgument = argument;
	}
	private Executable thirdArgument;
	
	public int getNbArguments(){
		//FIXME tests
		return 3;
	}
}
