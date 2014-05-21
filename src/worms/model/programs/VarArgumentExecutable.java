package worms.model.programs;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

public abstract class VarArgumentExecutable<T extends Executable> extends ArgumentExecutable {
	
	public VarArgumentExecutable(List<T> arguments) throws IllegalArgumentException{
		if(arguments == null)
			throw new IllegalArgumentException();
		for(T argument : arguments){
			if(!canHaveAsSubExecutable(argument))
				throw new IllegalArgumentException();
		}
		this.arguments = new ArrayList<T>(arguments);
	}

	@Basic @Raw
	public T getArgumentAt(int i) throws IndexOutOfBoundsException{
		return arguments.get(i);
	}
	@Basic @Raw
	public int getNbArguments(){
		return arguments.size();
	}
	private final List<T> arguments;
	
	@Override @Raw
	public Executable[] getSubExecutables(){
		return arguments.<Executable>toArray(new Executable[0]);
	}
}