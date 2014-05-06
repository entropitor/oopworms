package worms.model.programs;

public abstract class ArgumentExecutable {
	
	public boolean hasAsSubExecutable(Executable executable){
		if(executable == this)
			return true;
		return false;
	}
	
	public boolean canHaveAsSubExecutable(Executable executable){
		return executable != null && !hasAsSubExecutable(executable);
	}
	
	public abstract int getNbArguments();

}
