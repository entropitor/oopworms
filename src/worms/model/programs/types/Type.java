package worms.model.programs.types;

import be.kuleuven.cs.som.annotate.Value;

@Value
public interface Type {
	public Object getValue();
	public Type setValue(Object value) throws ClassCastException;
}
