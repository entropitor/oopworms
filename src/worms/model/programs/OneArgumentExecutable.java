package worms.model.programs;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

public abstract class OneArgumentExecutable<F extends Executable> extends ArgumentExecutable {
	
	//FIXME test
	public OneArgumentExecutable(F first) throws IllegalArgumentException{
		if(!canHaveAsSubExecutable(first))
			throw new IllegalArgumentException();
		this.firstArgument = first;
	}

	@Basic @Raw
	public F getFirstArgument() {
		return firstArgument;
	}
	private F firstArgument;
	
	@Override @Raw
	public Executable[] getSubExecutables(){
		//FIXME test
		return new Executable[]{firstArgument};
	}
}
