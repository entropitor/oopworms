package worms.model.programs;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

public abstract class ThreeArgumentExecutable<F extends Executable,S extends Executable,T extends Executable> extends ArgumentExecutable {

	//FIXME test
	public ThreeArgumentExecutable(F firstArgument, S secondArgument, T thirdArgument) throws IllegalArgumentException{
		if(!canHaveAsSubExecutable(firstArgument) || ! canHaveAsSubExecutable(secondArgument) || !canHaveAsSubExecutable(thirdArgument))
			throw new IllegalArgumentException();
		this.firstArgument = firstArgument;
		this.secondArgument = secondArgument;
		this.thirdArgument = thirdArgument;
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
	
	@Basic @Raw
	public T getThirdArgument() {
		return thirdArgument;
	}
	private T thirdArgument;
	
	@Override @Raw
	public Executable[] getSubExecutables(){
		//FIXME test
		return new Executable[]{firstArgument,secondArgument,thirdArgument};
	}
}
