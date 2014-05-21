package worms.model.programs;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

public abstract class TwoArgumentExecutable<F extends Executable,S extends Executable> extends ArgumentExecutable{

	public TwoArgumentExecutable(F firstArgument, S secondArgument) throws IllegalArgumentException{
		if(!canHaveAsSubExecutable(firstArgument) || ! canHaveAsSubExecutable(secondArgument))
			throw new IllegalArgumentException();
		this.firstArgument = firstArgument;
		this.secondArgument = secondArgument;
	}
	
	@Basic @Raw
	public F getFirstArgument() {
		return firstArgument;
	}
	private F firstArgument;
	
	@Basic @Raw
	public S getSecondArgument() {
		return secondArgument;
	}
	private S secondArgument;
	
	@Override @Raw
	public Executable[] getSubExecutables(){
		return new Executable[]{getFirstArgument(),getSecondArgument()};
	}
}
