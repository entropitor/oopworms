package worms.model.programs.types;


public class DoubleType implements Type {
	// TODO use canHaveAs and stuff, dummy implementation
	
	public DoubleType(double value){
		this.value = value;
	}

	@Override
	public Double getValue() {
		return value;
	}

	@Override
	public Type setValue(Object value) throws ClassCastException{
		// TODO Auto-generated method stub
		// TODO use canHaveAs and stuff, dummy implementation
		return new DoubleType((Double)value);
	}
	
	Double value;
	
}
