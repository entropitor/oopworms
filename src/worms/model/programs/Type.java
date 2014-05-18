package worms.model.programs;

import be.kuleuven.cs.som.annotate.Value;

@Value
public abstract class Type<C> {

	public Type(C value) {
		this.value = value;
	}

	public abstract Type<C> getNewTypeOfSameClass(C value);

	public C getValue() {
		return value;
	}

	private C value;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		// The following type cast will be succesfull and thus the suppresion of the waring is ok,
		// because this class 'Type' is abstract and therefore only subclasses of 'Type' can be used, 
		// and if two different subclasses are compared, this method will have quit on the previous
		// line of code.
		Type<C> other = (Type<C>) obj;
		if (getValue() == null) {
			if (other.getValue() != null)
				return false;
		} else if (!getValue().equals(other.getValue()))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		return getValue().hashCode();
	}
}
