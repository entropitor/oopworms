package worms.model.programs;

public abstract class ZeroArgumentExecutable extends ArgumentExecutable{

	@Override
	public boolean hasAsSubExecutable(Executable executable) {
		//FIXME tests
		return super.hasAsSubExecutable(executable);
	}
	
	public int getNbArguments(){
		//FIXME tests
		return 0;
	}

}
